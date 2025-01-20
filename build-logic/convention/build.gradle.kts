import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.github.anagramsolver.android.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

gradlePlugin {
    plugins {
        create("appAndroidApplicationPlugin") {
            id = "com.github.anagramsolver.android.app"
            implementationClass = "com.github.anagramsolver.android.ΑppAndroidApplicationPlugin"
        }

        create("appAndroidPlugin") {
            id = "com.github.anagramsolver.android"
            implementationClass = "com.github.anagramsolver.android.AppAndroidPlugin"
        }

        create("appKotlinPlugin") {
            id = "com.github.anagramsolver.kotlin"
            implementationClass = "com.github.anagramsolver.kotlin.AppKotlinPlugin"
        }
    }
}
