package allah.owns.me.allahware.guiscreen.hud;

import allah.owns.me.allahware.guiscreen.render.pinnables.Pinnable;
import allah.owns.me.allahware.util.Notification;
import allah.owns.me.allahware.util.NotificationUtil;

import java.util.ArrayList;

public class Notifications extends Pinnable {
    public Notifications() {
        super("Notifications", "notifications", 1, 0, 0);

        this.set_width(125);
        this.set_height(42);
    }

    @Override
    public void render() {
        ArrayList<Notification> notifications;
        NotificationUtil.update();

        notifications = NotificationUtil.get_notifications();

        int ttarstuuathfktsrtRENDERY = 0;
        for (Notification n : notifications) {
            int messageWidth = get(n.getMessage(), "width") + 25;
            int nWidth = Math.max(messageWidth, 125);
            create_rect(this.get_width() - nWidth, ttarstuuathfktsrtRENDERY, nWidth, ttarstuuathfktsrtRENDERY + 40, 0, 0, 0, 69);
            create_rect(this.get_width() - nWidth, ttarstuuathfktsrtRENDERY, this.get_width() - nWidth + 5, ttarstuuathfktsrtRENDERY + 40, n.getR(), n.getG(), n.getB(), 255);
            create_line(n.getMessage(), this.get_width() - nWidth + 10, ttarstuuathfktsrtRENDERY + (42 - (get(n.getMessage(), "height"))) / 2);
            ttarstuuathfktsrtRENDERY += 42;
        }
    }
}
