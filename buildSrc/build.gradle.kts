plugins {
    java
    `java-gradle-plugin`
    id("com.gradle.plugin-publish") version "0.10.1"

}

version = "0.1"

pluginBundle {
    website = "https://bintray.com/andimarek/discreet"
    vcsUrl = "https://bintray.com/andimarek/discreet"
    tags = listOf("")
}

gradlePlugin {
    plugins {
        create("discreet") {
            id = "com.andimarek.discreet"
            displayName = "Discreet dependencies"
            description = "Get discreet source code dependencies"
            implementationClass = "discreet.DiscreetPlugin"
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(gradleApi())
    implementation("com.github.javaparser:javaparser-symbol-solver-core:3.13.3")

}