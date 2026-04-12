import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.maven.publish)
}

android {
    namespace = "io.github.pridu.wavenavigationbar"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()

    coordinates(
        "io.github.pridu",
        "wave-navigation-bar",
        "1.1.1"
    )

    pom {
        name.set("WaveNavigationBar")
        description.set("A customizable NavigationBar library for Jetpack Compose.")
        inceptionYear.set("2026")
        url.set("https://github.com/pridu/WaveNavigationBar")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("pridu")
                name.set("sj.kim")
                email.set("qordir@gmail.com")
            }
        }
        scm {
            connection.set("scm:git:https://github.com/pridu/WaveNavigationBar.git")
            developerConnection.set("scm:git:ssh://github.com/pridu/WaveNavigationBar.git")
            url.set("https://github.com/pridu/WaveNavigationBar")
        }
    }
}