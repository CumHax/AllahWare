
package allah.owns.me.allahware.hacks.misc;

import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;

public class PingBypass extends Hack
{
    public PingBypass() {
        super(Category.MISC);
        this.name = "Ping Bypass";
        this.tag = "Pingbypass";
        this.description = "get gud ping";
    }
    
    @Override
    protected void enable() {
        super.enable();
        PingBypass.mc.player.sendChatMessage("My ping is now 5 ms, thanks to Allah");
        this.set_disable();
    }
}
