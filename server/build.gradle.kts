plugins {
    application
    kotlin("jvm")
    kotlin("plugin.serialization")
}

group = "fr.racomach"
version = "0.0.1"
application {
    mainClass.set("fr.racomach.ApplicationKt")
}

dependencies {
    implementation(project(":api"))
    implementation(project(":database"))
    implementation(libs.bundles.serverDependencies)
    testImplementation(libs.bundles.testDependencies)
}