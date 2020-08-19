package com.reoar.tabletweaks.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.reoar.tabletweaks.TableTweaks;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.client.gui.widget.ExtendedButton;

import javax.annotation.ParametersAreNonnullByDefault;

public class TableTweaksRerollButton extends ExtendedButton {
    protected final ResourceLocation button =
            new ResourceLocation(TableTweaks.MODID, "textures/gui/button_sprites.png");
    private final int tx;
    private final int ty;

    // TODO make the button display the amount of lapis lazuli and levels it will use.

    public TableTweaksRerollButton(int x, int y, int tx, int ty, IPressable handler) {
        super(x, y, 9, 9, new StringTextComponent(""), handler);
        this.tx = tx;
        this.ty = ty;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        isHovered =
                this.active
                        && this.visible
                        && mouseX >= this.x
                        && mouseY >= this.y
                        && mouseX < this.x + this.width
                        && mouseY < this.y + this.height;
        Minecraft.getInstance().getTextureManager().bindTexture(button);
        blit(matrixStack, x, y, tx, ty + (isHovered ? 9 : 0), 9, 9);
    }
}

