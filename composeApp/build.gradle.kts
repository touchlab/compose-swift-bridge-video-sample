import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("co.touchlab.skie") version "0.9.3"
    id("com.google.devtools.ksp") version "2.0.21-1.0.26"
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation("co.touchlab.compose:compose-swift-bridge:0.1.0")
        }
    }
}

android {
    namespace = "com.kgalligan.mapview"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.kgalligan.mapview"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
    dependencies {
        val composeSwiftBridgeKsp = "co.touchlab.compose:compose-swift-bridge-ksp:0.1.0"
        "kspCommonMainMetadata"(composeSwiftBridgeKsp) // Common Main generation required

        // iOS targets
        "kspIosSimulatorArm64"(composeSwiftBridgeKsp)
        "kspIosArm64"(composeSwiftBridgeKsp)
        "kspIosX64"(composeSwiftBridgeKsp)

        // All targets your module support, here, is Android only as a example
        "kspAndroid"(composeSwiftBridgeKsp)

        // add the SKIE SubPlugin that will generate the Swift code
        skieSubPlugin("co.touchlab.compose:compose-swift-bridge-skie:0.1.0")
    }
}

// Adds the required targetName for the KSP plugin
tasks.withType<com.google.devtools.ksp.gradle.KspTaskNative>().configureEach {
    options.add(SubpluginOption("apoption", "compose-swift-bridge.targetName=$target"))
}

// support for generating ksp code in commonCode
// see https://github.com/google/ksp/issues/567
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

kotlin.sourceSets.commonMain {
    kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
}