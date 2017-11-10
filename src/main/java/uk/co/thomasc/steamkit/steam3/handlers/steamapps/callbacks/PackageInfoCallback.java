package uk.co.thomasc.steamkit.steam3.handlers.steamapps.callbacks;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientPackageInfoResponse;
import uk.co.thomasc.steamkit.steam3.handlers.steamapps.SteamApps;
import uk.co.thomasc.steamkit.steam3.handlers.steamapps.types.Package;
import uk.co.thomasc.steamkit.steam3.handlers.steamapps.types.PackageStatus;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * This callback is received in response to calling
 * {@link SteamApps#getPackageInfo(int)}.
 */
public final class PackageInfoCallback extends CallbackMsg {
    /**
     * Gets the list of packages this response contains.
     */
    private final List<Package> packages = new ArrayList<Package>();
    /**
     * Gets a count of packages pending in this response.
     */
    public int packagesPending;

    public PackageInfoCallback(CMsgClientPackageInfoResponse msg) {
        for (final CMsgClientPackageInfoResponse.Package pkg : msg.packages) {
            packages.add(new Package(pkg, PackageStatus.OK));
        }
        for (final Integer pkg : msg.packagesUnknown) {
            packages.add(new Package(pkg, PackageStatus.Unknown));
        }
        packagesPending = msg.packagesPending;
    }

    /**
     * Gets the list of packages this response contains.
     */
    public List<Package> getPackages() {
        return this.packages;
    }
}
