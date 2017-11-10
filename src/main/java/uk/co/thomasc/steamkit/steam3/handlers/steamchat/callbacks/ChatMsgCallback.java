package uk.co.thomasc.steamkit.steam3.handlers.steamchat.callbacks;

import uk.co.thomasc.steamkit.base.generated.steamlanguage.EChatEntryType;
import uk.co.thomasc.steamkit.base.generated.steamlanguageinternal.msg.MsgClientChatMsg;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;
import uk.co.thomasc.steamkit.types.steamid.SteamID;

/**
 * This callback is fired when a chat room message arrives.
 */
public final class ChatMsgCallback extends CallbackMsg {
    /**
     * Gets the SteamID of the chatter.
     */
    private final SteamID chatterID;
    /**
     * Gets the SteamID of the chat room.
     */
    private final SteamID chatRoomID;
    /**
     * Gets chat entry type.
     */
    private final EChatEntryType chatMsgType;
    /**
     * Gets the message.
     */
    private String message = "";

    public ChatMsgCallback(MsgClientChatMsg msg, byte[] payload) {
        chatterID = msg.getSteamIdChatter();
        chatRoomID = msg.getSteamIdChatRoom();
        chatMsgType = msg.chatMsgType;
        if (payload != null && payload.length > 0) {
            message = new String(copyOfRange(payload, 0, payload.length - 1));
        }
    }

    public static byte[] copyOfRange(byte[] from, int start, int end) {
        int length = end - start;
        byte[] result = new byte[length];
        System.arraycopy(from, start, result, 0, length);
        return result;
    }

    /**
     * Gets the SteamID of the chatter.
     */
    public SteamID getChatterID() {
        return this.chatterID;
    }

    /**
     * Gets the SteamID of the chat room.
     */
    public SteamID getChatRoomID() {
        return this.chatRoomID;
    }

    /**
     * Gets chat entry type.
     */
    public EChatEntryType getChatMsgType() {
        return this.chatMsgType;
    }

    /**
     * Gets the message.
     */
    public String getMessage() {
        return this.message;
    }
}
