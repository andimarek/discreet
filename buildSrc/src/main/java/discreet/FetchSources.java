package discreet;

import org.gradle.api.DefaultTask;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.FileTree;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.nio.file.Path;

import static discreet.DiscreetPlugin.discreetConfig;
import static discreet.DiscreetPlugin.sourceDirName;

public class FetchSources extends DefaultTask {


    @TaskAction
    void execute() {
        Path srcPath = getProject().getBuildDir().toPath().resolve(sourceDirName);
        Configuration discreet = getProject().getConfigurations().getByName(discreetConfig);
        File singleFile = discreet.getSingleFile();
        FileTree files = getProject().zipTree(singleFile);

        getProject().copy(copySpec -> {
            copySpec.from(files).into(srcPath);
        });
    }
}
