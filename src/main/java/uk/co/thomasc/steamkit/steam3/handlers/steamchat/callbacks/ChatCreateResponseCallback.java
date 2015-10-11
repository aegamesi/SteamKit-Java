package uk.co.thomasc.steamkit.steam3.handlers.steamchat.callbacks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EChatRoomEnterResponse;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EChatRoomType;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EResult;
import uk.co.thomasc.steamkit.base.generated.steamlanguageinternal.msg.MsgClientChatEnter;
import uk.co.thomasc.steamkit.base.generated.steamlanguageinternal.msg.MsgClientCreateChatResponse;
import uk.co.thomasc.steamkit.steam3.handlers.steamchat.types.ChatMemberInfo;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;
import uk.co.thomasc.steamkit.types.steamid.SteamID;
import uk.co.thomasc.steamkit.util.stream.BinaryReader;

/**
 * This callback is fired in response to attemping to create a chat room.
 */
public final class ChatCreateResponseCallback extends CallbackMsg {
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
	 * Gets the result of the request
	 */
	@Getter
	private final EResult result;

	public ChatCreateResponseCallback(MsgClientCreateChatResponse msg) {
		chatID = msg.getSteamIdChat();
		friendID = msg.getSteamIdFriendChat();
		chatRoomType = msg.getChatRoomType();
		result = msg.getResult();
	}
}
