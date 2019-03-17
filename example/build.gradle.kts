plugins {
    java
    id("discreet")
}
version = "1.0-SNAPSHOT"



repositories {
    mavenCentral()
    maven(url = "https://dl.bintray.com/andimarek/discreet")
}

dependencies {
    "discreet"("discreet:async:2019-03-17T10-26-19:sources")
    "discreet"("discreet:async:2019-03-17T10-26-19:sources")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

