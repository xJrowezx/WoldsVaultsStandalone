package xyz.iwolfking.woldsvaults.mixin.the_vault.trinketer;

import iskallia.vault.client.data.ClientExpertiseData;
import iskallia.vault.skill.tree.ExpertiseTree;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ClientExpertiseData.class, remap = false)
public interface AccessorClientExpertiseData {

    @Accessor("EXPERTISES")
    ExpertiseTree getExpertises();
}
