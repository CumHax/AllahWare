package allah.owns.me.allahware.event;

import net.minecraft.util.EnumHandSide;

public class TransformSideFirstPersonEvent extends Stuff {

    private final EnumHandSide enumHandSide;

    public TransformSideFirstPersonEvent(EnumHandSide enumHandSide) {
        this.enumHandSide = enumHandSide;
    }

    public EnumHandSide getEnumHandSide() {
        return this.enumHandSide;
    }
}