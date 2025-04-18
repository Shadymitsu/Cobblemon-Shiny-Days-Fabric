# Cobblemon Shiny Days

Choose days of the week to increase shiny odds for either all Pokémon or specific Pokémon, giving your server events similar to Community Days in Pokémon GO.

## Dependencies

Cobblemon - [Modrinth](https://modrinth.com/mod/cobblemon/) / [Curseforge](https://www.curseforge.com/minecraft/mc-mods/cobblemon)

## Features

- Choose which days to increase shiny rates
- Increase shiny odds for all spawns or only specific Pokémon
- Broadcasts a message at a customisable interval to remind players what shiny rates are currently increased

## Installation

Place the .jar file in your /mods folder, and restart the server.

## Configuration

The configuration has 4 customisable options

- "species" : which Pokémon will have increased rates (can use "ALL" to increase odds of all Pokémon)
- "days" : which days to apply the specified rates to the specified Pokémon
- "multiplier" : how much to multiply odds by (uses the shiny rates in your Cobblemon config as a base rate, then adds this modifier) *e.g. Cobblemon shiny rate is 1/8192, a multiplier of 2.0 will make the rate 1/4096*
- "broadcastInterval" : how often to send the broadcast (in seconds) letting players know which Pokémon have increased shiny rates.

*Example Config*

```json
[
  {
    "species": [
      "ALL"
    ],
    "days": [
      "Saturday",
      "Sunday"
    ],
    "multiplier": 2.0,
    "broadcastInterval": 300
  },
  {
    "species": [
      "Bulbasaur",
      "Charmander",
      "Squirtle"
    ],
    "days": [
      "Monday"
    ],
    "multiplier": 10.0,
    "broadcastInterval": 300
  },
  {
    "species": [
      "Chikorita",
      "Cyndaquil",
      "Totodile"
    ],
    "days": [
      "Tuesday"
    ],
    "multiplier": 10.0,
    "broadcastInterval": 300
  },
  {
    "species": [
      "Treecko",
      "Torchic",
      "Mudkip"
    ],
    "days": [
      "Wednesday"
    ],
    "multiplier": 10.0,
    "broadcastInterval": 300
  },
  {
    "species": [
      "Turtwig",
      "Chimchar",
      "Piplup"
    ],
    "days": [
      "Thursday"
    ],
    "multiplier": 10.0,
    "broadcastInterval": 300
  },
  {
    "species": [
      "Snivy",
      "Tepig",
      "Oshawott"
    ],
    "days": [
      "Friday"
    ],
    "multiplier": 10.0,
    "broadcastInterval": 300
  }
]
```
The config above would work as follows

**Saturday and Sunday** - ALL Pokémon have 2x shiny rates

**Monday** - Bulbasaur, Charmander and Squirtle have 10x shiny rates

**Tuesday** - Chikorita, Cyndaquil and Totodile have 10x shiny rates

**Wednesday** - Treecko, Torchic and Mudkip have 10x shiny rates

**Thursday** - Turtwig, Chimchar and Piplup have 10x shiny rates

**Friday** - Snivy, Tepig and Oshawott have 10x shiny rates

## Authors

Created by Shadymitsu and ChatGPT
## License

[MIT](https://choosealicense.com/licenses/mit/)
