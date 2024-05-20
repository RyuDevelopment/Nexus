package com.starlight.nexus.util

/*
    * Author: T4yrn © 2024
    * Project: nexus
    * Date: 20/2/2024 - 12:58
*/

object StringUtil {

    @JvmStatic
    fun join(array: Array<String>,joiner: String):String {
        return this.join(array,joiner,"and ")
    }

    @JvmStatic
    fun join(array: Array<String>,joiner: String,lastWord: String):String {

        val toReturn = StringBuilder()

        for (it in array.withIndex()) {
            toReturn.append("${if (it.index != 0) if (it.index == array.lastIndex) " $lastWord" else joiner else ""}${it.value}")
        }

        return toReturn.toString()
    }

    @JvmStatic
    fun repeat(value: String,amount: Int):String {

        var toReturn = ""

        for (i in 0..amount) {
            toReturn += value
        }

        return toReturn
    }

    @JvmStatic
    fun capitalize(word: String):String {

        if (word.isEmpty()) {
            return word
        }

        return "${word[0].titlecaseChar()}${word.substring(1)}"
    }

    @JvmStatic
    fun pluralize(word: String,amount: Int):String {

        if (amount == 1) {
            return word
        }

        return "$word${if (word[word.lastIndex] == 's') "'" else "s"}"
    }

}