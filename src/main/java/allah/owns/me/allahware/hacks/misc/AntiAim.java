//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package allah.owns.me.allahware.hacks.misc;

import java.util.Random;
import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;

public class AntiAim extends Hack
{
    public AntiAim() {
        super(Category.MISC);
        this.name = "AntiAim";
        this.tag = "AntiAim";
        this.description = "Makes it hard to target you";
    }
    
    @Override
    public void update() {
        final Random r = new Random();
        AntiAim.mc.player.rotationPitch = (float)r.nextInt(150);
        AntiAim.mc.player.rotationYaw = (float)r.nextInt(150);
    }
}
