package allah.owns.me.allahware.event.events;

import allah.owns.me.allahware.event.EventCancellable;

public class EventPlayerJump extends EventCancellable {
    
    public double motion_x;
    public double motion_y;

    public EventPlayerJump(double motion_x, double motion_y) {
        super();

        this.motion_x = motion_x;
        this.motion_y = motion_y;
    }
}
