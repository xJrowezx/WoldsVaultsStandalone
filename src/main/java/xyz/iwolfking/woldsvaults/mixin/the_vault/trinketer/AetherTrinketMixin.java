package xyz.iwolfking.woldsvaults.mixin.the_vault.trinketer;

import iskallia.vault.client.data.ClientExpertiseData;
import iskallia.vault.gear.trinket.effects.AetherTrinket;
import iskallia.vault.skill.base.Skill;
import iskallia.vault.skill.expertise.type.TrinketerExpertise;
import iskallia.vault.skill.tree.ExpertiseTree;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = AetherTrinket.class, remap = false)
public class AetherTrinketMixin {

    @Redirect(method = "onClientTick", at = @At(value = "INVOKE", target = "Liskallia/vault/gear/trinket/effects/AetherTrinket$AetherConfig;getDurationTicks()I"))
    private int addMoreJetpackTime(AetherTrinket.AetherConfig instance) {
        ExpertiseTree tree = ((AccessorClientExpertiseData) new ClientExpertiseData()).getExpertises();
        double enhancement = tree.getAll(TrinketerExpertise.class, Skill::isUnlocked).stream().mapToDouble(TrinketerExpertise::getDamageAvoidanceChance).sum();

        return (int) Math.floor(instance.getDurationTicks() * (1 + enhancement));
    }
}
