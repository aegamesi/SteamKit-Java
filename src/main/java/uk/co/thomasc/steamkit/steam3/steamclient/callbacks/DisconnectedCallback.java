package uk.co.thomasc.steamkit.steam3.steamclient.callbacks;

import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;

/**
 * This callback is received when the steamclient is physically disconnected
 * from the Steam network.
 */
public final class DisconnectedCallback extends CallbackMsg {
    private boolean newconnection;

    public DisconnectedCallback(boolean newconnection) {
        this.newconnection = newconnection;
    }

    public boolean isNewconnection() {
        return this.newconnection;
    }
}
