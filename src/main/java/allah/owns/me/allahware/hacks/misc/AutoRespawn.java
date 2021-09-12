//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package allah.owns.me.allahware.hacks.misc;

import net.minecraft.client.gui.GuiGameOver;
import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;

public class AutoRespawn extends Hack
{
    public AutoRespawn() {
        super(Category.MISC);
        this.name = "Auto Respawn";
        this.tag = "AutoRespawn";
        this.description = "automatically respawns";
    }
    
    @Override
    public void update() {
        if (AutoRespawn.mc.player.isDead && AutoRespawn.mc.currentScreen instanceof GuiGameOver) {
            AutoRespawn.mc.player.respawnPlayer();
        }
    }
}
