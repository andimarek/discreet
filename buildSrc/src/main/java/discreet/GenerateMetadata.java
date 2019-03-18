package discreet;

import org.gradle.api.DefaultTask;
import org.gradle.api.Task;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.TaskAction;
import org.gradle.jvm.tasks.Jar;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GenerateMetadata extends DefaultTask {
    @TaskAction
    void execute() {
        JavaPluginConvention plugin = getProject().getConvention().getPlugin(JavaPluginConvention.class);
        List<Task> jars = new ArrayList<Task>(getProject().getTasksByName("jar", false));
        if (jars.size() == 0) {
            throw new RuntimeException("no jar task");
        }
        Jar jar = (Jar) jars.get(0);
        jar.manifest(manifest -> {
            Map<String, String> discreetDependencies = new LinkedHashMap<>();
            discreetDependencies.put("Discreet-Dependencies", "XXXXXXXX");
            manifest.attributes(discreetDependencies);
        });
        System.out.println("Jar2:" + jar.getManifest().getAttributes().keySet());
    }

}

