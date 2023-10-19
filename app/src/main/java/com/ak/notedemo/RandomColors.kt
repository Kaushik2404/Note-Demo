package com.ak.notedemo

import java.util.Arrays
import java.util.Collections
import java.util.Stack

class RandomColors {


    private val recycle: Stack<Int> = Stack()
    private val colors:Stack<Int> = Stack()
    init {
        recycle.addAll(
            Arrays.asList(
               R.color.themeok1,
               R.color.themeok2,
               R.color.themeok3,
               R.color.themeok4,
               R.color.themeok5
            )
        )
    }

    fun getColor(): Int {
        if (colors.size == 0)
            while (!recycle.isEmpty()) colors.push(recycle.pop())
        Collections.shuffle(colors)
        val c = colors.pop()
        recycle.push(c)
        return c
    }
}