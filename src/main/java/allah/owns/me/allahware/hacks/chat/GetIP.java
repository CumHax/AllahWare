//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package allah.owns.me.allahware.hacks.chat;

import net.minecraft.client.gui.GuiScreen;
import allah.owns.me.allahware.util.MessageUtil;
import net.minecraft.client.Minecraft;
import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;

public class GetIP extends Hack
{
    public GetIP() {
        super(Category.CHAT);
        this.name = "Get Server IP";
        this.tag = "GetIP";
        this.description = "Copy the ip of the server!";
    }
    
    public void enable() {
        if (Minecraft.getMinecraft().isIntegratedServerRunning()) {
            MessageUtil.send_client_message("You\u00c2´re not connected to a server!");
        }
        else {
            final String ip = Minecraft.getMinecraft().getCurrentServerData().serverIP;
            GuiScreen.setClipboardString(ip);
            MessageUtil.send_client_message(ip + " copied to your clipboard.");
        }
    }
}
