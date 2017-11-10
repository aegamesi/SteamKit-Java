package uk.co.thomasc.steamkit.steam3.handlers.steamchat.callbacks;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientChatInvite;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EChatRoomType;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;
import uk.co.thomasc.steamkit.types.gameid.GameID;
import uk.co.thomasc.steamkit.types.steamid.SteamID;

/**
 * This callback is fired when a chat invite is recieved.
 */
public final class ChatInviteCallback extends CallbackMsg {
    /**
     * Gets the SteamID of the user who was invited to the chat.
     */
    private final SteamID invitedID;
    /**
     * Gets the chat room SteamID.
     */
    private final SteamID chatRoomID;
    /**
     * Gets the SteamID of the user who performed the invitation.
     */
    private final SteamID patronID;
    /**
     * Gets the chat room type.
     */
    private final EChatRoomType chatRoomType;
    /**
     * Gets the SteamID of the chat friend.
     */
    private final SteamID friendChatID;
    /**
     * Gets the name of the chat room.
     */
    private final String chatRoomName;
    /**
     * Gets the GameID associated with this chat room, if it's a game lobby.
     */
    private final GameID gameID;

    public ChatInviteCallback(CMsgClientChatInvite invite) {
        invitedID = new SteamID(invite.steamIdInvited);
        chatRoomID = new SteamID(invite.steamIdChat);
        patronID = new SteamID(invite.steamIdPatron);
        chatRoomType = EChatRoomType.f(invite.chatroomType);
        friendChatID = new SteamID(invite.steamIdFriendChat);
        chatRoomName = invite.chatName;
        gameID = new GameID(invite.gameId);
    }

    /**
     * Gets the SteamID of the user who was invited to the chat.
     */
    public SteamID getInvitedID() {
        return this.invitedID;
    }

    /**
     * Gets the chat room SteamID.
     */
    public SteamID getChatRoomID() {
        return this.chatRoomID;
    }

    /**
     * Gets the SteamID of the user who performed the invitation.
     */
    public SteamID getPatronID() {
        return this.patronID;
    }

    /**
     * Gets the chat room type.
     */
    public EChatRoomType getChatRoomType() {
        return this.chatRoomType;
    }

    /**
     * Gets the SteamID of the chat friend.
     */
    public SteamID getFriendChatID() {
        return this.friendChatID;
    }

    /**
     * Gets the name of the chat room.
     */
    public String getChatRoomName() {
        return this.chatRoomName;
    }

    /**
     * Gets the GameID associated with this chat room, if it's a game lobby.
     */
    public GameID getGameID() {
        return this.gameID;
    }
}
