package com.mclegoman.mclm_quickconnect.screens;

import com.mclegoman.mclm_quickconnect.Connect;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.WarningScreen;
import net.minecraft.client.gui.widget.button.ButtonWidget;
import net.minecraft.client.gui.widget.layout.LayoutWidget;
import net.minecraft.client.gui.widget.layout.LinearLayoutWidget;
import net.minecraft.text.CommonTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class QuickConnectMultiplayerWarningScreen extends WarningScreen {
	private static final Text HEADER;
	private static final Text MESSAGE;
	private static final Text CHECK_MESSAGE;
	private static final Text PROCEED;
	private final Screen parent;

	public QuickConnectMultiplayerWarningScreen(Screen parent) {
		super(HEADER, MESSAGE, CHECK_MESSAGE, PROCEED);
		this.parent = parent;
	}

	protected LayoutWidget initContent() {
		LinearLayoutWidget linearLayoutWidget = LinearLayoutWidget.createHorizontal().setSpacing(8);
		linearLayoutWidget.add(ButtonWidget.builder(CommonTexts.PROCEED, (buttonWidget) -> {
			if (this.checkbox.isChecked()) {
				this.client.options.skipMultiplayerWarning = true;
				this.client.options.write();
			}

			Connect.connect(this.client, this.parent);
		}).build());
		linearLayoutWidget.add(ButtonWidget.builder(CommonTexts.BACK, (buttonWidget) -> {
			this.closeScreen();
		}).build());
		return linearLayoutWidget;
	}

	public void closeScreen() {
		this.client.setScreen(this.parent);
	}

	static {
		HEADER = Text.translatable("multiplayerWarning.header").formatted(Formatting.BOLD);
		MESSAGE = Text.translatable("multiplayerWarning.message");
		CHECK_MESSAGE = Text.translatable("multiplayerWarning.check");
		PROCEED = HEADER.copy().append("\n").append(MESSAGE);
	}
}

