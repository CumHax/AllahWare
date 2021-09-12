package allah.owns.me.allahware.hacks.render;

import allah.owns.me.allahware.guiscreen.settings.Setting;
import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;

public class Capes extends Hack {

    public Capes() {
        super(Category.RENDER);

        this.name = "Capes";
        this.tag = "Capes";
        this.description = "see epic capes behind epic dudes";
    }

    Setting cape = create("Cape", "CapeCape", "New", combobox("New", "OG"));
}
