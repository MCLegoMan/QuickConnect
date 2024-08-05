package com.mclegoman.mclm_quickconnect.mixin;

import com.mclegoman.mclm_quickconnect.Config;
import com.mclegoman.mclm_quickconnect.Connect;
import com.mclegoman.mclm_quickconnect.screens.QuickConnectMultiplayerWarningScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
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
	@Inject(method = "initWidgetsNormal", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/button/ButtonWidget$Builder;build()Lnet/minecraft/client/gui/widget/button/ButtonWidget;", ordinal = 2), cancellable = true)
    public void onInit(int y, int spacingY, CallbackInfo ci) {
		ci.cancel();
		Text text = this.getBanText();
		boolean bl = text == null;
		Tooltip tooltip = text != null ? Tooltip.create(text) : null;
	    this.addDrawableSelectableElement(com.mclegoman.mclm_quickconnect.widgets.QuickConnectButtonWidget.builder(Text.translatable("menu.quickconnect", Config.instance.name.value()), (button) -> {
			if (this.client.options.skipMultiplayerWarning) Connect.connect(this.client, this);
			else this.client.setScreen(new QuickConnectMultiplayerWarningScreen(this));

	    }).positionAndSize(this.width / 2 - 100, y + spacingY * 2, 200, 20).tooltip(tooltip).build()).active = bl;
    }
	@Inject(method = "areRealmsNotificationsEnabled", at = @At(value = "HEAD"), cancellable = true)
	public void areRealmsNotificationsEnabled(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(false);
	}
}
