package allah.owns.me.allahware.hacks.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;
import allah.owns.me.allahware.util.MessageUtil;
import net.minecraft.init.MobEffects;

public class WeaknessNotifier extends Hack {

    public WeaknessNotifier() {
        super(Category.CHAT);

        this.name        = "Weakness Detect"; //Commands and Clickgui
        this.tag         = "WeaknessDetect"; //Config and Arraylist
        this.description = "ballin"; //Useless but normally i add this
    }
    private boolean hasAnnounced = false;

    @Override
    public void update() {
            if (mc.player.isPotionActive(MobEffects.WEAKNESS) && !hasAnnounced) {
            hasAnnounced = true;
            MessageUtil.send_client_message(ChatFormatting.GRAY + "" + ChatFormatting.BOLD + "nigga you just got weaknessed by nn's");
        }
            if (!mc.player.isPotionActive(MobEffects.WEAKNESS) && hasAnnounced) {
                hasAnnounced = false;
                MessageUtil.send_client_message(ChatFormatting.GRAY + "" + ChatFormatting.BOLD + "You no longer have weakness");
        }
    }
}