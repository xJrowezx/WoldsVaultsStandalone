package xyz.iwolfking.woldsvaults.mixin.the_vault.trinketer;

import iskallia.vault.gear.trinket.TrinketEffect;
import iskallia.vault.gear.trinket.effects.VanillaAttributeTrinket;
import iskallia.vault.skill.base.Skill;
import iskallia.vault.skill.expertise.type.TrinketerExpertise;
import iskallia.vault.world.data.PlayerExpertisesData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Consumer;

@Mixin(value = VanillaAttributeTrinket.class, remap = false)
public abstract class MixinVanillaAttributeTrinket extends TrinketEffect<VanillaAttributeTrinket.Config> {

    @Shadow
    public abstract boolean runIfPresent(LivingEntity entity, Consumer<AttributeInstance> action);

    public MixinVanillaAttributeTrinket(ResourceLocation name) {
        super(name);
    }

    @Redirect(method = "addModifier", at = @At(value = "INVOKE", target = "Liskallia/vault/gear/trinket/effects/VanillaAttributeTrinket;runIfPresent(Lnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)Z"))
    private boolean applyTrinketerEnhancement(VanillaAttributeTrinket instance, LivingEntity entity, Consumer<AttributeInstance> action) {
        if (!(entity instanceof ServerPlayer playerx)) {
            return false;
        }
        double enhancement = PlayerExpertisesData.get(playerx.getLevel()).getExpertises(playerx).getAll(TrinketerExpertise.class, Skill::isUnlocked).stream().mapToDouble(TrinketerExpertise::getDamageAvoidanceChance).sum();

        VanillaAttributeTrinket.Modifier regular = this.getConfig().getModifier();
        VanillaAttributeTrinket.Modifier modified = new VanillaAttributeTrinket.Modifier(regular.name, regular.amount + enhancement, AttributeModifier.Operation.fromValue(regular.operation));

        return this.runIfPresent(playerx, (attributeData) -> attributeData.addTransientModifier(modified.toMCModifier()));

    }

}