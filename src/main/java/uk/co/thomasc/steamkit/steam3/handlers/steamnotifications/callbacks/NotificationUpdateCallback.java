package uk.co.thomasc.steamkit.steam3.handlers.steamnotifications.callbacks;

import uk.co.thomasc.steamkit.steam3.handlers.steamnotifications.SteamNotifications;
import uk.co.thomasc.steamkit.steam3.handlers.steamnotifications.types.NotificationType;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;

import java.util.Map;

/**
 * This callback is sent in response to any updates to notification counts
 */
public final class NotificationUpdateCallback extends CallbackMsg {
    /**
     * The map of notification types and counts
     */
    private final Map<NotificationType, Integer> notificationCounts;
    /**
     * The total number of notification counts
     */
    private final int totalNotificationCount;

    public NotificationUpdateCallback(SteamNotifications steamNotifications) {
        this.notificationCounts = steamNotifications.getNotificationCounts();
        this.totalNotificationCount = steamNotifications.getTotalNotificationCount();
    }

    /**
     * The map of notification types and counts
     */
    public Map<NotificationType, Integer> getNotificationCounts() {
        return this.notificationCounts;
    }

    /**
     * The total number of notification counts
     */
    public int getTotalNotificationCount() {
        return this.totalNotificationCount;
    }
}
