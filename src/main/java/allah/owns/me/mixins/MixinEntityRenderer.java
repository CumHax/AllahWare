package allah.owns.me.mixins;

import allah.owns.me.allahware.event.EventBus;
import allah.owns.me.allahware.event.events.EventSetupFog;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// External.

@Mixin(value = EntityRenderer.class)
public class MixinEntityRenderer {
    
    @Inject(method = "setupFog", at = @At("HEAD"), cancellable = true)
    public void setupFog(int startCoords, float partialTicks, CallbackInfo p_Info)
    {
        EventSetupFog event = new EventSetupFog(startCoords, partialTicks);
        EventBus.EVENT_BUS.post(event);
        
        if (event.isCancelled()) {
			return;
        }
    }
}
