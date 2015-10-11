package uk.co.thomasc.steamkit.steam3.handlers.steamchat.types;


import uk.co.thomasc.steamkit.base.generated.steamlanguage.EChatPermission;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EClanPermission;
import uk.co.thomasc.steamkit.types.MessageObject;
import uk.co.thomasc.steamkit.types.keyvalue.KeyValue;
import uk.co.thomasc.steamkit.types.steamid.SteamID;

/**
 * Represents the details of a user which is a member of a chatroom.
 */
public class ChatMemberInfo extends MessageObject {
	/**
	 * Initializes a new instance of the ChatMemberInfo class.
	 */
	public ChatMemberInfo() {
		super();
	}

	/**
	 * Initializes a new instance of the ChatMemberInfo class.
	 *
	 * @param keyValues The KeyValue backing store for this member info.
	 */
	public ChatMemberInfo(KeyValue keyValues) {
		super(keyValues);
	}

	/**
	 * Gets the clan permission details of this chat member.
	 */
	public EClanPermission getDetails() {
		int value = keyValues.get("Details").asInteger();
		return EClanPermission.f(value);
	}

	/**
	 * Gets the permissions this user has within the chatroom.
	 */
	public EChatPermission getPermissions() {
		int value = keyValues.get("permissions").asInteger();
		return EChatPermission.f(value);
	}

	/**
	 * Gets the SteamID of this user.
	 */
	public SteamID getSteamID() {
		long value = keyValues.get("steamID").asLong();
		return new SteamID(value);
	}
}