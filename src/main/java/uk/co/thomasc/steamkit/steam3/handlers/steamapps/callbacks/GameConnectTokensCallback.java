package uk.co.thomasc.steamkit.steam3.handlers.steamapps.callbacks;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientGameConnectTokens;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * This callback is fired when the client receives a list of game connect
 * tokens.
 */
public final class GameConnectTokensCallback extends CallbackMsg {
    /**
     * Gets a count of tokens to keep.
     */
    private final int tokensToKeep;
    /**
     * Gets the list of tokens.
     */
    private final List<byte[]> tokens = new ArrayList<byte[]>();

    public GameConnectTokensCallback(CMsgClientGameConnectTokens msg) {
        tokensToKeep = msg.maxTokensToKeep;
        for (final byte[] s : msg.tokens) {
            tokens.add(s);
        }
    }

    /**
     * Gets a count of tokens to keep.
     */
    public int getTokensToKeep() {
        return this.tokensToKeep;
    }

    /**
     * Gets the list of tokens.
     */
    public List<byte[]> getTokens() {
        return this.tokens;
    }
}
