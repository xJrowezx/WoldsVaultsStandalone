package xyz.iwolfking.woldsvaultsstandalone.mixin.the_vault;

import iskallia.vault.gear.GearRollHelper;
import iskallia.vault.gear.data.VaultGearData;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.init.ModGearAttributes;
import iskallia.vault.skill.base.Skill;
import iskallia.vault.skill.tree.ExpertiseTree;
import iskallia.vault.world.data.PlayerExpertisesData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.iwolfking.woldsvaultsstandalone.expertises.CraftsmanExpertise;

import java.util.Random;

@Mixin(value = GearRollHelper.class, remap = false)
public class MixinGearRollHelper {
    @Shadow @Final public static Random rand;

    @Inject(method = "canGenerateLegendaryModifier", at = @At(value = "TAIL"), cancellable = true)
    private static void canGenerateLegendaryModifier(Player player, VaultGearData data, CallbackInfoReturnable<Boolean> cir) {
        if(data.equals(VaultGearData.empty())) {
            return;
        }

        if ((Boolean) data.getFirstValue(ModGearAttributes.CRAFTED_BY).isPresent() && rand.nextFloat() < ModConfigs.VAULT_GEAR_CRAFTING_CONFIG.getLegendaryModifierChance()) {
            ExpertiseTree expertises = PlayerExpertisesData.get((ServerLevel) player.getLevel()).getExpertises(player);
            int craftsmanLevel = 0;

            for (CraftsmanExpertise craftsmanExpertise : expertises.getAll(CraftsmanExpertise.class, Skill::isUnlocked)) {
                craftsmanLevel = craftsmanExpertise.getCraftsmanLevel();
            }
            if(craftsmanLevel > 0) {
                cir.setReturnValue(true);
            }

        }

        if(data.getFirstValue(ModGearAttributes.GEAR_ROLL_TYPE_POOL).isPresent()) {
            if(data.getFirstValue(ModGearAttributes.GEAR_ROLL_TYPE_POOL).get().equals("jewel_crafted") && rand.nextFloat() < 0.02F) {
                cir.setReturnValue(true);
            }
        }
    }
}
