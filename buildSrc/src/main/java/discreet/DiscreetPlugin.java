package discreet;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;

import java.util.Set;

public class DiscreetPlugin implements Plugin<Project> {
    public static final String sourceDirName = "discreet-src";
    public static final String discreetConfig = "discreet";

    public void apply(Project project) {
        JavaPluginConvention javaPlugin = project.getConvention().getPlugin(JavaPluginConvention.class);
        SourceSetContainer sourceSets = javaPlugin.getSourceSets();
        SourceSet mainSourSet = sourceSets.findByName("main");
        SourceDirectorySet allJava = mainSourSet.getJava();
        allJava.srcDir(project.getBuildDir().toPath().resolve(sourceDirName));


        project.getConfigurations().create("discreet");
        project.getRepositories().maven(mavenArtifactRepository -> mavenArtifactRepository.setUrl("https://dl.bintray.com/andimarek/discreet"));
        project.getTasks().create("fetchDiscreetSources", FetchSources.class, (task) -> {
        });
        Set<Task> compileJavaTasks = project.getTasksByName("compileJava", false);
        for (Task task : compileJavaTasks) {
            task.dependsOn("fetchDiscreetSources");
        }
    }

}
