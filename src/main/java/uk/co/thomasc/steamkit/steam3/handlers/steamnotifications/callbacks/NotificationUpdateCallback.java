package uk.co.thomasc.steamkit.steam3.handlers.steamnotifications.callbacks;

import java.util.Map;

import lombok.Getter;
import uk.co.thomasc.steamkit.steam3.handlers.steamnotifications.SteamNotifications;
import uk.co.thomasc.steamkit.steam3.handlers.steamnotifications.types.NotificationType;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;

/**
 * This callback is sent in response to any updates to notification counts
 */
public final class NotificationUpdateCallback extends CallbackMsg {
	/**
	 * The map of notification types and counts
	 */
	@Getter
	private final Map<NotificationType, Integer> notificationCounts;

	/**
	 * The total number of notification counts
	 */
	@Getter
	private final int totalNotificationCount;


	public NotificationUpdateCallback(SteamNotifications steamNotifications) {
		this.notificationCounts = steamNotifications.getNotificationCounts();
		this.totalNotificationCount = steamNotifications.getTotalNotificationCount();
	}
}
