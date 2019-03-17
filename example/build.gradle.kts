plugins {
    java
    id("discreet")
}
version = "1.0-SNAPSHOT"


sourceSets {
    main {
        java {
            srcDir("build/discreet-src")
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    "discreet"("discreet:async:2019-03-17T10-26-19:sources")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

