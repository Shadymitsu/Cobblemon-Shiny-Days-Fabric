# Cobblemon Shiny Days (Localized Edition)

![Cobblemon Shiny Days](https://i.imgur.com/Mvl0Tkf.png)

Choose days of the week to increase shiny odds for either all Pokémon or specific Pokémon, giving your server events similar to Community Days in Pokémon GO.

**This fork implements server-side localization via Polymer**, meaning chat broadcasts and command outputs are no longer hardcoded. Players will dynamically see event announcements in their own language (English and Spanish supported out of the box) without needing to download external Resource Packs.

## Dependencies

* **Cobblemon** - [Modrinth](https://modrinth.com/mod/cobblemon/) / [Curseforge](https://www.curseforge.com/minecraft/mc-mods/cobblemon)
* **Fabric Language Kotlin** - [Modrinth](https://modrinth.com/mod/fabric-language-kotlin) / [Curseforge](https://www.curseforge.com/minecraft/mc-mods/fabric-language-kotlin)

## Features

* **Server-Side Localization:** Uses `Text.translatable()` and Polymer's virtual resource pack generation to translate broadcasts based on the player's client language settings.
* **Customizable Rates:** Choose which days to increase shiny rates.
* **Targeted Events:** Increase shiny odds for all spawns or only specific Pokémon, types, or labels.
* **Automated Announcements:** Broadcasts a localized message at a customizable interval to remind players what shiny rates are currently increased.

## Installation

1. Install the required dependencies on your Fabric server.
2. Place the compiled `.jar` file in your `/mods` folder.
3. Restart the server.

## Configuration

The configuration has 5 customizable options:

* **"species"**: Which Pokémon will have increased rates (can use "ALL" to increase odds of all Pokémon).
* **"labels"**: Pokémon with these labels will have their shiny rates increased ("legendary", "gen1", "gen2" etc.).
* **"types"**: Pokémon with these types will have their shiny rates increased ("water", "fire" etc.).
* **"days"**: Which days to apply the specified rates to the specified Pokémon.
* **"multiplier"**: How much to multiply odds by (uses the shiny rates in your Cobblemon config as a base rate, then adds this modifier). *e.g. Cobblemon shiny rate is 1/8192, a multiplier of 2.0 will make the rate 1/4096*.
* **"broadcastInterval"**: How often to send the broadcast (in seconds) letting players know which Pokémon have increased shiny rates.

*Example Config*

```json
[
  {
    "species": ["Pikachu", "Bulbasaur", "Charmander", "Squirtle"],
    "labels": ["legendary", "mythical"],
    "types": ["normal"],
    "days": ["Saturday", "Sunday"],
    "multiplier": 5.0,
    "broadcastInterval": 300
  },
  {
    "species": [],
    "labels": ["gen1"],
    "types": ["water"],
    "days": ["Monday"],
    "multiplier": 2.0,
    "broadcastInterval": 300
  }
]
```
The config above would work as follows

**Saturday and Sunday** - Pikachu, Bulbasaur, Charmander, Squirtle, Normal-type, legendary and mythical Pokemon have 5x shiny rates

**Monday** - All Gen 1 & Water-type Pokemon have 2x shiny rates

**Tuesday** - All Gen 2 & Fire-type Pokemon have 2x shiny rates

**Wednesday** - All Gen 3 & Grass-type Pokemon have 2x shiny rates

**Thursday** - All Gen 4 & Psychic-type Pokemon have 2x shiny rates

**Friday** - All Gen 5 & Steel-type Pokemon have 2x shiny rates

## Authors

Original Creation: Shadymitsu and ChatGPT  
Localization & Polymer Implementation: SrKalopsia

## License

[MIT](https://choosealicense.com/licenses/mit/)

