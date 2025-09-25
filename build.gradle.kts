plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

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
        buildConfigField("String", "LIBRARY_VERSION", "\"1.0.0\"")
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
    
    kotlinOptions {
        jvmTarget = "1.8"
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

dependencies {
    // Core Android dependencies
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.annotation:annotation:1.7.1")
    
    // Coroutines for async operations
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    
    // OkHttp for network requests
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    
    // JSON parsing - using Android's built-in JSON classes
    // No external JSON library needed - Android provides org.json classes

    // Testing dependencies
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

// Publishing configuration for JitPack
afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                
                groupId = "com.youtubetranscript"
                artifactId = "youtube-transcript-kotlin"
                version = "1.0.0"
                
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
