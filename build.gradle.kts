plugins {
    // Use stable, verified versions
    id("com.android.library") version "8.5.2"
    id("org.jetbrains.kotlin.android") version "2.2.20"
    id("maven-publish")
}

// Use JitPack-friendly group (com.github.<GitHubUser>) so consumers can depend directly
group = "com.github.vibhor-kulshrestha"
version = "1.0.5"

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
        buildConfigField("String", "LIBRARY_VERSION", "\"1.0.5\"")
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
    
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    // Core Android dependencies (stable)
    implementation("androidx.core:core-ktx:1.17.0")
    implementation("androidx.annotation:annotation:1.9.1")

    // Coroutines (match Kotlin 1.9.x line)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")

    // OkHttp (stable 4.x line for broad compatibility)
    implementation("com.squareup.okhttp3:okhttp:5.1.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:5.1.0")

    // JUnit
    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"]) // Safe now: component is created by AGP

                // Align published coordinates with JitPack expectations
                groupId = project.group.toString()
                artifactId = "youtube-transcript-kotlin"
                version = project.version.toString()

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
}
