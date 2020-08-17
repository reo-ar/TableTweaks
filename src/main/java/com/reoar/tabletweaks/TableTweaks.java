package com.reoar.tabletweaks;

import com.google.common.base.Throwables;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Predicate;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TableTweaks.MODID)
public class TableTweaks {
    // Directly reference a log4j logger.

    public static final String MODID = "tabletweaks";
    public static final String CHANNEL = "channel";
    public static final String PROTOCOL_VERSION = "1";
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Field guiLeftF =
            DistExecutor.unsafeCallWhenOn(
                    Dist.CLIENT,
                    () ->
                            () -> ObfuscationReflectionHelper.findField(ContainerScreen.class, "field_147003_i"));
    private static final Field guiTopF =
            DistExecutor.unsafeCallWhenOn(
                    Dist.CLIENT,
                    () ->
                            () -> ObfuscationReflectionHelper.findField(ContainerScreen.class, "field_147009_r"));
    public static boolean WORLD_IS_REMOTE = false;

    public TableTweaks() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }


    private void setup(final FMLCommonSetupEvent event) {
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    @SuppressWarnings("unused")
    public void onServerStarting(FMLServerStartingEvent event) {
        LOGGER.info("Tweaking your Enchanting Table!");
    }
}
