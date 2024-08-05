package com.mclegoman.mclm_quickconnect.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.CommonInputs;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ClickableWidgetStateTextures;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public abstract class QuickConnectPressableWidget extends ClickableWidget {
	private static final ClickableWidgetStateTextures TEXTURES = new ClickableWidgetStateTextures(Identifier.of("mclm_quickconnect", "widget/button"), Identifier.ofDefault("widget/button_disabled"), Identifier.of("mclm_quickconnect", "widget/button_highlighted"));
	public QuickConnectPressableWidget(int i, int j, int k, int l, Text text) {
		super(i, j, k, l, text);
	}

	public abstract void onPress();

	protected void drawWidget(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		MinecraftClient minecraftClient = MinecraftClient.getInstance();
		graphics.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
		RenderSystem.enableBlend();
		RenderSystem.enableDepthTest();
		graphics.drawGuiTexture(TEXTURES.getTexture(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight());
		graphics.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		int i = this.active ? 16777215 : 10526880;
		this.drawScrollableText(graphics, minecraftClient.textRenderer, i | MathHelper.ceil(this.alpha * 255.0F) << 24);
	}

	public void drawScrollableText(GuiGraphics graphics, TextRenderer renderer, int color) {
		this.drawScrollingText(graphics, renderer, 2, color);
	}

	public void onClick(double mouseX, double mouseY) {
		this.onPress();
	}

	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (this.active && this.visible) {
			if (CommonInputs.isToggle(keyCode)) {
				this.playDownSound(MinecraftClient.getInstance().getSoundManager());
				this.onPress();
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
