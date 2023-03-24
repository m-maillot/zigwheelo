group = "io.zigweelo"
version = "0.0.1"

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(uri("https://plugins.gradle.org/m2/"))
    }

    dependencies {
        // keeping this here to allow AS to automatically update
        classpath("com.android.tools.build:gradle:7.4.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.8.0")
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.4")
        classpath("com.google.gms:google-services:4.3.15")
    }
}

plugins {
    kotlin("multiplatform") version "1.8.0" apply false
    kotlin("plugin.serialization") version "1.8.0" apply false
    kotlin("jvm") version "1.8.0" apply false
    id("com.android.application") version "7.4.1" apply false
    id("com.android.library") version "7.4.1" apply false
    kotlin("android") version "1.8.0" apply false
    id("com.google.dagger.hilt.android") version "2.44.2" apply false
    id("com.squareup.sqldelight") version "1.5.4" apply false
    id("com.google.gms.google-services") version "4.3.14" apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers/") }
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    }
}
