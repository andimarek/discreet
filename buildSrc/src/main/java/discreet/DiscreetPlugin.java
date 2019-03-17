package discreet;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class DiscreetPlugin implements Plugin<Project> {
    public void apply(Project project) {
        project.getConfigurations().create("discreet");
        project.getRepositories().maven(mavenArtifactRepository -> mavenArtifactRepository.setUrl("https://dl.bintray.com/andimarek/discreet"));
        project.getTasks().create("fetchDiscreetSources", FetchSources.class, (task) -> {
        });
    }

    //tasks.register("asyncDep") {
//    doLast {
//        val asyncSources by configurations
//        copy({
//            from({
//                zipTree({ asyncSources.singleFile })
//            })
//            into("build/generated-src/")
//        })
//    }
//}

}
