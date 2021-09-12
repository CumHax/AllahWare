package allah.owns.me.allahware.hacks.misc;

import com.mojang.authlib.GameProfile;
import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;
import net.minecraft.client.entity.EntityOtherPlayerMP;

import java.util.UUID;

public class FakePlayer extends Hack {
    
    public FakePlayer() {
        super(Category.MISC);

		this.name        = "Fake Player";
		this.tag         = "FakePlayer";
		this.description = "hahahaaha what a noob its in beta ahahahahaha";
    }

    private EntityOtherPlayerMP fake_player;

    @Override
    protected void enable() {
        
        fake_player = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("a07208c2-01e5-4eac-a3cf-a5f5ef2a4700"), "Nigga0"));
        fake_player.copyLocationAndAnglesFrom(mc.player);
        fake_player.rotationYawHead = mc.player.rotationYawHead;
        mc.world.addEntityToWorld(-100, fake_player);

    }

    @Override
    protected void disable() {
        try {
            mc.world.removeEntity(fake_player);
        } catch (Exception ignored) {}
    }

}