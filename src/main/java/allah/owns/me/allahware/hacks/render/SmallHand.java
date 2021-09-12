package allah.owns.me.allahware.hacks.render;

//Imports

import allah.owns.me.allahware.guiscreen.settings.Setting;
import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;


//Module

public class SmallHand extends Hack {

    //Module Info
    public SmallHand() {
        super(Category.RENDER);

        this.name        = "Small Hand"; //Commands and Clickgui
        this.tag         = "SmallHand"; //Config and Arraylist
        this.description = "decreases your hand Y position"; //Useless but normally i add this
    }

    //Module Settings
    Setting multiplier = create("Multiplier", "SmallHandMultiplier", 90, 0, 360);

    public void update() {
        mc.player.renderArmPitch = multiplier.get_value(1);
    }


}
