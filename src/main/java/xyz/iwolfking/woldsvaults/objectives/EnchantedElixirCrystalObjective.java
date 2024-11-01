package xyz.iwolfking.woldsvaults.objectives;

import com.google.gson.JsonObject;
import iskallia.vault.block.VaultCrateBlock;
import iskallia.vault.core.data.adapter.Adapters;
import iskallia.vault.core.random.RandomSource;
import iskallia.vault.core.vault.ClassicPortalLogic;
import iskallia.vault.core.vault.Vault;
import iskallia.vault.core.vault.VaultLevel;
import iskallia.vault.core.vault.objective.*;
import iskallia.vault.item.crystal.CrystalData;
import iskallia.vault.item.crystal.objective.CrystalObjective;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.Optional;

public class EnchantedElixirCrystalObjective extends CrystalObjective {
    protected float objectiveProbability;

    public EnchantedElixirCrystalObjective() {
    }

    public void configure(Vault vault, RandomSource random) {
        int level = ((VaultLevel)vault.get(Vault.LEVEL)).get();
        vault.ifPresent(Vault.OBJECTIVES, (objectives) -> {
            objectives.add(EnchantedElixirObjective.create().add(LodestoneObjective.of(this.objectiveProbability).add(AwardCrateObjective.ofConfig(VaultCrateBlock.Type.ELIXIR, "enchanted_elixir", level, true)).add(VictoryObjective.of(300))));
            objectives.add(BailObjective.create(true, new ResourceLocation[]{ClassicPortalLogic.EXIT}));
            objectives.add(DeathObjective.create(true));
            objectives.set(Objectives.KEY, CrystalData.OBJECTIVE.getType(this));
        });
    }

    @Override
    public void addText(List<Component> tooltip, int minIndex, TooltipFlag flag, float time) {
        tooltip.add((new TextComponent("Objective: ")).append((new TextComponent("Enchanted Elixir")).withStyle(Style.EMPTY.withColor((Integer)this.getColor(time).orElseThrow()))));
    }

    public Optional<Integer> getColor(float time) {
        return Optional.of(7012807);
    }

    public Optional<CompoundTag> writeNbt() {
        CompoundTag nbt = new CompoundTag();
        Adapters.FLOAT.writeNbt(this.objectiveProbability).ifPresent((tag) -> {
            nbt.put("objective_probability", tag);
        });
        return Optional.of(nbt);
    }

    public void readNbt(CompoundTag nbt) {
        this.objectiveProbability = (Float)Adapters.FLOAT.readNbt(nbt.get("objective_probability")).orElse(0.0F);
    }

    public Optional<JsonObject> writeJson() {
        JsonObject json = new JsonObject();
        Adapters.FLOAT.writeJson(this.objectiveProbability).ifPresent((tag) -> {
            json.add("objective_probability", tag);
        });
        return Optional.of(json);
    }

    public void readJson(JsonObject json) {
        this.objectiveProbability = (Float)Adapters.FLOAT.readJson(json.get("objective_probability")).orElse(0.0F);
    }
}
