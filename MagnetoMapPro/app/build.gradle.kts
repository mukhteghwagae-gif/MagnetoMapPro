plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.magnetomap.pro"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.magnetomap.pro"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        ndk { abiFilters += listOf("arm64-v8a", "armeabi-v7a", "x86_64") }
        vectorDrawables { useSupportLibrary = true }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions { jvmTarget = "17" }

    packaging {
        resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" }
    }
}

dependencies {
    // ── ARCore ──────────────────────────────────────────────────────────
    implementation("com.google.ar:core:1.43.0")

    // ── Sceneform fork (maintained, free, open-source) ──────────────────
    implementation("com.gorisse.thomas.sceneform:sceneform:1.23.0")

    // ── AndroidX Core ───────────────────────────────────────────────────
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")

    // ── Navigation Component ────────────────────────────────────────────
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")

    // ── Room Database ───────────────────────────────────────────────────
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    // ── Hilt DI ─────────────────────────────────────────────────────────
    implementation("com.google.dagger:hilt-android:2.50")
    ksp("com.google.dagger:hilt-compiler:2.50")
    implementation("androidx.hilt:hilt-navigation-fragment:1.1.0")

    // ── Coroutines ───────────────────────────────────────────────────────
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    // ── Material Design 3 ────────────────────────────────────────────────
    implementation("com.google.android.material:material:1.11.0")

    // ── TensorFlow Lite (anomaly classification) ─────────────────────────
    implementation("org.tensorflow:tensorflow-lite:2.14.0")
    implementation("org.tensorflow:tensorflow-lite-support:0.4.4")
    implementation("org.tensorflow:tensorflow-lite-metadata:0.4.4")
    implementation("org.tensorflow:tensorflow-lite-gpu:2.14.0")
    implementation("org.tensorflow:tensorflow-lite-gpu-delegate-plugin:0.4.4")

    // ── MPAndroidChart (2D field strength graphs) ────────────────────────
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    // ── OpenCSV (survey export) ──────────────────────────────────────────
    implementation("com.opencsv:opencsv:5.9")

    // ── iText7 Community (PDF report generation) ─────────────────────────
    implementation("com.itextpdf:itext7-core:7.2.5")

    // ── Location ─────────────────────────────────────────────────────────
    implementation("com.google.android.gms:play-services-location:21.1.0")

    // ── WorkManager (background survey processing) ───────────────────────
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    // ── DataStore (settings) ─────────────────────────────────────────────
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // ── Gson ─────────────────────────────────────────────────────────────
    implementation("com.google.code.gson:gson:2.10.1")

    // ── Timber (logging) ─────────────────────────────────────────────────
    implementation("com.jakewharton.timber:timber:5.0.1")

    // ── Lottie (UI animations) ────────────────────────────────────────────
    implementation("com.airbnb.android:lottie:6.3.0")

    // ── Splash Screen API ─────────────────────────────────────────────────
    implementation("androidx.core:core-splashscreen:1.0.1")

    // ── Testing ───────────────────────────────────────────────────────────
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
