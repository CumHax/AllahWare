
package allah.owns.me.allahware.hacks.chat;

import allah.owns.me.Client;
import allah.owns.me.allahware.command.Commands;
import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;
import allah.owns.me.allahware.command.commands.Prefix;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatSuffix extends Hack {

    private String[] prefixes;

    public ChatSuffix () {
        super ( Category.CHAT );

        this.name="Chat Suffix";
        this.tag="ChatSuffix";
        this.description="ballin";
    }

    @SubscribeEvent
    public void onMessage(ClientChatEvent event)
    {
        if (mc.player == null || mc.world == null || event.getMessage().startsWith( Prefix.getPrefix()))
        {
            return;
        }

        for (String prefix : prefixes)
        {
            if (event.getMessage().startsWith(prefix)) return;
        }

        String msg;
        {
            msg = String.format("%s \uFF5C ᴀʟʟᴀʜᴡᴀʀᴇ.ᴄᴄ", event.getMessage());
        }

        event.setMessage(msg);

    }
}