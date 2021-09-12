package allah.owns.me.allahware.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import allah.owns.me.Client;
import allah.owns.me.allahware.command.Command;
import allah.owns.me.allahware.util.EzMessageUtil;
import allah.owns.me.allahware.util.MessageUtil;

public class EzMessage extends Command {

    public EzMessage() {
        super("ezmessage", "Set ez mode");
    }

    public boolean get_message(String[] message) {

        if (message.length == 1) {
            MessageUtil.send_client_error_message("message needed");
            return true;
        }

        if (message.length >= 2) {
            StringBuilder ez = new StringBuilder();
            boolean flag = true;
            for (String word : message) {
                if (flag) {
                    flag = false;
                    continue;
                }
                ez.append(word).append(" ");
            }
            EzMessageUtil.set_message(ez.toString());
            MessageUtil.send_client_message("ez message changed to " + ChatFormatting.BOLD + ez.toString());
            Client.get_config_manager().save_settings();
            return true;
        }

        return false;
    }
}
