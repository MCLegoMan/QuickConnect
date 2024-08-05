package com.mclegoman.mclm_quickconnect.mixin;

import com.mclegoman.mclm_quickconnect.Connect;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.GenericMessageScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.SelectServerScreen;
import net.minecraft.client.gui.screen.realms.RealmsMainScreen;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen {
	@Shadow
	@Final
	private static Text SAVING_WORLD;

	protected GameMenuScreenMixin(Text title) {
		super(title);
	}

	@Inject(method = "onDisconnect", at = @At(value = "HEAD"), cancellable = true)
	private void onDisconnect(CallbackInfo ci) {
		boolean bl = MinecraftClient.getInstance().isInSingleplayer();
		ServerInfo serverInfo = MinecraftClient.getInstance().getCurrentServerEntry();
		MinecraftClient.getInstance().world.disconnect();
		if (bl) {
			MinecraftClient.getInstance().disconnect(new GenericMessageScreen(SAVING_WORLD));
		} else {
			MinecraftClient.getInstance().disconnect();
		}

		TitleScreen titleScreen = new TitleScreen();
		if (bl) {
			MinecraftClient.getInstance().setScreen(titleScreen);
		} else if (serverInfo != null && serverInfo.isRealm()) {
			MinecraftClient.getInstance().setScreen(new RealmsMainScreen(titleScreen));
		} else {
			if (serverInfo != null && Connect.parent instanceof TitleScreen) MinecraftClient.getInstance().setScreen(Connect.parent);
			else MinecraftClient.getInstance().setScreen(new SelectServerScreen(titleScreen));
		}
		ci.cancel();
    }
}
