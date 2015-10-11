package uk.co.thomasc.steamkit.steam3.handlers.steamfriends.callbacks;

import lombok.Getter;
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
	@Getter
	private final SteamID steamId;

	/**
	 * Gets the success value
	 */
	@Getter
	private final int success;

	/**
	 * Gets the array of messages
	 */
	@Getter
	private final FriendMsg[] messages;


	public FriendMsgHistoryCallback(CMsgClientFSGetFriendMessageHistoryResponse msg) {
		steamId = new SteamID(msg.steamid);
		success = msg.success;

		messages = new FriendMsg[msg.messages.length];
		for(int i = 0; i < messages.length; i++) {
			FriendMessage protoMessage = msg.messages[i];
			FriendMsg newMessage = new FriendMsg(protoMessage.accountid, protoMessage.timestamp, protoMessage.message, protoMessage.unread);
			messages[i] = newMessage;
		}
	}

	public static final class FriendMsg {
		/**
		 * Gets the account id
		 */
		@Getter
		private final SteamID sender;

		/**
		 * Gets the timestamp
		 */
		@Getter
		private final long timestamp;

		/**
		 * Gets the message body
		 */
		@Getter
		private final String message;

		/**
		 * Gets if the message is unread
		 */
		@Getter
		private final boolean unread;

		FriendMsg(int accountid, long timestamp, String message, boolean unread) {
			this.sender = new SteamID(accountid, EUniverse.Public, EAccountType.Individual);
			this.timestamp = timestamp;
			this.message = message;
			this.unread = unread;
		}
	}
}
