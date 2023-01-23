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
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=true")
}

dependencies {
    implementation(project(":database"))
    implementation(project(":api"))

    implementation(libs.bundles.serverDependencies)

    // https://mvnrepository.com/artifact/com.google.firebase/firebase-admin
    implementation ("com.google.firebase:firebase-admin:9.1.1") {
        exclude(group = "com.google.cloud", module = "google-cloud-firestore")
    }

    testImplementation(libs.bundles.testDependencies)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

sourceSets.main {
    java.srcDirs("build/generated/ksp/main/kotlin")
}