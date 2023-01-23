plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
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
        kotlinCompilerExtensionVersion = "1.4.0"
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

    hilt {
        enableExperimentalClasspathAggregation = true
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

kapt {
    correctErrorTypes = true
}

dependencies {

    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    implementation(project(":api"))
    implementation(libs.bundles.androidDependencies)
    implementation(platform("com.google.firebase:firebase-bom:31.1.0"))
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-messaging-ktx")

    kapt(libs.bundles.androidKaptDependencies)

    testImplementation(libs.bundles.androidTestDependencies)
    androidTestImplementation(composeBom)
    androidTestImplementation(libs.bundles.androidAndroidTestDependencies)
    debugImplementation(libs.bundles.androidDebugDependencies)
}