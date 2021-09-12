package allah.owns.me.allahware.event;

import allah.owns.me.allahware.manager.CommandManager;
import allah.owns.me.allahware.manager.EventManager;
import net.minecraftforge.common.MinecraftForge;

public class EventRegister {
	public static void register_command_manager(CommandManager manager) {
		MinecraftForge.EVENT_BUS.register(manager);
	}

	public static void register_module_manager(EventManager manager) {
		MinecraftForge.EVENT_BUS.register(manager);
	}
}
