package uk.co.thomasc.steamkit.steam3.handlers.steamchat.callbacks;

import uk.co.thomasc.steamkit.base.generated.steamlanguage.EChatRoomType;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EResult;
import uk.co.thomasc.steamkit.base.generated.steamlanguageinternal.msg.MsgClientCreateChatResponse;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;
import uk.co.thomasc.steamkit.types.steamid.SteamID;

/**
 * This callback is fired in response to attemping to create a chat room.
 */
public final class ChatCreateResponseCallback extends CallbackMsg {
    /**
     * Gets SteamID of the chat room.
     */
    private final SteamID chatID;
    /**
     * Gets the friend ID.
     */
    private final SteamID friendID;
    /**
     * Gets the type of the chat room.
     */
    private final EChatRoomType chatRoomType;
    /**
     * Gets the result of the request
     */
    private final EResult result;

    public ChatCreateResponseCallback(MsgClientCreateChatResponse msg) {
        chatID = msg.getSteamIdChat();
        friendID = msg.getSteamIdFriendChat();
        chatRoomType = msg.getChatRoomType();
        result = msg.getResult();
    }

    /**
     * Gets SteamID of the chat room.
     */
    public SteamID getChatID() {
        return this.chatID;
    }

    /**
     * Gets the friend ID.
     */
    public SteamID getFriendID() {
        return this.friendID;
    }

    /**
     * Gets the type of the chat room.
     */
    public EChatRoomType getChatRoomType() {
        return this.chatRoomType;
    }

    /**
     * Gets the result of the request
     */
    public EResult getResult() {
        return this.result;
    }
}
