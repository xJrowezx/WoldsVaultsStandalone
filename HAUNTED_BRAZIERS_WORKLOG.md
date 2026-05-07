# Haunted Braziers Worklog

Purpose: persistent handoff notes for the Haunted Brazier objective cleanup pass. Append to this file on every related prompt so work can resume after an IDE/session crash or another tool can continue from this log alone.

## 2026-05-07 Prompt 1

User requested a new persistence log for this branch and a cleanup pass for the `haunted_braziers` objective.

Request:

- Compare Haunted Brazier files in standalone against:
  - Official Wolds mod: `E:\Git Repo's\Wolds-Vaults-Official-Mod`
  - Wolds pack: `E:\Git Repo's\Wolds-Vaults-Pack`
  - Vault Hunters jar: `C:\Users\Ethan\AppData\Roaming\PrismLauncher\instances\NewPack Test\minecraft\mods\the_vault-1.18.2-3.21.0.jar`
- Use the Vault Hunters jar unless a local decompiled copy is already available.
- Do not edit Haunted Braziers loot pools during the cleanup pass.
- There are separate loot pools for brazier drops and reward crates. At the final step, after confirming functionality, compare existing loot pools against official Wolds.

Initial branch/worktree state:

- Current repo: `E:\Git Repo's\WoldsVaultsStandalone`
- Existing uncommitted changes from prior work:
  - `build.gradle`
  - `gradle.properties`
  - `src/main/resources/assets/woldsvaults/lang/en_us.json`
  - `src/main/resources/woldsvaultsstandalone.mixins.json`
  - `src/main/java/xyz/iwolfking/woldsvaults/mixin/the_vault/custom/MixinClassicListenersLogic.java`
  - `src/main/resources/data/woldsvaults/loot_tables/blocks/xl_backpack.json`
- These existing changes are unrelated to Haunted Braziers and must not be reverted.

Initial standalone Haunted Brazier files found:

- `src/main/java/xyz/iwolfking/woldsvaults/configs/HauntedBraziersConfig.java`
- `src/main/java/xyz/iwolfking/woldsvaults/objectives/HauntedBraziersObjective.java`
- `src/main/java/xyz/iwolfking/woldsvaults/objectives/HauntedBraziersCrystalObjective.java`
- `src/main/java/xyz/iwolfking/woldsvaults/init/ModCustomVaultObjectiveEntries.java`
- `src/main/java/xyz/iwolfking/woldsvaults/init/ModConfigs.java`
- `src/main/java/xyz/iwolfking/woldsvaults/mixin/the_vault/MixinVaultObjectivesModule.java`
- `src/main/java/xyz/iwolfking/woldsvaults/mixin/the_vault/MixinLootInfoGroupDefinitionRegistry.java`
- `src/main/resources/default_configs/haunted_braziers.json`
- `src/main/resources/vhapi_configs/objective_seals.json`
- `src/main/resources/vhapi_configs/wold_modifier_pools.json`
- `src/main/resources/vhapi_configs/wold_modifiers.json`
- `src/main/resources/vhapi_configs/custom_objective_stats.json`
- `src/main/resources/vhapi_configs/custom_objective_loot_tables.json`
- `src/main/resources/assets/woldsvaults/lang/en_us.json`

Initial official Wolds references found:

- `src/main/java/xyz/iwolfking/woldsvaults/config/HauntedBraziersConfig.java`
- `src/main/java/xyz/iwolfking/woldsvaults/objectives/HauntedBraziersObjective.java`
- `src/main/java/xyz/iwolfking/woldsvaults/objectives/HauntedBraziersCrystalObjective.java`
- `src/main/java/xyz/iwolfking/woldsvaults/init/ModCustomVaultObjectiveEntries.java`
- `src/main/java/xyz/iwolfking/woldsvaults/init/ModConfigs.java`
- `src/main/java/xyz/iwolfking/woldsvaults/mixins/vaulthunters/MixinVaultObjectivesModule.java`
- `src/main/java/xyz/iwolfking/woldsvaults/mixins/vaulthunters/fixes/MixinVaultUtils.java`
- `src/main/java/xyz/iwolfking/woldsvaults/mixins/vaulthunters/custom/MixinWildSpawnerTileEntity.java`
- `src/main/java/xyz/iwolfking/woldsvaults/modifiers/vault/BrazierPoolsModifier.java`
- `src/main/java/xyz/iwolfking/woldsvaults/mixins/vaulthunters/modifiers/MixinVaultModifierTypeRegistry.java`
- generated resources for Haunted Braziers crate, lang, challenge config, modifier pools, modifiers, and loot info.

Initial Wolds pack references found:

- `config/the_vault/haunted_braziers.json`
- `config/the_vault/haunted_objective.json`
- `config/the_vault/gen/1.0/loot_tables/haunted_brazier_lvl0.json`
- `config/the_vault/gen/1.0/loot_tables/haunted_brazier_lvl20.json`
- `config/the_vault/gen/1.0/loot_tables/haunted_brazier_lvl50.json`
- `config/the_vault/gen/1.0/loot_tables/haunted_braziers_crate_50.json`
- `config/the_vault/gen/1.0/loot_tables/haunted_braziers_crate_100.json`
- `config/the_vault/gen/1.0/themes/classic_vault_haunted.json`
- `config/the_vault/bounty/task/completion.json`
- `kubejs/data/wolds_vaults/advancements/items/crystal_seal_spirits.json`
- `scripts/ExtraTags.zs` has brazier pillaging item tags. These are loot-adjacent and should only be compared at the final loot-pool review step.

Completed so far:

- Created this worklog.
- Searched standalone, official Wolds, and Wolds pack for Haunted/Brazier references.

Next steps:

- Compare standalone and official Java objective/config/crystal objective files.
- Compare non-loot config and lang/registration differences.
- Inspect Vault Hunters jar only where needed to validate base Monolith/Brazier behavior.
- Avoid editing Haunted Braziers loot pools until functionality is confirmed.

## 2026-05-07 Prompt 1 Continued

Comparison findings:

- `default_configs/haunted_braziers.json` matches the Wolds pack `config/the_vault/haunted_braziers.json`.
- Standalone `HauntedBraziersCrystalObjective` still awards `VaultCrateBlock.Type.MONOLITH`; official Wolds uses `VaultCrateBlock.Type.valueOf("HAUNTED_BRAZIERS")`.
- Standalone already extends the `VaultCrateBlock.Type` enum with `HAUNTED_BRAZIERS`, but `MixinVaultCrateBlock` does not map that type to a block.
- Standalone has explicit custom crate blocks for Alchemy and Ballistic Bingo only. Official Wolds generates a `vault_crate_haunted_braziers` crate block/resource set.
- Official Wolds includes `MixinVaultUtils.isBrazierVault` so Vault Hunters code treats `HauntedBraziersObjective` as a brazier vault.
- Official Wolds uses `VaultMod.id("haunting")` as an init modifier while standalone uses `WoldsVaults.id("haunting")`. Standalone config still defines `woldsvaults:haunting`, so this needs care before changing.
- Official Wolds has `BrazierPoolsModifier` and `all_bad_haunted_braziers` for challenge/modifier pool replacement support; standalone does not currently include this modifier type/config.
- Official Wolds has `MixinWildSpawnerTileEntity` to make Haunted Braziers use spooky/wraith spawner behavior. This is a larger overwrite and should be reviewed carefully before porting.
- Official `HauntedBraziersCrystalObjective.readNbt/readJson` falls back to `IntRoll.ofUniform(3,5)` if target is missing. Standalone currently falls back to `null`.

Planned first patch:

- Add standalone Haunted Braziers crate block/resource plumbing without touching reward loot-pool configs.
- Change Haunted Braziers objective crate award from `MONOLITH` to `HAUNTED_BRAZIERS`.
- Add `VaultUtils.isBrazierVault` mixin for `HauntedBraziersObjective`.
- Harden Haunted Braziers crystal target deserialization fallback.

Completed first patch:

- Added `ModBlocks.VAULT_CRATE_HAUNTED_BRAZIERS`.
- Registered `woldsvaults:vault_crate_haunted_braziers` block and block item.
- Updated `ModCustomVaultObjectiveEntries.HAUNTED_BRAZIERS` to expose the Haunted Braziers crate item instead of the vanilla monolith crate.
- Updated `MixinVaultCrateBlock` so `VaultCrateBlock.Type.HAUNTED_BRAZIERS` resolves to the new standalone crate block.
- Updated `HauntedBraziersCrystalObjective` to award `VaultCrateBlock.Type.valueOf("HAUNTED_BRAZIERS")` instead of `MONOLITH`.
- Updated `HauntedBraziersCrystalObjective` to use current `FindExitObjective.create(ClassicPortalLogic.EXIT)` / `BailObjective.create(true, ClassicPortalLogic.EXIT)` call style, matching official Wolds.
- Updated Haunted Braziers crystal target read fallbacks to `IntRoll.ofUniform(3, 5)` instead of `null`.
- Added `MixinVaultUtils` so `VaultUtils.isBrazierVault(vault)` returns true for `HauntedBraziersObjective`.
- Added Haunted Braziers crate blockstate/model/item model/empty block loot table/lang entry.
- Copied official `vault_crate_haunted_braziers.png` texture into standalone.
- Updated the Haunted Braziers init modifier from `woldsvaults:haunting` to `the_vault:haunting`, matching official Wolds and the Wolds pack active config.
- Added `objective.the_vault.haunted_braziers` and `objective.woldsvaultsstandalone.haunted_braziers` lang entries.

Verification so far:

- `.\gradlew.bat compileJava` succeeded after the first crate/VaultUtils/crystal patch.
- `.\gradlew.bat build` succeeded after the `the_vault:haunting` and lang update.
- Rebuilt jar: `build\libs\woldsvaultsstandalone-0.22.7-all.jar`.
- Verified the rebuilt jar contains:
  - `assets/woldsvaults/blockstates/vault_crate_haunted_braziers.json`
  - `assets/woldsvaults/models/block/vault_crate_haunted_braziers.json`
  - `assets/woldsvaults/models/item/vault_crate_haunted_braziers.json`
  - `assets/woldsvaults/textures/block/vault_crate_haunted_braziers.png`
  - `data/woldsvaults/loot_tables/blocks/vault_crate_haunted_braziers.json`
  - `xyz/iwolfking/woldsvaults/mixin/the_vault/fixes/MixinVaultUtils.class`
  - `HauntedBraziersObjective.class`
  - `HauntedBraziersCrystalObjective.class`

Still under review:

- Whether to port official `BrazierPoolsModifier` and `all_bad_haunted_braziers` challenge support. This is not a loot-pool edit, but it is only useful if standalone includes or wants the official Haunted Braziers challenge modifier behavior.
- Final loot-pool comparison remains deferred per user instruction.

## 2026-05-07 Prompt 1 Wild Spawner Follow-up

Reasoning:

- Official Wolds includes `MixinWildSpawnerTileEntity` specifically marked for Haunted Braziers.
- The Wolds pack has `config/the_vault/wild_spawner.json` with the spooky/wraith group, and `custom_entity_spawner.json` includes `all_ghosts` using `quark:wraith`.
- Standalone already has Quark references and `custom_spawners.json` with `all_ghosts`.
- This behavior affects runtime Haunted Brazier vault feel/spawning and does not edit loot pools.

Completed:

- Added standalone `the_vault.custom.MixinWildSpawnerTileEntity`.
- Registered the mixin in `woldsvaultsstandalone.mixins.json`.
- Ported official behavior:
  - `spawnEntity` uses buffed wraith spawning for `quark:wraith`.
  - `initSpawnerGroup` chooses the spooky wild spawner group for Haunted Braziers vaults and for vaults with `the_vault:spooky`.
- Added small defensive guards not present in official:
  - no-op if no current vault is found;
  - no-op if the configured wild spawner group list does not have index `3`;
  - no-op if entity spawn returns null.

Verification:

- `.\gradlew.bat compileJava` succeeded after adding the wild spawner mixin.
- `.\gradlew.bat build` succeeded after adding the wild spawner mixin.
- Rebuilt jar: `build\libs\woldsvaultsstandalone-0.22.7-all.jar`.
- Verified the rebuilt jar contains:
  - `xyz/iwolfking/woldsvaults/mixin/the_vault/custom/MixinWildSpawnerTileEntity.class`
  - `xyz/iwolfking/woldsvaults/mixin/the_vault/fixes/MixinVaultUtils.class`
  - Haunted Braziers crate blockstate/model/item model/texture/block loot table resources.

Current handoff status:

- First Haunted Braziers functional parity pass is implemented and builds.
- No Haunted Braziers brazier-drop loot pools or reward-crate loot pools were edited.
- The only loot-table file added in this pass is the empty block loot table for the physical Haunted Braziers crate block, matching the existing standalone Alchemy/Ballistic crate block pattern and official generated crate block loot table.
- Remaining optional review: `BrazierPoolsModifier` / `all_bad_haunted_braziers` challenge support. Standalone currently has no obvious Haunted Braziers challenge config using it, so it was not ported in this pass.
- Next runtime test should verify:
  - Seal of the Exorcist applies and enters a Haunted Braziers vault.
  - Objective pieces generate and count when lit.
  - The vault is treated as a brazier vault by Vault Hunters utility checks.
  - Wild spawners in Haunted Braziers use the spooky/wraith group.
  - After reaching target and exiting, the reward crate is the Haunted Braziers crate block and renders correctly.

## 2026-05-07 Prompt 2

User asked to check the Haunted Brazier HUD element because it should be linked to the new in-game HUD editing system. Other Wold objectives link there correctly, so Haunted Braziers may not be registered as an objective/settings page somewhere.

Initial focus:

- Inspect `MixinVaultObjectivesModule` and any HUD settings page registration.
- Compare Haunted Braziers handling against Alchemy, Zealot, Brutal Bosses, and other Wold objectives.
- Fix the HUD editor registration if missing.

Findings:

- Standalone `MixinVaultObjectivesModule` already registers `HauntedBraziersObjective` with HUD type key `monolith`.
- Standalone `MixinVaultObjectivesModule#getSize` already gives `HauntedBraziersObjective` the custom 155x25 objective HUD size.
- Official Wolds also maps `HauntedBraziersObjective` to `monolith`; it does not add a separate Haunted Braziers settings page.
- The standalone Haunted Braziers normal HUD render still anchors to `window.getGuiScaledWidth() / 2`, while official Wolds renders the normal bar relative to `midX = 0`.
- That means the objective can be registered with the HUD editor, but the bar can still draw at screen center instead of honoring the module placement.

Planned fix:

- Match official Wolds for the active Haunted Braziers HUD bar by rendering relative to the module origin.
- Do not create a separate Haunted Braziers HUD settings page unless runtime testing proves the vanilla `monolith` slot is insufficient.

Completed:

- Updated `HauntedBraziersObjective#render` normal/progress HUD branch so `midX = 0`, matching official Wolds.
- Left the completed objective prompt screen-centered, also matching official Wolds.
- Left `MixinVaultObjectivesModule` mapping as `monolith`, matching official Wolds and reusing the vanilla Monolith HUD editor slot.

Verification:

- `.\gradlew.bat build` succeeded.
- Rebuilt jar: `build\libs\woldsvaultsstandalone-0.22.7-all.jar`.
- Verified the rebuilt jar contains:
  - `xyz/iwolfking/woldsvaults/objectives/HauntedBraziersObjective.class`
  - `xyz/iwolfking/woldsvaults/mixin/the_vault/MixinVaultObjectivesModule.class`

Runtime expectation:

- Haunted Braziers should appear/edit under the Monolith HUD objective slot because both standalone and official Wolds map it to `monolith`.
- If runtime testing shows it needs its own named page, add a dedicated objective HUD settings default and settings page later.

## 2026-05-07 Prompt 3

User reported an in-game lang/display issue on the first Haunted Brazier modifier found during testing.

Screenshot observation:

- Brazier overlay displays raw key-like text: `1x 'gen_pog_gift. Uremania`
- Other visible lines render correctly:
  - `1x Ghost Town`
  - `35% Copiously`
  - `+8 Randomly Spawning Ghosts.`

User also noted this version likely cannot display animated GIFs without another mod such as Emoji/Emojiful, so any animated icon/GIF-based display should be avoided or converted to plain text/static supported formatting.

Initial focus:

- Find where `gen_pog_gift` / `Uremania` is configured or localized.
- Compare standalone, official Wolds, Wolds pack, and Vault Hunters modifier lang/config for the affected modifier.
- Fix display text using supported lang/plain text only.

Interrupted direction:

- Initial edit stripped unsupported Emojiful-style `:name:` tokens from modifier display names in `vhapi_configs/wold_modifiers.json`.
- User redirected: instead of removing these tokens, try to make sure the linked assets exist.
- Reverted the modifier display name stripping before continuing.

New focus:

- Determine whether `:gem_pog_gif:` style modifier names can be made to render by bundling assets in this standalone mod.
- Check Wolds pack Emojiful/KubeJS registration and whether standalone has any compatible runtime support.

Findings:

- Wolds pack uses `mods/emojiful.pw.toml` for `emojiful-1.18.2-3.0.1.jar`.
- Wolds pack registers custom emoji names through `kubejs/server_scripts/woldsvaults/emojis.js`.
- Emojiful recipe JSON shape is:
  - `type`: `emojiful:emoji_recipe`
  - `category`
  - `name`
  - `url`
- Standalone has normal modifier icon textures, but those do not make `:gem_pog_gif:` or other `:name:` text tokens render inline.
- Adding Emojiful is beyond scope because it changes other behavior in the pack.

Completed:

- Reverted the attempted `build.gradle` runtime dependency for Emojiful.
- Did not add an Emojiful dependency to `mods.toml`.
- Stripped unsupported `:name:` display tokens from modifier names in `vhapi_configs/wold_modifiers.json`.
- Kept modifier icons and behavior unchanged.

Verification:

- Confirmed `build.gradle` has no `emojiful` dependency.
- Confirmed `mods.toml` has no `emojiful` dependency.
- Confirmed no modifier display names in `vhapi_configs/wold_modifiers.json` start with a raw `:emoji:` token.
- `.\gradlew.bat build` succeeded.
- Rebuilt jar: `build\libs\woldsvaultsstandalone-0.22.7-all.jar`.
- Verified the rebuilt jar contains `vhapi_configs/wold_modifiers.json`.

## 2026-05-07 Prompt 4

User asked what Haunted Braziers gave as its reward crate before the new crate registration.

Verified from current diff/worklog:

- `ModCustomVaultObjectiveEntries.HAUNTED_BRAZIERS` previously used `setCrateItem(ModBlocks.VAULT_CRATE_MONOLITH)`.
- `HauntedBraziersCrystalObjective` previously awarded `AwardCrateObjective.ofConfig(VaultCrateBlock.Type.MONOLITH, "haunted_braziers", level, true)`.
- So the pre-fix reward crate was the Monolith crate block/item, while still using the Haunted Braziers reward config id string.

## 2026-05-07 Prompt 5

User asked whether Wolds has loot pools defined for the new Haunted Braziers crate.

Findings:

- Standalone currently has `vhapi_configs/custom_objective_loot_tables.json` mapping Haunted Braziers to normal base crate tables:
  - level 0: `the_vault:base_crate_0`
  - level 5: `the_vault:base_crate_0`
  - level 20: `the_vault:base_crate_20`
  - level 50: `the_vault:base_crate_50`
- Wolds pack has reward crate mappings in `config/the_vault/loot_table.json`:
  - level 0: `the_vault:base_crate_cursed_0`
  - level 5: `the_vault:base_crate_cursed_0`
  - level 20: `the_vault:base_crate_cursed_20`
  - level 50: `the_vault:haunted_braziers_crate_50`
  - level 100: `the_vault:haunted_braziers_crate_100`
- Wolds pack has actual generated loot table files:
  - `config/the_vault/gen/1.0/loot_tables/haunted_braziers_crate_50.json`
  - `config/the_vault/gen/1.0/loot_tables/haunted_braziers_crate_100.json`
- Official Wolds generated loot info references the same Haunted Braziers completion crate tables:
  - `the_vault:haunted_braziers_crate_50`
  - `the_vault:haunted_braziers_crate_100`
  - `the_vault:base_crate_cursed_0`
  - `the_vault:base_crate_cursed_20`

No files edited for this prompt.

## 2026-05-07 Prompt 7

User decided to leave Haunted Braziers mapped to the current standalone reward contents for now rather than switching to the Wolds Haunted-specific crate pools.

Current decision:

- Keep the custom Haunted Braziers physical crate.
- Keep existing standalone reward-content mappings for now.
- Do not port Wolds Haunted Braziers crate reward loot pools yet.

## 2026-05-07 Prompt 8 - Brutal Bosses Pass

User asked to do the same cleanup/parity pass for the Brutal Bosses objective:

- Compare standalone to official Wolds mod.
- Bring over updates to everything Brutal Bosses touches.
- Same loot-pool rule: do not edit reward loot pools yet; final comparison only after behavior is confirmed.
- User is testing blind and does not know this objective well.

Initial focus:

- Find all standalone Brutal Bosses objective files/config/resources.
- Compare against `E:\Git Repo's\Wolds-Vaults-Official-Mod`.
- Check Wolds pack config for runtime config differences.
- Patch non-loot behavior/resource/registration gaps.

Findings:

- Standalone already has:
  - `BrutalBossesObjective`
  - `BrutalBossesCrystalObjective`
  - `BrutalBossesRegistry`
  - HUD type key/page registration as `brutal_bosses`
  - `VaultCrateBlock.Type.BRUTAL_BOSSES` enum extension
  - Seal/config entries for `brutal_bosses`
- Standalone was still using the vanilla boss crate:
  - objective registry crate item: `ModBlocks.VAULT_CRATE_MONOLITH`
  - crystal award: `VaultCrateBlock.Type.BOSS`
- Official Wolds uses a dedicated Brutal Bosses crate block/item:
  - `woldsvaults:vault_crate_brutal_bosses`
  - `VaultCrateBlock.Type.valueOf("BRUTAL_BOSSES")`
- Official Wolds/Wolds pack use AtomicStryker's Infernal Mobs for Brutal Boss mechanics.
- The `NewPack Test` instance did not have an Infernal Mobs jar in `minecraft/mods`.
- Standalone currently uses Vault Hunters champion promotion for spawned Brutal Bosses instead.

Completed:

- Added dedicated `ModBlocks.VAULT_CRATE_BRUTAL_BOSSES`.
- Registered block `woldsvaults:vault_crate_brutal_bosses`.
- Registered the block item.
- Updated `ModCustomVaultObjectiveEntries.BRUTAL_BOSSES` to expose the new standalone Brutal Bosses crate item.
- Updated `MixinVaultCrateBlock` so `VaultCrateBlock.Type.BRUTAL_BOSSES` resolves to the new standalone crate block.
- Updated `BrutalBossesCrystalObjective` to award `VaultCrateBlock.Type.valueOf("BRUTAL_BOSSES")` instead of `BOSS`.
- Updated Brutal Bosses bail objective call to `BailObjective.create(true, ClassicPortalLogic.EXIT)`, matching official style.
- Added Brutal Bosses crate blockstate/model/item model/empty block loot table.
- Copied official `vault_crate_brutal_bosses.png` texture into standalone.
- Added Brutal Bosses crate/objective lang entries.
- Did not edit reward crate loot mappings.
- Did not add Infernal Mobs as a dependency.

Verification so far:

- First `.\gradlew.bat compileJava` failed because `ModCustomVaultObjectiveEntries` imports Vault Hunters `ModBlocks`, so the new standalone crate needed a fully qualified standalone `ModBlocks` reference.
- Fixed that reference.
- `.\gradlew.bat compileJava` then succeeded.
- `.\gradlew.bat build` succeeded.
- Rebuilt jar: `build\libs\woldsvaultsstandalone-0.22.7-all.jar`.
- Verified the rebuilt jar contains:
  - `assets/woldsvaults/blockstates/vault_crate_brutal_bosses.json`
  - `assets/woldsvaults/models/block/vault_crate_brutal_bosses.json`
  - `assets/woldsvaults/models/item/vault_crate_brutal_bosses.json`
  - `assets/woldsvaults/textures/block/vault_crate_brutal_bosses.png`
  - `data/woldsvaults/loot_tables/blocks/vault_crate_brutal_bosses.json`
  - `xyz/iwolfking/woldsvaults/objectives/BrutalBossesObjective.class`
  - `xyz/iwolfking/woldsvaults/objectives/BrutalBossesCrystalObjective.class`
  - `xyz/iwolfking/woldsvaults/mixin/the_vault/custom/MixinVaultCrateBlock.class`

Open Brutal Bosses parity gap:

- Official boss spawning applies Infernal Mobs modifiers and derives vault modifier rewards from the killed boss's infernal modifiers.
- Standalone still applies Vault champion behavior instead.
- Porting official boss mechanics requires deciding whether to add AtomicStryker's Infernal Mobs to the standalone/test pack; not done in this pass.

## 2026-05-07 Prompt 9

User reported the Brutal Bosses HUD element is way off to the side, same as Haunted Braziers was before the HUD render fix.

Initial focus:

- Inspect `BrutalBossesObjective#render`.
- Compare against official Wolds.
- Patch any screen-centered HUD rendering to be module-relative.

Findings:

- `BrutalBossesObjective` does not override `render` in standalone or official.
- It inherits `ObeliskObjective#render` from Vault Hunters.
- Official Wolds includes `MixinObeliskObjective`, which redirects `Window.getGuiScaledWidth()` inside `ObeliskObjective#render` to return `0`.
- This is the same module-relative HUD positioning pattern used for the Haunted Braziers render fix.

Completed:

- Added standalone `the_vault.fixes.MixinObeliskObjective`.
- Registered it in `woldsvaultsstandalone.mixins.json`.

Verification:

- `.\gradlew.bat build` succeeded.
- Rebuilt jar: `build\libs\woldsvaultsstandalone-0.22.7-all.jar`.
- Verified the rebuilt jar contains:
  - `xyz/iwolfking/woldsvaults/mixin/the_vault/fixes/MixinObeliskObjective.class`
  - `woldsvaultsstandalone.mixins.json`

## 2026-05-07 Prompt 10

User reported the Brutal Bosses objective overlay is centered after the Obelisk HUD mixin, but it does not update while completing tasks by killing bosses after clicking obelisks.

Initial focus:

- Inspect inherited `ObeliskObjective` progress state and render expectations.
- Compare standalone `BrutalBossesObjective` kill tracking against official Wolds and Vault Hunters base behavior.
- Patch the progress update path without changing reward loot pools.

Findings:

- Vault Hunters `ObeliskObjective` marks `WAVES` dirty after an obelisk is activated and after tracked mob death increments wave progress.
- Standalone `BrutalBossesObjective` overrides both paths and was mutating `Wave.ACTIVE`, `Wave.MOBS`, and `Wave.COUNT` without calling `markDirty(WAVES)`.
- That explains the server-side objective progressing while the client HUD keeps rendering stale wave data.

Completed:

- Added `this.markDirty(WAVES);` after Brutal Bosses boss death increments `Wave.COUNT`.
- Added `this.markDirty(WAVES);` after Brutal Bosses obelisk activation spawns and tracks the wave bosses.
- Did not edit reward crate loot pools or boss reward modifier pools.

Verification:

- `.\gradlew.bat build` succeeded.
- Rebuilt jar: `build\libs\woldsvaultsstandalone-0.22.7-all.jar`.
- Verified the rebuilt jar contains:
  - `xyz/iwolfking/woldsvaults/objectives/BrutalBossesObjective.class`
  - `xyz/iwolfking/woldsvaults/mixin/the_vault/fixes/MixinObeliskObjective.class`
  - `woldsvaultsstandalone.mixins.json`

## 2026-05-07 Prompt 11

User reported a Brutal Bosses summon named "Wold" had only 20 HP. iwolfking said standalone is missing the Wolds vault mob config that defines the mob stats correctly. User asked to grab it and make sure standalone has it.

Findings:

- Wolds pack has `config/the_vault/vault_mobs.json`; standalone had no bundled `default_configs/vault_mobs.json`.
- Official Wolds datagen defines the Wold boss vault mob override as `the_vault:wold` because official registers Wold under the Vault namespace.
- Standalone registers the Wold boss as `woldsvaults:wold`, so the official key would not apply here.

Completed:

- Added bundled `src/main/resources/default_configs/vault_mobs.json` from the Wolds pack.
- Added a `woldsvaults:wold` attribute override matching official Wold boss stats:
  - max health 450-500 at level 0, 510-550 at level 50, 550-650 at level 90, with 0.25 per-level scaling.
  - attack damage 4-8, crit chance 0.1-0.25, crit multiplier 1.5-2.0.
  - knockback resistance, movement speed, swim speed, indirect teleport chance, and teleport range copied from official Wold boss datagen.
- Updated `MixinModConfigs` startup config prep to:
  - copy bundled `vault_mobs.json` if `config/the_vault/vault_mobs.json` is missing.
  - merge/replace only the `woldsvaults:wold` override if a config already exists.

Notes:

- This does not change loot pools.
- This keeps the standalone entity id rather than changing Wold registration to `the_vault:wold`.

Verification:

- Confirmed `src/main/resources/default_configs/vault_mobs.json` is valid JSON and contains `ATTRIBUTE_OVERRIDES["woldsvaults:wold"]`.
- `.\gradlew.bat build` succeeded.
- Rebuilt jar: `build\libs\woldsvaultsstandalone-0.22.7-all.jar`.
- Verified the rebuilt jar contains:
  - `default_configs/vault_mobs.json`
  - `xyz/iwolfking/woldsvaults/mixin/the_vault/MixinModConfigs.class`

Testing note:

- Existing game configs are patched by the mod during Vault Hunters config registration. The test instance should be restarted with the rebuilt jar so the `woldsvaults:wold` override is merged before entering another Brutal Bosses vault.

## 2026-05-07 Prompt 11 – Unhinged Scavenger Parity Pass

User request: same cleanup/parity pass for Unhinged Scavenger as was done for Haunted Braziers and Brutal Bosses.

References:
- Official Wolds mod: `E:\Git Repo's\Wolds-Vaults-Official-Mod`
- Wolds pack: `E:\Git Repo's\Wolds-Vaults-Pack`
- Vault Hunters jar: available; decompiled sources also exist at `build/fg_cache/…/decompile/`

Rules: no loot pool edits; no new mod dependencies; preserve unrelated dirty changes.

Findings before edits:

- `ModCustomVaultObjectiveEntries.UNHINGED_SCAVENGER_HUNT` called `.setCrateItem(ModBlocks.VAULT_CRATE_SCAVENGER)`.
  The bare `ModBlocks` import resolves to `iskallia.vault.init.ModBlocks`, so this compiled fine but handed the vanilla VH Scavenger crate to the objective registry — same root bug as Haunted Braziers/Brutal Bosses before their pass.
- `UnhingedScavengerCrystalObjective.configure()` passed `VaultCrateBlock.Type.valueOf("SCAVENGER")` (vanilla VH type) to both `AwardCrateObjective.ofLootTable` and `AwardCrateObjective.ofConfig`. Official Wolds uses `VaultCrateBlock.Type.valueOf("UNHINGED_SCAVENGER")`.
- `MixinVaultCrateBlock.handleWoldCrate` handled ALCHEMY, BRUTAL_BOSSES, HAUNTED_BRAZIERS, BALLISTIC_BINGO but not UNHINGED_SCAVENGER.
- `MixinVaultCrateType` already extended the enum with UNHINGED_SCAVENGER — no change needed there.
- No standalone `VAULT_CRATE_UNHINGED_SCAVENGER` block existed in `ModBlocks`.
- No blockstate/model/item model/texture/block loot table assets existed for the scavenger crate.
- `MixinVaultObjectivesModule.checkForWoldObjectives` already returns `"scavenger"` for `UnhingedScavengerObjective` — matches official, no change needed.
- `getSize` does not need a special case for scavenger; the vanilla scavenger HUD is list-based, not bar-based, so the 155×25 bar size does not apply.
- `MixinScavengerObjective` (pouch handling) already ported — no change needed.
- Lang already had `"vault_objective.woldsvaultsstandalone.unhinged_scavenger"` and the JEI key; missing `"objective.the_vault.unhinged_scavenger"`, `"objective.woldsvaultsstandalone.unhinged_scavenger"`, and `"block.woldsvaults.vault_crate_unhinged_scavenger"`.
- HUD type key `"scavenger"` reuses the vanilla VH scavenger HUD slot — same pattern as official Wolds; no dedicated settings page added.
- Official Wolds has a `vault_diffuser/unhinged_scavenger.json` for soul dust diffuser config. Standalone has no diffuser; not ported (no dependency added).
- Wolds pack has `scavenger_hunt.json` and `scavenger.json` alongside `unhinged_scavenger.json`. These are separate VH configs loaded by the_vault mod, not by this standalone mod. Not ported.

Completed:

- Added `VAULT_CRATE_UNHINGED_SCAVENGER` field, static initializer, `registerBlocks` entry, and `registerBlockItems` entry to `ModBlocks`.
- Fixed `ModCustomVaultObjectiveEntries.UNHINGED_SCAVENGER_HUNT` to use `xyz.iwolfking.woldsvaults.init.ModBlocks.VAULT_CRATE_UNHINGED_SCAVENGER` (fully qualified to avoid the VH `ModBlocks` import shadow).
- Added `UNHINGED_SCAVENGER` case to `MixinVaultCrateBlock.handleWoldCrate` → `ModBlocks.VAULT_CRATE_UNHINGED_SCAVENGER`.
- Updated both `VaultCrateBlock.Type.valueOf("SCAVENGER")` occurrences in `UnhingedScavengerCrystalObjective.configure()` to `VaultCrateBlock.Type.valueOf("UNHINGED_SCAVENGER")`.
- Created `assets/woldsvaults/blockstates/vault_crate_unhinged_scavenger.json`.
- Created `assets/woldsvaults/models/block/vault_crate_unhinged_scavenger.json`.
- Created `assets/woldsvaults/models/item/vault_crate_unhinged_scavenger.json`.
- Copied `assets/woldsvaults/textures/block/vault_crate_unhinged_scavenger.png` from official Wolds.
- Created `data/woldsvaults/loot_tables/blocks/vault_crate_unhinged_scavenger.json` (empty block loot table, matching pattern of other standalone crate blocks).
- Added three lang entries: `"objective.the_vault.unhinged_scavenger"`, `"objective.woldsvaultsstandalone.unhinged_scavenger"`, `"block.woldsvaults.vault_crate_unhinged_scavenger"`.

Intentionally not changed:

- Reward loot pool mappings (deferred per instructions).
- `MixinVaultObjectivesModule` HUD key and size — already correct.
- `MixinScavengerObjective` pouch handling — already ported.
- `MixinVaultCrateType` UNHINGED_SCAVENGER enum extension — already present.
- `ObjectiveHelper.handleAddingNormalizedToVault` call in `UnhingedScavengerObjective.initServer` — official Wolds adds this; standalone does not have `ObjectiveHelper` in scope and the call is a pack-normalization utility, not a correctness fix.
- Soul dust diffuser config (`vault_diffuser/unhinged_scavenger.json`) — no diffuser block/mechanic in standalone.
- Wolds pack `scavenger_hunt.json` / `scavenger.json` — these are VH-native configs, not standalone mod defaults.

Build result:

- `.\gradlew.bat build` — BUILD SUCCESSFUL in 18s.
- Rebuilt jar: `build\libs\woldsvaultsstandalone-0.22.7-all.jar`.
- Verified jar contains:
  - `assets/woldsvaults/blockstates/vault_crate_unhinged_scavenger.json`
  - `assets/woldsvaults/models/block/vault_crate_unhinged_scavenger.json`
  - `assets/woldsvaults/models/item/vault_crate_unhinged_scavenger.json`
  - `assets/woldsvaults/textures/block/vault_crate_unhinged_scavenger.png`
  - `data/woldsvaults/loot_tables/blocks/vault_crate_unhinged_scavenger.json`
  - `default_configs/unhinged_scavenger.json` (pre-existing, unchanged)

Testing notes:

- Craft a Seal of the Psychopath crystal and run the vault.
- Confirm the reward crate placed on completion is the Unhinged Scavenger crate block with the correct texture.
- Confirm the JEI entry "Unhinged Scavenger Crate" shows the new block item.
- Confirm the in-game HUD shows the scavenger task list (vanilla scavenger HUD, keyed to `"scavenger"`).
- Open parity gap: reward loot pools still map to base crate tables; compare against Wolds pack at final loot-pool review step.

## 2026-05-07 Prompt 6

User asked for a breakdown of Haunted Braziers reward crate loot pools by level, listing items and weights in each tier.

Checked Wolds pack files:

- `config/the_vault/loot_table.json`
- `config/the_vault/gen/1.0/loot_tables/base_crate_cursed_0.json`
- `config/the_vault/gen/1.0/loot_tables/base_crate_cursed_20.json`
- `config/the_vault/gen/1.0/loot_tables/haunted_braziers_crate_50.json`
- `config/the_vault/gen/1.0/loot_tables/haunted_braziers_crate_100.json`

No files edited for this prompt.

## 2026-05-07 Prompt 12 – Ballistic Bingo sliding_timed_target fix

User reported that sliding_timed_target tasks (Multi Kill, Rapidly Kill Dwellers, Rapidly Kill Guardians) never register kills, while regular target tasks (Kill Guardians) work.

Root cause identified:

- `sliding_timed_target` counters have a `window` field. At runtime VH serializes the live window state as a `frames` array inside `window`. This corrupts the `ballistic_bingo.json` config file for subsequent loads — VH tries to deserialize the `frames` data as a config and fails silently, leaving all `sliding_timed_target` tasks permanently broken.
- Regular `target` counters are not affected.

Fix implemented in `MixinModConfigs`:

- Added `repairBallisticBingoConfig(configDir.resolve("ballistic_bingo.json"))` called from the HEAD injection of `ModConfigs.register()`.
- `repairBallisticBingoConfig` detects whether the on-disk `ballistic_bingo.json` has any `sliding_timed_target` entry with both a `frames` key and a primitive `window` value (the runtime corruption signature).
- If corruption is detected, it overwrites the file with the bundled `default_configs/ballistic_bingo.json`.
- `hasSerializedRuntimeTimedCounters(JsonElement)` does the recursive check.

Files changed:

- `src/main/java/xyz/iwolfking/woldsvaults/mixin/the_vault/MixinModConfigs.java`

Build result:

- `.\gradlew.bat classes` — BUILD SUCCESSFUL.

## 2026-05-07 Prompt 13 – Crystal seal application fix (vault_crystal.json merge)

User reported: unable to apply the Seal of the Psychopath (`woldsvaults:crystal_seal_unhinged`) to a vault crystal.

All of the following were already correct and were NOT the cause:

- `ModItems.CRYSTAL_SEAL_UNHINGED` is instantiated with `WoldsVaults.id("crystal_seal_unhinged")` and registered with the Forge item registry (line 274 of `ModItems.java`).
- `vhapi_configs/objective_seals.json` SEALS map already contained the `woldsvaults:crystal_seal_unhinged` block (committed, no git diff).
- `ModCustomVaultObjectiveEntries.UNHINGED_SCAVENGER_HUNT` is registered with Forge.
- `UnhingedScavengerCrystalObjective` has a working no-arg constructor, `readJson`, and `writeJson`.

Root cause identified (VHAPI reload behavior):

VHAPI stores manually-registered configs (from `addManualConfigFile`) in `VHAPIDataLoader.JSON_DATA`. However:

1. `JSON_DATA` is cleared and rebuilt from data packs on every `apply()` call (every server start and every `/reload`).
2. `initProcessors()` — which triggers `SetupEvents.addManualConfigs()` via the `MixinLoaderRegistry` injection — is guarded by `isInitialized` and only runs once per JVM session.
3. On the first server start of a session, `initProcessors()` runs, manual configs are re-added to `JSON_DATA`, `processMatchingConfigs()` populates `CUSTOM_CONFIGS`, and `afterConfigsLoad()` merges custom seals into in-memory `VAULT_CRYSTAL.SEALS`. This works.
4. On `/reload` within the same session, `initProcessors()` is skipped. `JSON_DATA` no longer contains manual configs. `CUSTOM_CONFIGS` is rebuilt empty. `afterConfigsLoad()` has nothing to merge. VH then re-reads `vault_crystal.json` from disk (which never had the custom seals written to it), resetting `VAULT_CRYSTAL.SEALS` to the vanilla set. All custom seals break.

Fix implemented in `MixinModConfigs`:

- Added `mergeObjectiveSealsIntoVaultCrystal(configDir.resolve("vault_crystal.json"))` call to the existing HEAD injection of `ModConfigs.register()` (same injection that handles entity_groups, vault_mobs, ballistic_bingo).
- `mergeObjectiveSealsIntoVaultCrystal` reads the `SEALS` object from the bundled `vhapi_configs/objective_seals.json` (single source of truth, no duplicate file needed) and for each seal key not already present in the on-disk `vault_crystal.json`, adds it and writes the file back.
- The merge is a no-op if `vault_crystal.json` does not yet exist (first-ever launch before VH creates it; VHAPI's in-memory mechanism covers that first load).
- On all subsequent runs and on every `/reload`, the seals are in `vault_crystal.json` before VH reads it, so `VAULT_CRYSTAL.SEALS` is always correct.
- All seven custom seals are covered: spirits (haunted_braziers), zealot, doomsayer (scaling_ballistic_bingo), titan (brutal_bosses), enchanter (enchanted_elixir), alchemy, unhinged (unhinged_scavenger).

Files changed:

- `src/main/java/xyz/iwolfking/woldsvaults/mixin/the_vault/MixinModConfigs.java`

No new bundled resource files were needed. The method reads from the already-bundled `vhapi_configs/objective_seals.json`.

Build result:

- `.\gradlew.bat classes` — BUILD SUCCESSFUL.

Confirmed working in-game by user.

## 2026-05-07 Prompt 14 – Unhinged Scavenger items/tasks parity

User reported Claude Code had already done most Unhinged Scavenger cleanup from the earlier handoff prompt, but the log was missing that first prompt/task list. User asked to pull the scav items and tasks from Wolds official/pack into standalone.

Initial findings:

- Standalone already has the official Wolds scav item model/texture assets for the custom gemstone and ender scav items. Hash comparison found no missing or differing assets among the official model/texture files.
- Standalone `src/main/resources/default_configs/unhinged_scavenger.json` was stale and much smaller than the Wolds pack config.
- Standalone config used legacy top-level `levels`; Wolds pack uses current `levelPools`.
- Wolds pack config includes the expanded task entries and multiplier overrides for the custom scav items, including the ender scav items.

Planned changes:

- Replace bundled standalone `default_configs/unhinged_scavenger.json` with the Wolds pack config from `E:\Git Repo's\Wolds-Vaults-Pack\config\the_vault\unhinged_scavenger.json`.
- Add a startup repair in `MixinModConfigs` that replaces an existing generated `config/the_vault/unhinged_scavenger.json` only when it is missing or still has the legacy `levels` shape, so current test instances receive the new Wolds task config.
- Do not edit Unhinged Scavenger reward loot table files.

Implemented:

- Replaced `src/main/resources/default_configs/unhinged_scavenger.json` with the Wolds pack `unhinged_scavenger.json`.
- Verified the copied file hash matches the Wolds pack source exactly.
- The new config has 7 task groups and 7 `levelPools` entries, and no legacy top-level `levels` key.
- Added `repairUnhingedScavengerConfig(...)` in `MixinModConfigs.register()` HEAD setup.
- Added `hasLegacyScavengerLevels(...)` detection so existing on-disk configs are replaced only when missing, invalid, or still using the old `levels` shape.
- Did not edit reward loot table files.

Build result:

- `.\gradlew.bat build` — BUILD SUCCESSFUL.
- Confirmed the built jar contains `default_configs/unhinged_scavenger.json` and `MixinModConfigs.class`.

## 2026-05-07 Prompt 15 – Unhinged Scavenger stale client config

User reported Unhinged Scavenger vault tasks still list dyed wool and golden apples, and asked whether an old client config needs to be deleted.

Findings:

- Bundled `src/main/resources/default_configs/unhinged_scavenger.json` contains no `wool`, `golden_apple`, or `golden apple` entries.
- Existing Prism test config at `C:\Users\Ethan\AppData\Roaming\PrismLauncher\instances\NewPack Test\minecraft\config\the_vault\unhinged_scavenger.json` exists and differs from the bundled config.
- That client file already has top-level `levelPools`, but only 3 task groups and 1 level-pool group. The bundled Wolds pack config has 7 task groups and 7 level-pool groups.
- Previous repair only replaced missing/invalid/legacy `levels` files, so this stale-but-new-shape config was skipped.

Planned change:

- Update the startup repair to replace an existing `unhinged_scavenger.json` whenever its parsed JSON differs from the bundled Wolds config.

Implemented:

- Updated `repairUnhingedScavengerConfig(...)` to parse the bundled default config and compare it to the on-disk config.
- If the on-disk config is missing, invalid, or differs from the bundled Wolds config, it rewrites the on-disk file from the bundled config.
- Added shared helper methods `readBundledJson(...)` and `readJson(...)`.

Build result:

- `.\gradlew.bat build` — BUILD SUCCESSFUL.

Testing note:

- With this jar installed, manual deletion of the old client config should not be required. On next startup, `config/the_vault/unhinged_scavenger.json` should be replaced before Vault Hunters reads configs.

## 2026-05-07 Prompt 16 – Unhinged Scavenger retest and log spam

User reported the Unhinged Scavenger task still showed wool and golden apple items, but noted they may have pasted the rebuilt jar without restarting the client. User also reported heavy in-vault log spam:

`Clear, cached size: 2, new size: 3`

Findings:

- The installed Prism jar `woldsvaultsstandalone-0.22.7-all.jar` exists with timestamp `2026-05-07 11:49:32 AM`.
- The installed jar contains the correct bundled `default_configs/unhinged_scavenger.json`.
- The installed jar bundled config hash matches the standalone source config and contains no `wool` or `golden_apple`.
- The live Prism generated file `config/the_vault/unhinged_scavenger.json` still differs from the bundled config and still contains wool/golden apple entries.
- This is consistent with the client not having been restarted after the new jar was installed; the startup repair runs during Vault Hunters config registration, not while the old JVM is still running.
- Exact log spam string was not found in standalone source, Wolds official source, Wolds pack files, or the checked `latest.log` at the time of inspection.
- Broad binary scan across all installed mod jars timed out.
- Narrow binary search did not find the exact string in the installed standalone jar or `the_vault-1.18.2-3.21.0.jar`.
- Search across plain `.log` files in the Prism logs directory did not find the string.

No code changes were made for this prompt.

## 2026-05-07 Prompt 20 – Remove Unhinged Scav config troubleshooting hook

User confirmed the manual/unsup-side approach is the desired path and asked to remove the mixin-based config fix attempt and any no-longer-required troubleshooting for it.

Changes made:

- Removed the `repairUnhingedScavengerConfig(configDir.resolve("unhinged_scavenger.json"))` call from `MixinModConfigs.register()`.
- Removed the helper methods that existed only for that troubleshooting path:
  - `repairUnhingedScavengerConfig(...)`
  - `readBundledJson(...)`
  - `readJson(...)`

Left intact:

- `mergeDefaultVaultMobs(...)`
- `repairBallisticBingoConfig(...)`
- `mergeObjectiveSealsIntoVaultCrystal(...)`

Build result:

- `.\gradlew.bat classes` — BUILD SUCCESSFUL.

## 2026-05-07 Prompt 19 – Correct source-of-truth check for Unhinged Scav item pool

User pointed out that the manually copied file was still using base VH scav items rather than the expected Unhinged Scav item pool.

Findings:

- The copied file was not malformed; it matches the current Wolds pack `config/the_vault/unhinged_scavenger.json`.
- That pack config itself starts with base VH scav chest and coin-stack items (`ripped_page`, `old_book`, `red_scroll`, etc.).
- The same config does include Wolds-specific scav items for ore and mob tasks later in the file (`scavenger_benitoite_gemstone`, `scavenger_enderman_eye`, `scavenger_ender_heart`, etc.).
- The separate Wolds official data file `data/woldsvaults/vault_configs/vault_diffuser/unhinged_scavenger.json` is a different config entirely; it is a diffuser-output value map, not the scavenger objective task-generation config.

Conclusion:

- I copied the Wolds pack scavenger objective config correctly, but that file is not a fully custom-only scav item pool.
- If the intended `unsup` file should use only custom Wolds scav items, that will need to be authored as a new config variant rather than copied verbatim from the current pack `unhinged_scavenger.json`.

No code changes were made for this prompt.

## 2026-05-07 Prompt 18 – Manual Unhinged Scavenger config generation

User decided to stop relying on automatic regeneration for `unhinged_scavenger.json` and asked to generate the correct file manually once so it can be included with the `unsup` repo.

Planned action:

- Copy the known-good Wolds Unhinged Scavenger config into the Prism instance at `config/the_vault/unhinged_scavenger.json`.
- Do not change mod code for this step.

Completed:

- Copied `src/main/resources/default_configs/unhinged_scavenger.json` into `C:\Users\Ethan\AppData\Roaming\PrismLauncher\instances\NewPack Test\minecraft\config\the_vault\unhinged_scavenger.json`.
- Verified the client config hash matches the source config exactly.
- Verified the client config contains no `wool` or `golden_apple` entries.
- Verified the client config has 7 task groups and 7 `levelPools` entries.

No code changes were made for this prompt.

## 2026-05-07 Prompt 17 – Confirm Unhinged Scavenger default generation path

User confirmed the client `unhinged_scavenger.json` kept regenerating with wool/golden apple entries after deleting it and restarting with the rebuilt jar. User asked to double check whether the generated file is actually coming from this mod's bundled default config.

Findings:

- `src/main/resources/default_configs/unhinged_scavenger.json` is present in source and bundled into the built jar.
- `UnhingedScavengerConfig` does not override `readConfig()`; it only overrides `getName()` and calls `super.reset()`.
- `ModConfigs.register()` currently loads `UNHINGED_SCAVENGER` through `new UnhingedScavengerConfig().readConfig()`.
- Decompiling `iskallia.vault.config.Config.readConfig()` from `the_vault-1.18.2-3.21.0.jar` shows:
  - it reads `config/the_vault/<name>.json` if present
  - if missing, it calls `generateConfig()`
  - `generateConfig()` calls `reset()` and `writeConfig()`
- Decompiling `iskallia.vault.config.ScavengerConfig.reset()` shows the built-in vanilla scavenger defaults are hardcoded there (wool items and golden apple style defaults), and those are what get written when the config is generated by VH.

Conclusion:

- The regenerated client `config/the_vault/unhinged_scavenger.json` is not being generated from this mod's bundled `default_configs/unhinged_scavenger.json`.
- It is being generated from Vault Hunters `ScavengerConfig.reset()` defaults unless the mod explicitly reads/copies/parses the bundled config first.

No code changes were made for this prompt.
