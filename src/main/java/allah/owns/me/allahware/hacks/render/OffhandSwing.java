package allah.owns.me.allahware.hacks.render;

import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;
import net.minecraft.util.EnumHand;

public class OffhandSwing extends Hack {

    public OffhandSwing() {
        super(Category.RENDER);
        this.name = "Offhand Swing";
        this.tag = "OffhandSwing";
        this.description = "Swing your offhand instead of mainhand";
    }

    public void update() {

        mc.player.swingingHand = EnumHand.OFF_HAND;

    }

}
