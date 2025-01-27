plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.dagger.hilt.android)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.kotlin.plugin.parcelize)
}

android {
    namespace = "com.gmail.denuelle42.denuanime"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.gmail.denuelle42.denuanime"
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
        getByName("debug") {
            isMinifyEnabled = false // Keeps debugging info for better traceability in development
            applicationIdSuffix = ".debug" // Optional: helps differentiate debug APK
            versionNameSuffix = "-debug"   // Optional: appends "-debug" to version name
            isDebuggable = true
        }

        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isDebuggable = false
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
        kotlinCompilerExtensionVersion = "1.5.14"
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

//    Google Font
    implementation(libs.androidx.ui.text.google.fonts)

//    Navigation
    implementation (libs.androidx.navigation.compose)

//    Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.hilt.work)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

//    serialization
    implementation(libs.kotlinx.serialization.json)

//    work manager
    implementation(libs.androidx.work)

//    icons extended
    implementation(libs.androidx.material.icons.extended)

//    Room DB
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

//    Encryption
    implementation(libs.bcrypt)
    implementation(libs.androidx.security)

//    Retrofit
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.gson)

//    Ok HTTP
    implementation(libs.logging.interceptor)

//    Coil
    implementation(libs.coil)
    implementation(libs.coil.network)
    implementation(libs.coil.test)

//    Palette
    implementation(libs.palette)

//    Shimmer
    implementation(libs.shimmer)

//    Paging 3
    implementation(libs.paging)
    implementation(libs.paging.compose)
}