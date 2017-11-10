package uk.co.thomasc.steamkit.steam3.handlers.steamchat.callbacks;

import uk.co.thomasc.steamkit.base.generated.steamlanguage.EChatInfoType;
import uk.co.thomasc.steamkit.base.generated.steamlanguageinternal.msg.MsgClientChatMemberInfo;
import uk.co.thomasc.steamkit.steam3.handlers.steamchat.types.StateChangeDetails;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;
import uk.co.thomasc.steamkit.types.steamid.SteamID;

/**
 * This callback is fired in response to chat member info being recieved.
 */
public final class ChatMemberInfoCallback extends CallbackMsg {
    /**
     * Gets SteamId of the chat room.
     */
    private final SteamID chatRoomID;
    /**
     * Gets the info type.
     */
    private final EChatInfoType type;
    /**
     * Gets the state change info for {@link EChatInfoType#StateChange} member
     * info updates.
     */
    private StateChangeDetails stateChangeInfo;

    public ChatMemberInfoCallback(MsgClientChatMemberInfo msg, byte[] payload) {
        chatRoomID = msg.getSteamIdChat();
        type = msg.type;
        switch (type) {
        case StateChange:
            stateChangeInfo = new StateChangeDetails(payload);
            break;
        }
    }

    /**
     * Gets SteamId of the chat room.
     */
    public SteamID getChatRoomID() {
        return this.chatRoomID;
    }

    /**
     * Gets the info type.
     */
    public EChatInfoType getType() {
        return this.type;
    }

    /**
     * Gets the state change info for {@link EChatInfoType#StateChange} member
     * info updates.
     */
    public StateChangeDetails getStateChangeInfo() {
        return this.stateChangeInfo;
    }
}
