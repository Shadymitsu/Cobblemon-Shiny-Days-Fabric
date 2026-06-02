package me.shadymitsu.cobblemonshinydays.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import me.shadymitsu.cobblemonshinydays.config.ConfigLoader
import me.shadymitsu.cobblemonshinydays.util.MessageUtils
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import java.time.LocalDateTime

object CheckCommand {

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
            context.source.sendSystemMessage(Component.translatable("cobblemonshinydays.command.no_boosts"))
            return 1
        }

        entriesToday.forEach { entry ->
            val multiplierText = entry.multiplier.toString()

            val speciesFormatted = entry.species
                .filterNot { it.equals("ALL", ignoreCase = true) }
                .map { Component.literal("§5$it") }

            val labelsFormatted = entry.labels.map {
                Component.literal("§5").append(Component.translatable("cobblemonshinydays.label.${it.lowercase()}"))
            }

            val typesFormatted = entry.types.map {
                Component.literal("§5").append(Component.translatable("cobblemonshinydays.type.${it.lowercase()}"))
            }

            val combined = speciesFormatted + labelsFormatted + typesFormatted

            val joined = MessageUtils.joinComponents(combined, "cobblemonshinydays.text.and")

            val multiplierComponent = Component.literal("§6${multiplierText}x")

            val msg = if (combined.isNotEmpty()) {
                Component.translatable("cobblemonshinydays.command.boost_with_species", joined, multiplierComponent)
            } else {
                Component.translatable("cobblemonshinydays.command.boost_no_species", multiplierComponent)
            }

            context.source.sendSystemMessage(msg)
        }

        return 1
    }
}
