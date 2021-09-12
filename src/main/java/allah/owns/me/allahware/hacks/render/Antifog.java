package allah.owns.me.allahware.hacks.render;

import allah.owns.me.allahware.event.events.EventSetupFog;
import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.renderer.GlStateManager;

public class Antifog extends Hack {
    
    public Antifog() {
        super(Category.RENDER);

        this.name = "Anti Fog";
        this.tag = "AntiFog";
        this.description = "see even more";
    }

    @EventHandler
    private Listener<EventSetupFog> setup_fog = new Listener<> (event -> {

        event.cancel();

        mc.entityRenderer.setupFogColor(false);

        GlStateManager.glNormal3f(0.0F, -1.0F, 0.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.colorMaterial(1028, 4608);

    });
}
