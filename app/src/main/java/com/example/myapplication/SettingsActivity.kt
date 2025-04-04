package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.models.SharedPreferencesSaves

class SettingsActivity : AppCompatActivity() {

    var diffInt: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        diffInt = SharedPreferencesSaves.loadDifficultyInt()
        changeText()

        val seekBarVolume = findViewById<SeekBar>(R.id.soundSeekBar)
        seekBarVolume.progress = SharedPreferencesSaves.loadVolume()
        seekBarVolume.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                SharedPreferencesSaves.saveVolume(progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    fun backButton(view: View){
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun clickChangeDifficulty(view: View) {

        diffInt += when(view.id){
            R.id.arrowLeft -> -1
            R.id.arrowRight -> 1
            else -> 0
        }

        diffInt = clamp(diffInt, 1,3)
        Log.d("L","diffInt ${diffInt}")
        SharedPreferencesSaves.saveDifficulty(diffInt)
        changeText()
    }

    private fun changeText(){
        var difficultyTextChage = findViewById<TextView>(R.id.difficultyTextChage)

        when(diffInt){
            1 -> difficultyTextChage.text = "Easy"
            2 -> difficultyTextChage.text = "Normal"
            3 -> difficultyTextChage.text = "Hard"
        }
    }

    private fun clamp(value: Int, min:Int, max:Int): Int{
        return if(value > max){
            1
        }else if(value < min){
            3
        } else {
             value
        }
    }
}