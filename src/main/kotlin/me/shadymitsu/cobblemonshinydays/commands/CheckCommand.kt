package me.shadymitsu.cobblemonshinydays.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import me.shadymitsu.cobblemonshinydays.config.ConfigLoader
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import java.time.LocalDateTime

object CheckCommand {

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

    fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        dispatcher.register(
            Commands.literal("shinyday")
                .executes { context -> sendShinyDayInfo(context) }
        )
    }

    private fun sendShinyDayInfo(context: CommandContext<CommandSourceStack>): Int {
        val day = LocalDateTime.now().dayOfWeek.name
        val config = ConfigLoader.loadConfig()

        val entriesToday = config.filter { entry ->
            entry.days.any { it.equals(day, ignoreCase = true) }
        }

        if (entriesToday.isEmpty()) {
            context.source.sendSystemMessage(Component.literal("§eThere are no shiny rate boosts today."))
            return 1
        }

        entriesToday.forEach { entry ->
            val speciesFormatted = entry.species
                .filterNot { it.equals("ALL", ignoreCase = true) }
                .map { it }

            val labelsFormatted = entry.labels.mapNotNull {
                labelDisplayNames[it.lowercase()] ?: it
            }

            val typesFormatted = entry.types.mapNotNull {
                typeDisplayNames[it.lowercase()] ?: it
            }

            val combined = speciesFormatted + labelsFormatted + typesFormatted

            val joined = when (combined.size) {
                0 -> ""
                1 -> combined[0]
                2 -> "${combined[0]} and ${combined[1]}"
                else -> combined.dropLast(1).joinToString(", ") + " and ${combined.last()}"
            }

            val msg = if (joined.isNotEmpty()) {
                "§e$joined Pokémon currently have §6${entry.multiplier}x §eshiny rates!"
            } else {
                "§ePokémon currently have §6${entry.multiplier}x §eshiny rates!"
            }

            context.source.sendSystemMessage(Component.literal(msg))
        }

        return 1
    }
}
