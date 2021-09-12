//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package allah.owns.me.allahware.hacks.misc;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;

public class AutoSuicide extends Hack
{
    public AutoSuicide() {
        super(Category.MISC);
        this.name = "AutoSuicide";
        this.tag = "AutoSuicide";
        this.description = "auto suicide";
    }
    
    @Override
    protected void enable() {
        AutoSuicide.mc.player.connection.sendPacket((Packet)new CPacketChatMessage("/kill"));
        this.disable();
    }
}
