package allah.owns.me.allahware.hacks.render;

import allah.owns.me.allahware.guiscreen.settings.Setting;
import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

//Module

public class NoRender extends Hack {

    //Module Info
    public NoRender() {
        super(Category.RENDER);

        this.name        = "No Render"; //Commands and Clickgui
        this.tag         = "NoRender"; //Config and Arraylist
        this.description = "stop the renderization of some things"; //Useless but normally i add this
    }

    //Module Settings
    Setting falling = create("Falling Blocks", "Falling", true);
    Setting weather = create("Wheather", "Weather", true);
    Setting potionef = create("Potion Icons", "PotionIcons", true);
    Setting pumpkin = create("Pumpkin Overlay", "Pumpkin", true);
    Setting portal = create("Portal Overlay", "PortalOverlay", true);
    Setting fireworks = create("FireWorks", "FireWorks", false);
    Setting advancements = create("Advancements", "Advancements", false);
    Setting fire = create("Fire", "Fire", true);
    Setting hurtcam = create("Hurt Cam", "HurtCam", true);
    Setting armor = create("Armor", "Armor", false);
    Setting noboss = create("NoBossOverlay", "NoBossOverlay", false);



    @Override
    public void update() {
        if(falling.get_value(true))
        for (final Entity e : mc.world.loadedEntityList) {
            if (e instanceof EntityFallingBlock) {
                mc.world.removeEntity(e);

            if (this.mc.world == null) {
                return;
            } 
            if (this.mc.world.isRaining() && weather.get_value(true)) {
                this.mc.world.setRainStrength(0.0f);
            }

            }
        }
    }
}




