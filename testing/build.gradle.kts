plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.testing"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    api(project(":core"))
    // Local Unit Tests
    api ("junit:junit:4.13.2")
    api ("androidx.arch.core:core-testing:2.2.0")
    api ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    api ("org.mockito.kotlin:mockito-kotlin:4.1.0")
    api ("io.mockk:mockk:1.12.0")
    api ("org.jetbrains.kotlin:kotlin-test-junit:1.5.31")

}