
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.appevalaucion"
    compileSdk {
        version = release(36)

    }

    composeCompiler {
        reportsDestination = layout.buildDirectory.dir("compose_compiler")
        metricsDestination = layout.buildDirectory.dir("compose_compiler")
    }

    defaultConfig {
        applicationId = "com.example.appevalaucion"
        minSdk = 24
        targetSdk = 36
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

    packaging {
        resources {
            // Excluir los archivos LICENSE.md duplicados que causan el conflicto
            excludes += "META-INF/LICENSE.md"

            // A veces también es necesario excluir estos otros archivos comunes:
            excludes += "META-INF/LICENSE-apache-2.0.txt"
            excludes += "META-INF/LICENSE"
            excludes += "META-INF/NOTICE.md"
            excludes += "META-INF/NOTICE"
            excludes += "META-INF/ASL2.0"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }



    viewBinding{
        enable = true
    }
}



dependencies {
        implementation(libs.androidx.material3)
    implementation(libs.play.services.location)
    val nav_version = "2.9.6"

    // Retrofit
    implementation(libs.retrofit)
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")

    // ViewModel utilities for Compose
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Activity Compose
    implementation(libs.androidx.activity.compose)

    implementation("io.coil-kt:coil-compose:2.6.0")


    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.10.2")

    implementation("com.mapbox.maps:android-ndk27:11.16.2")

    implementation("com.mapbox.extension:maps-compose-ndk27:11.16.2")


    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("io.coil-kt:coil-compose:2.7.0")
    implementation(libs.androidx.compose.ui.text.google.fonts)


    implementation("androidx.compose.material:material-icons-extended:1.7.8")
    implementation(libs.androidx.lifecycle.lifecycle.viewmodel.compose) //
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")
    testImplementation(libs.junit)


    // Pruebas Unitarias
    // MockK
    testImplementation("io.mockk:mockk:1.14.7")
    // Si también haces pruebas instrumentadas (androidTest), añade esta línea también:
    androidTestImplementation("io.mockk:mockk-android:1.14.7")
    // --- Instrumented Tests (androidTest - UI Tests) ---

    // Dependencias base de AndroidX Test (Versiones actualizadas y compatibles)
    // Usa las versiones del libs.versions.toml si existen, si no, usa cableado:
    androidTestImplementation(libs.androidx.junit) // Usa una version consistente
    androidTestImplementation(libs.androidx.espresso.core) // Usa una version consistente

    // Dependencias de Compose UI Test (usando el BOM para compatibilidad)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4) // Usa la versión gestionada por el BOM/libs

    androidTestImplementation("androidx.test:runner:1.7.0")
    androidTestImplementation("androidx.test:rules:1.7.0")

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}