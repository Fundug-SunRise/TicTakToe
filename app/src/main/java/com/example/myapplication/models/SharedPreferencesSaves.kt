package com.example.myapplication.models

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesSaves{
    private lateinit var sharedPref: SharedPreferences
    private const val NAME: String = "prefs_Bd"

    fun init(context: Context) {
        sharedPref = context.getSharedPreferences(
            NAME,
            Context.MODE_PRIVATE
        )
    }

    private const val DIFF: String = "difficulty"
    private const val VOLUME: String = "volume"

    fun saveDifficulty(_difficulty: difficulty){
        var diff: Int = when(_difficulty){
            difficulty.easy -> 1
            difficulty.normal -> 2
            difficulty.hard ->  3
            else -> 1
        }

        sharedPref.edit().putInt(DIFF,diff).apply()
    }
    fun saveDifficulty(_difficulty: Int){
        sharedPref.edit().putInt(DIFF,_difficulty).apply()
    }
    fun loadDifficulty(): difficulty{
        return when(sharedPref.getInt(DIFF, 1)){
            1 -> difficulty.easy
            2 -> difficulty.normal
            3 -> difficulty.hard
            else -> difficulty.easy
        }
    }
    fun loadDifficultyInt(): Int{
        return sharedPref.getInt(DIFF, 1)
    }

    fun saveVolume(strange: Int){
        sharedPref.edit().putInt(VOLUME,strange).apply()
    }

    fun loadVolume(): Int{
        return sharedPref.getInt(VOLUME, 20)
    }



    fun saveGame(board: Array<Array<Int>>, pve: Boolean){
        sharedPref.edit().putString(getKey(pve), array2DToString(board)).apply()
    }

    fun loadGame(pve: Boolean): Array<Array<Int>>{
        return stringTo2DArray(sharedPref.getString(getKey(pve), "").toString())
    }

    fun clearGame(pve: Boolean){
        sharedPref.edit().remove(getKey(pve)).apply()
    }

    private fun getKey(pve: Boolean): String{
        return if (pve == true){
            "pve"
        }else{
            "pvp"
        }
    }

    private fun array2DToString(array: Array<Array<Int>>): String {
        return array.joinToString(";") {
            lamb ->
            lamb.joinToString(",")
        }
    }

    private fun stringTo2DArray(str: String): Array<Array<Int>> {
        return (if (str.isEmpty()) {
            arrayOf(
                arrayOf(0,0,0),
                arrayOf(0,0,0),
                arrayOf(0,0,0)
            )
        } else {
            str.split(";")
                .map { lamb ->
                    lamb.split(",")
                        .map { it.toInt() }
                        .toTypedArray()
                }
                .toTypedArray()
        })
    }

}