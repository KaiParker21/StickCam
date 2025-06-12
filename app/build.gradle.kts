plugins {
    alias(
        libs.plugins.android.application
    )
    alias(
        libs.plugins.kotlin.android
    )
    alias(
        libs.plugins.kotlin.compose
    )
}

android {
    namespace =
        "com.skye.stickcam"
    compileSdk =
        35

    defaultConfig {
        applicationId =
            "com.skye.stickcam"
        minSdk =
            24
        targetSdk =
            35
        versionCode =
            1
        versionName =
            "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled =
                false
            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility =
            JavaVersion.VERSION_11
        targetCompatibility =
            JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget =
            "11"
    }
    buildFeatures {
        compose =
            true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }

}

dependencies {

    val cameraxVersion = "1.3.0"

    implementation("androidx.camera:camera-core:$cameraxVersion")
    implementation("androidx.camera:camera-camera2:$cameraxVersion")
    implementation("androidx.camera:camera-lifecycle:$cameraxVersion")
    implementation("androidx.camera:camera-view:$cameraxVersion")

    implementation("androidx.compose.material:material-icons-extended-android:1.7.8")

    implementation ("org.tensorflow:tensorflow-lite:2.14.0")
    implementation ("org.tensorflow:tensorflow-lite-support:0.4.3")
    implementation ("org.tensorflow:tensorflow-lite-task-vision:0.4.3")

    implementation ("com.github.skydoves:colorpicker-compose:1.0.0")
    implementation("io.coil-kt:coil-compose:2.5.0")

    implementation(libs.androidx.navigation.compose)

    implementation(
        libs.androidx.core.ktx
    )
    implementation(
        libs.androidx.lifecycle.runtime.ktx
    )
    implementation(
        libs.androidx.activity.compose
    )
    implementation(
        platform(
            libs.androidx.compose.bom
        )
    )
    implementation(
        libs.androidx.ui
    )
    implementation(
        libs.androidx.ui.graphics
    )
    implementation(
        libs.androidx.ui.tooling.preview
    )
    implementation(
        libs.androidx.material3
    )
    testImplementation(
        libs.junit
    )
    androidTestImplementation(
        libs.androidx.junit
    )
    androidTestImplementation(
        libs.androidx.espresso.core
    )
    androidTestImplementation(
        platform(
            libs.androidx.compose.bom
        )
    )
    androidTestImplementation(
        libs.androidx.ui.test.junit4
    )
    debugImplementation(
        libs.androidx.ui.tooling
    )
    debugImplementation(
        libs.androidx.ui.test.manifest
    )
}