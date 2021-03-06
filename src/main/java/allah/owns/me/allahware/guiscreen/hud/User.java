package allah.owns.me.allahware.guiscreen.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import allah.owns.me.Client;
import allah.owns.me.allahware.guiscreen.render.Draw;
import allah.owns.me.allahware.guiscreen.render.pinnables.Pinnable;
import allah.owns.me.allahware.util.TimeUtil;
import net.minecraft.util.math.MathHelper;

public class User extends Pinnable {
	public User() {
		super("User", "User", 1, 0, 0);
	}

	private int scaled_width;
	private int scaled_height;
	private int scale_factor;

	@Override
	public void render() {
		updateResolution();
		int nl_r = Client.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
		int nl_g = Client.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
		int nl_b = Client.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
		int nl_a = Client.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);

		int time = TimeUtil.get_hour();
		String line;

		if (time >= 0 && time < 12) {
			line = "Morning, " + ChatFormatting.GOLD + ChatFormatting.BOLD + mc.player.getName() + ChatFormatting.RESET + " welcome nigga";
		} else if (time >= 12 && time < 16) {
			line = "Afternoon, " + ChatFormatting.GOLD + ChatFormatting.BOLD +  mc.player.getName() + ChatFormatting.RESET + " welcome nigga";
		} else if (time >= 16 && time < 24) {
			line = "Evening, " + ChatFormatting.GOLD + ChatFormatting.BOLD +  mc.player.getName() + ChatFormatting.RESET + " welcome nigga";
		} else {
			line = "Welcome, " + ChatFormatting.GOLD + ChatFormatting.BOLD +  mc.player.getName() + ChatFormatting.RESET + " welcome nigga";
		}

		mc.fontRenderer.drawStringWithShadow(line, scaled_width / 2f - mc.fontRenderer.getStringWidth(line) / 2f, 20f, new Draw.TravisColor(nl_r,nl_g,nl_b,nl_a).hex());

		this.set_width(this.get(line, "width") + 2);
		this.set_height(this.get(line, "height") + 2);
	}

	public void updateResolution() {
		this.scaled_width = mc.displayWidth;
		this.scaled_height = mc.displayHeight;
		this.scale_factor = 1;
		final boolean flag = mc.isUnicode();
		int i = mc.gameSettings.guiScale;
		if (i == 0) {
			i = 1000;
		}
		while (this.scale_factor < i && this.scaled_width / (this.scale_factor + 1) >= 320 && this.scaled_height / (this.scale_factor + 1) >= 240) {
			++this.scale_factor;
		}
		if (flag && this.scale_factor % 2 != 0 && this.scale_factor != 1) {
			--this.scale_factor;
		}
		final double scaledWidthD = this.scaled_width / (double)this.scale_factor;
		final double scaledHeightD = this.scaled_height / (double)this.scale_factor;
		this.scaled_width = MathHelper.ceil(scaledWidthD);
		this.scaled_height = MathHelper.ceil(scaledHeightD);
	}
}
