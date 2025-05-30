package me.shadymitsu.cobblemonshinydays

import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.api.events.pokemon.ShinyChanceCalculationEvent
import me.shadymitsu.cobblemonshinydays.broadcast.BroadcastManager
import me.shadymitsu.cobblemonshinydays.commands.CheckCommand
import me.shadymitsu.cobblemonshinydays.config.ConfigLoader
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import java.time.LocalDateTime

class CobblemonShinyDays : ModInitializer {
    override fun onInitialize() {
        println("Cobblemon Shiny Days mod loaded!")

        val config = ConfigLoader.loadConfig()
        println("Cobblemon Shiny Days config loaded with ${config.size} time block(s).")

        // Register shiny chance logic
        CobblemonEvents.SHINY_CHANCE_CALCULATION.subscribe { event ->
            handleShinyChanceCalculation(event)
        }

        // Start broadcasting
        BroadcastManager.startBroadcasting()

        // Shutdown hook
        ServerLifecycleEvents.SERVER_STOPPING.register {
            println("Cobblemon Shiny Days: Server is stopping, shutting down...")
            BroadcastManager.shutdown()
        }

        // Register /shinyday command
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            CheckCommand.register(dispatcher)
        }
    }

    private fun handleShinyChanceCalculation(event: ShinyChanceCalculationEvent) {
        val day = LocalDateTime.now().dayOfWeek.name
        val speciesName = event.pokemon.species.name
        val pokemonTypes = event.pokemon.types.map { it.name.lowercase() }
        val config = ConfigLoader.loadConfig()

        val multiplier = config.firstOrNull {
            val matchesSpecies = it.species.any { s -> s.equals("ALL", ignoreCase = true) } ||
                    it.species.any { s -> s.equals(speciesName, ignoreCase = true) }

            val matchesLabels = it.labels.any { label -> event.pokemon.hasLabels(label) }

            val matchesTypes = it.types.any { type -> pokemonTypes.contains(type.lowercase()) }

            val matchesDay = it.days.any { configDay -> configDay.equals(day, ignoreCase = true) }

            (matchesSpecies && matchesDay) || (matchesLabels && matchesDay) || (matchesTypes && matchesDay)
        }?.multiplier

        if (multiplier != null) {
            event.addModificationFunction { base, _, _ -> base / multiplier }
        }
    }

}
