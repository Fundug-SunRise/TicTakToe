package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.models.SharedPreferencesSaves


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        SharedPreferencesSaves.init(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun playGame(view: View) {
        var it_pve = false

        if(view.id == R.id.pve){
            it_pve = true
        }

        var intent: Intent = Intent(this, GameActivity::class.java).apply {
            putExtra("ITS_PVP", it_pve)
        }

        startActivity(intent)
    }

    fun settingMenu(view: View) {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    fun backButton(view: View){
        startActivity(Intent(this, MainActivity::class.java))
    }


}