# Consumer ProGuard rules for youtube-transcript-kotlin

# Keep all public classes and methods
-keep public class com.youtubetranscript.** { *; }

# Keep all data classes
-keep class com.youtubetranscript.models.** { *; }

# Keep all core classes
-keep class com.youtubetranscript.core.** { *; }

# Keep exception classes
-keep class com.youtubetranscript.TranscriptException { *; }

# Keep the main API class
-keep class com.youtubetranscript.YouTubeTranscriptApi { *; }
