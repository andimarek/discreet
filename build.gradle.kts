import java.text.SimpleDateFormat
import java.util.*

plugins {
    java
    id("com.jfrog.bintray") version "1.8.4"
    `maven-publish`
}
group = "com.andimarek.discreet"


configure(subprojects.filter { it.path.contains("modules") }) {

    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "com.jfrog.bintray")

    group = "com.andimarek.discreet"
    version = SimpleDateFormat("yyyy-MM-dd\'T\'HH-mm-ss").format(Date())

    tasks.register<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        from(sourceSets.main.get().allJava)
    }


    publishing {
        publications {
            create<MavenPublication>(project.name) {
                artifact(tasks["sourcesJar"])
            }
        }
    }
    bintray {
        user = findProperty("BINTRAY_USER") as? String
        key = findProperty("BINTRAY_KEY") as? String
        setPublications(project.name)
        publish = true
        with(pkg) {
            repo = "discreet"
            name = project.name
            setLicenses("MIT")
            vcsUrl = "https://github.com/andimarek/discreet"
            with(version) {
                name = project.version.toString()
                desc = project.description
            }
        }
    }
}


tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
    gradleVersion = "5.2.1"
}
