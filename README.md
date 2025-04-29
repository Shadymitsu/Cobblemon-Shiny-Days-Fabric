![Cobblemon Shiny Days](https://i.imgur.com/Mvl0Tkf.png)

Choose days of the week to increase shiny odds for either all Pokémon or specific Pokémon, giving your server events similar to Community Days in Pokémon GO.

## Dependencies

Cobblemon - [Modrinth](https://modrinth.com/mod/cobblemon/) / [Curseforge](https://www.curseforge.com/minecraft/mc-mods/cobblemon)

Fabric Language Kotlin (for Fabric servers) - [Modrinth](https://modrinth.com/mod/fabric-language-kotlin) / [Curseforge](https://www.curseforge.com/minecraft/mc-mods/fabric-language-kotlin)

## Features

- Choose which days to increase shiny rates
- Increase shiny odds for all spawns or only specific Pokémon
- Broadcasts a message at a customisable interval to remind players what shiny rates are currently increased

## Installation

Place the .jar file in your /mods folder, and restart the server.

## Configuration

The configuration has 5 customisable options

- "species" : which Pokémon will have increased rates (can use "ALL" to increase odds of all Pokémon)
- "labels" : Pokémon with these labels will have their shiny rates increased ("legendary", "gen1", "gen2" etc.)
- "days" : which days to apply the specified rates to the specified Pokémon
- "multiplier" : how much to multiply odds by (uses the shiny rates in your Cobblemon config as a base rate, then adds this modifier) *e.g. Cobblemon shiny rate is 1/8192, a multiplier of 2.0 will make the rate 1/4096*
- "broadcastInterval" : how often to send the broadcast (in seconds) letting players know which Pokémon have increased shiny rates.

*Example Config*

```json
[
  {
    "species": ["Pikachu", "Bulbasaur", "Charmander", "Squirtle"],
    "labels": ["legendary", "mythical"],
    "days": ["Saturday", "Sunday"],
    "multiplier": 5.0,
    "broadcastInterval": 300
  },
  {
    "species": [],
    "labels": ["gen1"],
    "days": ["Monday"],
    "multiplier": 2.0,
    "broadcastInterval": 300
  },
  {
    "species": [],
    "labels": ["gen2"],
    "days": ["Tuesday"],
    "multiplier": 2.0,
    "broadcastInterval": 300
  },
  {
    "species": [],
    "labels": ["gen3"],
    "days": ["Wednesday"],
    "multiplier": 2.0,
    "broadcastInterval": 300
  },
  {
    "species": [],
    "labels": ["gen4"],
    "days": ["Thursday"],
    "multiplier": 2.0,
    "broadcastInterval": 300
  },
  {
    "species": [],
    "labels": ["gen5"],
    "days": ["Friday"],
    "multiplier": 2.0,
    "broadcastInterval": 300
  }
]

```
The config above would work as follows

**Saturday and Sunday** - Pikachu, Bulbasaur, Charmander, Squirtle and all legendary and mythical Pokemon have 5x shiny rates

**Monday** - All Gen 1 Pokemon have 2x shiny rates

**Tuesday** - All Gen 2 Pokemon have 2x shiny rates

**Wednesday** - All Gen 3 Pokemon have 2x shiny rates

**Thursday** - All Gen 4 Pokemon have 2x shiny rates

**Friday** - All Gen 5 Pokemon have 2x shiny rates

## Authors

Created by Shadymitsu and ChatGPT
## License

[MIT](https://choosealicense.com/licenses/mit/)
