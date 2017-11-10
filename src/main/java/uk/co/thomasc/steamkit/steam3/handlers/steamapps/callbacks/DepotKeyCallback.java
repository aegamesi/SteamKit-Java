package uk.co.thomasc.steamkit.steam3.handlers.steamapps.callbacks;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientGetDepotDecryptionKeyResponse;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EResult;
import uk.co.thomasc.steamkit.steam3.handlers.steamapps.SteamApps;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;

/**
 * This callback is recieved in response to calling
 * {@link SteamApps#getDepotDecryptionKey()}.
 */
public final class DepotKeyCallback extends CallbackMsg {
    /**
     * Gets the result of requesting this encryption key.
     */
    private final EResult result;
    /**
     * Gets the DepotID this encryption key is for.
     */
    private final int depotID;
    /**
     * Gets the encryption key for this depot.
     */
    private final byte[] depotKey;

    public DepotKeyCallback(CMsgClientGetDepotDecryptionKeyResponse msg) {
        result = EResult.f(msg.eresult);
        depotID = msg.depotId;
        depotKey = msg.depotEncryptionKey;
    }

    /**
     * Gets the result of requesting this encryption key.
     */
    public EResult getResult() {
        return this.result;
    }

    /**
     * Gets the DepotID this encryption key is for.
     */
    public int getDepotID() {
        return this.depotID;
    }

    /**
     * Gets the encryption key for this depot.
     */
    public byte[] getDepotKey() {
        return this.depotKey;
    }
}
