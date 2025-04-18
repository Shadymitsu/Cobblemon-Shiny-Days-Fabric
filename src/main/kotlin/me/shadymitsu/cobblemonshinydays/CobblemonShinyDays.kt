package me.shadymitsu.cobblemonshinydays

import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.api.events.pokemon.ShinyChanceCalculationEvent
import me.shadymitsu.cobblemonshinydays.broadcast.BroadcastManager
import me.shadymitsu.cobblemonshinydays.config.ConfigLoader
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import java.time.LocalDateTime

class CobblemonShinyDays : ModInitializer {
    override fun onInitialize() {
        println("Cobblemon Shiny Days mod loaded!")

        val config = ConfigLoader.loadConfig()
        println("Cobblemon Shiny Days config loaded with ${config.size} time block(s).")

        CobblemonEvents.SHINY_CHANCE_CALCULATION.subscribe { event ->
            handleShinyChanceCalculation(event)
        }

        BroadcastManager.startBroadcasting()

        ServerLifecycleEvents.SERVER_STOPPING.register {
            println("Cobblemon Shiny Days: Server is stopping, shutting down...")
            BroadcastManager.shutdown()
        }
    }

    private fun handleShinyChanceCalculation(event: ShinyChanceCalculationEvent) {
        val day = LocalDateTime.now().dayOfWeek.name
        val speciesName = event.pokemon.species.name

        val config = ConfigLoader.loadConfig()
        val multiplier = config.firstOrNull {
            (it.species.contains("ALL") ||
                    it.species.any { s -> s.equals(speciesName, ignoreCase = true) }) &&
                    it.days.any { configDay -> configDay.equals(day, ignoreCase = true) }
        }?.multiplier

        if (multiplier != null) {
            event.addModificationFunction { base, _, _ ->
                base / multiplier }
        }
    }
}

