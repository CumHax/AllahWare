package allah.owns.me.allahware.util;

import allah.owns.me.Client;

import java.util.ArrayList;

public class NotificationUtil {

    private static final ArrayList<Notification> active_notifications = new ArrayList<>();

    public static void send_notification (Notification message) {
        active_notifications.add(0, message);
    }

    public static ArrayList<Notification> get_notifications () {
        return active_notifications;
    }

    public static void update () {
        active_notifications.removeIf(notification -> (System.currentTimeMillis() - notification.getTimeCreated()) > 5000);

        int maxnotifications = Client.get_setting_manager().get_setting_with_tag("HUD", "maxnotifications").get_value(1);
        if (maxnotifications < active_notifications.size()) {
            active_notifications.remove(maxnotifications - 1);
        }
    }
}
