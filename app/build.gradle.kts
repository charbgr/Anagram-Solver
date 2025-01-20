plugins {
    id("com.github.anagramsolver.android.app")
}

android {
    namespace = "com.github.charbgr.anagramsolver"

    defaultConfig {
        applicationId = "com.github.charbgr.anagramsolver"
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
}

appAndroid {
    compose(
        activity = true,
        ui = true,
        foundation = true,
    )
}

dependencies {
    implementation(project(":modules:designsystem"))
}
