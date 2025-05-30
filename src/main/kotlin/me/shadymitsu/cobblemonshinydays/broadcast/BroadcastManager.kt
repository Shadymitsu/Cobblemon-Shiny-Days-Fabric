package me.shadymitsu.cobblemonshinydays.broadcast

import com.cobblemon.mod.common.util.server
import me.shadymitsu.cobblemonshinydays.config.ConfigLoader
import net.minecraft.network.chat.Component
import java.time.LocalDateTime
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object BroadcastManager {

    private val scheduler = Executors.newSingleThreadScheduledExecutor()

    // Mapping of labels to nicely formatted names
    private val labelDisplayNames = mapOf(
        "legendary" to "Legendary",
        "restricted" to "Restricted",
        "mythical" to "Mythical",
        "ultra_beast" to "Ultra Beast",
        "fossil" to "Fossil",
        "powerhouse" to "Powerhouse",
        "baby" to "Baby",
        "regional" to "Regional",
        "kantonian_form" to "Kantonian Form",
        "johtonian_form" to "Johtonian Form",
        "hoennian_form" to "Hoennian Form",
        "sinnohan_form" to "Sinnohan Form",
        "unovan_form" to "Unovan Form",
        "kalosian_form" to "Kalosian Form",
        "alolan_form" to "Alolan Form",
        "galarian_form" to "Galarian Form",
        "hisuian_form" to "Hisuian Form",
        "paldean_form" to "Paldean Form",
        "mega" to "Mega",
        "primal" to "Primal",
        "gmax" to "Gigantamax",
        "totem" to "Totem",
        "paradox" to "Paradox",
        "gen1" to "Gen 1",
        "gen2" to "Gen 2",
        "gen3" to "Gen 3",
        "gen4" to "Gen 4",
        "gen5" to "Gen 5",
        "gen6" to "Gen 6",
        "gen7" to "Gen 7",
        "gen7b" to "Gen 7b",
        "gen8" to "Gen 8",
        "gen8a" to "Gen 8a",
        "gen9" to "Gen 9",
        "customized_official" to "Customized Official",
        "custom" to "Custom"
    )

    // Display names for elemental types
    private val typeDisplayNames = mapOf(
        "normal" to "Normal",
        "fire" to "Fire",
        "water" to "Water",
        "electric" to "Electric",
        "grass" to "Grass",
        "ice" to "Ice",
        "fighting" to "Fighting",
        "poison" to "Poison",
        "ground" to "Ground",
        "flying" to "Flying",
        "psychic" to "Psychic",
        "bug" to "Bug",
        "rock" to "Rock",
        "ghost" to "Ghost",
        "dragon" to "Dragon",
        "dark" to "Dark",
        "steel" to "Steel",
        "fairy" to "Fairy"
    )

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
                            .map { "§5$it" }

                        val labelFormatted = entry.labels
                            .mapNotNull { formatLabel(it)?.let { formatted -> "§5$formatted" } }

                        val typeFormatted = entry.types
                            .mapNotNull { formatType(it)?.let { formatted -> "§5$formatted" } }

                        val allTerms = speciesFormatted + labelFormatted + typeFormatted
                        val joined = when (allTerms.size) {
                            0 -> ""
                            1 -> allTerms[0]
                            2 -> "${allTerms[0]}§r or ${allTerms[1]}"
                            else -> allTerms.dropLast(1).joinToString("§r, ") + "§r or ${allTerms.last()}"
                        }

                        val message = if (hasAllSpecies) {
                            "§eToday is a §6Shiny Day! §eIf you're lucky, you may encounter a shiny Pokémon!"
                        } else {
                            "§eToday is a §6Shiny Day! §eIf you're lucky, you may encounter a shiny $joined §ePokémon!"
                        }

                        broadcastToServer(message)
                    }
                }
            }, 0, intervalSeconds!!.toLong(), TimeUnit.SECONDS)
        }
    }

    fun formatLabel(label: String): String? {
        return labelDisplayNames[label.lowercase()]
    }

    fun formatType(type: String): String? {
        return typeDisplayNames[type.lowercase()]
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
