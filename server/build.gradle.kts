import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm")
    kotlin("plugin.serialization")
}

group = "fr.racomach"
version = "0.0.1"

application {
    mainClass.set("fr.racomach.server.ApplicationKt")
}

dependencies {
    implementation(project(":database"))
    implementation(project(":api"))
    implementation(libs.bundles.serverDependencies)

    testImplementation(libs.bundles.testDependencies)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}