
package allah.owns.me.allahware.hacks.movement;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;

public class NoFall extends Hack
{
    public NoFall() {
        super(Category.MOVEMENT);

        this.name = "NoFall";
        this.tag = "NoFall";
        this.description = "fall go brrn't";
        this.toggle_message = false;
    }
    
    @Override
    protected void enable() {
        if (NoFall.mc.player != null) {
            NoFall.mc.player.fallDistance = 0.0f;
        }
    }
    
    @Override
    protected void disable() {
        if (NoFall.mc.player != null) {
            NoFall.mc.player.fallDistance = 0.0f;
        }
    }
    
    @Override
    public void update() {
        if (NoFall.mc.player.fallDistance != 0.0f) {
            NoFall.mc.player.connection.sendPacket((Packet)new CPacketPlayer(true));
        }
    }
}
