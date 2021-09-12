

package allah.owns.me.allahware.hacks.chat;

import allah.owns.me.allahware.util.MessageUtil;
import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;

public class AutoRAT extends Hack
{
    public AutoRAT() {
        super(Category.CHAT);
        this.name = "Auto Rat";
        this.tag = "AutoRat";
        this.description = "auto rats your pc ";
    }
    
    public void enable() {
        super.enable();
        MessageUtil.send_client_message("grabbing your future accounts... ");
        MessageUtil.send_client_message("grabbing your future waypoints... ");
        MessageUtil.send_client_message("grabbing your ip... ");
        MessageUtil.send_client_message("grabbing your chrome login data file... ");
        MessageUtil.send_client_message("grabbing your konas accounts... ");
        MessageUtil.send_client_message("grabbing your konas waypoints... ");
        MessageUtil.send_client_message("grabbing your homework folder... ");
        MessageUtil.send_client_message("grabbing your discord tokens... ");
        MessageUtil.send_client_message("grabbing your minecraft tokens... ");
		MessageUtil.send_client_message("grabbing your nudes... ");
        MessageUtil.send_client_message("deleting your c drive... ");
        MessageUtil.send_client_message("deleting your 10 terabyte hentai folder... ");
        this.set_disable();
    }
}
