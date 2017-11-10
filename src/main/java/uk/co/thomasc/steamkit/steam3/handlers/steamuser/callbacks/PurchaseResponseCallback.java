package uk.co.thomasc.steamkit.steam3.handlers.steamuser.callbacks;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientPurchaseResponse;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EResult;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;
import uk.co.thomasc.steamkit.types.MessageObject;
import uk.co.thomasc.steamkit.util.stream.BinaryReader;

/**
 * This callback is recieved when wallet info is recieved from the network.
 */
public final class PurchaseResponseCallback extends CallbackMsg {
    /**
     * Gets the result of this purchase
     */
    private final EResult result;
    /**
     * Gets the purchase result details.
     * 0 -- success?
     * 14 -- invalid key?
     */
    private final int purchaseResultDetails;
    /**
     * Gets the balance of the wallet, in cents.
     */
    private final MessageObject purchaseReceiptInfo;

    public PurchaseResponseCallback(CMsgClientPurchaseResponse wallet) {
        result = EResult.f(wallet.eresult);
        purchaseResultDetails = wallet.purchaseResultDetails;
        purchaseReceiptInfo = new MessageObject();
        purchaseReceiptInfo.readFromBinary(new BinaryReader(wallet.purchaseReceiptInfo));
    }

    /**
     * Gets the result of this purchase
     */
    public EResult getResult() {
        return this.result;
    }

    /**
     * Gets the purchase result details.
     * 0 -- success?
     * 14 -- invalid key?
     */
    public int getPurchaseResultDetails() {
        return this.purchaseResultDetails;
    }

    /**
     * Gets the balance of the wallet, in cents.
     */
    public MessageObject getPurchaseReceiptInfo() {
        return this.purchaseReceiptInfo;
    }
}
