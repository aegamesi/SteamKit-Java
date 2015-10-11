package uk.co.thomasc.steamkit.steam3.handlers.steamfriends.callbacks;

import lombok.Getter;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientFriendMsgIncoming;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EChatEntryType;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;
import uk.co.thomasc.steamkit.types.steamid.SteamID;

/**
 * This callback is fired in response to receiving an echo message from another instance.
 */
public final class FriendMsgEchoCallback extends CallbackMsg {
	/**
	 * Gets or sets the recipient.
	 */
	@Getter
	private final SteamID recipient;

	/**
	 * Gets the chat entry type.
	 */
	@Getter
	private final EChatEntryType entryType;

	/**
	 * Gets a value indicating whether this message is from a limited account.
	 */
	@Getter
	private final boolean fromLimitedAccount;

	/**
	 * Gets the message.
	 */
	@Getter
	private String message;

	public FriendMsgEchoCallback(CMsgClientFriendMsgIncoming msg) {
		recipient = new SteamID(msg.steamidFrom);
		entryType = EChatEntryType.f(msg.chatEntryType);

		fromLimitedAccount = msg.fromLimitedAccount;

		if (msg.message != null && msg.message.length > 0) {
			message = new String(msg.message);
		}
	}
}
