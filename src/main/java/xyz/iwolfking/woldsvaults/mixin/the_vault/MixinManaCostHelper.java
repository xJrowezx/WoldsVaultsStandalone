package xyz.iwolfking.woldsvaults.mixin.the_vault;

import iskallia.vault.VaultMod;
import iskallia.vault.gear.attribute.type.VaultGearAttributeTypeMerger;
import iskallia.vault.skill.base.Skill;
import iskallia.vault.snapshot.AttributeSnapshot;
import iskallia.vault.snapshot.AttributeSnapshotHelper;
import iskallia.vault.util.calc.ManaCostHelper;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.woldsvaults.init.ModGearAttributes;


@Mixin(value = ManaCostHelper.class, remap = false)
public abstract class MixinManaCostHelper {


    @Inject(method = "adjustManaCost(Lnet/minecraft/server/level/ServerPlayer;Liskallia/vault/skill/base/Skill;F)F", at = @At("TAIL"), cancellable = true)
    private static void costReduction(ServerPlayer player, Skill skill, float cost, CallbackInfoReturnable<Float> cir) {
        float finalCost = cir.getReturnValueF();

        AttributeSnapshot snapshot = AttributeSnapshotHelper.getInstance().getSnapshot(player);

        float reduction = snapshot.getAttributeValue(ModGearAttributes.MANA_COST_REDUCTION, VaultGearAttributeTypeMerger.floatSum());

        // clamp reduction to [0, 0.95] so nothing goes negative
        reduction = Math.max(0.0F, Math.min(reduction, 0.95F));

        float newCost = finalCost * (1.0F - reduction);

        cir.setReturnValue(Math.max(0.0F, newCost));
    }
}
