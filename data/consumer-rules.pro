# ── Gson (used by Retrofit converter-gson) ──────────────────────────
# Keep all data-model fields so Gson reflection works
-keep class com.david.f1stats.data.model.** { *; }

# Keep @SerializedName annotations
-keepclassmembers,allowobfuscation class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# ── Retrofit ────────────────────────────────────────────────────────
# Keep Retrofit service interfaces (methods are resolved via reflection)
-keep,allowobfuscation interface com.david.f1stats.data.source.network.** { *; }

# ── Room ────────────────────────────────────────────────────────────
# Room entities and DAOs are handled by Room's own keep rules,
# but keep the entity class names for safety
-keep class com.david.f1stats.data.model.favoriteRace.FavoriteRace { *; }
-keep interface com.david.f1stats.data.source.local.RaceDao { *; }
