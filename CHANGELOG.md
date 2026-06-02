# Changelog

All notable changes to this project will be documented in this file.

## [Unreleased / Fork Version]

### Added
* **Server-Side Localization**: Integrated Polymer Core to handle virtual server resource packs for language injection.
* **Language Files**: Added `en_us.json` and `es_es.json` inside the `src/main/resources/data/cobblemonshinydays/lang/` directory to support native English and Spanish clients.

### Changed
* **Code Refactor**: Replaced all hardcoded English strings in `CheckCommand.kt` and `BroadcastManager.kt` with `Text.translatable()` calls.
* **Variable Interpolation**: Modified the broadcast logic to pass dynamic variables (like Pokemon species, types, and multipliers) as format arguments directly into the translation keys.

### Removed
* Hardcoded chat messages, allowing full compatibility with non-English Minecraft clients out of the box.
