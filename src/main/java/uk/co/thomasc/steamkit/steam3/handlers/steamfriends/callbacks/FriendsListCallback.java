package uk.co.thomasc.steamkit.steam3.handlers.steamfriends.callbacks;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientFriendsList;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EFriendRelationship;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;
import uk.co.thomasc.steamkit.types.steamid.SteamID;

/**
 * This callback is fired when the client receives a list of friends.
 */
public final class FriendsListCallback extends CallbackMsg {
	/**
	 * Gets a value indicating whether this {@link FriendsListCallback} is an
	 * incremental update.
	 */
	@Getter
	private final boolean incremental;

	/**
	 * Gets the friend list.
	 */
	@Getter
	private final Set<Friend> friendList = new HashSet<Friend>();

	public FriendsListCallback(CMsgClientFriendsList msg) {
		incremental = msg.bincremental;

		for (final CMsgClientFriendsList.Friend friend : msg.friends) {
			friendList.add(new Friend(friend));
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
		 * Gets the relationship to this friend.
		 */
		@Getter
		private final EFriendRelationship relationship;

		public Friend(CMsgClientFriendsList.Friend friend) {
			steamId = new SteamID(friend.ulfriendid);
			relationship = EFriendRelationship.f(friend.efriendrelationship);
		}
	}

}
