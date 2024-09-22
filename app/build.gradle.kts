plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)

}

android {
    namespace = "com.pankajjangid.cryptotest"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pankajjangid.cryptotest"
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
    /*room {
        schemaDirectory("$projectDir/schemas")
    }*/
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
    implementation(libs.androidx.junit.ktx)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.junit)
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation (libs.truth)
    testImplementation (libs.truth.java8.extension)


    // Navigation Component
    dependencies {
        implementation("androidx.navigation:navigation-fragment-ktx:2.8.1")
        implementation("androidx.navigation:navigation-ui-ktx:2.8.1")

// Room components

        implementation(libs.androidx.room.runtime)
        annotationProcessor(libs.androidx.room.compiler)
        implementation(libs.androidx.room.ktx)
        testImplementation(libs.androidx.room.testing)
        ksp(libs.androidx.room.compiler)
       // ksp(libs.room.compiler)

        //Gson
        implementation(libs.gson)
//Koin
        implementation(libs.koin.androidx.compose)

// Lifecycle components
        implementation(libs.androidx.lifecycle.extensions)
        implementation(libs.androidx.lifecycle.common.java8)
        implementation(libs.androidx.lifecycle.viewmodel.ktx)


    }

}