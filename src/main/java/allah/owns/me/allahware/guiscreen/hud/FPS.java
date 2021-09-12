package allah.owns.me.allahware.guiscreen.hud;

import allah.owns.me.Client;
import allah.owns.me.allahware.guiscreen.render.pinnables.Pinnable;
import net.minecraft.client.Minecraft;

public class FPS extends Pinnable {
    
    public FPS() {
        super("Fps", "Fps", 1, 0, 0);
    }

    @Override
	public void render() {
		int nl_r = Client.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
		int nl_g = Client.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
		int nl_b = Client.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
		int nl_a = Client.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);

		String line = "FPS: " + get_fps();

		create_line(line, this.docking(1, line), 2, nl_r, nl_g, nl_b, nl_a);

		this.set_width(this.get(line, "width") + 2);
		this.set_height(this.get(line, "height") + 2);
	}
	
    public String get_fps() {
		int fps = Minecraft.getDebugFPS();
		if (fps >= 60) {
			return "\u00A7a"+Integer.toString(fps);
		} else if (fps >= 30) {
			return "\u00A73"+Integer.toString(fps);
		} else {
			return "\u00A74"+Integer.toString(fps);
		}
    }
}
