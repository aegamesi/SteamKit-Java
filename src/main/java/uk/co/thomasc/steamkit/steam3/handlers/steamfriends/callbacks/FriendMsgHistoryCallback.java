package uk.co.thomasc.steamkit.steam3.handlers.steamfriends.callbacks;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientFSGetFriendMessageHistoryResponse;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientFSGetFriendMessageHistoryResponse.FriendMessage;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EAccountType;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EUniverse;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;
import uk.co.thomasc.steamkit.types.steamid.SteamID;

/**
 * This callback is fired in response to receiving message history for a friend
 */
public final class FriendMsgHistoryCallback extends CallbackMsg {
    /**
     * Gets the steamID
     */
    private final SteamID steamId;
    /**
     * Gets the success value
     */
    private final int success;
    /**
     * Gets the array of messages
     */
    private final FriendMsg[] messages;

    public FriendMsgHistoryCallback(CMsgClientFSGetFriendMessageHistoryResponse msg) {
        steamId = new SteamID(msg.steamid);
        success = msg.success;
        messages = new FriendMsg[msg.messages.length];
        for (int i = 0; i < messages.length; i++) {
            FriendMessage protoMessage = msg.messages[i];
            FriendMsg newMessage = new FriendMsg(protoMessage.accountid, protoMessage.timestamp, protoMessage.message, protoMessage.unread);
            messages[i] = newMessage;
        }
    }


    public static final class FriendMsg {
        /**
         * Gets the account id
         */
        private final SteamID sender;
        /**
         * Gets the timestamp
         */
        private final long timestamp;
        /**
         * Gets the message body
         */
        private final String message;
        /**
         * Gets if the message is unread
         */
        private final boolean unread;

        FriendMsg(int accountid, long timestamp, String message, boolean unread) {
            this.sender = new SteamID(accountid, EUniverse.Public, EAccountType.Individual);
            this.timestamp = timestamp;
            this.message = message;
            this.unread = unread;
        }

        public SteamID getSender() {
            return this.sender;
        }

        public long getTimestamp() {
            return this.timestamp;
        }

        public String getMessage() {
            return this.message;
        }

        public boolean isUnread() {
            return this.unread;
        }
    }

    /**
     * Gets the steamID
     */
    public SteamID getSteamId() {
        return this.steamId;
    }

    /**
     * Gets the success value
     */
    public int getSuccess() {
        return this.success;
    }

    /**
     * Gets the array of messages
     */
    public FriendMsg[] getMessages() {
        return this.messages;
    }
}
