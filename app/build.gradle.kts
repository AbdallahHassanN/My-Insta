plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    kotlin("kapt")
    alias(libs.plugins.com.google.dagger.hilt.android)
    //id("com.google.gms.google-services")
    alias(libs.plugins.com.google.gms.google.services)
}

android {
    namespace = "com.example.myinsta"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myinsta"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.bundles.androidx.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.test.ext)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))

    androidTestImplementation(libs.compose.ui.test)

    debugImplementation(libs.compose.ui.tooling)

    implementation(libs.bundles.androidx.hilt.impl)
    kapt(libs.bundles.androidx.hilt.kapt)
    implementation(libs.org.core.ktx)

    implementation(libs.bundles.androidx.room)
    annotationProcessor(libs.androidx.room.compiler)
    kapt(libs.androidx.room.compiler)

    implementation(libs.bundles.retrofit)

    implementation(libs.github.glide)
    annotationProcessor(libs.github.glide.compiler)

    implementation(platform(libs.com.google.firebase))
    implementation(libs.bundles.firebase)
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")

}