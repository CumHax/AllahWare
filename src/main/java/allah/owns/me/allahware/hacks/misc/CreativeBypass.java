//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package allah.owns.me.allahware.hacks.misc;

import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;

public class CreativeBypass extends Hack
{
    public CreativeBypass() {
        super(Category.MISC);
        this.name = "CreativeBypass";
        this.tag = "CreativeBypass";
        this.description = "";
    }
    
    @Override
    protected void enable() {
        if (CreativeBypass.mc.player != null) {
            CreativeBypass.mc.player.sendChatMessage("Im a cute femboy uwu fuck me please uwuwuwuwu");
        }
        CreativeBypass.mc.player.sendChatMessage("/kill");
    }
}
