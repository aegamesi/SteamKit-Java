package uk.co.thomasc.steamkit.steam3.handlers.steamapps.callbacks;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientAppInfoResponse;
import uk.co.thomasc.steamkit.steam3.handlers.steamapps.SteamApps;
import uk.co.thomasc.steamkit.steam3.handlers.steamapps.types.App;
import uk.co.thomasc.steamkit.steam3.handlers.steamapps.types.AppInfoStatus;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * This callback is received in response to calling
 * {@link SteamApps#getAppInfo(int)}.
 */
public final class AppInfoCallback extends CallbackMsg {
    /**
     * Gets the list of apps this response contains.
     */
    private final List<App> apps = new ArrayList<App>();
    /**
     * Gets the number of apps pending in this response.
     */
    private final int appsPending;

    public AppInfoCallback(CMsgClientAppInfoResponse msg) {
        for (final CMsgClientAppInfoResponse.App app : msg.apps) {
            apps.add(new App(app, AppInfoStatus.OK));
        }
        for (final Integer app : msg.appsUnknown) {
            apps.add(new App(app, AppInfoStatus.Unknown));
        }
        appsPending = msg.appsPending;
    }

    /**
     * Gets the list of apps this response contains.
     */
    public List<App> getApps() {
        return this.apps;
    }

    /**
     * Gets the number of apps pending in this response.
     */
    public int getAppsPending() {
        return this.appsPending;
    }
}
