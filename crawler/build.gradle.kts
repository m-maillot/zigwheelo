plugins {
    application
    kotlin("jvm")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(project(":database"))
    implementation(libs.bundles.crawlerDependencies)
}