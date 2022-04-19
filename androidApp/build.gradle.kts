plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "io.brewdict.application.android"
        minSdk = 24
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.annotation:annotation:1.3.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.2")

    //Jetpack Compose Dependencies
    implementation ("androidx.activity:activity-compose:1.4.0")
    implementation ("androidx.compose.material:material:1.1.1")
    implementation ("androidx.compose.animation:animation:1.1.1")
    implementation("androidx.compose.ui:ui:1.1.1")
    implementation ("androidx.compose.ui:ui-tooling:1.1.1")
    implementation ("androidx.compose.runtime:runtime-livedata:1.1.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.1")
    implementation ("com.google.accompanist:accompanist-appcompat-theme:0.16.0")
    implementation ("com.google.accompanist:accompanist-swiperefresh:0.23.1")
    implementation ("androidx.navigation:navigation-compose:2.4.2")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.1.1")
}