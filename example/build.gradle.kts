plugins {
    java
    id("com.andimarek.discreet")
}
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    "discreet"("discreet:async:2019-03-17T10-26-19:sources")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

