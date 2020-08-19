package com.reoar.tabletweaks.packets;

import com.reoar.tabletweaks.config.RerollConfig;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.EnchantmentContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketReroll {
    public static void encode (PacketReroll packet, PacketBuffer buffer) {}

    public static PacketReroll decode (PacketBuffer buffer){
        return new PacketReroll();
    }

    public static void reroll(PacketReroll packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // TODO move these to it's own separate function
            ServerPlayerEntity player = ctx.get().getSender();
            boolean rerollEnabled = RerollConfig.REROLL_ENABLED.get();
            if (rerollEnabled && player != null && player.openContainer instanceof EnchantmentContainer) {
                EnchantmentContainer container = (EnchantmentContainer) player.openContainer;

                ItemStack lapis = container.tableInventory.getStackInSlot(1);
                int playerLevel = player.experienceLevel;
                int lapisPerReroll = RerollConfig.LAPIS_PER_REROLL.get();
                int levelsPerReroll = RerollConfig.LEVELS_PER_REROLL.get();

                if (playerLevel > levelsPerReroll && lapis.getCount() > lapisPerReroll) {
                    IInventory inventory = container.tableInventory;

                    ItemStack newLapisStack = lapis.copy();
                    newLapisStack.shrink(lapisPerReroll);
                    inventory.setInventorySlotContents(1, newLapisStack);

                    player.addExperienceLevel(-levelsPerReroll);
                    player.xpSeed = player.world.rand.nextInt();
                    container.xpSeed.set(player.xpSeed);
                    container.onCraftMatrixChanged(inventory);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
