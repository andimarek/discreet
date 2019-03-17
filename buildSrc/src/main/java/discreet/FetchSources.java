package discreet;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.expr.Name;
import org.gradle.api.DefaultTask;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.FileTree;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static discreet.DiscreetPlugin.discreetConfig;
import static discreet.DiscreetPlugin.sourceDirName;

public class FetchSources extends DefaultTask {

    private String packagePrefix;
    @TaskAction
    void execute() {
        packagePrefix = getProject().getGroup() + "." + getProject().getName();
        Path srcPath = buildSrcPath();
        System.out.println("srcPath: " + srcPath);
        Configuration discreet = getProject().getConfigurations().getByName(discreetConfig);
        File singleFile = discreet.getSingleFile();
        FileTree files = getProject().zipTree(singleFile);


        getProject().copy(copySpec -> {
            copySpec.from(files).into(srcPath);
        });

        try {
            Files.find(srcPath, 1000,
                    (path, fileAttribute) -> fileAttribute.isRegularFile() &&
                            path.toFile().getName().endsWith(".java"))
                    .forEach(path -> {
                        patchPackageName(path);
                    });
        } catch (
                IOException e) {
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

    private void patchPackageName(Path path) {
        try {
            String javaFile = new String(Files.readAllBytes(path), "UTF-8");
            CompilationUnit compilationUnit = StaticJavaParser.parse(javaFile);
            Optional<PackageDeclaration> packageDeclaration = compilationUnit.getPackageDeclaration();
            if (!packageDeclaration.isPresent()) {
                return;
            }
            Name name = packageDeclaration.get().getName();
            name.setIdentifier(packagePrefix + "." + name.getIdentifier());
            byte[] patchedFile = compilationUnit.toString().getBytes("UTF-8");
            Files.write(path, patchedFile);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
