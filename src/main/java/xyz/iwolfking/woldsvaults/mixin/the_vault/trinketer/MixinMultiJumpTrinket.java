package xyz.iwolfking.woldsvaults.mixin.the_vault.trinketer;

import iskallia.vault.client.data.ClientExpertiseData;
import iskallia.vault.gear.trinket.effects.MultiJumpTrinket;
import iskallia.vault.skill.base.Skill;
import iskallia.vault.skill.expertise.type.TrinketerExpertise;
import iskallia.vault.skill.tree.ExpertiseTree;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = MultiJumpTrinket.class, remap = false)
public class MixinMultiJumpTrinket {

    @ModifyConstant(method = "onClientTick", constant = @Constant(intValue = 2))
    private static int addMoreJumps(int constant) {
        ExpertiseTree tree = ((AccessorClientExpertiseData) new ClientExpertiseData()).getExpertises();
        double enhancement = tree.getAll(TrinketerExpertise.class, Skill::isUnlocked).stream().mapToDouble(TrinketerExpertise::getDamageAvoidanceChance).sum();

        return (int) Math.floor(constant * (1 + enhancement));
    }

}
