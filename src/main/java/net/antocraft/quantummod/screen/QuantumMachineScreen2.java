package net.antocraft.quantummod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.antocraft.quantummod.QuantumMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class QuantumMachineScreen2 extends AbstractContainerScreen<QuantumMachineMenu2> {
    private static final ResourceLocation texture = new ResourceLocation(QuantumMod.MOD_ID, "textures/gui/quantum_machine_2_gui.png"); //TODO this is placeholder

    public QuantumMachineScreen2(QuantumMachineMenu2 menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, texture);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(texture, x, y, 0, 0, imageWidth, imageHeight);
        guiGraphics.fill(x + 15, y + 30, x + 25, y + 60, 0xFF555555);
        guiGraphics.fill(x + 16, y + 31 + (28 - menu.getScaledEnergy()), x + 24, y + 59, 0xFFCC2222);

        renderProgressArrow(guiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isCrafting()) {
            guiGraphics.blit(texture, x + 75, y + 40, 0, 166, menu.getScaledProgress(), 8);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);

        int energy = menu.data.get(2);
        int maxEnergy = menu.data.get(3);

        Component energyTooltip = Component.literal("Energy: " + energy + " / " + maxEnergy);
        if(isHovering(15, 30, 10, 30, mouseX, mouseY)) guiGraphics.renderTooltip(font, energyTooltip, mouseX, mouseY);
    }
}