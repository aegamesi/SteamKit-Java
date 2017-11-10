package uk.co.thomasc.steamkit.steam3.handlers.steamfriends.callbacks;

import uk.co.thomasc.steamkit.base.generated.steamlanguage.EResult;
import uk.co.thomasc.steamkit.base.generated.steamlanguageinternal.msg.MsgClientSetIgnoreFriendResponse;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;

/**
 * This callback is fired in response to an attempt at ignoring a friend.
 */
public final class IgnoreFriendCallback extends CallbackMsg {
    /**
     * Gets the result of ignoring a friend.
     */
    private final EResult result;

    public IgnoreFriendCallback(MsgClientSetIgnoreFriendResponse response) {
        result = response.result;
    }

    /**
     * Gets the result of ignoring a friend.
     */
    public EResult getResult() {
        return this.result;
    }
}
