//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package allah.owns.me.allahware.hacks.movement;

import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;

public class Flight extends Hack
{
    public Flight() {
        super(Category.MOVEMENT);
        this.name = "Flight";
        this.tag = "Flight";
        this.description = "flight go brrr";
    }
    
    @Override
    protected void enable() {
        Flight.mc.player.capabilities.isFlying = true;
    }
    
    @Override
    protected void disable() {
        if (Flight.mc.player != null) {
            Flight.mc.player.capabilities.isFlying = false;
        }
    }
    
    @Override
    public void update() {
        Flight.mc.player.capabilities.isFlying = true;
    }
}
