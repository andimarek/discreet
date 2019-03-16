//plugins {
//    java
//}
//
//group = "discreet"
//version = "1.0-SNAPSHOT"
//
//repositories {
//    mavenCentral()
//}
//
//dependencies {
//    testCompile("junit", "junit", "4.12")
//}
//
//configure<JavaPluginConvention> {
//    sourceCompatibility = JavaVersion.VERSION_1_8
//}

tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
    gradleVersion = "5.2.1"
}
