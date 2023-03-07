buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://jitpack.io")
        google()
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
        maven("https://www.jetbrains.com/intellij-repository/releases")
    }
 //     dependencies {
		//				classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")
 //       classpath("com.google.android.gms:oss-licenses-plugin:0.10.6")
//}
   }

// Top-level build file where you can add configuration options common to all sub-projects/modules.
//  plugins {
 //     alias(libs.plugins.android.application) apply false
 //     alias(libs.plugins.android.library) apply false
  //    alias(libs.plugins.kotlin) apply false
//  }

tasks.register("clean", Delete::class.java, Action<Delete> {
    delete(rootProject.buildDir)
})
