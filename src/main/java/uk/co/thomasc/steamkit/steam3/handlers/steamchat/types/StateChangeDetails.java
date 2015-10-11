package uk.co.thomasc.steamkit.steam3.handlers.steamchat.types;

import java.io.IOException;

import lombok.Getter;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EChatMemberStateChange;
import uk.co.thomasc.steamkit.types.steamid.SteamID;
import uk.co.thomasc.steamkit.util.stream.BinaryReader;

/**
 * Represents state change information.
 */
public final class StateChangeDetails {
	/**
	 * Gets the SteamID of the chatter that was acted on.
	 */
	@Getter
	private SteamID chatterActedOn;

	/**
	 * Gets the state change for the acted on SteamID.
	 */
	@Getter
	private EChatMemberStateChange stateChange;

	/**
	 * Gets the SteamID of the chatter that acted on {@link #chatterActedOn}.
	 */
	@Getter
	private SteamID chatterActedBy;

	/**
	 * Gets the member information for a user that has joined the chat room.
	 * This field is only populated when StateChange is EChatMemberStateChange.Entered
	 */
	@Getter
	private ChatMemberInfo memberInfo;

	public StateChangeDetails(byte[] data) {
		final BinaryReader is = new BinaryReader(data);

		try {
			chatterActedOn = new SteamID(is.readLong());
			stateChange = EChatMemberStateChange.f(is.readInt());
			chatterActedBy = new SteamID(is.readLong());

			if(stateChange == EChatMemberStateChange.Entered) {
				memberInfo = new ChatMemberInfo();
				memberInfo.readFromBinary(is);
			}
		} catch (final IOException e) {
			uk.co.thomasc.steamkit.util.logging.DebugLog.writeLine("NEW_EX", "Exception: %s", e);
		}

		// based off disassembly
		//  - for InfoUpdate, a ChatMemberInfo object is present
		//  - for MemberLimitChange, looks like an ignored uint64 (probably steamid) followed
		//     by an int which likely represents the member limit
	}
}
