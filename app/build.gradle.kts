plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.ahmed.newsapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ahmed.newsapp"
        minSdk = 31
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Splash Api
    implementation ("androidx.core:core-splashscreen:1.0.1")

    //Compose Navigation
    val nav_version = "2.6.0"
    implementation ("androidx.navigation:navigation-compose:$nav_version")

    //Dagger Hilt
//    implementation("com.google.dagger:hilt-android:2.55")
//    kapt("com.google.dagger:hilt-compiler:2.55")


//    implementation(libs.hilt.android)
//    kapt(libs.hilt.compiler)
//    implementation(libs.androidx.hilt.navigation.compose)

    //Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.51")
    kapt("com.google.dagger:hilt-compiler:2.51")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")


    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    //Coil
    implementation("io.coil-kt:coil-compose:2.4.0")

    //Datastore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //Compose Foundation
    implementation ("androidx.compose.foundation:foundation:1.4.3")

    //Accompanist
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.31.4-beta")

    //Paging 3
    val paging_version = "3.1.1"
    implementation("androidx.paging:paging-runtime:$paging_version")
    implementation ("androidx.paging:paging-compose:3.2.0-rc01")

    //Room
    implementation (libs.androidx.room.runtime)
    kapt (libs.androidx.room.compiler)
    implementation (libs.androidx.room.ktx)
}