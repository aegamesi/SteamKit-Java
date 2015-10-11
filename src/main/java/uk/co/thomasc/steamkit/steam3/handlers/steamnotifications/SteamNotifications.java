package uk.co.thomasc.steamkit.steam3.handlers.steamnotifications;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import uk.co.thomasc.steamkit.base.ClientMsgProtobuf;
import uk.co.thomasc.steamkit.base.IPacketMsg;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientCommentNotifications;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientItemAnnouncements;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientOfflineMessageNotification;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientRequestCommentNotifications;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientRequestItemAnnouncements;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientRequestOfflineMessageCount;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientUserNotifications;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientUserNotifications.Notification;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EMsg;
import uk.co.thomasc.steamkit.steam3.handlers.ClientMsgHandler;
import uk.co.thomasc.steamkit.steam3.handlers.steamnotifications.callbacks.NotificationOfflineMsgCallback;
import uk.co.thomasc.steamkit.steam3.handlers.steamnotifications.callbacks.NotificationUpdateCallback;
import uk.co.thomasc.steamkit.steam3.handlers.steamnotifications.types.NotificationType;

/**
 * This handler is used for grabbing/processing user notifications from steam
 */
public final class SteamNotifications extends ClientMsgHandler {
	@Getter
	private final Map<NotificationType, Integer> notificationCounts;

	public SteamNotifications() {
		// Initialize the array of notificaitons
		notificationCounts = new HashMap<NotificationType, Integer>();
		for(NotificationType notificationType : NotificationType.values())
			notificationCounts.put(notificationType, 0);
	}

	/**
	 * Gets the total number of user notifications
	 */
	public int getTotalNotificationCount() {
		int count = 0;
		for(Integer num : notificationCounts.values())
			count += num;

		// subtract the generic comment notification, to avoid double counting
		count -= notificationCounts.get(NotificationType.COMMENTS);

		return count;
	}

	/**
	 * Requests the notification for offline messages
	 */
	public void requestNotificationOfflineMessages() {
		final ClientMsgProtobuf<CMsgClientRequestOfflineMessageCount> request = new ClientMsgProtobuf<CMsgClientRequestOfflineMessageCount>(CMsgClientRequestOfflineMessageCount.class, EMsg.ClientFSRequestOfflineMessageCount);

		getClient().send(request);
	}

	/**
	 * Requests the notification for new comments
	 */
	public void requestNotificationComments() {
		final ClientMsgProtobuf<CMsgClientRequestCommentNotifications> request = new ClientMsgProtobuf<CMsgClientRequestCommentNotifications>(CMsgClientRequestCommentNotifications.class, EMsg.ClientRequestCommentNotifications);

		getClient().send(request);
	}

	/**
	 * Requests the notification for new items
	 */
	public void requestNotificationItem() {
		final ClientMsgProtobuf<CMsgClientRequestItemAnnouncements> request = new ClientMsgProtobuf<CMsgClientRequestItemAnnouncements>(CMsgClientRequestItemAnnouncements.class, EMsg.ClientRequestItemAnnouncements);

		getClient().send(request);
	}

	/**
	 * Requests the notification for new items
	 */
	public void requestNotificationGeneric() {

	}

	/**
	 * Handles a client message. This should not be called directly.
	 */
	@Override
	public void handleMsg(IPacketMsg packetMsg) {
		switch (packetMsg.getMsgType()) {
			case ClientFSOfflineMessageNotification:
				handleNotificationOfflineMessage(packetMsg);
				break;
			case ClientCommentNotifications:
				handleNotificationComment(packetMsg);
				break;
			case ClientItemAnnouncements:
				handleNotificationItem(packetMsg);
				break;
			case ClientUserNotifications:
				handleNotificationGeneric(packetMsg);

		}
	}

	void handleNotificationOfflineMessage(IPacketMsg packetMsg) {
		final ClientMsgProtobuf<CMsgClientOfflineMessageNotification> infoResponse = new ClientMsgProtobuf<CMsgClientOfflineMessageNotification>(CMsgClientOfflineMessageNotification.class, packetMsg);

		notificationCounts.put(NotificationType.OFFLINE_MSGS, infoResponse.getBody().offlineMessages);

		final NotificationOfflineMsgCallback callback = new NotificationOfflineMsgCallback(infoResponse.getBody());
		getClient().postCallback(callback);

		final NotificationUpdateCallback genericCallback = new NotificationUpdateCallback(this);
		getClient().postCallback(genericCallback);
	}

	void handleNotificationComment(IPacketMsg packetMsg) {
		final ClientMsgProtobuf<CMsgClientCommentNotifications> infoResponse = new ClientMsgProtobuf<CMsgClientCommentNotifications>(CMsgClientCommentNotifications.class, packetMsg);

		notificationCounts.put(NotificationType.COMMENTS, infoResponse.getBody().countNewComments);
		notificationCounts.put(NotificationType.COMMENTS_OWNER, infoResponse.getBody().countNewCommentsOwner);
		notificationCounts.put(NotificationType.COMMENTS_SUBSCRIPTIONS, infoResponse.getBody().countNewCommentsSubscriptions);

		final NotificationUpdateCallback genericCallback = new NotificationUpdateCallback(this);
		getClient().postCallback(genericCallback);
	}

	void handleNotificationItem(IPacketMsg packetMsg) {
		final ClientMsgProtobuf<CMsgClientItemAnnouncements> infoResponse = new ClientMsgProtobuf<CMsgClientItemAnnouncements>(CMsgClientItemAnnouncements.class, packetMsg);

		notificationCounts.put(NotificationType.ITEMS, infoResponse.getBody().countNewItems);

		final NotificationUpdateCallback genericCallback = new NotificationUpdateCallback(this);
		getClient().postCallback(genericCallback);
	}

	// this is for trade offers.
	void handleNotificationGeneric(IPacketMsg packetMsg) {
		final ClientMsgProtobuf<CMsgClientUserNotifications> infoResponse = new ClientMsgProtobuf<CMsgClientUserNotifications>(CMsgClientUserNotifications.class, packetMsg);

		notificationCounts.remove(NotificationType.TRADE_OFFERS);

		for(Notification notification : infoResponse.getBody().notifications) {
			if(notification.userNotificationType == 1) {
				notificationCounts.put(NotificationType.TRADE_OFFERS, notification.count);
			}
		}

		if(!notificationCounts.containsKey(NotificationType.TRADE_OFFERS))
			notificationCounts.put(NotificationType.TRADE_OFFERS, 0);


		final NotificationUpdateCallback genericCallback = new NotificationUpdateCallback(this);
		getClient().postCallback(genericCallback);
	}
}
