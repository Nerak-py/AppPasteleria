
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
    val nav_version = "2.9.5"

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // ViewModel utilities for Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.3")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.3")

    // Activity Compose
    implementation("androidx.activity:activity-compose:1.9.0")




    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.10.2")

    implementation("com.mapbox.maps:android-ndk27:11.16.2")

    implementation("com.mapbox.extension:maps-compose-ndk27:11.16.2")


    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("androidx.compose.ui:ui-text-google-fonts:1.6.7")


    implementation("androidx.compose.material:material-icons-extended:1.7.8")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0") //
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    testImplementation("junit:junit:4.13.2")


    // Pruebas Unitarias
    // MockK
    testImplementation("io.mockk:mockk:1.13.8")
    // Si también haces pruebas instrumentadas (androidTest), añade esta línea también:
    androidTestImplementation("io.mockk:mockk-android:1.13.8")
    // --- Instrumented Tests (androidTest - UI Tests) ---

    // Dependencias base de AndroidX Test (Versiones actualizadas y compatibles)
    // Usa las versiones del libs.versions.toml si existen, si no, usa cableado:
    androidTestImplementation("androidx.test.ext:junit:1.1.5") // Usa una version consistente
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1") // Usa una version consistente

    // Dependencias de Compose UI Test (usando el BOM para compatibilidad)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4) // Usa la versión gestionada por el BOM/libs

    androidTestImplementation("androidx.test:runner:1.6.1")
    androidTestImplementation("androidx.test:rules:1.6.1")

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}