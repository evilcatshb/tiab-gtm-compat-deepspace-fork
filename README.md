# TIAB GTM Compat

<p align="center">
  <a href="https://portal.craftlands.host/aff.php?aff=150">
    <img src="https://media.forgecdn.net/attachments/description/1266962/description_3146a8cd-ca5d-46df-b248-9f7647d9b451.png">
  </a>
</p>
<p align="center">
  <a href="https://curseforge.com/minecraft/mc-mods/tiab-gtm-compat">
    <img alt="curseforge" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/curseforge_vector.svg">
  </a>
</p>

## About

A mc20.1 Forge mod that makes the Time in a Bottle tick-acceleration work correctly with GregTech Community Edition Modern machines.

## The problem

Time in a Bottle accelerates blocks by calling `serverTick()` N times per game tick. GT machines consume EU on each tick call, but power cables only deliver EU once per real game tick. Machines stall after the first sub-tick even when you have plenty of power generation.

## The fix

1. Single-block GT machines running inside a TIAB field process recipes at the accelerated rate without starving for EU.
2. Multiblocks and Cleanrooms (`CleanroomLogic`) bypasses the normal recipe tick path. This just patches it separately so multiblocks also benefit correctly.

## How the magic works

A Mixin on `RecipeLogic.handleRecipeWorking()` (and a separate one on `CleanroomLogic.serverTick()`) tracks `level.gameTime`. On the first call per game tick the normal EU consumption happens. On any subsequent calls within the same tick (TIAB extra ticks), EU draw is skipped but recipe progress still advances if and only if the natural tick had enough power.

## Dependencies

| Dependency | Version         |
|---|-----------------|
| GregTech Modern (GTCEu) | 1.6.4 or 7.4.0+ |
| Time in a Bottle | 4.0.4+          |
| Kotlin for Forge | 4.12.0+         |

## Credits

- [Akoto090](https://github.com/Akoto090)
- [Lowtrixx](https://github.com/Lowtrixx) (idea)
- [JuiceyBeans](https://github.com/JuiceyBeans), [screret](https://github.com/screret) (GregTech Addon Template)

## License

This mod is licensed under [MIT](https://mit-license.org/)
