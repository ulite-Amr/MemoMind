plugins {
    id(BuildPlugins.ANDROID_LIBRARY)
    id(BuildPlugins.KOTLIN_ANDROID)
}

android {
    namespace = "com.danielstone.materialaboutlibrary"
    compileSdk = BuildAndroidConfig.COMPILE_SDK_VERSION
    buildToolsVersion = "33.0.0"

    defaultConfig {
        minSdk = BuildAndroidConfig.MIN_SDK_VERSION
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
 //---------Android x -----------//

		coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.0")
    
    implementation(libs.androidxappcompat)
    implementation(libs.preference)
    implementation(libs.recyclerview)
    implementation(libs.swiperefreshlayout)
    implementation(libs.androidxconstraintlayout)
    implementation(libs.androidxcorektx)
    implementation(libs.androidxannotation)
    //--------------------------------//
    
    //---------Google Libs------------//
    implementation(libs.material)
    implementation(libs.ossLicenses)
    //--------------------------------//
    
  //--------------------------------//
    
    //--------Crash Activity--------//
    implementation(libs.blangj)
    //--------------------------------//
    
}
