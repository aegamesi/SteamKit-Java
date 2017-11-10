package uk.co.thomasc.steamkit.steam3.handlers.steamuser.callbacks;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientNewLoginKey;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;

/**
 * This callback is returned some time after logging onto the network.
 */
public final class LoginKeyCallback extends CallbackMsg {
    /**
     * Gets the login key.
     */
    private final String loginKey;
    /**
     * Gets the unique ID.
     */
    private final int uniqueId;

    public LoginKeyCallback(CMsgClientNewLoginKey logKey) {
        loginKey = logKey.loginKey;
        uniqueId = logKey.uniqueId;
    }

    /**
     * Gets the login key.
     */
    public String getLoginKey() {
        return this.loginKey;
    }

    /**
     * Gets the unique ID.
     */
    public int getUniqueId() {
        return this.uniqueId;
    }
}
