package uk.co.thomasc.steamkit.steam3.handlers.steamuser.callbacks;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientRequestWebAPIAuthenticateUserNonceResponse;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EResult;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;

public class WebAPIUserNonceCallback extends CallbackMsg {
    /**
     * The result of the request.
     */
    private final EResult result;
    /**
     * The authentication nonce.
     */
    private final String nonce;

    public WebAPIUserNonceCallback(CMsgClientRequestWebAPIAuthenticateUserNonceResponse msg) {
        result = EResult.f(msg.eresult);
        nonce = msg.webapiAuthenticateUserNonce;
    }

    /**
     * Get the result of this request.
     */
    public EResult getResult() {
        return this.result;
    }

    /**
     * Get the authentication nonce.
     */
    public String getNonce() {
        return this.nonce;
    }
}