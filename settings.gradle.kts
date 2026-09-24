pluginManagement {
    repositories {
        maven {
            url = uri("http://nexus.dep-info.iut-nantes.univ-nantes.prive/repository/public/")
            isAllowInsecureProtocol = true
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "flip7-etu"
