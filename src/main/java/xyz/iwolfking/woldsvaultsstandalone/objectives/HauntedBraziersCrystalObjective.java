package xyz.iwolfking.woldsvaultsstandalone.objectives;

import com.google.gson.JsonObject;
import iskallia.vault.block.VaultCrateBlock;
import iskallia.vault.core.data.adapter.Adapters;
import iskallia.vault.core.random.RandomSource;
import iskallia.vault.core.vault.ClassicPortalLogic;
import iskallia.vault.core.vault.Vault;
import iskallia.vault.core.vault.VaultLevel;
import iskallia.vault.core.vault.objective.*;
import iskallia.vault.core.world.roll.IntRoll;
import iskallia.vault.item.crystal.CrystalData;
import iskallia.vault.item.crystal.objective.CrystalObjective;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.TooltipFlag;
import xyz.iwolfking.woldsvaultsstandalone.init.ModConfigs;

import java.util.List;
import java.util.Optional;

public class HauntedBraziersCrystalObjective extends CrystalObjective {
    protected IntRoll target;
    protected float objectiveProbability;

    public HauntedBraziersCrystalObjective() {
    }

    public HauntedBraziersCrystalObjective(IntRoll target, float objectiveProbability) {
        this.target = target;
        this.objectiveProbability = objectiveProbability;
    }

    @Override
    public void configure(Vault vault, RandomSource random) {
        int level = ((VaultLevel)vault.get(Vault.LEVEL)).get();
        vault.ifPresent(Vault.OBJECTIVES, (objectives) -> {
            objectives.add(HauntedBraziersObjective.of(this.target.get(random), this.objectiveProbability, ModConfigs.HAUNTED_BRAZIERS.getStackModifierPool(level),  ModConfigs.HAUNTED_BRAZIERS.getOverStackModifierPool(level), ModConfigs.HAUNTED_BRAZIERS.getOverStackLootTable(level)).add(FindExitObjective.create(new ResourceLocation[]{ClassicPortalLogic.EXIT}).add(AwardCrateObjective.ofConfig(VaultCrateBlock.Type.MONOLITH, "haunted_braziers", level, true))));
            objectives.add(BailObjective.create(true, new ResourceLocation[]{ClassicPortalLogic.EXIT}));
            objectives.add(DeathObjective.create(true));
            objectives.set(Objectives.KEY, CrystalData.OBJECTIVE.getType(this));
        });
    }

    @Override
    public void addText(List<Component> tooltip, int minIndex, TooltipFlag flag, float time) {
        tooltip.add((new TextComponent("Objective: ")).append((new TextComponent("Light the Haunted Braziers")).withStyle(Style.EMPTY.withColor((Integer)this.getColor(time).orElseThrow()))));
    }


    @Override
    public Optional<Integer> getColor(float time) {
        return Optional.of(11452927);
    }

    @Override
    public Optional<CompoundTag> writeNbt() {
        CompoundTag nbt = new CompoundTag();
        Adapters.INT_ROLL.writeNbt(this.target).ifPresent((target) -> {
            nbt.put("target", target);
        });
        Adapters.FLOAT.writeNbt(this.objectiveProbability).ifPresent((tag) -> {
            nbt.put("objective_probability", tag);
        });
        return Optional.of(nbt);
    }

    @Override
    public void readNbt(CompoundTag nbt) {
        this.target = (IntRoll)Adapters.INT_ROLL.readNbt(nbt.getCompound("target")).orElse((IntRoll) null);
        this.objectiveProbability = (Float)Adapters.FLOAT.readNbt(nbt.get("objective_probability")).orElse(0.0F);
    }

    @Override
    public Optional<JsonObject> writeJson() {
        JsonObject json = new JsonObject();
        Adapters.INT_ROLL.writeJson(this.target).ifPresent((target) -> {
            json.add("target", target);
        });
        Adapters.FLOAT.writeJson(this.objectiveProbability).ifPresent((tag) -> {
            json.add("objective_probability", tag);
        });
        return Optional.of(json);
    }

    @Override
    public void readJson(JsonObject json) {
        this.target = (IntRoll)Adapters.INT_ROLL.readJson(json.getAsJsonObject("target")).orElse((IntRoll) null);
        this.objectiveProbability = (Float)Adapters.FLOAT.readJson(json.get("objective_probability")).orElse(0.0F);
    }
}
