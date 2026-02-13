package xyz.iwolfking.woldsvaults.mixin.the_vault;

import iskallia.vault.core.data.adapter.basic.TypeSupplierAdapter;
import iskallia.vault.skill.base.Skill;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.iwolfking.woldsvaults.abilities.*;
import xyz.iwolfking.woldsvaults.expertises.CraftsmanExpertise;
import xyz.iwolfking.woldsvaults.expertises.EclecticGearExpertise;
import xyz.iwolfking.woldsvaults.expertises.NavigatorExpertise;
import xyz.iwolfking.woldsvaults.expertises.PylonPilfererExpertise;


@Mixin(targets = "iskallia/vault/skill/base/Skill$Adapter", remap = false)
public abstract class MixinSkill extends TypeSupplierAdapter<Skill> {
    public MixinSkill(String key, boolean nullable) {
        super(key, nullable);
    }

    @Inject(method = "<init>()V", at = @At("RETURN"))
    private void addSkills(CallbackInfo ci) {

        this.register("craftsman", CraftsmanExpertise.class, CraftsmanExpertise::new);
        this.register("colossus", ColossusAbility.class,ColossusAbility::new);
        this.register("sneaky_getaway", SneakyGetawayAbility.class,SneakyGetawayAbility::new);
        this.register("vein_miner_chain", VeinMinerChainAbility.class, VeinMinerChainAbility::new);
        this.register("levitate", LevitateAbility.class,LevitateAbility::new);
        this.register("augmentation_luck", EclecticGearExpertise.class, EclecticGearExpertise::new);
        this.register("pylon_pilferer", PylonPilfererExpertise.class, PylonPilfererExpertise::new);
        this.register("navigator", NavigatorExpertise.class, NavigatorExpertise::new);
        this.register("crit_hit_chance", CritHitChanceTalent.class, CritHitChanceTalent::new);
        this.register("crit_hit_damage", CritHitDamageTalent.class, CritHitDamageTalent::new);
    }
}
