package uk.co.thomasc.steamkit.steam3.steamclient.callbacks;

import lombok.Getter;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;
import uk.co.thomasc.steamkit.util.util.NetHelpers;

/**
 * This callback is received when the client has received the CM list from Steam.
 */
public final class CMListCallback extends CallbackMsg {
	/**
	 * Gets the CM server list.
	 */
	@Getter
	private final String[] serverList;

	public CMListCallback(SteammessagesClientserver.CMsgClientCMList result) {
		serverList = new String[result.cmAddresses.length];

		for(int i = 0; i < serverList.length; i++) {
			serverList[i] = (NetHelpers.getIPAddress(result.cmAddresses[i]).getHostAddress()) + ":" + result.cmPorts[i];
		}
	}
}
