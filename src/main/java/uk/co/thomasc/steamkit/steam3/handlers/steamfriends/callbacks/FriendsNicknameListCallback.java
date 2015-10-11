package uk.co.thomasc.steamkit.steam3.handlers.steamfriends.callbacks;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientPlayerNicknameList;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;
import uk.co.thomasc.steamkit.types.steamid.SteamID;

/**
 * This callback is fired when the client receives a list of friend nicknames.
 */
public final class FriendsNicknameListCallback extends CallbackMsg {
	/**
	 * Gets a value indicating whether this {@link FriendsNicknameListCallback} is an
	 * incremental update.
	 */
	@Getter
	private final boolean incremental;

	/**
	 * Gets the friend list.
	 */
	@Getter
	private final Set<Friend> nicknameList = new HashSet<Friend>();

	public FriendsNicknameListCallback(CMsgClientPlayerNicknameList msg) {
		incremental = msg.incremental;

		for (final CMsgClientPlayerNicknameList.PlayerNickname nicknameEntry : msg.nicknames) {
			nicknameList.add(new Friend(nicknameEntry));
		}
	}

	/**
	 * Represents a single friend entry in a client's friendlist.
	 */
	public final class Friend {
		/**
		 * Gets the SteamID of the friend.
		 */
		@Getter
		private final SteamID steamId;

		/**
		 * Gets the nickname to this friend.
		 */
		@Getter
		private final String nickname;

		public Friend(CMsgClientPlayerNicknameList.PlayerNickname nicknameEntry) {
			steamId = new SteamID(nicknameEntry.steamid);
			nickname = nicknameEntry.nickname;
		}
	}
}
