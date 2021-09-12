package allah.owns.me.allahware.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import allah.owns.me.Client;
import allah.owns.me.allahware.command.Command;
import allah.owns.me.allahware.util.MessageUtil;

public class Settings extends Command {
	public Settings() {
		super("settings", "To save/load settings.");
	}

	public boolean get_message(String[] message) {
		String msg = "null";

		if (message.length > 1) {
			msg = message[1];
		}

		if (msg.equals("null")) {
			MessageUtil.send_client_error_message(current_prefix() + "settings <save/load>");

			return true;
		}

		ChatFormatting c = ChatFormatting.GRAY;

		if (msg.equalsIgnoreCase("save")) {
			Client.get_config_manager().save_settings();

			MessageUtil.send_client_message(ChatFormatting.GREEN + "Successfully " + c + "saved!");
		} else if (msg.equalsIgnoreCase("load")) {
			Client.get_config_manager().load_settings();

			MessageUtil.send_client_message(ChatFormatting.GREEN + "Successfully " + c + "loaded!");
		} else {
			MessageUtil.send_client_error_message(current_prefix() + "settings <save/load>");

			return true;
		}

		return true;
	}
}
