package com.mclegoman.mclm_quickconnect;

import org.quiltmc.config.api.ReflectiveConfig;
import org.quiltmc.config.api.annotations.Comment;
import org.quiltmc.config.api.values.TrackedValue;
import org.quiltmc.loader.api.config.v2.QuiltConfig;

public class Config extends ReflectiveConfig {
	public static final Config instance = QuiltConfig.create("mclm_quickconnect", "main", Config.class);
	@Comment("The name on the title screen button.")
	public final TrackedValue<String> name = this.value("Quick Connect");
	@Comment("The server address.")
	public final TrackedValue<String> server_address = this.value("localhost:25565");
}
