package allah.owns.me.allahware.command.commands;


import allah.owns.me.allahware.command.Command;
import allah.owns.me.allahware.manager.CommandManager;
import allah.owns.me.allahware.util.MessageUtil;


public class Prefix extends Command {
	public Prefix() {
		super("prefix", "Change prefix.");
	}

	public static String getPrefix () {
		return null;
	}

    public boolean get_message(String[] message) {
		String prefix = "null";

		if (message.length > 1) {
			prefix = message[1];
		}

		if (message.length > 2) {
			MessageUtil.send_client_error_message(current_prefix() + "prefix <character>");

			return true;
		}

		if (prefix.equals("null")) {
			MessageUtil.send_client_error_message(current_prefix() + "prefix <character>");

			return true;
		}

		CommandManager.set_prefix(prefix);

		MessageUtil.send_client_message("The new prefix is " + prefix);

		return true;
	}
}
