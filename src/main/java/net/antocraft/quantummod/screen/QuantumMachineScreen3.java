package net.antocraft.quantummod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.antocraft.quantummod.QuantumMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class QuantumMachineScreen3 extends AbstractContainerScreen<QuantumMachineMenu3> {
    private static final ResourceLocation texture = new ResourceLocation(QuantumMod.MOD_ID, "textures/gui/quantum_machine_3_gui.png"); //TODO this is placeholder

    public QuantumMachineScreen3(QuantumMachineMenu3 menu, Inventory playerInventory, Component title) {
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

        renderSlot(guiGraphics, x + 52, y + 36, 0);
        renderSlot(guiGraphics, x + 108, y + 36, 1);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isCrafting()) {
            guiGraphics.blit(texture, x + 75, y + 40, 0, 166, menu.getScaledProgress(), 8);
        }
    }

    private void renderSlot(GuiGraphics guiGraphics, int x, int y, int slot) {
        ItemStack item = itemStack(slot);
        if(!item.isEmpty()) {
            guiGraphics.renderItem(item, x, y);
            guiGraphics.renderItemDecorations(font, item, x, y);
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

        if(isHovering(52, 36, 16, 16, mouseX, mouseY) && !itemStack(0).isEmpty()) guiGraphics.renderTooltip(font, itemStack(0), mouseX, mouseY);
        if(isHovering(108, 36, 16, 16, mouseX, mouseY) && !itemStack(1).isEmpty()) guiGraphics.renderTooltip(font, itemStack(1), mouseX, mouseY);
    }

    private ItemStack itemStack(int slot) {
        return menu.slots.get(slot).getItem();
    }
}