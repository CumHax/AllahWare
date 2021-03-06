package allah.owns.me.allahware.hacks;

import allah.owns.me.Client;
import allah.owns.me.allahware.guiscreen.settings.Setting;

public class ClickGUI extends Hack {

	public ClickGUI() {
		super(Category.GUI);

		this.name        = "GUI";
		this.tag         = "GUI";
		this.description = "The main gui";

		set_bind(Client.KEY_GUI);
	}

	Setting label_frame = create("info", "ClickGUIInfoFrame", "Frames");

	Setting name_frame_r = create("Name R", "ClickGUINameFrameR", 255, 0, 255);
	Setting name_frame_g = create("Name G", "ClickGUINameFrameG", 255, 0, 255);
	Setting name_frame_b = create("Name B", "ClickGUINameFrameB", 255, 0, 255);

	Setting background_frame_r = create("Background R", "ClickGUIBackgroundFrameR", 24, 0, 255);
	Setting background_frame_g = create("Background G", "ClickGUIBackgroundFrameG", 24, 0, 255);
	Setting background_frame_b = create("Background B", "ClickGUIBackgroundFrameB", 24, 0, 255);
	Setting background_frame_a = create("Background A", "ClickGUIBackgroundFrameA", 69, 0, 255);

	Setting border_frame_r = create("Border R", "ClickGUIBorderFrameR", 255, 0, 255);
	Setting border_frame_g = create("Border G", "ClickGUIBorderFrameG", 255, 0, 255);
	Setting border_frame_b = create("Border B", "ClickGUIBorderFrameB", 255, 0, 255);
	Setting border_frame_a = create("Border A", "ClickGUIBorderFrameA", 255, 0, 255);

	Setting label_widget = create("info", "ClickGUIInfoWidget", "Widgets");

	Setting name_widget_r = create("Name R", "ClickGUINameWidgetR", 255, 0, 255);
	Setting name_widget_g = create("Name G", "ClickGUINameWidgetG", 255, 0, 255);
	Setting name_widget_b = create("Name B", "ClickGUINameWidgetB", 255, 0, 255);

	Setting background_widget_r = create("Background R", "ClickGUIBackgroundWidgetR", 255, 0, 255);
	Setting background_widget_g = create("Background G", "ClickGUIBackgroundWidgetG", 255, 0, 255);
	Setting background_widget_b = create("Background B", "ClickGUIBackgroundWidgetB", 255, 0, 255);
	Setting background_widget_a = create("Background A", "ClickGUIBackgroundWidgetA", 100, 0, 255);

	Setting border_widget_r = create("Border R", "ClickGUIBorderWidgetR", 255, 0, 255);
	Setting border_widget_g = create("Border G", "ClickGUIBorderWidgetG", 255, 0, 255);
	Setting border_widget_b = create("Border B", "ClickGUIBorderWidgetB", 255, 0, 255);

	@Override
	public void update() {
		// Update frame colors.
		Client.click_gui.theme_frame_name_r = name_frame_r.get_value(1);
		Client.click_gui.theme_frame_name_g = name_frame_g.get_value(1);
		Client.click_gui.theme_frame_name_b = name_frame_b.get_value(1);

		Client.click_gui.theme_frame_background_r = background_frame_r.get_value(1);
		Client.click_gui.theme_frame_background_g = background_frame_g.get_value(1);
		Client.click_gui.theme_frame_background_b = background_frame_b.get_value(1);
		Client.click_gui.theme_frame_background_a = background_frame_a.get_value(1);

		Client.click_gui.theme_frame_border_r = border_frame_r.get_value(1);
		Client.click_gui.theme_frame_border_g = border_frame_g.get_value(1);
		Client.click_gui.theme_frame_border_b = border_frame_b.get_value(1);
		Client.click_gui.theme_frame_border_a = border_frame_a.get_value(1);

		// Update widget colors.
		Client.click_gui.theme_widget_name_r = name_widget_r.get_value(1);
		Client.click_gui.theme_widget_name_g = name_widget_g.get_value(1);
		Client.click_gui.theme_widget_name_b = name_widget_b.get_value(1);

		Client.click_gui.theme_widget_background_r = background_widget_r.get_value(1);
		Client.click_gui.theme_widget_background_g = background_widget_g.get_value(1);
		Client.click_gui.theme_widget_background_b = background_widget_b.get_value(1);
		Client.click_gui.theme_widget_background_a = background_widget_a.get_value(1);

		Client.click_gui.theme_widget_border_r = border_widget_r.get_value(1);
		Client.click_gui.theme_widget_border_g = border_widget_g.get_value(1);
		Client.click_gui.theme_widget_border_b = border_widget_b.get_value(1);
	}

	@Override
	public void enable() {
		if (mc.world != null && mc.player != null) {
			mc.displayGuiScreen(Client.click_gui);
		}
	}

	@Override
	public void disable() {
		if (mc.world != null && mc.player != null) {
			mc.displayGuiScreen(null);
		}
	}
}
