package discreet;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.gradle.api.DefaultTask;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.FileTree;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;

import static com.github.javaparser.StaticJavaParser.parseName;
import static discreet.DiscreetPlugin.discreetConfig;
import static discreet.DiscreetPlugin.sourceDirName;

public class FetchSources extends DefaultTask {

    private String packagePrefix;

    public void setPackagePrefix(String packagePrefix) {
        this.packagePrefix = packagePrefix;
    }

    @Input
    public String getPackagePrefix() {
        return packagePrefix;
    }

    @TaskAction
    void execute() {
        if (packagePrefix == null) {
            packagePrefix = getProject().getGroup() + "." + getProject().getName();
        }
        Path srcPath = buildSrcPath();
        Configuration discreet = getProject().getConfigurations().getByName(discreetConfig);
        Set<File> jars = discreet.getFiles();
        getLogger().info("Dependencies jars: {}", jars);
        for (File singleSourceJar : jars) {

            FileTree sourceFiles = getProject().zipTree(singleSourceJar);

            getProject().copy(copySpec -> {
                copySpec.from(sourceFiles).into(srcPath);
            });

        }
        try {

            Map<Name, Name> oldNewNames = new LinkedHashMap<>();
            List<Path> allFiles = new ArrayList<>();

            BiPredicate<Path, BasicFileAttributes> fileFilter =
                    (path, fileAttribute) -> fileAttribute.isRegularFile() && path.toFile().getName().endsWith(".java");

            Files.find(srcPath, 1000, fileFilter).forEach(path -> {
                allFiles.add(path);
                patchPackageName(path, oldNewNames);
            });


            allFiles.forEach(file -> {
                patchNames(file, oldNewNames);
            });

        } catch (
                IOException e) {
            throw new UncheckedIOException(e);
        }

    }

    private void patchNames(Path file, Map<Name, Name> oldNewNames) {
        try {
            CompilationUnit compilationUnit = readCompilationUnit(file);

            compilationUnit.accept(new VoidVisitorAdapter<CompilationUnit>() {
                @Override
                public void visit(Name name, CompilationUnit cu) {
                    oldNewNames.forEach((oldName, newName) -> {
                        if (name.asString().startsWith(oldName.asString())) {
                            Name patched = parseName(name.asString().replace(oldName.asString(), newName.asString()));
                            System.out.println(name + " patched to " + patched);
                            patched.getQualifier().ifPresent(name::setQualifier);
                            name.setIdentifier(patched.getIdentifier());
                        }
                    });
                }
            }, compilationUnit);
            writeCompilationUnit(file, compilationUnit);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Path buildSrcPath() {
        Path srcPath = getProject().getBuildDir().toPath().resolve(sourceDirName);
        String[] split = packagePrefix.split("\\.");
        for (String pathElement : split) {
            srcPath = srcPath.resolve(pathElement);
        }
        return srcPath;

    }

    private void patchPackageName(Path file, Map<Name, Name> oldNewNames) {
        try {
            CompilationUnit compilationUnit = readCompilationUnit(file);
            Optional<PackageDeclaration> packageDeclarationOptional = compilationUnit.getPackageDeclaration();
            if (!packageDeclarationOptional.isPresent()) {
                return;
            }

            PackageDeclaration packageDeclaration = packageDeclarationOptional.get();
            Name oldName = packageDeclaration.getName();
            Name newName = parseName(packagePrefix + "." + oldName.asString());
            oldNewNames.put(oldName, newName);
            packageDeclaration.setName(newName);
            writeCompilationUnit(file, compilationUnit);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private CompilationUnit readCompilationUnit(Path file) throws IOException {
        String javaFile = new String(Files.readAllBytes(file), "UTF-8");
        return StaticJavaParser.parse(javaFile);
    }

    private void writeCompilationUnit(Path file, CompilationUnit compilationUnit) throws IOException {
        byte[] patchedFile = compilationUnit.toString().getBytes("UTF-8");
        Files.write(file, patchedFile);
    }

}
