import com.youtubetranscript.YouTubeTranscriptApi
import com.youtubetranscript.TranscriptException

/**
 * Example usage of YouTube Transcript Kotlin library
 */
suspend fun main() {
    try {
        // Example 1: Basic usage
        val videoUrl = "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
        val videoId = YouTubeTranscriptApi.extractVideoId(videoUrl)
        println("Video ID: $videoId")
        
        // Example 2: Fetch transcript
        val transcriptSegments = YouTubeTranscriptApi.getTranscript(videoId)
        val transcriptText = transcriptSegments.joinToString(" ") { it.text }
        println("Transcript: $transcriptText")
        
        // Example 3: Get available languages
        val availableLanguages = YouTubeTranscriptApi.getAvailableLanguages(videoId)
        println("Available languages: $availableLanguages")
        
        // Example 4: Fetch transcript in specific language
        val englishTranscript = YouTubeTranscriptApi.getTranscript(
            videoId = videoId,
            languages = listOf("en", "es", "fr")
        )
        println("English transcript: ${englishTranscript.joinToString(" ") { it.text }}")
        
    } catch (e: TranscriptException) {
        when (e) {
            is com.youtubetranscript.VideoUnavailable -> println("Video is not available")
            is com.youtubetranscript.TranscriptsDisabled -> println("Transcripts are disabled for this video")
            is com.youtubetranscript.NoTranscriptFound -> println("No transcript found: ${e.availableLanguages}")
            else -> println("Error: ${e.message}")
        }
    }
}
