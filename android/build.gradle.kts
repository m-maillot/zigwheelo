plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {

    namespace = "fr.racomach.zigwheelo"
    compileSdk = 33

    defaultConfig {
        applicationId = "fr.racomach.zigwheelo"
        minSdk = 26
        targetSdk = 33

        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
        resources.excludes.add("META-INF/licenses/**")
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }
}

dependencies {

    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    implementation(project(":api"))
    implementation(libs.bundles.androidDependencies)

    testImplementation(libs.bundles.androidTestDependencies)
    androidTestImplementation(composeBom)
    androidTestImplementation(libs.bundles.androidAndroidTestDependencies)
    debugImplementation(libs.bundles.androidDebugDependencies)
}