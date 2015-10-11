package uk.co.thomasc.steamkit.steam3.handlers.steamuser.callbacks;

import java.net.InetAddress;
import java.util.Date;

import lombok.Getter;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientLogonResponse;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EAccountFlags;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EResult;
import uk.co.thomasc.steamkit.steam3.handlers.steamuser.SteamUser;
import uk.co.thomasc.steamkit.steam3.handlers.steamuser.types.LogOnDetails;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;
import uk.co.thomasc.steamkit.types.steam2ticket.Steam2Ticket;
import uk.co.thomasc.steamkit.types.steamid.SteamID;
import uk.co.thomasc.steamkit.util.util.NetHelpers;

/**
 * This callback is returned in response to an attempt to log on to the Steam3
 * network through {@link SteamUser}.
 */
public final class LoggedOnCallback extends CallbackMsg {
	/**
	 * Gets the result of the logon.
	 */
	@Getter
	private final EResult result;

	/**
	 * Gets the extended result of the logon.
	 */
	@Getter
	private final EResult extendedResult;

	/**
	 * Gets the out of game secs per heartbeat value. This is used internally by
	 * SteamKit to initialize heartbeating.
	 */
	@Getter
	private final int outOfGameSecsPerHeartbeat;

	/**
	 * Gets the in game secs per heartbeat value. This is used internally by
	 * SteamKit to initialize heartbeating.
	 */
	@Getter
	private final int inGameSecsPerHeartbeat;

	/**
	 * Gets or sets the public IP of the client
	 */
	@Getter
	private final InetAddress publicIP;

	/**
	 * Gets the Steam3 server time.
	 */
	@Getter
	private final Date serverTime;

	/**
	 * Gets the account flags assigned by the server.
	 */
	@Getter
	private final EAccountFlags accountFlags;

	/**
	 * Gets the client steam ID.
	 */
	@Getter
	private final SteamID clientSteamID;

	/**
	 * Gets the email domain.
	 */
	@Getter
	private final String emailDomain;
	
	/**
	 * Gets the WebAPI User Nonce
	 */
	@Getter
	private final String webAPIUserNonce;

	/**
	 * Gets the Steam2 CellID.
	 */
	@Getter
	private final int cellId;

	/**
	 * Gets the Steam2 ticket. This is used for authenticated content downloads
	 * in Steam2. This field will only be set when
	 * {@link LogOnDetails#requestSteam2Ticket} has been set to true.
	 */
	@Getter
	private Steam2Ticket steam2Ticket = null;

	public LoggedOnCallback(CMsgClientLogonResponse resp) {
		result = EResult.f(resp.eresult);
		extendedResult = EResult.f(resp.eresultExtended);

		outOfGameSecsPerHeartbeat = resp.outOfGameHeartbeatSeconds;
		inGameSecsPerHeartbeat = resp.inGameHeartbeatSeconds;

		publicIP = NetHelpers.getIPAddress(resp.publicIp);

		serverTime = new Date(resp.rtime32ServerTime);

		accountFlags = EAccountFlags.f(resp.accountFlags);

		clientSteamID = new SteamID(resp.clientSuppliedSteamid);

		emailDomain = resp.emailDomain;

		cellId = resp.cellId;
		
		webAPIUserNonce = resp.webapiAuthenticateUserNonce;

		if(resp.steam2Ticket.length > 0)
			steam2Ticket = new Steam2Ticket(resp.steam2Ticket);
	}
}
