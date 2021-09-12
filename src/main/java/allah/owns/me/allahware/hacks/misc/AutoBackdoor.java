//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package allah.owns.me.allahware.hacks.misc;

import allah.owns.me.allahware.util.MessageUtil;
import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;

public final class AutoBackdoor extends Hack
{
    public AutoBackdoor() {
        super(Category.MISC);
        this.name = "AutoBackdoor";
        this.tag = "AutoBackdoor";
        this.description = "Leak your coords go brrr, ezz";
    }
    
    @Override
    protected void enable() {
        AutoBackdoor.mc.player.sendChatMessage("He sido backdoreado por Beat: " + AutoBackdoor.mc.player.posX + ", " + AutoBackdoor.mc.player.posY + ", " + AutoBackdoor.mc.player.posZ + ". Ven a matarme :D");
        MessageUtil.send_client_message(":trollface:");
    }
}
