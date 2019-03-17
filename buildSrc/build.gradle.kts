plugins {
    java
    `java-gradle-plugin`
}

gradlePlugin {
    plugins {
        create("discreet") {
            id = "discreet"
            implementationClass = "discreet.DiscreetPlugin"
        }
    }
}

dependencies {
    compileOnly(gradleApi())
}