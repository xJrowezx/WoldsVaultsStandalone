package xyz.iwolfking.woldsvaults.init;

import iskallia.vault.VaultMod;
import iskallia.vault.gear.trinket.TrinketEffect;
import iskallia.vault.gear.trinket.effects.AttributeTrinket;
import iskallia.vault.gear.trinket.effects.VanillaAttributeTrinket;
import iskallia.vault.init.ModGearAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;


public class ModTrinkets {
    private static final VanillaAttributeTrinket RUNNING_SHOES;

    public static void init(RegistryEvent.Register<TrinketEffect<?>> event) {
        IForgeRegistry<TrinketEffect<?>> registry = event.getRegistry();
        registry.register(RUNNING_SHOES);
    }

    static {
        RUNNING_SHOES =  new VanillaAttributeTrinket(VaultMod.id("running_shoes"), Attributes.MOVEMENT_SPEED, 0.40f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

}
