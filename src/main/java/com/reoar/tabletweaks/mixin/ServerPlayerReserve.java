package com.reoar.tabletweaks.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Surrogate;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerReserve extends PlayerEntity {
    public ServerPlayerReserve(World world, BlockPos blockPos, float i, GameProfile gameProfile) {
        super(world, blockPos, i, gameProfile);
    }

    @Inject(method = "copyFrom", at = @At("TAIL"))
    public void copyFrom(ServerPlayerEntity that, boolean keepEverything, CallbackInfo ci) {
        ListNBT tags = that.inventory.write(new ListNBT());
        for (int i = 0; i < tags.size(); ++i) {
            CompoundNBT compoundNBT = tags.getCompound(i);
            if (compoundNBT.contains("LapisReserve")) {
                ListNBT tag = this.inventory.write(new ListNBT());
                for (int j = 0; j < tags.size(); ++j) {
                    CompoundNBT compoundNBTj = tag.getCompound(j);
                    if (compoundNBTj.contains("LapisReserve")) {
                        tag.set(j, compoundNBT);
                        break;
                    }
                }
                this.inventory.read(tag);
                break;
            }
        }
    }

    @Surrogate
    public boolean isSpectator() {
        return false;
    }

    @Surrogate
    public boolean isCreative() {
        return false;
    }
}
