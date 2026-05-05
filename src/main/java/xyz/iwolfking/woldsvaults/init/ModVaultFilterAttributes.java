package xyz.iwolfking.woldsvaults.init;

import xyz.iwolfking.woldsvaults.integration.vaultfilters.*;

public class ModVaultFilterAttributes {
    public static void initAttributes() {
        new HasUnusualAffixAttribute(true).register(HasUnusualAffixAttribute::new);
        new UnusualPrefixAttribute("Attack Damage").register(UnusualPrefixAttribute::new);
        new UnusualSuffixAttribute("Attack Damage").register(UnusualSuffixAttribute::new);
        new AntiqueAttribute("Acquired taste").register(AntiqueAttribute::new);
        new VaultDollCompletedAttribute(true).register(VaultDollCompletedAttribute::new);
    }
}
