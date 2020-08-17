package com.reoar.tabletweaks.mixin;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInventory.class)
public class PlayerReserve {
    @Dynamic
    public ItemStack lapisreserve = ItemStack.EMPTY;

    @Inject(method = "write", at = @At("HEAD"))
    public void write(ListNBT tag, @SuppressWarnings("rawtypes") CallbackInfoReturnable info) {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putByte("LapisReserve", (byte) 0);
        lapisreserve.write(compoundNBT);
        tag.add(compoundNBT);
    }

    @Inject(method = "read", at = @At("HEAD"), cancellable = true)
    public void read(ListNBT tag, CallbackInfo info) {
        for (int i = 0; i < tag.size(); ++i) {
            CompoundNBT compoundNBT = tag.getCompound(i);
            if (compoundNBT.contains("LapisReserve")) {
                lapisreserve = ItemStack.read(compoundNBT);
                tag.remove(i);
                break;
            }
        }
    }
}
