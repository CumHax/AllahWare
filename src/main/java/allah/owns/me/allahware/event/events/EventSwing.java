package allah.owns.me.allahware.event.events;

import allah.owns.me.allahware.event.EventCancellable;
import net.minecraft.util.EnumHand;

public class EventSwing extends EventCancellable {
    
    public EnumHand hand;

    public EventSwing(EnumHand hand) {
        super();
        this.hand = hand;
    }
}
