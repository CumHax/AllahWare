package allah.owns.me.allahware.guiscreen.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import allah.owns.me.Client;
import allah.owns.me.allahware.guiscreen.render.pinnables.Pinnable;

public class Coordinates extends Pinnable {
	ChatFormatting dg = ChatFormatting.DARK_GRAY;
	ChatFormatting db = ChatFormatting.DARK_BLUE;
	ChatFormatting dr = ChatFormatting.DARK_RED;

	public Coordinates() {
		super("Coordinates", "Coordinates", 1, 0, 0);
	}

	@Override
	public void render() {
		int nl_r = Client.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
		int nl_g = Client.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
		int nl_b = Client.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
		int nl_a = Client.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);

		String x = Client.g + "[" + Client.r + Integer.toString((int) (mc.player.posX)) + Client.g + "]" + Client.r;
		String y = Client.g + "[" + Client.r + Integer.toString((int) (mc.player.posY)) + Client.g + "]" + Client.r;
		String z = Client.g + "[" + Client.r + Integer.toString((int) (mc.player.posZ)) + Client.g + "]" + Client.r;

		String x_nether = Client.g + "[" + Client.r + Long.toString(Math.round(mc.player.dimension != -1 ? (mc.player.posX / 8) : (mc.player.posX * 8))) + Client.g + "]" + Client.r;
		String z_nether = Client.g + "[" + Client.r + Long.toString(Math.round(mc.player.dimension != -1 ? (mc.player.posZ / 8) : (mc.player.posZ * 8))) + Client.g + "]" + Client.r;

		String line = "XYZ " + x + y + z + " XZ " + x_nether + z_nether;

		create_line(line, this.docking(1, line), 2, nl_r, nl_g, nl_b, nl_a);

		this.set_width(this.get(line, "width"));
		this.set_height(this.get(line, "height") + 2);
	}
}
