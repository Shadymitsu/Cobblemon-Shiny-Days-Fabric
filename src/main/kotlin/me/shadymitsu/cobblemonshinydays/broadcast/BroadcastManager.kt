package me.shadymitsu.cobblemonshinydays.broadcast

import com.cobblemon.mod.common.util.server
import me.shadymitsu.cobblemonshinydays.config.ConfigLoader
import net.minecraft.network.chat.Component
import java.time.LocalDateTime
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object BroadcastManager {

    private val scheduler = Executors.newSingleThreadScheduledExecutor()

    fun startBroadcasting() {
        val config = ConfigLoader.loadConfig()

        val groupedByInterval = config
            .filter { it.broadcastInterval != null }
            .groupBy { it.broadcastInterval }

        for ((intervalSeconds, entries) in groupedByInterval) {
            scheduler.scheduleAtFixedRate({
                val now = LocalDateTime.now()
                val currentDay = now.dayOfWeek.name

                entries.forEach { entry ->
                    // Handle case-insensitive day matching
                    val isActiveDay = entry.days.any { it.equals(currentDay, ignoreCase = true) }

                    if (isActiveDay) {
                        // For species, handle comma separation and "and" between last two species
                        val speciesText = when {
                            entry.species.any { it.equals("ALL", ignoreCase = true) } -> "all Pokémon"
                            entry.species.size > 1 -> {
                                val lastPokemon = entry.species.last()
                                val allButLast = entry.species.dropLast(1).joinToString(", ")
                                "$allButLast and $lastPokemon"
                            }
                            else -> entry.species.first() // If only one species is listed
                        }

                        val message = "§eToday is a §6Shiny Day! §5$speciesText §ehave increased shiny rates!"
                        broadcastToServer(message)
                    }
                }
            }, 0, intervalSeconds!!.toLong(), TimeUnit.SECONDS)
        }
    }

    private fun broadcastToServer(message: String) {
        val component = Component.literal(message)
        server()?.playerList?.players?.forEach { player ->
            player.sendSystemMessage(component)
        }
    }

    fun shutdown() {
        scheduler.shutdownNow()
        println("Cobblemon Shiny Days: Broadcast scheduler shut down successfully.")
    }

}