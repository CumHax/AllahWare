package allah.owns.me.allahware.hacks.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;
import allah.owns.me.allahware.util.FriendUtil;
import allah.owns.me.allahware.util.MessageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;

public class VisualRange extends Hack {

	private List<String> people;

	public VisualRange() {
		super(Category.CHAT);

		this.name        = "Visual Range";
		this.tag         = "VisualRange";
		this.description = "bc using ur eyes is overrated";
	}

	@Override
	public void enable() {
		people = new ArrayList<>();
	}

	@Override
	public void update() {
		if (mc.world == null | mc.player == null) return;

		List<String> peoplenew = new ArrayList<>();
		List<EntityPlayer> playerEntities = mc.world.playerEntities;

		for (Entity e : playerEntities) {
			if (e.getName().equals(mc.player.getName())) continue;
			peoplenew.add(e.getName());
		}

		if (peoplenew.size() > 0) {
			for (String name : peoplenew) {
				if (!people.contains(name)) {
					if (FriendUtil.isFriend(name)) {
						MessageUtil.send_client_message("I see an epic dude called " + ChatFormatting.RESET + ChatFormatting.GREEN + name + ChatFormatting.RESET + " :D");
					} else {
						MessageUtil.send_client_message("I see an nigga Called  " + ChatFormatting.RESET + ChatFormatting.RED + name + ChatFormatting.RESET + " kinda nn :yawning_face;");
					}
					people.add(name);
				}
			}
		}
	}
}
