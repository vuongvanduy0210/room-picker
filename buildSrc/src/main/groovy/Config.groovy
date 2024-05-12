class Versions {
    static final String kotlin = "1.9.22"
    static final String hilt = "2.50"
    static final String moshi = "1.15.1"
    static final String room = "2.6.1"
    static final String nav = "2.7.6"
    static final String coroutine = "1.7.3"
    static final String glide = "4.14.2"
    static final String ksp ="1.9.22-1.0.17"
    static final String gradle = "8.2.2"

    static final String retrofit = "2.9.0"

    static final String coreKtx = "1.12.0"
    static final String appCompat = "1.6.1"
    static final String material = "1.11.0"
    static final String constraintLayout = "2.1.4"
    static final String espressoCore = "3.4.0"

    static final String jUnit = "4.13.2"
    static final String junitVersion = "1.1.3"
    static final String espresso = "3.3.0"
    static final String ktlint = "11.5.1"

    static final String timber = "5.0.1"

    static final String lottie = "5.2.0"

    static final String leak_canary = "2.9.1"

    static final String cloudinary = "2.5.0"

    static final String toasty = "1.5.2"

    //UI
    static final String flexBoxLayout = "3.0.0"
    static final String viewPager2 = "1.0.0"

    static final String gson = "2.8.8"

}

class ClassPath {
    static final String ktLint = "org.jlleitschuh.gradle:ktlint-gradle:${Versions.ktlint}"
    static final String kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    static final String dagger_hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    static final String nav = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.nav}"
    static final String ksp = "com.google.devtools.ksp:symbol-processing-gradle-plugin:${Versions.ksp}"
    static final String gradle = "com.android.tools.build:gradle:${Versions.gradle}"
}

class Plugins {
    static final String ktLint = "org.jlleitschuh.gradle.ktlint"

}

class Deps {
    static final String core_ktx = "androidx.core:core-ktx:${Versions.coreKtx}"
    static final String appcompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    static final String material = "com.google.android.material:material:${Versions.material}"
    static final String constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    static final String androidx_espresso_core = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"

    //compose
    static final String navigation_fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.nav}"
    static final String navigation_ktx = "androidx.navigation:navigation-ui-ktx:${Versions.nav}"

    //Hilt
    static final String dagger_hilt= "com.google.dagger:hilt-android:${Versions.hilt}"
    static final String dagger_hilt_compiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"

    //OkHttp3
    static final String OkHttp3 = "com.squareup.okhttp3:okhttp"
    static final String OkHttp3_logging = "com.squareup.okhttp3:logging-interceptor"

    //Retrofit
    static final String retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    static final String retrofit_coroutine_adapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"
    static final String retrofit_converter_moshi = "com.squareup.retrofit2:converter-moshi:2.9.0"

    //Moshi
    static final String moshi_codegen = "com.squareup.moshi:moshi-kotlin-codegen:$Versions.moshi"
    static final String moshi = "com.squareup.moshi:moshi:$Versions.moshi"
    static final String moshi_kotlin = "com.squareup.moshi:moshi-kotlin:$Versions.moshi"
    static final String moshi_adapter = "com.squareup.moshi:moshi-adapters:$Versions.moshi"

    // coroutines
    static final String coroutine_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$Versions.coroutine"
    static final String coroutine_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$Versions.coroutine"

    //Glide
    static final String glide = "com.github.bumptech.glide:glide:$Versions.glide"
    static final String glide_compiler = "com.github.bumptech.glide:compiler:$Versions.glide"

    //Room
    static final String room_runtime = "androidx.room:room-runtime:$Versions.room"
    static final String room_compiler = "androidx.room:room-compiler:$Versions.room"
    static final String room_ktx = "androidx.room:room-ktx:$Versions.room"

    //Timber
    static final String timber = "com.jakewharton.timber:timber:$Versions.timber"

    //Lottie
    static final String lottie = "com.airbnb.android:lottie:$Versions.lottie"

    //Leak Canary
    static final String leak_canary = "com.squareup.leakcanary:leakcanary-android:$Versions.leak_canary"

    //Cloudinary
    static final String cloudinary = "com.cloudinary:cloudinary-android:$Versions.cloudinary"

    //Toasty
    static final String toasty = "com.github.GrenderG:Toasty:$Versions.toasty"

    // Testing
    static final String junit = "junit:junit:${Versions.jUnit}"
    static final String androidx_junit = "androidx.test.ext:junit:${Versions.junitVersion}"
    static final String espresso_core = "androidx.test.espresso:espresso-core:$Versions.espressoCore"

    //UI
    static final String flexBoxlayout = "com.google.android.flexbox:flexbox:${Versions.flexBoxLayout}"
    static final String viewPager2 = "androidx.viewpager2:viewpager2:${Versions.viewPager2}"

    static final String gson = "com.google.code.gson:gson:${Versions.gson}"
}


class AppConfigs {
    static final String app_name = "Room Picker"
    static final String app_name_dev = "$app_name-Dev"

    static final String application_id = "com.gianghv.android"
    static final int compile_sdk_version = 34
    static final int min_sdk_version = 24
    static final int target_sdk_version = 34
    static final int version_code = 1
    static final String version_name = "1.0"
    static final int version_code_release = 1
    static final String version_name_release = "1.0"
}
