//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package allah.owns.me.allahware.hacks.movement;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.guiscreen.settings.Setting;
import allah.owns.me.allahware.hacks.Hack;

public class HighJump extends Hack
{
    Setting packet;
    
    public HighJump() {
        super(Category.MOVEMENT);
        this.packet = this.create("Packet", "HighJumpPacket", true);
        this.name = "HighJump";
        this.tag = "HighJump";
        this.description = "Jump very high";
    }
    
    @Override
    public void update() {
        if (this.packet.get_value(false)) {
            HighJump.mc.player.onGround = true;
        }
        else if (this.packet.get_value(true)) {
            HighJump.mc.player.onGround = true;
            HighJump.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(HighJump.mc.player.posX, HighJump.mc.player.posY + 0.41999998688698, HighJump.mc.player.posZ, true));
            HighJump.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(HighJump.mc.player.posX, HighJump.mc.player.posY + 0.7531999805211997, HighJump.mc.player.posZ, true));
            HighJump.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(HighJump.mc.player.posX, HighJump.mc.player.posY + 1.00133597911214, HighJump.mc.player.posZ, true));
            HighJump.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(HighJump.mc.player.posX, HighJump.mc.player.posY + 1.16610926093821, HighJump.mc.player.posZ, true));
        }
    }
}
