package me.shadymitsu.cobblemonshinydays.broadcast

import com.cobblemon.mod.common.util.server
import me.shadymitsu.cobblemonshinydays.config.ConfigLoader
import me.shadymitsu.cobblemonshinydays.util.MessageUtils
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
                    val isActiveDay = entry.days.any { it.equals(currentDay, ignoreCase = true) }

                    if (isActiveDay) {
                        val hasAllSpecies = entry.species.any { it.equals("ALL", ignoreCase = true) }

                        val speciesFormatted = entry.species
                            .filterNot { it.equals("ALL", ignoreCase = true) }
                            .map { Component.literal("§5$it") }

                        val labelFormatted = entry.labels
                            .map { Component.literal("§5").append(Component.translatable("cobblemonshinydays.label.${it.lowercase()}")) }

                        val typeFormatted = entry.types
                            .map { Component.literal("§5").append(Component.translatable("cobblemonshinydays.type.${it.lowercase()}")) }

                        val allTerms = speciesFormatted + labelFormatted + typeFormatted
                        val joined = MessageUtils.joinComponents(allTerms, "cobblemonshinydays.text.or")

                        val message = if (hasAllSpecies) {
                            Component.translatable("cobblemonshinydays.broadcast.all_species")
                        } else {
                            Component.translatable("cobblemonshinydays.broadcast.specific_species", joined)
                        }

                        broadcastToServer(message)
                    }
                }
            }, 0, intervalSeconds!!.toLong(), TimeUnit.SECONDS)
        }
    }

    private fun broadcastToServer(message: Component) {
        server()?.playerList?.players?.forEach { player ->
            player.sendSystemMessage(message)
        }
    }

    fun shutdown() {
        scheduler.shutdownNow()
        println("Cobblemon Shiny Days: El programador de anuncios se ha cerrado correctamente.")
    }
}
