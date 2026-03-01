package xyz.iwolfking.woldsvaults.mixin.the_vault.trinketer;

import iskallia.vault.gear.attribute.VaultGearAttributeInstance;
import iskallia.vault.gear.trinket.TrinketHelper;
import iskallia.vault.skill.base.Skill;
import iskallia.vault.skill.expertise.type.TrinketerExpertise;
import iskallia.vault.snapshot.AttributeSnapshot;
import iskallia.vault.snapshot.AttributeSnapshotCalculator;
import iskallia.vault.world.data.PlayerExpertisesData;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AttributeSnapshotCalculator.class, remap = false)
public class AttributeSnapshotCalculatorMixin {

    @Unique
    private static ServerPlayer woldsVaults_Standalone$player;

    @Inject(method = "lambda$computeCuriosSnapshot$2", at = @At("HEAD"))
    private static void storeServerPlayer(ServerPlayer player, AttributeSnapshot snapshot, TrinketHelper.TrinketStack gearTrinket, CallbackInfo ci) {
        woldsVaults_Standalone$player = player;
    }

    @Redirect(method = "lambda$computeCuriosSnapshot$4", at = @At(value = "INVOKE", target = "Liskallia/vault/gear/attribute/VaultGearAttributeInstance;getValue()Ljava/lang/Object;"))
    private static Object enhanceAttributes(VaultGearAttributeInstance instance) {
        ServerPlayer player = woldsVaults_Standalone$player;

        float enhancement = (float) PlayerExpertisesData.get(player.getLevel()).getExpertises(player).getAll(TrinketerExpertise.class, Skill::isUnlocked).stream().mapToDouble(TrinketerExpertise::getDamageAvoidanceChance).sum();

        if (instance.getValue() instanceof Float base) {
            return base * (1f + enhancement);
        }
        return instance.getValue();
    }

}
