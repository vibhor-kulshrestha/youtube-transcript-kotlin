# YouTube Transcript Kotlin

[![](https://jitpack.io/v/vibhor-kulshrestha/youtube-transcript-kotlin.svg)](https://jitpack.io/#vibhor-kulshrestha/youtube-transcript-kotlin)

A Kotlin/Android library for fetching YouTube video transcripts, similar to Python's `youtube-transcript-api`.

## Features

- 🎯 **Simple API**: Easy-to-use functions for fetching transcripts
- 🌍 **Multi-language Support**: Fetch transcripts in any available language
- 🔄 **Fallback Logic**: Automatically finds any available transcript if preferred language isn't available
- 📱 **Android Compatible**: Built specifically for Android/Kotlin projects
- 🛡️ **Error Handling**: Comprehensive error handling with detailed messages
- 📦 **Lightweight**: Minimal dependencies (OkHttp, JSON parsing)

## Installation

### 1. Add JitPack repository
For Groovy (settings.gradle):
```gradle
pluginManagement {
    repositories {
        maven { url 'https://jitpack.io' }
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        maven { url 'https://jitpack.io' }
        google()
        mavenCentral()
    }
}
```
For Kotlin DSL (settings.gradle.kts):
```kotlin
pluginManagement {
    repositories {
        maven("https://jitpack.io")
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        maven("https://jitpack.io")
        google()
        mavenCentral()
    }
}
```

### 2. Add dependency
Using the published coordinates (group = `com.github.vibhor-kulshrestha`):
```gradle
dependencies {
    implementation("com.github.vibhor-kulshrestha:youtube-transcript-kotlin:1.0.0")
}
```
If you reference a commit instead of a tag, use the short commit hash as the version.

### 3. (Optional) Enable Java 17
```gradle
android {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
}
```

## Usage

### Basic Usage

```kotlin
// Extract video ID from URL
val videoId = YouTubeTranscriptApi.extractVideoId("https://www.youtube.com/watch?v=dQw4w9WgXcQ")

// Fetch transcript
val transcriptSegments = YouTubeTranscriptApi.getTranscript(videoId)

// Convert to text
val transcriptText = transcriptSegments.joinToString(" ") { it.text }
```

### Advanced Usage

```kotlin
// Fetch transcript in specific language
val transcriptSegments = YouTubeTranscriptApi.getTranscript(
    videoId = videoId,
    languages = listOf("en", "es", "fr") // Try English first, then Spanish, then French
)

// Get available languages
val availableLanguages = YouTubeTranscriptApi.getAvailableLanguages(videoId)

// List all transcripts
val transcriptList = YouTubeTranscriptApi.listTranscripts(videoId)
```

### Error Handling

```kotlin
try {
    val transcript = YouTubeTranscriptApi.getTranscript(videoId)
    // Use transcript
} catch (e: TranscriptException) {
    when (e) {
        is VideoUnavailable -> println("Video is not available")
        is TranscriptsDisabled -> println("Transcripts are disabled for this video")
        is NoTranscriptFound -> println("No transcript found: ${e.availableLanguages}")
        else -> println("Error: ${e.message}")
    }
}
```

## API Reference

### YouTubeTranscriptApi

#### `extractVideoId(url: String): String`
Extracts video ID from YouTube URL.

#### `getTranscript(videoId: String, languages: List<String> = listOf("en"), preserveFormatting: Boolean = false): List<TranscriptSegment>`
Fetches transcript segments for a video.

#### `getAvailableLanguages(videoId: String): List<String>`
Gets list of available language codes.

#### `listTranscripts(videoId: String): TranscriptList`
Gets detailed transcript list with all available transcripts.

### TranscriptSegment

```kotlin
data class TranscriptSegment(
    val text: String,        // The transcript text
    val start: Float,        // Start time in seconds
    val duration: Float      // Duration in seconds
)
```

### TranscriptList

```kotlin
class TranscriptList {
    fun findTranscript(languageCodes: List<String>): Transcript
    fun findManuallyCreatedTranscript(languageCodes: List<String>): Transcript
    fun findGeneratedTranscript(languageCodes: List<String>): Transcript
    fun getAvailableLanguages(): List<String>
    fun getAllTranscripts(): List<Transcript>
}
```

## Error Types

- `VideoUnavailable`: Video is not available or private
- `TranscriptsDisabled`: Transcripts are disabled for this video
- `NoTranscriptFound`: No transcript found for requested languages
- `InvalidVideoId`: Invalid video ID format
- `YouTubeRequestFailed`: Network or API request failed

## Requirements

- Android API 21+
- Kotlin 1.9+
- Java 17 (toolchain / compile options)
- OkHttp 4.12.0+

## Releasing (Maintainers)
1. Update `version` in `build.gradle.kts` (root module)
2. Commit and push
3. Create a Git tag matching the version (e.g. `git tag v1.1.0 && git push origin v1.1.0`)
4. Visit JitPack page and build the new tag
5. Consumers can then use the new version: `implementation("com.github.vibhor-kulshrestha:youtube-transcript-kotlin:v1.1.0")`

## License

MIT License - see [LICENSE](LICENSE)

## Contributing

Contributions are welcome! Feel free to open issues and PRs.

## Acknowledgments

- Inspired by Python's [youtube-transcript-api](https://github.com/jdepoix/youtube-transcript-api)
- Built for the Android/Kotlin ecosystem
