package allah.owns.me.allahware.hacks;

public enum Category {
	COMBAT("Combat", "Combat", false),
	RENDER("Render", "Render", false),
	MOVEMENT("Movement", "Movement", false),
	CHAT("Chat", "Chat", false),
	MISC("Misc", "Misc", false),
	EXPLOIT("Exploit", "Exploit", false),
	GUI("GUI", "GUI", false);

	String name;
	String tag;
	boolean hidden;

	Category(String name, String tag, boolean hidden) {
		this.name   = name;
		this.tag    = tag;
		this.hidden = hidden;
	}

	public boolean is_hidden() {
		return this.hidden;
	}

	public String get_name() {
		return this.name;
	}

	public String get_tag() {
		return this.tag;
	}
}
