package allah.owns.me.allahware.event.events;

import allah.owns.me.allahware.event.EventCancellable;
import net.minecraft.entity.Entity;

public class EventEntityRemoved extends EventCancellable {
    
    private final Entity entity;

    public EventEntityRemoved(Entity entity) {
        this.entity = entity;
    }

    public Entity get_entity() {
        return this.entity;
    }
}
