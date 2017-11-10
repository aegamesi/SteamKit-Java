package uk.co.thomasc.steamkit.steam3.handlers.steamtrading.callbacks;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgTrading_InitiateTradeResponse;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EEconTradeResponse;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;
import uk.co.thomasc.steamkit.types.steamid.SteamID;

/**
 * This callback is fired when this client receives the response from a trade
 * proposal.
 */
public final class TradeResultCallback extends CallbackMsg {
    /**
     * Gets the Trade ID that this result is for.
     */
    private final int tradeID;
    /**
     * Gets the response of the trade proposal.
     */
    private final EEconTradeResponse response;
    /**
     * Gets the SteamID of the client that responded to the proposal.
     */
    private final SteamID otherClient;

    public TradeResultCallback(CMsgTrading_InitiateTradeResponse msg) {
        tradeID = msg.tradeRequestId;
        response = EEconTradeResponse.f(msg.response);
        otherClient = new SteamID(msg.otherSteamid);
    }

    /**
     * Gets the Trade ID that this result is for.
     */
    public int getTradeID() {
        return this.tradeID;
    }

    /**
     * Gets the response of the trade proposal.
     */
    public EEconTradeResponse getResponse() {
        return this.response;
    }

    /**
     * Gets the SteamID of the client that responded to the proposal.
     */
    public SteamID getOtherClient() {
        return this.otherClient;
    }
}
