package allah.owns.me.allahware.guiscreen;

import allah.owns.me.Client;
import allah.owns.me.allahware.guiscreen.render.components.Frame;
import allah.owns.me.allahware.hacks.Category;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

// Hacks.

public class GUI extends GuiScreen {
	private final ArrayList<Frame> frame;

	private int frame_x;

	private Frame current;

	// Frame.
	public int theme_frame_name_r = 0;
	public int theme_frame_name_g = 0;
	public int theme_frame_name_b = 0;
	public int theme_frame_name_a = 0;

	public int theme_frame_background_r = 0;
	public int theme_frame_background_g = 0;
	public int theme_frame_background_b = 0;
	public int theme_frame_background_a = 0;

	public int theme_frame_border_r = 0;
	public int theme_frame_border_g = 0;
	public int theme_frame_border_b = 0;
	public int theme_frame_border_a = 0;

	// Module.
	public int theme_widget_name_r = 0;
	public int theme_widget_name_g = 0;
	public int theme_widget_name_b = 0;
	public int theme_widget_name_a = 0;

	public int theme_widget_background_r = 0;
	public int theme_widget_background_g = 0;
	public int theme_widget_background_b = 0;
	public int theme_widget_background_a = 0;

	public int theme_widget_border_r = 0;
	public int theme_widget_border_g = 0;
	public int theme_widget_border_b = 0;

	private final Minecraft mc = Minecraft.getMinecraft();

	public GUI() {
		this.frame   = new ArrayList<>();
		this.frame_x = 10;

		for (Category categorys : Category.values()) {
			if (categorys.is_hidden()) {
				continue;
			}

			Frame frames = new Frame(categorys);

			frames.set_x(this.frame_x);

			this.frame.add(frames);

			this.frame_x += frames.get_width() + 5;

			this.current = frames;
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}


	@Override
	public void onGuiClosed() {
		Client.get_hack_manager().get_module_with_tag("GUI").set_active(false);

		Client.get_config_manager().save_settings();
	}

	@Override
	protected void keyTyped(char char_, int key) {
		for (Frame frame : this.frame) {
			frame.bind(char_, key);

			if (key == Client.KEY_GUI_ESCAPE && !frame.is_binding()) {
				mc.displayGuiScreen(null);
			}

			if (key == Keyboard.KEY_DOWN || key == 200) {
				frame.set_y(frame.get_y()-1);
			}

			if (key == Keyboard.KEY_UP || key == 208) {
				frame.set_y(frame.get_y()+1);
			}

		}
	}

	@Override
	protected void mouseClicked(int mx, int my, int mouse) {
		for (Frame frames : this.frame) {
			frames.mouse(mx, my, mouse);

			// If left click.
			if (mouse == 0) {
				if (frames.motion(mx, my) && frames.can()) {
					frames.does_button_for_do_widgets_can(false);

					this.current = frames;

					this.current.set_move(true);

					this.current.set_move_x(mx - this.current.get_x());
					this.current.set_move_y(my - this.current.get_y());
				}
			}
		}
	}

	@Override
	protected void mouseReleased(int mx, int my, int state) {
		for (Frame frames : this.frame) {
			frames.does_button_for_do_widgets_can(true);
			frames.mouse_release(mx, my, state);
			frames.set_move(false);
		}

		set_current(this.current);
	}

	@Override
	public void drawScreen(int mx, int my, float tick) {
		this.drawDefaultBackground();

		for (Frame frames : this.frame) {
			frames.render(mx, my);
		}
	}

	public void set_current(Frame current) {
		this.frame.remove(current);
		this.frame.add(current);
	}

	public Frame get_current() {
		return this.current;
	}

	public ArrayList<Frame> get_array_frames() {
		return this.frame;
	}

	public Frame get_frame_with_tag(String tag) {
		Frame frame_requested = null;

		for (Frame frames : get_array_frames()) {
			if (frames.get_tag().equals(tag)) {
				frame_requested = frames;
			}
		}

		return frame_requested;
	}
}
