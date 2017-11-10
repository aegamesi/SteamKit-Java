package uk.co.thomasc.steamkit.steam3.handlers.steamchat.callbacks;

import uk.co.thomasc.steamkit.base.generated.steamlanguage.EChatAction;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EChatActionResult;
import uk.co.thomasc.steamkit.base.generated.steamlanguageinternal.msg.MsgClientChatActionResult;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;
import uk.co.thomasc.steamkit.types.steamid.SteamID;

/**
 * This callback is fired when a chat action has completed.
 */
public final class ChatActionResultCallback extends CallbackMsg {
    /**
     * Gets the SteamID of the chat room the action was performed in.
     */
    private final SteamID chatRoomID;
    /**
     * Gets the SteamID of the chat member the action was performed on.
     */
    private final SteamID chatterID;
    /**
     * Gets the chat action that was performed.
     */
    private final EChatAction action;
    /**
     * Gets the result of the chat action.
     */
    private final EChatActionResult result;

    public ChatActionResultCallback(MsgClientChatActionResult result) {
        chatRoomID = result.getSteamIdChat();
        chatterID = result.getSteamIdUserActedOn();
        action = result.chatAction;
        this.result = result.actionResult;
    }

    /**
     * Gets the SteamID of the chat room the action was performed in.
     */
    public SteamID getChatRoomID() {
        return this.chatRoomID;
    }

    /**
     * Gets the SteamID of the chat member the action was performed on.
     */
    public SteamID getChatterID() {
        return this.chatterID;
    }

    /**
     * Gets the chat action that was performed.
     */
    public EChatAction getAction() {
        return this.action;
    }

    /**
     * Gets the result of the chat action.
     */
    public EChatActionResult getResult() {
        return this.result;
    }
}
