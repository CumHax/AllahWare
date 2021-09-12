//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package allah.owns.me.allahware.hacks.render;

import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;

public class NoWeather extends Hack
{
    public NoWeather() {
        super(Category.RENDER);
        this.name = "No Weather";
        this.tag = "NoWeather";
        this.description = "Disables weather render";
    }
    
    @Override
    public void update() {
        if (NoWeather.mc.world != null) {
            if (NoWeather.mc.world.isRaining()) {
                NoWeather.mc.world.setRainStrength(0.0f);
            }
            if (NoWeather.mc.world.isThundering()) {
                NoWeather.mc.world.setThunderStrength(0.0f);
            }
        }
    }
}
