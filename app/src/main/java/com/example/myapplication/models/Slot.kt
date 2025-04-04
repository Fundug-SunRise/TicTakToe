package com.example.myapplication.models

import com.example.myapplication.R
import android.widget.ImageView

class Slot() {
    lateinit var image: ImageView
    lateinit var type: Types

    var posX: Int = 0
    var posY: Int = 0

    constructor(_image: ImageView, x: Int, y: Int) : this() {
        image = _image
        type = Types.None

        posY = y
        posX = x
    }

    constructor (_image: ImageView, _type: Types) : this() {
        image = _image
        type = _type
    }

    fun getTypeSlot(): Types{
        return type
    }

    fun getPositionSlot(): Pair<Int, Int>{
        return Pair(posX,posY)
    }

    fun setTypeSlot(_type: Types){
        type = _type

        when(_type){
            Types.X -> image.setImageResource(R.drawable.x_icon_bold)
            Types.O -> image.setImageResource(R.drawable.o_icon_bold)
            Types.None -> image.setImageResource(R.drawable.never)
        }
    }

}