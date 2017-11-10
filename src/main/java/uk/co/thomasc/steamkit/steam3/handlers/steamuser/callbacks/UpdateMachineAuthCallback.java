package uk.co.thomasc.steamkit.steam3.handlers.steamuser.callbacks;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientUpdateMachineAuth;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;

/**
 * This callback is recieved when the backend wants the client to update it's
 * local machine authentication data.
 */
public final class UpdateMachineAuthCallback extends CallbackMsg {
    /**
     * Gets the sentry file data that should be written.
     */
    private final byte[] data;
    /**
     * Gets the number of bytes to write.
     */
    private final int bytesToWrite;
    /**
     * Gets the offset to write to.
     */
    private final int offset;
    /**
     * Gets the name of the sentry file to write.
     */
    private final String fileName;
    /**
     * Gets the one-time-password details.
     */
    private final OTPDetailsCallback oneTimePassword;

    public UpdateMachineAuthCallback(CMsgClientUpdateMachineAuth msg) {
        data = msg.bytes;
        bytesToWrite = msg.cubtowrite;
        offset = msg.offset;
        fileName = msg.filename;
        oneTimePassword = new OTPDetailsCallbackInternal();
        oneTimePassword.type = msg.otpType;
        oneTimePassword.identifier = msg.otpIdentifier;
        ((OTPDetailsCallbackInternal) oneTimePassword).setSharedSecret(msg.otpSharedsecret);
        ((OTPDetailsCallbackInternal) oneTimePassword).setTimeDrift(msg.otpTimedrift);
    }

    /**
     * Gets the sentry file data that should be written.
     */
    public byte[] getData() {
        return this.data;
    }

    /**
     * Gets the number of bytes to write.
     */
    public int getBytesToWrite() {
        return this.bytesToWrite;
    }

    /**
     * Gets the offset to write to.
     */
    public int getOffset() {
        return this.offset;
    }

    /**
     * Gets the name of the sentry file to write.
     */
    public String getFileName() {
        return this.fileName;
    }

    /**
     * Gets the one-time-password details.
     */
    public OTPDetailsCallback getOneTimePassword() {
        return this.oneTimePassword;
    }
}
