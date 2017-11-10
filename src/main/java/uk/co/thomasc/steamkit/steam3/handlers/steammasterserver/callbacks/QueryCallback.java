package uk.co.thomasc.steamkit.steam3.handlers.steammasterserver.callbacks;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgGMSClientServerQueryResponse;
import uk.co.thomasc.steamkit.steam3.handlers.steammasterserver.SteamMasterServer;
import uk.co.thomasc.steamkit.steam3.handlers.steammasterserver.types.QueryDetails;
import uk.co.thomasc.steamkit.steam3.handlers.steammasterserver.types.Server;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * This callback is received in response to calling
 * {@link SteamMasterServer#serverQuery(QueryDetails)}.
 */
public final class QueryCallback extends CallbackMsg {
    /**
     * Gets the list of servers.
     */
    private final List<Server> servers = new ArrayList<Server>();

    public QueryCallback(CMsgGMSClientServerQueryResponse msg) {
        for (final CMsgGMSClientServerQueryResponse.Server s : msg.servers) {
            servers.add(new Server(s));
        }
    }

    /**
     * Gets the list of servers.
     */
    public List<Server> getServers() {
        return this.servers;
    }
}
