plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    //id("com.android.application")
    //id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
}

android {
    namespace = "com.example.westocked"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.westocked"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

// …
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = "1.3.2" // или актуальная версия

    buildFeatures {
        // Включаем Compose
        compose = true
    }

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
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        // Укажите актуальную версию компилятора Compose (например, 1.5.1, проверьте документацию)
        kotlinCompilerExtensionVersion = "1.5.1"
}

dependencies {
    implementation("androidx.compose.material:material-icons-extended:1.4.3")
    // или, если используете Material3:
    implementation("androidx.compose.material3:material3:1.1.0")

    // Ktor клиент
    implementation("io.ktor:ktor-client-core:2.3.3")
    implementation("io.ktor:ktor-client-cio:2.3.3")
    // Для работы с JSON (опционально, если хотите парсить ответ)
    implementation("io.ktor:ktor-client-content-negotiation:2.3.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.3")
    // Коррутины
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.0")

    /*implementation(platform("io.github.jan-tennert.supabase:bom:VERSION"))
    implementation("io.github.jan-tennert.supabase:[module]")

    // Jetpack Compose
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.material3:material3:1.1.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.0")
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.0")
    implementation("androidx.activity:activity-compose:1.7.2")

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.supabase.kt)

    implementation(libs.jan.tennert.supabase.kt)


    // Для выполнения запросов можно добавить Ktor (если требуется)
    implementation("io.ktor:ktor-client-core:2.3.0")
    implementation("io.ktor:ktor-client-cio:2.3.0")

    //implementation(platform("io.github.jan-tennert.supabase:bom:$3.1.1"))
    implementation(libs.postgrest.kt)
    //implementation("io.ktor:ktor-client-android:$2.1")*/



    // CameraX
    //implementation("androidx.camera:camera-camera2:1.2.0")
    //implementation("androidx.camera:camera-lifecycle:1.2.0")
    //implementation("androidx.camera:camera-view:1.0.0-alpha31")
    //implementation("androidx.camera:camera-compose:1.2.0-alpha08")

    implementation("androidx.camera:camera-camera2:1.2.0")
    implementation("androidx.camera:camera-lifecycle:1.2.0")
    implementation("androidx.camera:camera-view:1.2.0")
    implementation("com.google.mlkit:barcode-scanning:17.0.3")

    implementation("com.journeyapps:zxing-android-embedded:4.3.0")

    implementation("androidx.compose.foundation:foundation:1.3.1")
    implementation("com.google.accompanist:accompanist-insets:0.30.1")



    // ML Kit Barcode Scanning
    /*implementation("com.google.mlkit:barcode-scanning:17.0.3")
    implementation(libs.google.barcode.scanning)
    implementation("com.google.android.gms:play-services-mlkit-barcode-scanning:16.2.1")*/

    //ZXing scanner
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation("com.google.zxing:core:3.4.1")



    implementation("androidx.compose.ui:ui:1.3.3")
    implementation("androidx.compose.material:material:1.3.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    // Для отладки preview:
    debugImplementation("androidx.compose.ui:ui-tooling:1.3.3")

    /*implementation("androidx.compose.material:material-icons-core:1.4.0")
    implementation("androidx.compose.material:material-icons-extended:1.4.0")*/

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

    implementation(platform("androidx.compose:compose-bom:2023.06.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    implementation("androidx.activity:activity-compose:1.7.2")

    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    //implementation("com.github.supabase-community.supabase-kt:core:1.0.0-beta-4")
    //implementation("com.github.supabase-community.supabase-kt:postgrest:1.0.0-beta-4")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.compose.material3:material3:1.1.0-alpha08")
    implementation("io.github.jan-tennert.supabase:supabase-kt:2.6.1")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.4")
}}}