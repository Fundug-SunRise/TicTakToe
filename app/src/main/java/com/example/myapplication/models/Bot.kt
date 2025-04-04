package com.example.myapplication.models

import android.util.Log
import java.text.DateFormatSymbols
import java.util.Timer
import kotlin.random.Random

class Bot {

    private var backEndGame: BackEndGame

    constructor(_backEndGame: BackEndGame) {
        backEndGame = _backEndGame
    }

    companion object{
        const val PLAYER_SYMB: Int = 1
        const val BOT_SYMB: Int = 2
        const val NEVER: Int = 0

        val corners = listOf(Pair(0, 0), Pair(0, 2), Pair(2, 0), Pair(2, 2))
        val edges = listOf(Pair(0, 1), Pair(1, 0), Pair(1, 2), Pair(2, 1))
    }

    fun moving(_difficulty: difficulty) {

        when (_difficulty) {
            difficulty.easy -> modeEasy()
            difficulty.normal -> modeMedium()
            difficulty.hard -> modeHard()
        }
    }

    private fun modeEasy() {
        makeStep(randomSlot())
    }

    private fun modeMedium(){
        var copyBoard = backEndGame.board.copyOf()
        makeStep(findMediumSlot(copyBoard))
    }
    private fun modeHard(){
        var copyBoard = backEndGame.board.copyOf()
        makeStep(findHardSlot(copyBoard))
    }

    private fun makeStep(slot: Slot){
        slot.setTypeSlot(Types.X)
        backEndGame.addToTable(slot)
        backEndGame.chageMoving()
    }

    private fun findMediumSlot(copyBoard: Array<Array<Int>>): Slot{
        //чек на блок игрока
        for(col in 0..2){
            for(cell in 0..2){
                if(copyBoard[col][cell] == NEVER){
                    copyBoard[col][cell] = PLAYER_SYMB
                    if(checkWin(copyBoard, PLAYER_SYMB)){
                        copyBoard[col][cell] = NEVER
                        return boardToSlot(col,cell)
                    }
                    copyBoard[col][cell] = NEVER
                }
            }
        }

        //чек углов
        corners.forEach {
            (col, cell) ->
            if(copyBoard[col][cell] == NEVER) return boardToSlot(col, cell)
        }

        //чек краев
        edges.forEach {
                (col, cell) ->
            if(copyBoard[col][cell] == NEVER) return boardToSlot(col, cell)
        }

        return randomSlot()
    }

    private fun findHardSlot(copyBoard: Array<Array<Int>>): Slot{
        //чек на победу бота
        for(col in 0..2){
            for(cell in 0..2){
                if(copyBoard[col][cell] == NEVER){
                    copyBoard[col][cell] = BOT_SYMB
                    if(checkWin(copyBoard, BOT_SYMB)){
                        copyBoard[col][cell] = NEVER
                        return boardToSlot(col,cell)
                    }
                    copyBoard[col][cell] = NEVER
                }
            }
        }

        //чек на блок игрока
        for(col in 0..2){
            for(cell in 0..2){
                if(copyBoard[col][cell] == NEVER){
                    copyBoard[col][cell] = PLAYER_SYMB
                    if(checkWin(copyBoard, PLAYER_SYMB)){
                        copyBoard[col][cell] = NEVER
                        return boardToSlot(col,cell)
                    }
                    copyBoard[col][cell] = NEVER
                }
            }
        }

        //чек центра
        if(copyBoard[1][1] == NEVER) return boardToSlot(1,1)

        //чек углов
        corners.forEach {
                (col, cell) ->
            if(copyBoard[col][cell] == NEVER) return boardToSlot(col, cell)
        }

        //чек краев
        edges.forEach {
                (col, cell) ->
            if(copyBoard[col][cell] == NEVER) return boardToSlot(col, cell)
        }

        return randomSlot()
    }

    private fun checkWin(board: Array<Array<Int>>, symbol: Int): Boolean {
        // Проверка строк и столбцов
        for (i in 0..2) {
            if (board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) return true
            if (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol) return true
        }
        // Диагонали
        if (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) return true
        if (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol) return true
        return false
    }

    private fun boardToSlot(x: Int, y: Int): Slot{
        backEndGame.matrixs.values.forEach {
            slot ->
            if (slot.getPositionSlot() == Pair(x,y)) return slot
        }
        return TODO()
    }

    private fun randomSlot(): Slot{
        var rand = Random(1)
        Log.d("L", "random bot ${0}")
        backEndGame.matrixs.values.forEach {
                slot->
            if(slot.getTypeSlot() == Types.None && rand.nextBoolean()){
                return slot
            }
        }
        return TODO()
    }
}