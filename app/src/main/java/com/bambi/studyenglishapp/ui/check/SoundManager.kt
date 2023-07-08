package com.bambi.studyenglishapp.ui.check

import android.content.Context
import android.media.MediaPlayer

class SoundManager(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null

    fun playSound(soundResId: Int) {
        //MediaPlayerの初期化
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context, soundResId)

        //音声の再生
        mediaPlayer?.start()

        //音声再生完了時の処理
        mediaPlayer?.setOnCompletionListener {
            // MediaPlayerのリソースを解放
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }
}
