package allah.owns.me.allahware.guiscreen.hud;

import allah.owns.me.Client;
import allah.owns.me.allahware.event.EventHandler;
import allah.owns.me.allahware.guiscreen.render.pinnables.Pinnable;

public class TPS extends Pinnable {

    public TPS() {
        super("TPS", "TPS", 1, 0, 0);
    }

    @Override
	public void render() {
		int nl_r = Client.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
		int nl_g = Client.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
		int nl_b = Client.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
		int nl_a = Client.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);

		String line = "TPS: " + getTPS();

		create_line(line, this.docking(1, line), 2, nl_r, nl_g, nl_b, nl_a);

		this.set_width(this.get(line, "width") + 2);
		this.set_height(this.get(line, "height") + 2);
    }

    public String getTPS() {
        try {
            int tps = Math.round(EventHandler.INSTANCE.get_tick_rate());
        if (tps >= 16) {
            return "\u00A7a"+Integer.toString(tps);
        } else if (tps >= 10) {
            return "\u00A73"+Integer.toString(tps);
        } else {
            return "\u00A74"+Integer.toString(tps);
        }
        } catch (Exception e) {
            return "oh no " +e;
        }
    }
    
}
