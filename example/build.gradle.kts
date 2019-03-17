plugins {
    java
    id("com.andimarek.discreet")
}
group = "com.andimarek.discreet"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    "discreet"("com.andimarek.discreet:async:2019-03-18T08-26-43:sources")
    "discreet"("com.andimarek.discreet:string-utils:2019-03-18T08-25-56:sources")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

