//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package allah.owns.me.allahware.hacks.misc;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.guiscreen.settings.Setting;
import allah.owns.me.allahware.hacks.Hack;

public class AutoKit extends Hack
{
    private final Setting kit;
    
    public AutoKit() {
        super(Category.MISC);
        this.kit = this.create("", "", false);
        this.name = "AutoKit";
        this.tag = "AutoKit";
        this.description = "auto kit";
    }
    
    @Override
    protected void enable() {
        if (this.kit.get_value(true)) {
            AutoKit.mc.player.connection.sendPacket((Packet)new CPacketChatMessage("/kit"));
            this.kit.set_value(true);
            this.toggle();
        }
        else {
            AutoKit.mc.player.connection.sendPacket((Packet)new CPacketChatMessage("/kit"));
        }
        this.toggle();
    }
}
