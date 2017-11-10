package uk.co.thomasc.steamkit.steam3.handlers.steamapps.callbacks;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientGetAppOwnershipTicketResponse;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EResult;
import uk.co.thomasc.steamkit.steam3.handlers.steamapps.SteamApps;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;

/**
 * This callback is received in response to calling
 * {@link SteamApps#getAppOwnershipTicket(int)}.
 */
public final class AppOwnershipTicketCallback extends CallbackMsg {
    /**
     * Gets the result of requesting the ticket.
     */
    private final EResult result;
    /**
     * Gets the AppID this ticket is for.
     */
    private final int appID;
    /**
     * Gets the ticket data.
     */
    private final byte[] ticket;

    public AppOwnershipTicketCallback(CMsgClientGetAppOwnershipTicketResponse msg) {
        result = EResult.f(msg.eresult);
        appID = msg.appId;
        ticket = msg.ticket;
    }

    /**
     * Gets the result of requesting the ticket.
     */
    public EResult getResult() {
        return this.result;
    }

    /**
     * Gets the AppID this ticket is for.
     */
    public int getAppID() {
        return this.appID;
    }

    /**
     * Gets the ticket data.
     */
    public byte[] getTicket() {
        return this.ticket;
    }
}
