package allah.owns.me.allahware.manager;

import allah.owns.turok.draw.RenderHelp;
import allah.owns.me.allahware.event.events.EventRender;
import allah.owns.me.allahware.hacks.*;
import allah.owns.me.allahware.hacks.chat.*;
import allah.owns.me.allahware.hacks.combat.*;
import allah.owns.me.allahware.hacks.exploit.*;
import allah.owns.me.allahware.hacks.misc.*;
import allah.owns.me.allahware.hacks.movement.*;
import allah.owns.me.allahware.hacks.render.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Comparator;

public class ModuleManager {

	public static ArrayList<Hack> array_hacks = new ArrayList<>();

	public static Minecraft mc = Minecraft.getMinecraft();

	public ModuleManager() {

		// CLick GUI and HUD.
		add_hack(new ClickGUI());
		add_hack(new ClickHUD());

		// Chat.
		add_hack(new WeaknessNotifier());
		add_hack(new BurrowAnnouncer());
		add_hack(new AutoRAT());
		add_hack(new GetCoords());
		add_hack(new GetIP());
		add_hack(new ChatSuffix());
		add_hack(new AutoExcuse());
		add_hack(new VisualRange());
		add_hack(new Totempop());
		add_hack(new ClearChat());
		add_hack(new ChatMods());
		add_hack(new AutoEz());
		add_hack(new AntiRacist());
		add_hack(new Announcer());

		// Combat.
		add_hack (new AutoAnvil());
		add_hack(new GhostEXP());
		add_hack(new AutoPiston());
		add_hack(new Burrow());
		add_hack(new Criticals());
		add_hack(new KillAura());
		add_hack(new Surround());
		add_hack(new Velocity());
		add_hack(new AutoCrystal());
		add_hack(new HoleFill());
		add_hack(new Trap());
		add_hack(new Socks());
		add_hack(new SelfTrap());
		add_hack(new AutoArmour());
		add_hack(new Auto32k());
		add_hack(new Webfill());
		add_hack(new AutoWeb());
		add_hack(new BedAura());
		add_hack(new Offhand());
		add_hack(new AutoGapple());
		add_hack(new AutoTotem());
		add_hack(new AutoMine());

		// Exploit.
		add_hack(new Auto5b5tDupe());
		add_hack(new XCarry());
		add_hack(new NoSwing());
		add_hack(new PortalGodMode());
		add_hack(new PacketMine());
		add_hack(new EntityMine());
		add_hack(new BuildHeight());
		add_hack(new CoordExploit());
		add_hack(new NoHandshake());

		// Movement.
		add_hack(new BetterStrafe());
		add_hack(new AutoEat());
		add_hack(new Blink());
		add_hack(new Flight());
		add_hack(new Freecam());
		add_hack(new HighJump());
		add_hack(new Jesus());
		add_hack(new LongJump());
		add_hack(new MCP());
		add_hack(new NoSlowDown());
		add_hack(new NoVoid());
		add_hack(new ReverseStep());
		add_hack(new Scaffold());
		add_hack(new Sprint());
		add_hack(new Step());
		add_hack(new StepOld());
		add_hack(new Strafe());
		add_hack(new Timer());
		
		// Render.
		add_hack (new SmallHand());
		add_hack(new OffhandSwing());
		add_hack(new GSViewModel());
		add_hack(new FOVSlider());
		add_hack(new NoRender());
		add_hack(new NoWeather());
		add_hack(new PerspectiveModule());
		add_hack(new Trajectories());
		add_hack(new Trajectories());
		add_hack(new Highlight());
		add_hack(new HoleESP());
		add_hack(new ShulkerPreview());
		add_hack(new ViewmodleChanger());
		add_hack(new VoidESP());
		add_hack(new Antifog());
		add_hack(new NameTags());
		add_hack(new FuckedDetector());
		add_hack(new Tracers());
		add_hack(new SkyColour());
		add_hack(new Chams());
		add_hack(new Capes());
		add_hack(new AlwaysNight());
		add_hack(new CityEsp());

		// Misc.
		add_hack(new AntiAim());
		add_hack(new AutoKit());
		add_hack(new AutoRespawn());
		add_hack(new AutoSuicide());
		add_hack(new CreativeBypass());
		add_hack(new PingBypass());
		add_hack(new MiddleClickFriends());
		add_hack(new StopEXP());
		add_hack(new AutoReplenish());
		add_hack(new AutoNomadHut());
		add_hack(new FastUtil());
		add_hack(new FakePlayer());
		add_hack(new Speedmine());

		array_hacks.sort(Comparator.comparing(Hack::get_name));
	}

	public void add_hack(Hack module) {
		array_hacks.add(module);
	}

	public ArrayList<Hack> get_array_hacks() {
		return array_hacks;
	}

	public ArrayList<Hack> get_array_active_hacks() {
		ArrayList<Hack> actived_modules = new ArrayList<>();

		for (Hack modules : get_array_hacks()) {
			if (modules.is_active()) {
				actived_modules.add(modules);
			}
		}

		return actived_modules;
	}

	public Vec3d process(Entity entity, double x, double y, double z) {
		return new Vec3d(
			(entity.posX - entity.lastTickPosX) * x,
			(entity.posY - entity.lastTickPosY) * y,
			(entity.posZ - entity.lastTickPosZ) * z);
	}

	public Vec3d get_interpolated_pos(Entity entity, double ticks) {
		return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(process(entity, ticks, ticks, ticks)); // x, y, z.
	}

	public void render(RenderWorldLastEvent event) {
		mc.profiler.startSection("wurstplus");
		mc.profiler.startSection("setup");

		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		GlStateManager.disableDepth();

		GlStateManager.glLineWidth(1f);

		Vec3d pos = get_interpolated_pos(mc.player, event.getPartialTicks());

		EventRender event_render = new EventRender(RenderHelp.INSTANCE, pos);

		event_render.reset_translation();

		mc.profiler.endSection();

		for (Hack modules : get_array_hacks()) {
			if (modules.is_active()) {
				mc.profiler.startSection(modules.get_tag());

				modules.render(event_render);

				mc.profiler.endSection();
			}
		}

		mc.profiler.startSection("release");

		GlStateManager.glLineWidth(1f);

		GlStateManager.shadeModel(GL11.GL_FLAT);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
		GlStateManager.enableDepth();
		GlStateManager.enableCull();

		RenderHelp.release_gl();

		mc.profiler.endSection();
		mc.profiler.endSection();
	}

	public void update() {
		for (Hack modules : get_array_hacks()) {
			if (modules.is_active()) {
				modules.update();
			}
		}
	}

	public void render() {
		for (Hack modules : get_array_hacks()) {
			if (modules.is_active()) {
				modules.render();
			}
		}
	}

	public void bind(int event_key) {
		if (event_key == 0) {
			return;
		}

		for (Hack modules : get_array_hacks()) {
			if (modules.get_bind(0) == event_key) {
				modules.toggle();
			}
		}
	}

	public Hack get_module_with_tag(String tag) {
		Hack module_requested = null;

		for (Hack module : get_array_hacks()) {
			if (module.get_tag().equalsIgnoreCase(tag)) {
				module_requested = module;
			}
		}

		return module_requested;
	}

	public ArrayList<Hack> get_modules_with_category(Category category) {
		ArrayList<Hack> module_requesteds = new ArrayList<>();

		for (Hack modules : get_array_hacks()) {
			if (modules.get_category().equals(category)) {
				module_requesteds.add(modules);
			}
		}

		return module_requesteds;
	}

}
