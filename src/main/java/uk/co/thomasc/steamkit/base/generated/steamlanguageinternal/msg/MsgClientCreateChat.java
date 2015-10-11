package uk.co.thomasc.steamkit.base.generated.steamlanguageinternal.msg;

import java.io.IOException;

import uk.co.thomasc.steamkit.base.generated.steamlanguage.EChatPermission;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EChatRoomType;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EMsg;
import uk.co.thomasc.steamkit.base.generated.steamlanguageinternal.ISteamSerializableMessage;
import uk.co.thomasc.steamkit.types.gameid.GameID;
import uk.co.thomasc.steamkit.types.steamid.SteamID;
import uk.co.thomasc.steamkit.util.stream.BinaryReader;
import uk.co.thomasc.steamkit.util.stream.BinaryWriter;

public class MsgClientCreateChat implements ISteamSerializableMessage {

	@Override
	public EMsg getEMsg() {
		return EMsg.ClientCreateChat;
	}

	// chatRoomType
	private int chatRoomType = 0;

	public EChatRoomType getChatRoomType() {
		return EChatRoomType.f(chatRoomType);
	}

	public void setChatRoomType(EChatRoomType chatRoomType) {
		this.chatRoomType = chatRoomType.v();
	}

	// gameId
	private long gameID = 0;

	public GameID getGameId() {
		return new GameID(gameID);
	}

	public void setGameId(GameID GameID) {
		gameID = GameID.toLong();
	}

	// steamIdClan
	private long steamIdClan = 0;

	public SteamID getSteamIdClan() {
		return new SteamID(steamIdClan);
	}

	public void setSteamIdClan(SteamID steamId) {
		steamIdClan = steamId.convertToLong();
	}

	// permissionOfficer
	private int permissionOfficer = 0;

	public EChatPermission getPermissionOfficer() {
		return EChatPermission.f(permissionOfficer);
	}

	public void setPermissionOfficer(EChatPermission permissionOfficer) {
		this.permissionOfficer = permissionOfficer.v();
	}

	// permissionMember
	private int permissionMember = 0;

	public EChatPermission getPermissionMember() {
		return EChatPermission.f(permissionMember);
	}

	public void setPermissionMember(EChatPermission permissionMember) {
		this.permissionMember = permissionMember.v();
	}

	// permissionAll
	private int permissionAll = 0;

	public EChatPermission getPermissionAll() {
		return EChatPermission.f(permissionAll);
	}

	public void setPermissionAll(EChatPermission permissionAll) {
		this.permissionAll = permissionAll.v();
	}

	// membersMax
	private long membersMax = 0;

	public long getMembersMax() {
		return membersMax;
	}

	public void setMembersMax(long membersMax) {
		this.membersMax = membersMax;
	}

	// chatFlags
	private byte chatFlags = 0;

	public byte getChatFlags() {
		return chatFlags;
	}

	public void setChatFlags(byte flags) {
		this.chatFlags = flags;
	}

	// steamIdFriendChat
	private long steamIdFriendChat = 0;

	public SteamID getSteamIdFriendChat() {
		return new SteamID(steamIdFriendChat);
	}

	public void setSteamIdFriendChat(SteamID steamId) {
		steamIdFriendChat = steamId.convertToLong();
	}

	// steamIdInvited
	private long steamIdInvited = 0;

	public SteamID getSteamIdInvited() {
		return new SteamID(steamIdInvited);
	}

	public void setSteamIdInvited(SteamID steamId) {
		steamIdInvited = steamId.convertToLong();
	}
	
	//
	public MsgClientCreateChat() {

	}

	@Override
	public void serialize(BinaryWriter stream) throws IOException {
		stream.write(chatRoomType);

		stream.write(gameID);

		stream.write(steamIdClan);

		stream.write(permissionOfficer);
		stream.write(permissionMember);
		stream.write(permissionAll);

		stream.write(membersMax);

		stream.write(chatFlags);

		stream.write(steamIdFriendChat);
		stream.write(steamIdInvited);
	}

	@Override
	public void deSerialize(BinaryReader stream) throws IOException {
		chatRoomType = stream.readInt();

		gameID = stream.readLong();

		steamIdClan = stream.readLong();

		permissionOfficer = stream.readInt();
		permissionMember = stream.readInt();
		permissionAll = stream.readInt();

		membersMax = stream.readLong();

		chatFlags = stream.readByte();

		steamIdFriendChat = stream.readLong();
		steamIdInvited = stream.readLong();
	}
}
