package uk.co.thomasc.steamkit.steam3.handlers.steamchat.callbacks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EChatRoomEnterResponse;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EChatRoomType;
import uk.co.thomasc.steamkit.base.generated.steamlanguageinternal.msg.MsgClientChatEnter;
import uk.co.thomasc.steamkit.steam3.handlers.steamchat.types.ChatMemberInfo;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;
import uk.co.thomasc.steamkit.types.steamid.SteamID;
import uk.co.thomasc.steamkit.util.stream.BinaryReader;

/**
 * This callback is fired in response to attempting to join a chat.
 */
public final class ChatEnterCallback extends CallbackMsg {
	/**
	 * Gets SteamID of the chat room.
	 */
	@Getter
	private final SteamID chatID;

	/**
	 * Gets the friend ID.
	 */
	@Getter
	private final SteamID friendID;

	/**
	 * Gets the type of the chat room.
	 */
	@Getter
	private final EChatRoomType chatRoomType;

	/**
	 * Gets the SteamID of the chat room owner.
	 */
	@Getter
	private final SteamID ownerID;

	/**
	 * Gets clan SteamID that owns this chat room.
	 */
	@Getter
	private final SteamID clanID;

	/**
	 * Gets the chat flags.
	 */
	@Getter
	private final byte chatFlags;

	/**
	 * Gets the chat enter response.
	 */
	@Getter
	private final EChatRoomEnterResponse enterResponse;

	/**
	 * Gets the number of users currently in this chat room.
	 */
	@Getter
	private final int numChatMembers;

	/**
	 * Gets the name of the chat room.
	 */
	@Getter
	private String chatRoomName;

	/**
	 * Gets a list of ChatMemberInfo instances for each of the members of this chat room.
	 */
	@Getter
	private List<ChatMemberInfo> chatMembers;

	public ChatEnterCallback(MsgClientChatEnter msg, BinaryReader payload) {
		chatID = msg.getSteamIdChat();
		friendID = msg.getSteamIdFriend();

		chatRoomType = msg.chatRoomType;

		ownerID = msg.getSteamIdOwner();
		clanID = msg.getSteamIdClan();

		chatFlags = msg.chatFlags;

		enterResponse = msg.enterResponse;

		numChatMembers = msg.numMembers;

		// reading the payload
		try {
			chatRoomName = payload.readNullTermString();

			if(enterResponse != EChatRoomEnterResponse.Success) {
				// the rest of the payload depends on a successful chat enter
				return;
			}

			chatMembers = new ArrayList<ChatMemberInfo>();
			for(int i = 0; i < numChatMembers; i++) {
				ChatMemberInfo memberInfo = new ChatMemberInfo();
				memberInfo.readFromBinary(payload);
				chatMembers.add(memberInfo);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
