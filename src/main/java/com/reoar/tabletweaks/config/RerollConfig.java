package com.reoar.tabletweaks.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class RerollConfig {

    public static ForgeConfigSpec.BooleanValue REROLL_ENABLED;
    public static ForgeConfigSpec.IntValue LAPIS_PER_REROLL;
    public static ForgeConfigSpec.IntValue LEVELS_PER_REROLL;

    public static void init(ForgeConfigSpec.Builder SERVER_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        SERVER_BUILDER.comment("TableTweaks Enchanting Reroll");
        REROLL_ENABLED = SERVER_BUILDER
                .comment("Enables or disables the re-roll function")
                .define("rerollEnabled", true);

        LAPIS_PER_REROLL = SERVER_BUILDER
                .comment("Number of Lapis Lazuli used per Enchantment seed re-roll")
                .defineInRange("lapisPerReroll", 3, 0, 10000000);

        LEVELS_PER_REROLL = SERVER_BUILDER
                .comment("Number of levels used per Enchantment seed re-roll")
                .defineInRange("levelsPerReroll", 1, 0, 10000000);
    }
}
