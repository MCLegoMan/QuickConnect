package com.mclegoman.mclm_quickconnect.mixin;

import com.mclegoman.mclm_quickconnect.Config;
import com.mclegoman.mclm_quickconnect.Connect;
import com.mclegoman.mclm_quickconnect.screens.QuickConnectMultiplayerWarningScreen;
import com.mclegoman.mclm_quickconnect.widgets.QuickConnectButtonWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.button.ButtonWidget;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
	@Shadow
	@Nullable
	protected abstract Text getBanText();
	protected TitleScreenMixin(Text title) {
		super(title);
	}
	@Redirect(method = "initWidgetsNormal", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;addDrawableSelectableElement(Lnet/minecraft/client/gui/Element;)Lnet/minecraft/client/gui/Element;", ordinal = 2))
	public Element disableRealms(TitleScreen instance, Element element) {
		if (element instanceof ButtonWidget button) {
			button.visible = false;
			button.active = false;
			return button;
		}
		return element;
	}
	@Inject(method = "initWidgetsNormal", at = @At("TAIL"))
	public void init(int y, int spacingY, CallbackInfo ci) {
		Text text = this.getBanText();
		QuickConnectButtonWidget quickConnectWidget = QuickConnectButtonWidget.builder(Text.translatable("menu.quickconnect", Config.instance.name.value()), (button) -> {
			if (MinecraftClient.getInstance().options.skipMultiplayerWarning) Connect.connect(MinecraftClient.getInstance(), this);
			else MinecraftClient.getInstance().setScreen(new QuickConnectMultiplayerWarningScreen(this));
		}).positionAndSize(this.width / 2 - 100, y + spacingY * 2, 200, 20).build();
		quickConnectWidget.active = (text == null);
		this.addDrawableSelectableElement(quickConnectWidget);
	}

	@Inject(method = "areRealmsNotificationsEnabled", at = @At(value = "HEAD"), cancellable = true)
	public void areRealmsNotificationsEnabled(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(false);
	}

	@Inject(method = "onDisplayed", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;onDisplayed()V", shift = At.Shift.AFTER), cancellable = true)
	public void onDisplayed(CallbackInfo ci) {
		ci.cancel();
	}
}
