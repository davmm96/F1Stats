package com.david.f1stats.utils

import android.content.Context
import android.media.MediaPlayer
import com.david.f1stats.R

class MusicHelper(context: Context) {

    private val mediaPlayer: MediaPlayer = MediaPlayer.create(context, R.raw.f1theme)

    fun playMusic() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

    fun pauseMusic() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }
}
