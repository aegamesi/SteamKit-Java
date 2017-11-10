package uk.co.thomasc.steamkit.steam3.handlers;

import uk.co.thomasc.steamkit.base.IPacketMsg;
import uk.co.thomasc.steamkit.steam3.steamclient.SteamClient;

public abstract class ClientMsgHandler {
    /**
     * Gets the underlying {@link SteamClient} for use in sending replies.
     */
    private SteamClient client;

    /**
     * Initializes a new instance of the {@link ClientMsgHandler} class.
     */
    public ClientMsgHandler() {
    }

    public void setup(SteamClient client) {
        this.client = client;
    }

    /**
     * Handles a client message. This should not be called directly.
     *
     * @param packetMsg The packet message that contains the data.
     */
    public abstract void handleMsg(IPacketMsg packetMsg);

    /**
     * Gets the underlying {@link SteamClient} for use in sending replies.
     */
    protected SteamClient getClient() {
        return this.client;
    }
}
