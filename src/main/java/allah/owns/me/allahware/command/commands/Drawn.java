package allah.owns.me.allahware.command.commands;

import allah.owns.me.Client;
import allah.owns.me.allahware.command.Command;
import allah.owns.me.allahware.hacks.Hack;
import allah.owns.me.allahware.util.DrawnUtil;
import allah.owns.me.allahware.util.MessageUtil;

import java.util.List;

public class Drawn extends Command {
    
    public Drawn() {
        super("drawn", "Hide elements of the array list");
    }

    public boolean get_message(String[] message) {

        if (message.length == 1) {
            MessageUtil.send_client_error_message("module name needed");

            return true;
        }

        if (message.length == 2) {

            if (is_module(message[1])) {
                DrawnUtil.add_remove_item(message[1]);
                Client.get_config_manager().save_settings();
            } else {
                MessageUtil.send_client_error_message("cannot find module by name: " + message[1]);
            }
            return true;
        }

        return false;
    }

    public boolean is_module(String s) {

        List<Hack> modules = Client.get_hack_manager().get_array_hacks();

        for (Hack module : modules) {
            if (module.get_tag().equalsIgnoreCase(s)) {
                return true;
            }
        }

        return false;

    }
}
