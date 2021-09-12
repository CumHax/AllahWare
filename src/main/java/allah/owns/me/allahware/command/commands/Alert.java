package allah.owns.me.allahware.command.commands;


import allah.owns.me.Client;
import allah.owns.me.allahware.command.Command;
import allah.owns.me.allahware.hacks.Hack;
import allah.owns.me.allahware.util.MessageUtil;


public class Alert extends Command {
	public Alert() {
		super("alert", "if the module should spam chat or not");
	}

	public boolean get_message(String[] message) {
		String module = "null";
		String state  = "null";

		if (message.length > 1) {
			module = message[1];
		}

		if (message.length > 2) {
			state = message[2];
		}

		if (message.length > 3) {
			MessageUtil.send_client_error_message(current_prefix() + "t <ModuleName> <True/On/False/Off>");

			return true;
		}

		if (module.equals("null")) {
			MessageUtil.send_client_error_message(current_prefix() + "t <ModuleName> <True/On/False/Off>");

			return true;
		}

		if (state.equals("null")) {
			MessageUtil.send_client_error_message(current_prefix() + "t <ModuleName> <True/On/False/Off>");

			return true;
		}

		module = module.toLowerCase();
		state  = state.toLowerCase();

		Hack module_requested = Client.get_hack_manager().get_module_with_tag(module);

		if (module_requested == null) {
			MessageUtil.send_client_error_message("This module does not exist.");

			return true;
		}

		boolean value = true;

		if (state.equals("true") || state.equals("on")) {
			value = true;
		} else if (state.equals("false") || state.equals("off")) {
			value = false;
		} else {
			MessageUtil.send_client_error_message("This value does not exist. <True/On/False/Off>");

			return true;
		}

		module_requested.set_if_can_send_message_toggle(value);

		MessageUtil.send_client_message("The actual value of " + module_requested.get_name() +  " is " + Boolean.toString(module_requested.can_send_message_when_toggle()) + ".");

		return true;
	}
}
