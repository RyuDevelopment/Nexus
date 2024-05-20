package com.starlight.nexus.util

/*
    * Author: T4yrn © 2024
    * Project: nexus
    * Date: 27/2/2024 - 16:00
*/

class AnimatedText(
    private val values: List<String>
) : Runnable {

    private var textIndex: Int = 0
    private var letterIndex: Int = 0
    private var appendAt: Long? = null
    private var pausedAt: Long? = null
    private var rendered: String = ""
    private var typerInterval: Long = 100L
    private var typerPause: Long = 2000L

    override fun run() {

        if (pausedAt == null) {
            val currentText = values[textIndex]

            if (appendAt == null || System.currentTimeMillis() >= appendAt!! + typerInterval) {
                rendered += currentText[++letterIndex]
                appendAt = System.currentTimeMillis()

                if (letterIndex >= currentText.length - 1) {
                    pausedAt = System.currentTimeMillis()
                }
            }
        } else {
            if (System.currentTimeMillis() >= pausedAt!! + typerPause) {
                letterIndex = 0
                pausedAt = null
                rendered = ""

                if (textIndex++ >= values.size - 1) {
                    textIndex = 0
                }
            }
        }
    }

    fun getAnimatedText(): String {
        return rendered
    }

}