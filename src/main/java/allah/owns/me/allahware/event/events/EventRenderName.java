package allah.owns.me.allahware.event.events;

import allah.owns.me.allahware.event.EventCancellable;
import net.minecraft.client.entity.AbstractClientPlayer;

public class EventRenderName extends EventCancellable {
    public AbstractClientPlayer Entity;
    public double X;
    public double Y;
    public double Z;
    public String Name;
    public double DistanceSq;
    
    public EventRenderName(AbstractClientPlayer entityIn, double x, double y, double z, String name, double distanceSq) {
		super();

		Entity = entityIn;
        x = X;
        y = Y;
        z = Z;
        Name = name;
        DistanceSq = distanceSq;
	  }
}
