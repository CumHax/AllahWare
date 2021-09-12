package allah.owns.me.allahware.hacks.chat;

import java.util.Random;

import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;

public class AutoExcuse extends Hack
{
    int diedTime;
    
    public AutoExcuse() {
        super(Category.CHAT);
		
        this.diedTime = 0;
        this.name = "Auto Cope";
        this.tag = "AutoExcuse";
        this.description = "tell people why you died";
    }
    
    @Override
    public void update() {
        if (this.diedTime > 0) {
            --this.diedTime;
        }
        if (AutoExcuse.mc.player.isDead) {
            this.diedTime = 500;
        }
        if (!AutoExcuse.mc.player.isDead && this.diedTime > 0) {
            final Random rand = new Random();
            final int randomNum = rand.nextInt(6) + 1;
            if (randomNum == 1) {
                AutoExcuse.mc.player.sendChatMessage("you won because you are a pingplayer :((");
            }
            if (randomNum == 2) {
                AutoExcuse.mc.player.sendChatMessage("i was in my hentai file :(");
            }
            if (randomNum == 3) {
                AutoExcuse.mc.player.sendChatMessage("bro im good i was testing configs :((");
            }
            if (randomNum == 5) {
                AutoExcuse.mc.player.sendChatMessage("im desync :(");
            }
            if (randomNum == 6) {
                AutoExcuse.mc.player.sendChatMessage("youre a cheater :(");
            }
            this.diedTime = 0;
        }
    }
}
