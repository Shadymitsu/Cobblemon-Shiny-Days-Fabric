package me.shadymitsu.cobblemonshinydays.config

import com.google.gson.GsonBuilder
import java.io.File
import java.io.FileWriter

object ConfigCreator {

    private val configFile = File("config/cobblemonshinydays/config.json")

    fun createDefaultConfig() {
        val defaultConfig = listOf(
            ConfigFields(
                species = listOf("ALL"),
                days = listOf("Saturday", "Sunday"),
                multiplier = 1.0f,
                broadcastInterval = 60 // Set default interval to 60 seconds (1 minute)
            )
        )
        saveConfig(defaultConfig)
    }

    private fun saveConfig(config: List<ConfigFields>) {
        val gson = GsonBuilder().setPrettyPrinting().create()
        configFile.parentFile.mkdirs()
        FileWriter(configFile).use { writer ->
            gson.toJson(config, writer)
        }
    }

    fun getDefaultConfig(): List<ConfigFields> {
        return listOf(
            ConfigFields(
                species = listOf("ALL"),
                days = listOf("Saturday", "Sunday"),
                multiplier = 1.0f,
                broadcastInterval = 60 // Default interval for broadcast (1 minute)
            )
        )
    }
}