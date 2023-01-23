plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version "1.3.0-rc05"
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(project(":api"))
                implementation(compose.web.core)
                implementation(compose.runtime)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = ""
    }
}