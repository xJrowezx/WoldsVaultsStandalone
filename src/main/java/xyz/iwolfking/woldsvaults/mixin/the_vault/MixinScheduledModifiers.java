package xyz.iwolfking.woldsvaults.mixin.the_vault;

import iskallia.vault.core.random.JavaRandom;
import iskallia.vault.core.random.RandomSource;
import iskallia.vault.core.vault.ScheduledModifiers;
import iskallia.vault.core.vault.Vault;
import iskallia.vault.core.vault.influence.VaultGod;
import iskallia.vault.core.vault.time.TickClock;
import iskallia.vault.core.vault.time.TickStopwatch;
import iskallia.vault.gear.attribute.custom.RandomGodVaultModifierAttribute;
import iskallia.vault.gear.attribute.talent.RandomVaultModifierAttribute;
import iskallia.vault.gear.attribute.type.VaultGearAttributeTypeMerger;
import iskallia.vault.init.ModGearAttributes;
import iskallia.vault.snapshot.AttributeSnapshot;
import iskallia.vault.snapshot.AttributeSnapshotHelper;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.UUID;

@Mixin(value = ScheduledModifiers.class, remap = false)
public class MixinScheduledModifiers {
    @Shadow @Final private List<ScheduledModifiers.Entry> cache;

    /**
     * @author iwolfking
     * @reason Test setting vault god for temporal modifiers
     */
    @Overwrite
    public void onJoin(Vault vault, ServerPlayer player) {
        RandomSource random = JavaRandom.ofScrambled(player.getUUID().getLeastSignificantBits() ^ ((UUID) vault.get(Vault.ID)).getMostSignificantBits());
        TickClock clock = vault.get(Vault.CLOCK);
        int activationWindow = clock.get(TickClock.LOGICAL_TIME) + clock.get(TickClock.DISPLAY_TIME);

        // Stopwatch vaults (Herald, Hollow Court) start at 0, so use their limit when present; nextInt needs a positive bound.
        if (clock instanceof TickStopwatch stopwatch && stopwatch.has(TickStopwatch.LIMIT)) {
            activationWindow = Math.max(activationWindow, stopwatch.get(TickStopwatch.LIMIT));
        }

        activationWindow = Math.max(1, activationWindow);
        AttributeSnapshot snapshot = AttributeSnapshotHelper.getInstance().getSnapshot(player);

        for (RandomVaultModifierAttribute attribute : snapshot.getAttributeValue(ModGearAttributes.RANDOM_VAULT_MODIFIER, VaultGearAttributeTypeMerger.asList())) {
            int activationTime = random.nextInt(activationWindow);
            this.cache.add(new ScheduledModifiers.Entry(attribute.getModifier(), attribute.getCount(), attribute.getTime(), activationTime).setVaultGod(VaultGod.IDONA));
        }

        for (RandomGodVaultModifierAttribute attribute : snapshot.getAttributeValue(ModGearAttributes.RANDOM_VAULT_GOD_MODIFIER, VaultGearAttributeTypeMerger.asList())) {
            int activationTime = random.nextInt(activationWindow);
            this.cache.add((new ScheduledModifiers.Entry(attribute.getModifier(), attribute.getCount(), attribute.getTime(), activationTime)).setVaultGod(attribute.getVaultGod()));
        }

    }
}
