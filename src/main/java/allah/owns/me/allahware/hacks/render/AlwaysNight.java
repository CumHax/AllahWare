package allah.owns.me.allahware.hacks.render;

import allah.owns.me.allahware.event.events.EventPacket;
import allah.owns.me.allahware.event.events.EventRender;
import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.server.SPacketTimeUpdate;

public class AlwaysNight extends Hack {

    public AlwaysNight() {
        super(Category.RENDER);

        this.name = "Always Night";
        this.tag = "AlwaysNight";
        this.description = "see even less";
    }

    @EventHandler
    private Listener<EventRender> on_render = new Listener<>(event -> {
        if (mc.world == null) return;
        mc.world.setWorldTime(18000);
    });

    @Override
    public void update() {
        if (mc.world == null) return;
        mc.world.setWorldTime(18000);
    }
    
    @EventHandler
    private Listener<EventPacket.ReceivePacket> recieve_packet = new Listener<>(event -> {
    	if (event.get_packet() instanceof SPacketTimeUpdate) {
    		event.cancel();
    	}
    });
}
