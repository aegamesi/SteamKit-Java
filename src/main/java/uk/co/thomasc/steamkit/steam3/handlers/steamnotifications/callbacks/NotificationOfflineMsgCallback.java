package uk.co.thomasc.steamkit.steam3.handlers.steamnotifications.callbacks;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientOfflineMessageNotification;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EAccountType;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EUniverse;
import uk.co.thomasc.steamkit.steam3.handlers.steamnotifications.SteamNotifications;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;
import uk.co.thomasc.steamkit.types.steamid.SteamID;

import java.util.HashSet;
import java.util.Set;

/**
 * This callback is received in response to calling
 * {@link SteamNotifications#requestNotificationOfflineMessages()}.
 */
public final class NotificationOfflineMsgCallback extends CallbackMsg {
    /**
     * Gets the number of offline messages
     */
    private final int offlineMessages;
    /**
     * Gets the friends with offline messages
     */
    private final Set<SteamID> friendsWithOfflineMessages;

    public NotificationOfflineMsgCallback(CMsgClientOfflineMessageNotification msg) {
        offlineMessages = msg.offlineMessages;
        friendsWithOfflineMessages = new HashSet<SteamID>();
        for (int friend : msg.friendsWithOfflineMessages)
            friendsWithOfflineMessages.add(new SteamID(friend, EUniverse.Public, EAccountType.Individual));
    }

    /**
     * Gets the number of offline messages
     */
    public int getOfflineMessages() {
        return this.offlineMessages;
    }

    /**
     * Gets the friends with offline messages
     */
    public Set<SteamID> getFriendsWithOfflineMessages() {
        return this.friendsWithOfflineMessages;
    }
}
