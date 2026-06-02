package me.shadymitsu.cobblemonshinydays.util

import net.minecraft.network.chat.Component

object MessageUtils {
    fun joinComponents(components: List<Component>, conjunctionKey: String): Component {
        if (components.isEmpty()) return Component.empty()
        if (components.size == 1) return components[0]

        val result = Component.literal("")
        for (i in 0 until components.size - 2) {
            result.append(components[i]).append(Component.literal("§e, "))
        }
        result.append(components[components.size - 2])
        result.append(Component.literal(" §e")).append(Component.translatable(conjunctionKey)).append(Component.literal(" "))
        result.append(components.last())
        return result
    }
}
