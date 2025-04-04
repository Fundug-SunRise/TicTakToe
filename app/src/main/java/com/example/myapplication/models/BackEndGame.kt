package com.example.myapplication.models

import android.util.Log
import android.widget.ImageView
import com.example.myapplication.R

class BackEndGame {

    var matrixs: Map<Int, Slot>
    var whoMoving: Types
    var movingIc: ImageView

    var board: Array<Array<Int>>

    constructor(_matrixs: Map<Int, Slot>, _whoMoving: Types, _movingIc: ImageView, _board: Array<Array<Int>>){
        matrixs = _matrixs
        whoMoving = _whoMoving
        movingIc = _movingIc
        board = _board
    }

    fun addToTable(slot: Slot){
        var maskNum: Int = when (whoMoving) {
            Types.O -> 1
            Types.X -> 2
            else -> 0
        }
        board[slot.posX][slot.posY] = maskNum

        board.forEach { row ->
            Log.d("L","${row.contentToString()}")
        }
    }
    fun applyLoad(){
        matrixs.values.forEach { slot ->
            when(board[slot.posX][slot.posY]){
                1 -> slot.setTypeSlot(Types.O)
                2 -> slot.setTypeSlot(Types.X)
                else -> slot.setTypeSlot(Types.None)
            }
        }
    }

    fun hasZero(): Boolean {
        board.forEach {
            arr-> arr.forEach { slot ->
            if(arr[slot] == 0){
                return true
            }
        }
        }
        return false
    }

    var step:Int = 0
    fun checkStep(): Boolean{
        step++

        return if(step >= 9){
            true
        }else{
            false
        }
    }

    fun chageMoving(){
        when(whoMoving) {
            Types.O -> {
                whoMoving = Types.X
                movingIc.setImageResource(R.drawable.x_icon_bold)
            }

            Types.X -> {
                whoMoving = Types.O
                movingIc.setImageResource(R.drawable.o_icon_bold)
            }

            else -> {
                whoMoving = Types.None
                movingIc.setImageResource(R.drawable.never)
            }
        }

        Log.d("L","resours = ${movingIc.resources}, type = ${whoMoving}")

    }

    fun checkWinner(): Int {
        val lines = listOf( //Этот масив для проверки типо как маска или хз че

            //Это горизонталь снизу
            listOf(Pair(0, 0), Pair(0, 1), Pair(0, 2)),
            listOf(Pair(1, 0), Pair(1, 1), Pair(1, 2)),
            listOf(Pair(2, 0), Pair(2, 1), Pair(2, 2)),
            //Это Вертикаль
            listOf(Pair(0, 0), Pair(1, 0), Pair(2, 0)),
            listOf(Pair(0, 1), Pair(1, 1), Pair(2, 1)),
            listOf(Pair(0, 2), Pair(1, 2), Pair(2, 2)),
            //А это на искосок
            listOf(Pair(0, 0), Pair(1, 1), Pair(2, 2)),
            listOf(Pair(0, 2), Pair(1, 1), Pair(2, 0))
        )

        for (line in lines) {
            val (a, b, c) = line
            val val1 = board[a.first][a.second]
            val val2 = board[b.first][b.second]
            val val3 = board[c.first][c.second]

            if (val1 != 0 && val1 == val2 && val2 == val3) {
                return val1
            }

        }
        return 0
    }

}