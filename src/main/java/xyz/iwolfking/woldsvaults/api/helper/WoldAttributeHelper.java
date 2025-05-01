package xyz.iwolfking.woldsvaults.api.helper;

import iskallia.vault.gear.attribute.type.VaultGearAttributeTypeMerger;
import iskallia.vault.init.ModGearAttributes;
import iskallia.vault.snapshot.AttributeSnapshot;
import iskallia.vault.snapshot.AttributeSnapshotHelper;
import net.minecraft.world.entity.LivingEntity;

public class WoldAttributeHelper {
    public static float getAdditionalAbilityPower(LivingEntity entity) {
        float additionalMultiplier = 0.0F;
        float apMulti = 1.0F;
        AttributeSnapshot snapshot = AttributeSnapshotHelper.getInstance().getSnapshot(entity);
        additionalMultiplier += (Float)snapshot.getAttributeValue(ModGearAttributes.ABILITY_POWER, VaultGearAttributeTypeMerger.floatSum());
        apMulti += snapshot.getAttributeValue(ModGearAttributes.ABILITY_POWER_PERCENT, VaultGearAttributeTypeMerger.floatSum());
        return additionalMultiplier * apMulti;
    }
}
