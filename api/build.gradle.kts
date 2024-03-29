import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.android.library")
    id("com.squareup.sqldelight")
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 23
        targetSdk = 31
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    namespace = "fr.racomach.api"
}

kotlin {
    android()

    /* Targets configuration omitted. 
    *  To find out how to configure the targets, please follow the link:
    *  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */
    jvm()

    js(IR) {
        useCommonJs()
        browser()
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            linkerOpts = mutableListOf("-lsqlite3")
            binaryOption("bundleId", "fr.racomach.api")
            baseName = "api"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.bundles.apiDependencies)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                api(libs.bundles.apiAndroidDependencies)
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(libs.bundles.apiJsDependencies)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(libs.bundles.apiJvmDependencies)
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                //Network
                implementation(libs.bundles.apiIOSDependencies)
            }
        }
    }
}

sqldelight {
    database("ZigWheeloDatabase") {
        packageName = "fr.racomach.zigwheelo"
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

// issue with sqldelight and Kotlin 1.8 https://github.com/cashapp/sqldelight/issues/3746#issuecomment-1397762235
afterEvaluate {
    for (task in tasks) {
        if (task.group != "sqldelight") continue
        if (task.name == "generateSqlDelightInterface" || task.name == "verifySqlDelightMigration") {
            continue
        }

        if (
            !task.name.startsWith("generateCommonMain") &&
            !task.name.startsWith("verifyCommonMain")
        ) {
            task.enabled = false
        }
    }
}