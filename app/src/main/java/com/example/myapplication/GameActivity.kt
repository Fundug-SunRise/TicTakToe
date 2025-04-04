package com.example.myapplication

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.models.Slot
import com.example.myapplication.models.Types
import com.example.myapplication.models.BackEndGame
import com.example.myapplication.models.Bot
import com.example.myapplication.models.MusicController
import com.example.myapplication.models.SharedPreferencesSaves
import kotlinx.coroutines.sync.Mutex

class GameActivity : AppCompatActivity() {

    private lateinit var matrixs: Map<Int, Slot>     //Map это словарь (кто его так назвал?)
    private lateinit var whoMoving: Types
    private lateinit var movingIc: ImageView
    private lateinit var backEndGame: BackEndGame
    private var pve: Boolean = false

    private var end: Boolean = false
    private lateinit var bot: Bot

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        movingIc = findViewById<ImageView>(R.id.whomoving)

        pve = intent.getBooleanExtra("ITS_PVP", false)

        movingIc.setImageResource(R.drawable.o_icon_bold)
        whoMoving = Types.O

        initMatrix()

        end = false
        backEndGame = BackEndGame(matrixs,whoMoving,movingIc,SharedPreferencesSaves.loadGame(pve))

        backEndGame.applyLoad()
        bot = Bot(backEndGame)

        MusicController.init(this)
        MusicController.startMusic()


        endGame()

    }

    fun showGameDialog(winner: Int) {
        val dialog = Dialog(this).apply {

            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(false)


            setContentView(R.layout.dialog_win)

            findViewById<Button>(R.id.gotoRestart).setOnClickListener {
                val intent = Intent(this@GameActivity, GameActivity::class.java).apply {
                    putExtra("ITS_PVP", pve)
                }
                startActivity(intent)
                dismiss()
            }

            findViewById<Button>(R.id.gotoMenu).setOnClickListener {

                startActivity(Intent(this@GameActivity, MainActivity::class.java))
                dismiss()
            }

            val winnerIco = findViewById<ImageView>(R.id.winnerDialog)
            when(winner){
                1 -> winnerIco.setImageResource(R.drawable.o_icon_bold)
                2 -> winnerIco.setImageResource(R.drawable.x_icon_bold)
                else -> {
                    winnerIco.setImageResource(R.drawable.never)
                    findViewById<TextView>(R.id.moving).text = "Draw"
                }
            }

        }

        dialog.show()

    }

    private fun initMatrix(){
        matrixs = mapOf(
            R.id.slot0 to Slot(findViewById<ImageView>(R.id.slot0), 0,0), R.id.slot1 to Slot(findViewById<ImageView>(R.id.slot1), 1,0), R.id.slot2 to Slot(findViewById<ImageView>(R.id.slot2), 2,0),
            R.id.slot3 to Slot(findViewById<ImageView>(R.id.slot3), 0,1), R.id.slot4 to Slot(findViewById<ImageView>(R.id.slot4), 1,1), R.id.slot5 to Slot(findViewById<ImageView>(R.id.slot5), 2,1),
            R.id.slot6 to Slot(findViewById<ImageView>(R.id.slot6), 0,2), R.id.slot7 to Slot(findViewById<ImageView>(R.id.slot7), 1,2), R.id.slot8 to Slot(findViewById<ImageView>(R.id.slot8), 2,2)
        )
    }

    fun clickSlot(view: View){
        var slot = matrixs[view.id]

        if(slot?.getTypeSlot() == Types.None){ //Короче чтоб не забыть slot? это сокращения от slot == null иначе компилятор ругается

            slot.setTypeSlot(backEndGame.whoMoving)
            backEndGame.addToTable(slot)

            backEndGame.chageMoving()

            endGame()
            if(pve && !end){
                bot.moving(SharedPreferencesSaves.loadDifficulty())
            }
            endGame()
        }

        //Shift + Alt + стрелочки перемещать код

    }

    fun endGame(){
        var winner = backEndGame.checkWinner()

        if(winner != 0 || !backEndGame.hasZero()){
            end = true
            showGameDialog(winner)
            SharedPreferencesSaves.clearGame(pve)
            MusicController.stopMusic()
            return
        }else{
            SharedPreferencesSaves.saveGame(backEndGame.board,pve)
        }
    }

    fun backButton(view: View){
        startActivity(Intent(this, MainActivity::class.java))
        MusicController.stopMusic()
    }

    override fun onPause() {
        super.onPause()
        MusicController.stopMusic()
    }

}