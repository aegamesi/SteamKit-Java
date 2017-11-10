package uk.co.thomasc.steamkit.steam3.handlers.steamapps.callbacks;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientLicenseList;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EResult;
import uk.co.thomasc.steamkit.steam3.handlers.steamapps.types.License;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * This callback is fired during logon, informing the client of it's available
 * licenses.
 */
public final class LicenseListCallback extends CallbackMsg {
    /**
     * Gets the result of the message.
     */
    private final EResult result;
    /**
     * Gets the license list.
     */
    private final List<License> licenseList = new ArrayList<License>();

    public LicenseListCallback(CMsgClientLicenseList msg) {
        result = EResult.f(msg.eresult);
        for (final CMsgClientLicenseList.License l : msg.licenses) {
            licenseList.add(new License(l));
        }
    }

    /**
     * Gets the result of the message.
     */
    public EResult getResult() {
        return this.result;
    }

    /**
     * Gets the license list.
     */
    public List<License> getLicenseList() {
        return this.licenseList;
    }
}
