package allah.owns.me.allahware.event.events;

import allah.owns.me.allahware.event.EventCancellable;

public class EventMotionUpdate extends EventCancellable {

    public int stage;

    public EventMotionUpdate(int stage) {
        super();
        this.stage = stage;
    }
}
