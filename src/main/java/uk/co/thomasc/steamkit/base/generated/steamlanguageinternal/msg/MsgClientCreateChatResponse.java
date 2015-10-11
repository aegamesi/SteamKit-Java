package uk.co.thomasc.steamkit.base.generated.steamlanguageinternal.msg;

import java.io.IOException;

import uk.co.thomasc.steamkit.base.generated.steamlanguage.EChatRoomType;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EMsg;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EResult;
import uk.co.thomasc.steamkit.base.generated.steamlanguageinternal.ISteamSerializableMessage;
import uk.co.thomasc.steamkit.types.steamid.SteamID;
import uk.co.thomasc.steamkit.util.stream.BinaryReader;
import uk.co.thomasc.steamkit.util.stream.BinaryWriter;

public class MsgClientCreateChatResponse implements ISteamSerializableMessage {

	@Override
	public EMsg getEMsg() {
		return EMsg.ClientCreateChat;
	}

	// result
	private int result = 0;

	public EResult getResult() {
		return EResult.f(result);
	}

	public void setResult(EResult result) {
		this.result = result.v();
	}


	// steamIdChat
	private long steamIdChat = 0;

	public SteamID getSteamIdChat() {
		return new SteamID(steamIdChat);
	}

	public void setSteamIdChat(SteamID steamId) {
		steamIdChat = steamId.convertToLong();
	}


	// chatRoomType
	private int chatRoomType = 0;

	public EChatRoomType getChatRoomType() {
		return EChatRoomType.f(chatRoomType);
	}

	public void setChatRoomType(EChatRoomType chatRoomType) {
		this.chatRoomType = chatRoomType.v();
	}


	// steamIdFriendChat
	private long steamIdFriendChat = 0;

	public SteamID getSteamIdFriendChat() {
		return new SteamID(steamIdFriendChat);
	}

	public void setSteamIdFriendChat(SteamID steamId) {
		steamIdFriendChat = steamId.convertToLong();
	}

	//
	public MsgClientCreateChatResponse() {

	}

	@Override
	public void serialize(BinaryWriter stream) throws IOException {
		stream.write(result);

		stream.write(steamIdChat);

		stream.write(chatRoomType);

		stream.write(steamIdFriendChat);
	}

	@Override
	public void deSerialize(BinaryReader stream) throws IOException {
		result = stream.readInt();

		steamIdChat = stream.readLong();

		chatRoomType = stream.readInt();

		steamIdFriendChat = stream.readLong();
	}
}
