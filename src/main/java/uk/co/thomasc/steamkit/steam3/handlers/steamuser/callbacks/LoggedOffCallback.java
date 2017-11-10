package uk.co.thomasc.steamkit.steam3.handlers.steamuser.callbacks;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientLoggedOff;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EResult;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;

/**
 * This callback is returned in response to a log off attempt, or when the
 * client is told to log off by the server.
 */
public final class LoggedOffCallback extends CallbackMsg {
    /**
     * Gets the result of the log off.
     */
    private final EResult result;

    public LoggedOffCallback(CMsgClientLoggedOff resp) {
        result = EResult.f(resp.eresult);
    }

    /**
     * Gets the result of the log off.
     */
    public EResult getResult() {
        return this.result;
    }
}
