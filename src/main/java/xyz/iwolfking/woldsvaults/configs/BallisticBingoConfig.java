package xyz.iwolfking.woldsvaults.configs;

import iskallia.vault.config.BingoConfig;
import xyz.iwolfking.vhapi.api.lib.core.readers.CustomVaultConfigReader;
import xyz.iwolfking.vhapi.api.util.JsonUtils;

import java.io.IOException;
import java.io.InputStream;

public class BallisticBingoConfig extends BingoConfig {

    public BallisticBingoConfig() {
    }

    @Override
    public String getName() {
        return "ballistic_bingo";
    }

    @Override
    protected void reset() {
        try (InputStream stream = this.getClass().getResourceAsStream("/default_configs/ballistic_bingo.json")) {
            CustomVaultConfigReader<BallisticBingoConfig> reader = new CustomVaultConfigReader<>();
            if(stream == null) {
                throw new IOException();
            }
            this.pools = reader.readCustomConfig("ballistic_bingo", JsonUtils.parseJsonContentFromStream(stream), BallisticBingoConfig.class).pools;
        } catch (IOException e) {
            System.out.println("Failed to read default Ballistic Bingo config...");
            throw new RuntimeException(e);
        }
    }


}
