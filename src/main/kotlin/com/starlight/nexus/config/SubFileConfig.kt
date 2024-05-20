package com.starlight.nexus.config

import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

/*
    * Author: T4yrn © 2024
    * Project: nexus
    * Date: 29/2/2024 - 18:17
*/

class SubFileConfig(plugin: JavaPlugin, folderName: String, fileName: String) {

    private val file: File
    val config: FileConfiguration

    init {
        val folder = File(plugin.dataFolder, folderName)
        if (!folder.exists()) {
            folder.mkdirs()
        }

        file = File(folder, fileName)
        if (!file.exists()) {
            if (plugin.getResource("$folderName/$fileName") == null) {
                try {
                    file.createNewFile()
                } catch (e: IOException) {
                    plugin.logger.severe("Failed to create new file $fileName")
                }
            } else {
                plugin.saveResource("$folderName/$fileName", false)
            }
        }
        config = YamlConfiguration.loadConfiguration(file)
    }

    fun save() {
        try {
            config.save(file)
        } catch (e: IOException) {
            Bukkit.getLogger().severe("Could not save config file $file")
            e.printStackTrace()
        }
    }
}