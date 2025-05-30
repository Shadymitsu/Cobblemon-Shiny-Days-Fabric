package me.shadymitsu.cobblemonshinydays.config

data class ConfigFields(

    val species: List<String>,
    val types: List<String>,
    val labels: List<String>,  // New field for labels
    val days: List<String>,
    val multiplier: Float,
    val broadcastInterval: Int? = null  // in seconds, optional

)
