package me.shadymitsu.cobblemonshinydays.util

import me.shadymitsu.cobblemonshinydays.broadcast.BroadcastManager
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents

object ShutdownHandler {

    fun registerShutdownListener() {
        ServerLifecycleEvents.SERVER_STOPPING.register { _ ->
            println("CobblemonShinyDays: Server is stopping, shutting down scheduler...")
            BroadcastManager.shutdown()
        }
    }
}
