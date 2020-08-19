package com.reoar.tabletweaks;

import com.reoar.tabletweaks.config.TableTweaksConfig;
import com.reoar.tabletweaks.gui.TableTweaksRerollButtonExec;
import com.reoar.tabletweaks.packets.PacketReroll;
import net.minecraft.client.gui.screen.EnchantmentScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TableTweaks.MODID)
public class TableTweaks {
    // Directly reference a log4j logger.

    public static final String MODID = "tabletweaks";
    public static final Logger LOGGER = LogManager.getLogger();
    private static final String CHANNEL = "channel";
    private static final String NET_VERS = "1";
    public static SimpleChannel NET_INST;


    public TableTweaks() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        TableTweaksConfig.loadConfig(TableTweaksConfig.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("tabletweaks-client.toml"));
        TableTweaksConfig.loadConfig(TableTweaksConfig.SERVER_CONFIG, FMLPaths.CONFIGDIR.get().resolve("tabletweaks-server.toml"));
    }


    private void setup(final FMLCommonSetupEvent event) {
        NET_INST =
                NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, CHANNEL), () -> NET_VERS, NET_VERS::equals, NET_VERS::equals);
        NET_INST.registerMessage(1, PacketReroll.class, PacketReroll::encode, PacketReroll::decode, PacketReroll::reroll);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        LOGGER.info("Tweaking your Enchanting Table!");
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onGuiInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof EnchantmentScreen) {
            EnchantmentScreen gui = (EnchantmentScreen) event.getGui();
            event.addWidget(new TableTweaksRerollButtonExec(gui.getGuiLeft() + 59, gui.getGuiTop() + 4));
        }
    }
}
