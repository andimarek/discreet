import java.text.SimpleDateFormat
import java.util.*

plugins {
    java
    id("com.jfrog.bintray") version "1.8.4"
    `maven-publish`
}

version = SimpleDateFormat("yyyy-MM-dd\'T\'HH-mm-ss").format(Date())
repositories {
}

tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allJava)
}

publishing {
    publications {
        create<MavenPublication>("async") {
            artifact(tasks["sourcesJar"])
        }
    }
}

bintray {
    user = findProperty("BINTRAY_USER") as? String
    key = findProperty("BINTRAY_KEY") as? String
    setPublications("async")
    publish = true
    with(pkg) {
        repo = "discreet"
        name = "async"
        setLicenses("MIT")
        vcsUrl = "https://github.com/andimarek/discreet"
        with(version) {
            name = project.version.toString()
            desc = project.description
        }
    }
}
