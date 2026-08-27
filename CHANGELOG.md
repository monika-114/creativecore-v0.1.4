# Changelog

## 0.1.0
- 初始核心流程。
- 新增空白物质、基底物质、创造物质、创造核心、创造工作台。
- 新增凋灵、末影龙、末地返回传送门三段世界交互仪式。
- 新增“空”桶与瓶装“　”的虚空打捞与装瓶机制。
- 新增创造工作台专属占位配方：牛刷怪蛋。

## v0.1.0 build-fix-1
- Fixed the 1.21.1 `Container` import used by `CreativeCraftingResultSlot`.
- Replaced the unavailable `CustomRecipe.Serializer` with 1.21.1 `SimpleCraftingRecipeSerializer` for void bottling.
- Made dragon ritual `SavedData` use an explicit `DataFixTypes` entry for safer reload persistence on 1.21.1.

## v0.1.0 build-fix-2
- Creative Matter now spawns weightlessly above the centre of the End exit fountain, preventing it from falling through the return portal into the Overworld.
- End void-bucket trigger changed from Y -40 +/- 5 to Y -10 +/- 5.
- Void-bucket ascent speed doubled from 0.075 to 0.15 blocks/tick.
- Void-bucket network update interval reduced from 10 ticks to 1 tick and client-side velocity is kept constant for smoother ascent.
- Returning void buckets can now be picked up during ascent.
- On reaching the recorded return height, a void bucket now remains hovering with no gravity instead of falling again.

## v0.1.0 build-fix-3
- Creative Core entity is no longer pickable/attackable and cannot be hit by projectiles.
- Creative Core entity now always uses the glowing outline flag.
- When a non-spectator player comes within 5 blocks, the entity form converts into a normal Creative Core ItemEntity with no pickup delay.
- Removed direct right-click collection from the persistent entity form.
