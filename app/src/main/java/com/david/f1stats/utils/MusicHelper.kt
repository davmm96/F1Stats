package com.david.f1stats.utils

import android.content.Context
import android.media.MediaPlayer
import com.david.f1stats.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MusicHelper @Inject constructor(@ApplicationContext private val context: Context) {

    private val mediaPlayer: MediaPlayer = MediaPlayer
        .create(context, R.raw.f1theme)
        .apply { isLooping = true }

    fun playMusic() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.seekTo(0)
            mediaPlayer.start()
        }
    }

    fun pauseMusic() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }
}
