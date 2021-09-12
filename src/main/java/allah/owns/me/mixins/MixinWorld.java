package allah.owns.me.mixins;

import allah.owns.me.allahware.event.EventBus;
import allah.owns.me.allahware.event.events.EventEntityRemoved;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = World.class)
public class MixinWorld {
    @Inject(method = "onEntityRemoved", at = @At("HEAD"), cancellable = true)
    public void onEntityRemoved(Entity event_packet, CallbackInfo p_Info) {
        EventEntityRemoved l_Event = new EventEntityRemoved(event_packet);

        EventBus.EVENT_BUS.post(l_Event);
    }
}