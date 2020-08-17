package com.reoar.tabletweaks.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.EnchantmentContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.IWorldPosCallable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Surrogate;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.ParametersAreNonnullByDefault;

@Mixin(net.minecraft.inventory.container.EnchantmentContainer.class)
public class EnchantmentContainerMixin extends Container {

    @Mutable
    @Final
    @Shadow
    private final IWorldPosCallable worldPosCallable;

    protected EnchantmentContainerMixin(ContainerType<?> type, int id, IInventory inventory, IWorldPosCallable worldPosCallable) {
        super(type, id);
        this.worldPosCallable = worldPosCallable;
    }

    @Inject(method = "<init>*", at = @At("RETURN"))
    public void open(int id, PlayerInventory playerInventory, IWorldPosCallable worldPosCallable, CallbackInfo ci) {
        ListNBT tags = playerInventory.write(new ListNBT());
        for (int i = 0; i < tags.size(); ++i) {
            CompoundNBT compoundNBT = tags.getCompound(i);
            if (compoundNBT.contains("LapisReserve")) {
                ItemStack lapis = ItemStack.read(compoundNBT);
                this.inventorySlots.get(1).putStack(lapis);
                break;
            }
        }
    }

    @Inject(method = "onContainerClosed", at = @At("HEAD"), cancellable = true)
    public void close(PlayerEntity player, CallbackInfo info) {
        ListNBT tag = player.inventory.write(new ListNBT());
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putByte("LapisReserve", (byte) 0);
        this.inventorySlots.get(1).getStack().write(compoundNBT);
        for (int i = 0; i < tag.size(); ++i) {
            if (tag.getCompound(i).contains("LapisReserve")) {
                tag.set(i, compoundNBT);
                break;
            }
        }
        player.inventory.read(tag);
        super.onContainerClosed(player);
        this.worldPosCallable.consume((world, blockPos) -> player.inventory.placeItemBackInInventory(world, this.inventorySlots.get(0).getStack()));
        info.cancel();

    }

    @Surrogate
    @ParametersAreNonnullByDefault
    public boolean canInteractWith(PlayerEntity playerIn) {
        return false;
    }
}
