package com.mclegoman.mclm_quickconnect.widgets;

import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class QuickConnectButtonWidget extends QuickConnectPressableWidget {
	protected static final NarrationFactory DEFAULT_NARRATION = Supplier::get;
	protected final PressAction onPress;
	protected final NarrationFactory narrationFactory;
	public static Builder builder(Text message, PressAction onPress) {
		return new Builder(message, onPress);
	}
	protected QuickConnectButtonWidget(int x, int y, int width, int height, Text message, PressAction onPress, NarrationFactory narrationFactory) {
		super(x, y, width, height, message);
		this.onPress = onPress;
		this.narrationFactory = narrationFactory;
	}
	public void onPress() {
		this.onPress.onPress(this);
	}
	protected MutableText getNarrationMessage() {
		return this.narrationFactory.createNarrationMessage(super::getNarrationMessage);
	}
	public void updateNarration(NarrationMessageBuilder builder) {
		this.appendDefaultNarrations(builder);
	}
	public static class Builder {
		private final Text message;
		private final PressAction onPress;
		@Nullable
		private Tooltip tooltip;
		private int x;
		private int y;
		private int width = 150;
		private int height = 20;
		private NarrationFactory narrationFactory;
		public Builder(Text message, PressAction onPress) {
			this.narrationFactory = DEFAULT_NARRATION;
			this.message = message;
			this.onPress = onPress;
		}
		public Builder position(int x, int y) {
			this.x = x;
			this.y = y;
			return this;
		}
		public Builder width(int width) {
			this.width = width;
			return this;
		}
		public Builder size(int width, int height) {
			this.width = width;
			this.height = height;
			return this;
		}
		public Builder positionAndSize(int x, int y, int width, int height) {
			return this.position(x, y).size(width, height);
		}
		public Builder tooltip(@Nullable Tooltip tooltip) {
			this.tooltip = tooltip;
			return this;
		}
		public Builder narration(NarrationFactory narrationFactory) {
			this.narrationFactory = narrationFactory;
			return this;
		}
		public com.mclegoman.mclm_quickconnect.widgets.QuickConnectButtonWidget build() {
			com.mclegoman.mclm_quickconnect.widgets.QuickConnectButtonWidget quickconnectButtonWidget = new com.mclegoman.mclm_quickconnect.widgets.QuickConnectButtonWidget(this.x, this.y, this.width, this.height, this.message, this.onPress, this.narrationFactory);
			return quickconnectButtonWidget;
		}
	}
	public interface PressAction {
		void onPress(com.mclegoman.mclm_quickconnect.widgets.QuickConnectButtonWidget quickconnectButtonWidget);
	}
	public interface NarrationFactory {
		MutableText createNarrationMessage(Supplier<MutableText> supplier);
	}
}
