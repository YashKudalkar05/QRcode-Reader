plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.qrcodeutility"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.qrcodeutility"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

}




dependencies {

    implementation(libs.lifecycle.livedata)
    implementation (libs.lifecycle.viewmodel)
    implementation (libs.room.runtime)
    annotationProcessor (libs.room.compiler)
    //implementation(libs.zxing.embedded)
    implementation ("androidx.camera:camera-core:1.3.0")
    implementation ("androidx.camera:camera-camera2:1.3.0")
    implementation ("androidx.camera:camera-lifecycle:1.3.0")
    implementation ("androidx.camera:camera-view:1.3.0")

    implementation ("com.google.mlkit:barcode-scanning:17.3.0")

    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)



}
