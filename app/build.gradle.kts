plugins {
    id(BuildPlugins.ANDROID_APPLICATION)
    id(BuildPlugins.KOTLIN_ANDROID)
    id(BuildPlugins.OSS_LICENSES)
    id(BuildPlugins.KTLINT)
}
 
android {
    namespace = BuildAndroidConfig.APPLICATION_ID
    compileSdk = BuildAndroidConfig.COMPILE_SDK_VERSION
    buildToolsVersion = BuildAndroidConfig.BUILD_TOOLS_VERSION

    defaultConfig {
        applicationId = BuildAndroidConfig.APPLICATION_ID
        minSdk = BuildAndroidConfig.MIN_SDK_VERSION
        targetSdk = BuildAndroidConfig.TARGET_SDK_VERSION
        versionCode = BuildAndroidConfig.VERSION_CODE
        versionName = BuildAndroidConfig.VERSION_NAME

        vectorDrawables.useSupportLibrary = BuildAndroidConfig.SUPPORT_LIBRARY_VECTOR_DRAWABLES
    }
    signingConfigs {
        val TESTKEY = "uliteamr"
        getByName(BuildType.DEBUG) {
            storeFile = file("amr332.keystore")
            storePassword = TESTKEY
            keyAlias = "amr332"
            keyPassword = TESTKEY
        }
    }

    buildTypes {
        getByName(BuildType.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            applicationIdSuffix = BuildTypeDebug.applicationIdSuffix
            versionNameSuffix = BuildTypeDebug.versionNameSuffix
        }

        getByName(BuildType.RELEASE) {
            signingConfig = signingConfigs.getByName(BuildType.DEBUG)
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
				isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    lintOptions {
        isAbortOnError = false
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
    
   //---------Google Libs------------//
       implementation(project(":materialAbout"))
  //--------------------------------//
    
    //--------Crash Activity--------//
    implementation(libs.blangj)
    //--------------------------------//
  }

}