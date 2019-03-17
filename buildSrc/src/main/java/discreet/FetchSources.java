package discreet;

import org.gradle.api.DefaultTask;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ResolvedConfiguration;
import org.gradle.api.tasks.TaskAction;

public class FetchSources extends DefaultTask {

    @TaskAction
    void execute() {
        Configuration discreet = getProject().getConfigurations().getByName("discreet");
        ResolvedConfiguration resolvedConfiguration = discreet.getResolvedConfiguration();
        System.out.println(resolvedConfiguration.getFiles());
    }
}
