// Generated by delombok at Wed Jun 15 22:04:52 CDT 2016
package uk.co.thomasc.steamkit.steam3.handlers.steamapps.callbacks;

import java.util.ArrayList;
import java.util.List;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientPackageInfoResponse;
import uk.co.thomasc.steamkit.steam3.handlers.steamapps.SteamApps;
import uk.co.thomasc.steamkit.steam3.handlers.steamapps.types.Package;
import uk.co.thomasc.steamkit.steam3.handlers.steamapps.types.PackageStatus;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;

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
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public List<Package> getPackages() {
		return this.packages;
	}
}
