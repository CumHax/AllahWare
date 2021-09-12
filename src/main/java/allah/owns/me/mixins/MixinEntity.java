package allah.owns.me.mixins;

import allah.owns.me.allahware.event.EventBus;
import allah.owns.me.allahware.event.events.EventEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// External.

@Mixin(value = Entity.class)
public class MixinEntity {
	// Inject.
	@Redirect(method = "applyEntityCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;addVelocity(DDD)V"))
	public void velocity(Entity entity, double x, double y, double z) {
		EventEntity.EventColision event = new EventEntity.EventColision(entity, x, y, z);

		EventBus.EVENT_BUS.post(event);

		if (event.isCancelled()) {
			return;
		}

		entity.motionX += x;
		entity.motionY += y;
		entity.motionZ += z;

		entity.isAirBorne = true;
	}

	@Shadow
    public void move(MoverType type, double x, double y, double z)
    {

	}
	
	@Shadow
    public double motionX;

    @Shadow
    public double motionY;

    @Shadow
    public double motionZ;
}
