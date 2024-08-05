package com.mclegoman.mclm_quickconnect;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.SelectServerScreen;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;

public class Connect {
	public static Screen parent = new SelectServerScreen(new TitleScreen());
	public static void connect(MinecraftClient client, Screen parent) {
		ConnectScreen.connect(parent, client, ServerAddress.parse(Config.instance.server_address.value()), new ServerInfo(Config.instance.name.value(), Config.instance.server_address.value(), ServerInfo.ServerType.OTHER), false, null);
	}
}
