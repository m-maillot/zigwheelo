plugins {
    application
    kotlin("jvm") version "1.6.10"
}

group = "fr.racomach"
version = "0.0.1"
application {
    mainClass.set("fr.racomach.ApplicationKt")
}

dependencies {
    implementation(libs.bundles.serverDependencies)
    testImplementation(libs.bundles.testDependencies)
}