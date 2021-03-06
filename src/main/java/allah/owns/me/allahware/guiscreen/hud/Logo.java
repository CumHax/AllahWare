package allah.owns.me.allahware.guiscreen.hud;

import allah.owns.me.allahware.guiscreen.render.pinnables.Pinnable;
import allah.owns.me.allahware.util.TextureHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class Logo extends Pinnable {
    
    public Logo() {
        super("Logo", "Logo", 1, 0, 0);
    }

    ResourceLocation r = new ResourceLocation("custom/mm.png");

    @Override
	public void render() {

		GL11.glPushMatrix();
        GL11.glTranslatef(this.get_x(), this.get_y(), 0.0F);
        TextureHelper.drawTexture(r, this.get_x(), this.get_y(), 100, 25);
        GL11.glPopMatrix();

		this.set_width(100);
		this.set_height(25);
	}
}
