package xyz.iwolfking.woldsvaults.mixin.the_vault.trinketer;

import iskallia.vault.gear.trinket.TrinketHelper;
import iskallia.vault.gear.trinket.effects.DamageImmunityTrinket;
import iskallia.vault.init.ModEffects;
import iskallia.vault.skill.base.Skill;
import iskallia.vault.skill.expertise.type.TrinketerExpertise;
import iskallia.vault.world.data.PlayerExpertisesData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = DamageImmunityTrinket.class, remap = false)
public class DamageImmunityTrinketMixin {

    @Inject(method = "lambda$onPotionEffect$0", at = @At("HEAD"))
    private static void addTimeImmunity(PotionEvent.PotionApplicableEvent event, TrinketHelper.TrinketStack<?> immunityTrinket, CallbackInfo ci) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        int levels = PlayerExpertisesData.get(player.getLevel()).getExpertises(player).getAll(TrinketerExpertise.class, Skill::isUnlocked).stream().mapToInt(expertise -> expertise.getSpentLearnPoints() / expertise.getLearnPointCost()).sum();

        if (levels < 3) {
            return;
        }

        if (immunityTrinket.trinket().getRegistryName() != null && immunityTrinket.trinket().getRegistryName().getPath().equals("carapace") && event.getPotionEffect().getEffect() != ModEffects.TIMER_ACCELERATION) {
            return;
        }

        event.setResult(Event.Result.DENY);
    }
}
