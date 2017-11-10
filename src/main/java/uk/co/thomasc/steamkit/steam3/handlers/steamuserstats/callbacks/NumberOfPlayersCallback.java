package uk.co.thomasc.steamkit.steam3.handlers.steamuserstats.callbacks;

import uk.co.thomasc.steamkit.base.generated.steamlanguage.EResult;
import uk.co.thomasc.steamkit.base.generated.steamlanguageinternal.msg.MsgClientGetNumberOfCurrentPlayersResponse;
import uk.co.thomasc.steamkit.steam3.handlers.steamuserstats.SteamUserStats;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;
import uk.co.thomasc.steamkit.types.gameid.GameID;

/**
 * This callback is fired in response to
 * {@link SteamUserStats#getNumberOfCurrentPlayers(GameID)}.
 */
public class NumberOfPlayersCallback extends CallbackMsg {
    /**
     * Gets the result of the request.
     */
    private final EResult result;
    /**
     * Gets the current number of players according to Steam.
     */
    private final int numPlayers;

    public NumberOfPlayersCallback(MsgClientGetNumberOfCurrentPlayersResponse resp) {
        result = resp.result;
        numPlayers = resp.numPlayers;
    }

    /**
     * Gets the result of the request.
     */
    public EResult getResult() {
        return this.result;
    }

    /**
     * Gets the current number of players according to Steam.
     */
    public int getNumPlayers() {
        return this.numPlayers;
    }
}
