package allah.owns.me.allahware.hacks.movement;

import allah.owns.me.allahware.guiscreen.settings.Setting;
import allah.owns.me.allahware.hacks.Category;
import allah.owns.me.allahware.hacks.Hack;

public class Timer extends Hack {
    Setting timerSpeed = create("Speed", "TimerSpeed", 1.15, 0.1, 5.0);

    public Timer() {
        super(Category.MOVEMENT);
        name = "Timer";
        tag = "timer";
        description = "Change game speed";
    }

    @Override
    public void update() {
        mc.timer.tickLength = (float) (50.0f / timerSpeed.get_value(1.0));
    }
}
