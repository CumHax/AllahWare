//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package allah.owns.me.allahware.hacks;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.WorldClient;
import javax.annotation.Nullable;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.Minecraft;

public class Wrapper
{
    public static final Minecraft mc;
    
    @Nullable
    public static EntityPlayerSP getPlayer() {
        return Wrapper.mc.player;
    }
    
    @Nullable
    public static WorldClient getWorld() {
        return Wrapper.mc.world;
    }
    
    public static FontRenderer getFontRenderer() {
        return Wrapper.mc.fontRenderer;
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
