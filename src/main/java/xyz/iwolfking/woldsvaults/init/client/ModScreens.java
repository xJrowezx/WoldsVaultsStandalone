package xyz.iwolfking.woldsvaults.init.client;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.iwolfking.woldsvaults.blocks.containers.VaultSalvagerScreen;
import xyz.iwolfking.woldsvaults.init.ModContainers;
@OnlyIn(Dist.CLIENT)
public class ModScreens {
    public static void register() {
        MenuScreens.register(ModContainers.VAULT_SALVAGER_CONTAINER, VaultSalvagerScreen::new);
    }
}
