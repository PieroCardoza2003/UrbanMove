package com.capstone.urbanmove.utils

import kotlin.random.Random

class RandomColor {

    companion object {
        fun getColor(): Int {
            val random = Random(System.currentTimeMillis())
            return 0xff000000.toInt() or random.nextInt(0x00ffffff)
        }
    }

}