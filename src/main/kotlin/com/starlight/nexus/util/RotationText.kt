package com.starlight.nexus.util

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class RotationText(
    private val plugin: JavaPlugin
) {

    fun getRotationText(values: MutableList<String>, delay: Int) {
        rotateText(values, delay)
    }

    private fun rotateText(values: MutableList<String>, delay: Int): String {
        var currentIndex = 0
        var currentText = values[currentIndex]

        val animationTask = object : BukkitRunnable() {
            override fun run() {
                currentIndex = (currentIndex + 1) % values.size
                currentText = values[currentIndex]
            }
        }

        animationTask.runTaskTimerAsynchronously(plugin, 0, delay * 20L)

        return currentText
    }

}