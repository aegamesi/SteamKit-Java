package uk.co.thomasc.steamkit.steam3.handlers.steammasterserver.types;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgGMSClientServerQueryResponse;
import uk.co.thomasc.steamkit.util.cSharp.ip.IPEndPoint;
import uk.co.thomasc.steamkit.util.util.NetHelpers;

/**
 * Represents a single server.
 */
public final class Server {
    /**
     * Gets the IP endpoint of the server.
     */
    private final IPEndPoint endPoint;
    /**
     * Gets the number of Steam authenticated players on this server.
     */
    private final int authedPlayers;

    public Server(CMsgGMSClientServerQueryResponse.Server server) {
        endPoint = new IPEndPoint(NetHelpers.getIPAddress(server.serverIp), (short) server.serverPort);
        authedPlayers = server.authPlayers;
    }

    /**
     * Gets the IP endpoint of the server.
     */
    public IPEndPoint getEndPoint() {
        return this.endPoint;
    }

    /**
     * Gets the number of Steam authenticated players on this server.
     */
    public int getAuthedPlayers() {
        return this.authedPlayers;
    }
}
