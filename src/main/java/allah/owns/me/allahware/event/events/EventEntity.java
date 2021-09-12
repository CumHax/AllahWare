package allah.owns.me.allahware.event.events;

import allah.owns.me.allahware.event.EventCancellable;
import net.minecraft.entity.Entity;

// External.


public class EventEntity extends EventCancellable {
	private Entity entity;

	public EventEntity(Entity entity) {
		this.entity = entity;
	}

	public Entity get_entity() {
		return this.entity;
	}

	public static class EventColision extends EventEntity {
		private double x, y, z;

		public EventColision(Entity entity, double x, double y, double z) {
			super(entity);

			this.x = x;
			this.y = y;
			this.z = z;
		}

		public void set_x(double x) {
			this.x = x;
		}

		public void set_y(double y) {
			this.y = y;
		}

		public void set_z(double z) {
			this.z = z;
		}

		public double get_x() {
			return this.x;
		}

		public double get_y() {
			return this.y;
		}

		public double get_z() {
			return this.z;
		}
	}
}
