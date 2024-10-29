package xyz.iwolfking.woldsvaultsstandalone.mixin.the_vault;

import iskallia.vault.core.data.adapter.basic.TypeSupplierAdapter;
import iskallia.vault.skill.base.Skill;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.iwolfking.woldsvaultsstandalone.expertises.CraftsmanExpertise;


@Mixin(targets = "iskallia/vault/skill/base/Skill$Adapter", remap = false)
public abstract class MixinSkill extends TypeSupplierAdapter<Skill> {
    public MixinSkill(String key, boolean nullable) {
        super(key, nullable);
    }

    @Inject(method = "<init>()V", at = @At("RETURN"))
    private void addSkills(CallbackInfo ci) {

        this.register("craftsman", CraftsmanExpertise.class, CraftsmanExpertise::new);
    }
}
