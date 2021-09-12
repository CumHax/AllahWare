package allah.owns.me.allahware.command;

import allah.owns.me.allahware.manager.CommandManager;

public class Command {
	String name;
	String description;

	public Command(String name, String description) {
		this.name        = name;
		this.description = description;
	}

	public boolean get_message(String[] message) {
		return false;
	}

	public String get_name() {
		return this.name;
	}

	public String get_description() {
		return this.description;
	}

	public String current_prefix() {
		return CommandManager.get_prefix();
	}
}
