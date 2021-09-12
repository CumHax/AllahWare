package allah.owns.me.allahware.hacks.render;

import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;

public class FullBright extends Hack {
    public FullBright() {
        super(Category.RENDER);

        this.name = "Full Bright";
        this.tag = "FullBright";
        this.description = "Makes everything bright";
    }

    @Override
    protected void enable() {
        mc.gameSettings.gammaSetting = 10;
    }

    @Override
    public void update() {
        if (mc.gameSettings.gammaSetting != 10) {
            mc.gameSettings.gammaSetting = 10;
        }
    }
}
