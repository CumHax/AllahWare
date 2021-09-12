package allah.owns.me.allahware.command.commands;

import allah.owns.me.allahware.command.Command;
import allah.owns.me.allahware.util.Notification;
import allah.owns.me.allahware.util.NotificationUtil;

public class NotificationTest extends Command {
    public NotificationTest() {
        super("notificationtest", "send urself notification");
    }

    public boolean get_message (String[] message) {
        NotificationUtil.send_notification(new Notification("Test notification"));
        return true;
    }
}
