package uk.co.thomasc.steamkit.steam3.handlers.steamfriends.callbacks;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientFriendMsgIncoming;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EChatEntryType;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;
import uk.co.thomasc.steamkit.types.steamid.SteamID;

/**
 * This callback is fired in response to receiving an echo message from another instance.
 */
public final class FriendMsgEchoCallback extends CallbackMsg {
    /**
     * Gets or sets the recipient.
     */
    private final SteamID recipient;
    /**
     * Gets the chat entry type.
     */
    private final EChatEntryType entryType;
    /**
     * Gets a value indicating whether this message is from a limited account.
     */
    private final boolean fromLimitedAccount;
    /**
     * Gets the message.
     */
    private String message;

    public FriendMsgEchoCallback(CMsgClientFriendMsgIncoming msg) {
        recipient = new SteamID(msg.steamidFrom);
        entryType = EChatEntryType.f(msg.chatEntryType);
        fromLimitedAccount = msg.fromLimitedAccount;
        if (msg.message != null && msg.message.length > 0) {
            message = new String(msg.message);
        }
    }

    /**
     * Gets or sets the recipient.
     */
    public SteamID getRecipient() {
        return this.recipient;
    }

    /**
     * Gets the chat entry type.
     */
    public EChatEntryType getEntryType() {
        return this.entryType;
    }

    /**
     * Gets a value indicating whether this message is from a limited account.
     */
    public boolean isFromLimitedAccount() {
        return this.fromLimitedAccount;
    }

    /**
     * Gets the message.
     */
    public String getMessage() {
        return this.message;
    }
}
