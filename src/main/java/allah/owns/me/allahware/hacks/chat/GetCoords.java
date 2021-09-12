//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package allah.owns.me.allahware.hacks.chat;

import net.minecraft.client.gui.GuiScreen;
import allah.owns.me.allahware.util.MessageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.client.Minecraft;
import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;

public class GetCoords extends Hack
{
    public GetCoords() {
        super(Category.CHAT);
        this.name = "Get Coords";
        this.tag = "GetCoords";
        this.description = "Copy the ip of the server!";
    }
    
    public void enable() {
        final String coords = this.getFormattedCoordinates((Entity)Minecraft.getMinecraft().player);
        MessageUtil.send_client_message("Coords copied to your clipboard.");
        GuiScreen.setClipboardString(coords);
    }
    
    private String getFormattedCoordinates(final Entity entity) {
        return (int)entity.posX + ", " + (int)entity.posY + ", " + (int)entity.posZ;
    }
}
