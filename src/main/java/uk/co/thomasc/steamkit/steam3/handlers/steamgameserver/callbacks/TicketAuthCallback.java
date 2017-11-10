package uk.co.thomasc.steamkit.steam3.handlers.steamgameserver.callbacks;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientTicketAuthComplete;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EAuthSessionResponse;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;
import uk.co.thomasc.steamkit.types.gameid.GameID;
import uk.co.thomasc.steamkit.types.steamid.SteamID;

/**
 * This callback is fired when ticket authentication has completed.
 */
public final class TicketAuthCallback extends CallbackMsg {
    /**
     * Gets the SteamID the ticket auth completed for.
     */
    private final SteamID steamId;
    /**
     * Gets the GameID the ticket was for.
     */
    private final GameID gameId;
    /**
     * Gets the authentication state.
     */
    private final int state;
    /**
     * Gets the auth session response.
     */
    private final EAuthSessionResponse authSessionResponse;
    /**
     * Gets the ticket CRC.
     */
    private final int ticketCRC;
    /**
     * Gets the ticket sequence.
     */
    private final int ticketSequence;

    public TicketAuthCallback(CMsgClientTicketAuthComplete tickAuth) {
        steamId = new SteamID(tickAuth.steamId);
        gameId = new GameID(tickAuth.gameId);
        state = tickAuth.estate;
        authSessionResponse = EAuthSessionResponse.f(tickAuth.eauthSessionResponse);
        ticketCRC = tickAuth.ticketCrc;
        ticketSequence = tickAuth.ticketSequence;
    }

    /**
     * Gets the SteamID the ticket auth completed for.
     */
    public SteamID getSteamId() {
        return this.steamId;
    }

    /**
     * Gets the GameID the ticket was for.
     */
    public GameID getGameId() {
        return this.gameId;
    }

    /**
     * Gets the authentication state.
     */
    public int getState() {
        return this.state;
    }

    /**
     * Gets the auth session response.
     */
    public EAuthSessionResponse getAuthSessionResponse() {
        return this.authSessionResponse;
    }

    /**
     * Gets the ticket CRC.
     */
    public int getTicketCRC() {
        return this.ticketCRC;
    }

    /**
     * Gets the ticket sequence.
     */
    public int getTicketSequence() {
        return this.ticketSequence;
    }
}
