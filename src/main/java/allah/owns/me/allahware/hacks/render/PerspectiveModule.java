//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package allah.owns.me.allahware.hacks.render;

import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;

public class PerspectiveModule extends Hack
{
    public static PerspectiveModule INSTANCE;
    public float cameraPitch;
    public float cameraYaw;
    
    public PerspectiveModule() {
        super(Category.RENDER);
        this.name = "PerspectiveModule";
        this.tag = "PerspectiveModule";
        this.description = "PerspectiveModule";
        PerspectiveModule.INSTANCE = this;
    }
    
    public void disable() {
        if (PerspectiveModule.mc.player != null && PerspectiveModule.mc.gameSettings.thirdPersonView == 1) {
            PerspectiveModule.mc.gameSettings.thirdPersonView = 0;
        }
    }
    
    @Override
    public void update() {
        if (PerspectiveModule.mc.player != null && PerspectiveModule.mc.gameSettings.thirdPersonView != 1) {
            PerspectiveModule.mc.gameSettings.thirdPersonView = 1;
        }
    }
    
    @SubscribeEvent
    public void onKey(final InputEvent.KeyInputEvent event) {
        this.cameraPitch = PerspectiveModule.mc.player.rotationPitch;
        this.cameraYaw = PerspectiveModule.mc.player.rotationYaw;
        PerspectiveModule.mc.gameSettings.thirdPersonView = (this.is_active() ? 1 : 0);
    }
    
    @SubscribeEvent
    public void cameraSetup(final EntityViewRenderEvent.CameraSetup event) {
        if (this.is_active()) {
            event.setPitch(this.cameraPitch);
            event.setYaw(this.cameraYaw);
        }
    }
}
