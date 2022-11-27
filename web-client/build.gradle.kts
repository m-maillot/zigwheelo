plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version "1.2.1-rc03"
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