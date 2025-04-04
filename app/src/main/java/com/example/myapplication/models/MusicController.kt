package com.example.myapplication.models

import android.content.Context
import android.health.connect.datatypes.units.Volume
import android.media.MediaPlayer
import com.example.myapplication.R.raw.ultrakill_ost

object MusicController {

    lateinit var mediaPlayer: MediaPlayer
    const val maxVolume: Float = 100f

    fun init(context: Context){
        mediaPlayer = MediaPlayer.create(context, ultrakill_ost)
        var volume: Float = SharedPreferencesSaves.loadVolume() / maxVolume
        mediaPlayer.setVolume(volume,volume)
    }

    fun startMusic(){
        mediaPlayer.start()
    }

    fun stopMusic(){
        mediaPlayer.stop()
    }

    fun chageVolume(volume: Int){
        var floatVolume = volume/ maxVolume
        mediaPlayer.setVolume(floatVolume,floatVolume)
    }
}