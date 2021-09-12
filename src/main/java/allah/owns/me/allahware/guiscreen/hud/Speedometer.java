package allah.owns.me.allahware.guiscreen.hud;

import allah.owns.me.Client;
import allah.owns.me.allahware.guiscreen.render.pinnables.Pinnable;
import net.minecraft.util.math.MathHelper;

import java.text.DecimalFormat;

public class Speedometer extends Pinnable {

    public Speedometer() {
        super("Speedometer", "Speedometer", 1, 0, 0);
    }

    @Override
    public void render() {

        int nl_r = Client.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        int nl_g = Client.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        int nl_b = Client.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        int nl_a = Client.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);

        final double x = mc.player.posX - mc.player.prevPosX;
        final double z = mc.player.posZ - mc.player.prevPosZ;
        final float tr = (mc.timer.tickLength / 1000.0f);

        String bps = "M/s: " + new DecimalFormat("#.#").format(MathHelper.sqrt(x * x + z * z) / tr);

        create_line(bps, this.docking(1, bps), 2, nl_r, nl_g, nl_b, nl_a);

        this.set_width(this.get(bps, "width") + 2);
        this.set_height(this.get(bps, "height")  + 2);
    }
}
