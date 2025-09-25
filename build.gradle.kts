plugins {
    // Use stable, compatible versions (AGP 8.5.x + Kotlin 1.9.24)
    id("com.android.library") version "8.5.1"
    id("org.jetbrains.kotlin.android") version "1.9.24"
    id("maven-publish")
}

// JitPack-friendly coordinates
group = "com.github.vibhor-kulshrestha"
version = "1.0.5"

val libVersion = version

android {
    namespace = "com.youtubetranscript"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        targetSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        // Library metadata
        buildConfigField("String", "LIBRARY_NAME", "\"youtube-transcript-kotlin\"")
        buildConfigField("String", "LIBRARY_VERSION", "\"${libVersion}\"")
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
        // Set Java 17 for AGP 8.5.x compatibility and JDK 17 target on JitPack
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        buildConfig = true
    }

    // Only sources jar (omit javadoc jar to avoid empty Javadoc issues on pure-Kotlin project)
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    // Revert to widely-used stable versions to reduce JitPack resolution risk
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.annotation:annotation:1.8.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")

    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

publishing {
    publications {
        // Kotlin DSL equivalent of Groovy example you saw
        create<MavenPublication>("release") {
            groupId = project.group.toString()
            artifactId = "youtube-transcript-kotlin"
            version = project.version.toString()

            // Defer attaching the Android release component until variants are ready
            afterEvaluate { from(components["release"]) }

            pom {
                name.set("YouTube Transcript Kotlin")
                description.set("A Kotlin library for fetching YouTube video transcripts, similar to Python's youtube-transcript-api")
                url.set("https://github.com/vibhor-kulshrestha/youtube-transcript-kotlin")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }

                developers {
                    developer {
                        id.set("vibhor-kulshrestha")
                        name.set("Vibhor Kulshrestha")
                        email.set("vibhorkulshrestha2001@gmail.com")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/vibhor-kulshrestha/youtube-transcript-kotlin.git")
                    developerConnection.set("scm:git:ssh://github.com:vibhor-kulshrestha/youtube-transcript-kotlin.git")
                    url.set("https://github.com/vibhor-kulshrestha/youtube-transcript-kotlin")
                }
            }
        }
    }
}
