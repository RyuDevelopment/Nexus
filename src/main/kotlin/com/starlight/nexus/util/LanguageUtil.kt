package com.starlight.nexus.util

/*
    * Author: T4yrn © 2024
    * Project: nexus
    * Date: 20/2/2024 - 12:58
*/

object LanguageUtil {

    val STANDARD = "abcdefghijklmnopqrstuvwxyz".toCharArray()
    val STANDARD_GALACTIC = mutableMapOf(
        'a' to "ᔑ",
        'b' to "ʖ",
        'c' to "ᓵ",
        'd' to "↸",
        'e' to "ᒷ",
        'f' to "⎓",
        'g' to "⊣",
        'h' to "⍑",
        'i' to "╎",
        'j' to "⋮",
        'k' to "ꖌ",
        'l' to "ꖎ",
        'm' to "ᒲ",
        'n' to "リ",
        'o' to "フ",
        'p' to "!¡",
        'q' to "ᑑ",
        'r' to "∷",
        's' to "ᓭ",
        't' to "ℸ ̣",
        'u' to "⚍",
        'v' to "⍊",
        'w' to "∴",
        'x' to "̇/",
        'y' to "׀׀",
        'z' to "⨅",
        '.' to "·-·"
    )

    private val MINECRAFT_COLOR_CHARS = arrayOf(
        '&',
        '§'
    )

    fun fromLatinToGalactic(text: String,ignoreMinecraftColorChar: Boolean = false):String {
        return buildString {

            for ((index,char) in text.toCharArray().withIndex()) {

                if (STANDARD_GALACTIC.containsKey(char)) {

                    if (ignoreMinecraftColorChar && index > 0 && MINECRAFT_COLOR_CHARS.contains(text[index - 1])) {
                        continue
                    }

                    append(STANDARD_GALACTIC[char]!!)
                }

            }

        }
    }
}