# ── Stack traces ────────────────────────────────────────────────────
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# ── Kotlin ──────────────────────────────────────────────────────────
-dontwarn kotlin.**
-dontwarn kotlinx.**

# ── OkHttp / Okio ──────────────────────────────────────────────────
-dontwarn okhttp3.**
-dontwarn okio.**

# ── Gson ────────────────────────────────────────────────────────────
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.** { *; }

# ── Retrofit ────────────────────────────────────────────────────────
-keepattributes Exceptions
-dontwarn retrofit2.**
