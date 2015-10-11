package uk.co.thomasc.steamkit.steam3.handlers.steamuser;

import uk.co.thomasc.steamkit.base.ClientMsgProtobuf;
import uk.co.thomasc.steamkit.base.IPacketMsg;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientAccountInfo;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientLogOff;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientLoggedOff;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientLogon;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientLogonResponse;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientNewLoginKey;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientNewLoginKeyAccepted;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientSessionToken;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientWalletInfoUpdate;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientPurchaseResponse;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientRegisterKey;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientUpdateMachineAuth;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientUpdateMachineAuthResponse;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EAccountType;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EMsg;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EResult;
import uk.co.thomasc.steamkit.base.generated.steamlanguageinternal.msg.MsgClientLogon;
import uk.co.thomasc.steamkit.steam3.handlers.ClientMsgHandler;
import uk.co.thomasc.steamkit.steam3.handlers.steamuser.callbacks.AccountInfoCallback;
import uk.co.thomasc.steamkit.steam3.handlers.steamuser.callbacks.LoggedOffCallback;
import uk.co.thomasc.steamkit.steam3.handlers.steamuser.callbacks.LoggedOnCallback;
import uk.co.thomasc.steamkit.steam3.handlers.steamuser.callbacks.LoginKeyCallback;
import uk.co.thomasc.steamkit.steam3.handlers.steamuser.callbacks.PurchaseResponseCallback;
import uk.co.thomasc.steamkit.steam3.handlers.steamuser.callbacks.SessionTokenCallback;
import uk.co.thomasc.steamkit.steam3.handlers.steamuser.callbacks.UpdateMachineAuthCallback;
import uk.co.thomasc.steamkit.steam3.handlers.steamuser.callbacks.WalletInfoCallback;
import uk.co.thomasc.steamkit.steam3.handlers.steamuser.types.LogOnDetails;
import uk.co.thomasc.steamkit.steam3.handlers.steamuser.types.MachineAuthDetails;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.JobCallback;
import uk.co.thomasc.steamkit.types.JobID;
import uk.co.thomasc.steamkit.types.steamid.SteamID;
import uk.co.thomasc.steamkit.util.util.NetHelpers;
import uk.co.thomasc.steamkit.util.util.Utils;

/**
 * This handler handles all user log on/log off related actions and callbacks.
 */
public final class SteamUser extends ClientMsgHandler {

	/**
	 * Gets the SteamID of this client. This value is assigned after a logon
	 * attempt has succeeded.
	 * 
	 * @return The SteamID.
	 */
	public SteamID getSteamId() {
		return getClient().getSteamId();
	}

	/**
	 * Logs the client into the Steam3 network. The client should already have
	 * been connected at this point. Results are returned in a
	 * {@link LoggedOnCallback}.
	 * 
	 * @param details
	 *            The details to use for logging on.
	 * @exception IllegalArgumentException
	 *                Username and password are not set or are not provided.
	 */
	public void logOn(LogOnDetails details, String machineID) {
		if (details == null) {
			throw new IllegalArgumentException("details");
		}
		/*if (details.username.length() == 0 || details.password.length() == 0) {
			throw new IllegalArgumentException("LogOn requires a username and password to be set in 'details'.");
		}*/

		final ClientMsgProtobuf<CMsgClientLogon> logon = new ClientMsgProtobuf<CMsgClientLogon>(CMsgClientLogon.class, EMsg.ClientLogon);

		final SteamID steamId = new SteamID(0, details.accountInstance, getClient().getConnectedUniverse(), EAccountType.Individual);

		final int localIp = (int) NetHelpers.getIPAddress(getClient().getLocalIP());

		logon.getProtoHeader().clientSessionid = 0;
		logon.getProtoHeader().steamid = steamId.convertToLong();

		logon.getBody().obfustucatedPrivateIp = localIp ^ MsgClientLogon.ObfuscationMask;

		if(details.username.length() > 0)
			logon.getBody().accountName = details.username;
		if(details.password.length() > 0)
			logon.getBody().password = details.password;
		if(details.loginkey.length() > 0)
			logon.getBody().loginKey = details.loginkey;


		logon.getBody().protocolVersion = MsgClientLogon.CurrentProtocol;
		logon.getBody().clientOsType = Utils.getOSType().v();
		logon.getBody().clientLanguage = "english";

		logon.getBody().steam2TicketRequest = details.requestSteam2Ticket;
		logon.getBody().shouldRememberPassword = details.shouldRememberPassword;

		logon.getBody().machineName = "ICE";

		// we're now using the latest steamclient package version, this is required to get a proper sentry file for steam guard
		logon.getBody().clientPackageVersion = 1771; // TODO: determine if this is still required

		// this is not a proper machine id that Steam accepts
		// but it's good enough for identifying a machine
		logon.getBody().machineId = machineID.getBytes();//ByteString.copyFrom(Utils.generateMachineID());

		// steam guard
		if (details.authCode.length() > 0) {
			logon.getBody().authCode = details.authCode;
		}

		if(details.twoFactorCode.length() > 0) {
			logon.getBody().twoFactorCode = details.twoFactorCode;
		}

		if (details.sentryFileHash != null) {
			logon.getBody().shaSentryfile = details.sentryFileHash;
		} else {
			logon.getBody().shaSentryfile = new byte[0];
		}

		logon.getBody().eresultSentryfile = (details.sentryFileHash != null ? EResult.OK : EResult.FileNotFound).v();

		getClient().send(logon);
	}

	/**
	 * Logs the client into the Steam3 network as an anonymous user. The client
	 * should already have been connected at this point. Results are returned in
	 * a {@link LoggedOnCallback}.
	 */
	public void logOnAnonymous(String machineID) {
		final ClientMsgProtobuf<CMsgClientLogon> logon = new ClientMsgProtobuf<CMsgClientLogon>(CMsgClientLogon.class, EMsg.ClientLogon);

		final SteamID auId = new SteamID(0, 0, getClient().getConnectedUniverse(), EAccountType.AnonUser);

		logon.getProtoHeader().clientSessionid = 0;
		logon.getProtoHeader().steamid = auId.convertToLong();

		logon.getBody().protocolVersion = MsgClientLogon.CurrentProtocol;
		logon.getBody().clientOsType = Utils.getOSType().v();

		// this is not a proper machine id that Steam accepts
		// but it's good enough for identifying a machine
		logon.getBody().machineId = machineID.getBytes();

		getClient().send(logon);
	}

	/**
	 * Logs the user off of the Steam3 network. This method does not disconnect
	 * the client. Results are returned in a {@link LoggedOffCallback}.
	 */
	public void logOff() {
		final ClientMsgProtobuf<?> logOff = new ClientMsgProtobuf<CMsgClientLogOff>(CMsgClientLogOff.class, EMsg.ClientLogOff);
		getClient().send(logOff);
	}

	/**
	 * Set the currently playing game of the user
	 * @param appid		The appid of the game to "play" -- 0 for no game.
	 */
	public void setPlayingGame(int appid) {
		ClientMsgProtobuf<SteammessagesClientserver.CMsgClientGamesPlayed> playGame;
		playGame = new ClientMsgProtobuf<SteammessagesClientserver.CMsgClientGamesPlayed>(SteammessagesClientserver.CMsgClientGamesPlayed.class, EMsg.ClientGamesPlayed);
		SteammessagesClientserver.CMsgClientGamesPlayed.GamePlayed gamePlayed = new SteammessagesClientserver.CMsgClientGamesPlayed.GamePlayed();
		gamePlayed.gameId = appid;
		playGame.getBody().gamesPlayed = new SteammessagesClientserver.CMsgClientGamesPlayed.GamePlayed[]{gamePlayed};
		getClient().send(playGame);
	}

	/**
	 * Requests to register a product key to this account
	 * @param key		The product key
	 */
	public void registerProductKey(String key) {
		ClientMsgProtobuf<CMsgClientRegisterKey> registerKey = new ClientMsgProtobuf<CMsgClientRegisterKey>(CMsgClientRegisterKey.class, EMsg.ClientRegisterKey);
		registerKey.getBody().key = key;
		getClient().send(registerKey);
	}

	/**
	 * Sends a machine auth response. This should normally be used in response
	 * to a {@link UpdateMachineAuthCallback}.
	 * 
	 * @param details
	 *            The details pertaining to the response.
	 */
	public void sendMachineAuthResponse(MachineAuthDetails details) {
		final ClientMsgProtobuf<CMsgClientUpdateMachineAuthResponse> response = new ClientMsgProtobuf<CMsgClientUpdateMachineAuthResponse>(CMsgClientUpdateMachineAuthResponse.class, EMsg.ClientUpdateMachineAuthResponse);

		// so we respond to the correct message
		response.getProtoHeader().jobidTarget = details.jobId;

		response.getBody().cubwrote = details.bytesWritten;
		response.getBody().eresult = details.result.v();

		response.getBody().filename = details.fileName;
		response.getBody().filesize = details.fileSize;

		response.getBody().getlasterror = details.lastError;
		response.getBody().offset = details.offset;

		response.getBody().shaFile = details.sentryFileHash;

		response.getBody().otpIdentifier = details.oneTimePassword.identifier;
		response.getBody().otpType = details.oneTimePassword.type;
		response.getBody().otpValue = details.oneTimePassword.value;

		getClient().send(response);
	}

	/**
	 * Handles a client message. This should not be called directly.
	 */
	@Override
	public void handleMsg(IPacketMsg packetMsg) {
		switch (packetMsg.getMsgType()) {
		case ClientLogOnResponse:
			handleLogOnResponse(packetMsg);
			break;
		case ClientNewLoginKey:
			handleLoginKey(packetMsg);
			break;
		case ClientSessionToken:
			handleSessionToken(packetMsg);
			break;
		case ClientLoggedOff:
			handleLoggedOff(packetMsg);
			break;
		case ClientUpdateMachineAuth:
			handleUpdateMachineAuth(packetMsg);
			break;
		case ClientAccountInfo:
			handleAccountInfo(packetMsg);
			break;
		case ClientWalletInfoUpdate:
			handleWalletInfo(packetMsg);
			break;
		case ClientPurchaseResponse:
			handlePurchaseResponse(packetMsg);
			break;
		}
	}

	void handleLoggedOff(IPacketMsg packetMsg) {
		final ClientMsgProtobuf<CMsgClientLoggedOff> loggedOff = new ClientMsgProtobuf<CMsgClientLoggedOff>(CMsgClientLoggedOff.class, packetMsg);

		getClient().postCallback(new LoggedOffCallback(loggedOff.getBody()));
	}

	void handleUpdateMachineAuth(IPacketMsg packetMsg) {
		final ClientMsgProtobuf<CMsgClientUpdateMachineAuth> machineAuth = new ClientMsgProtobuf<CMsgClientUpdateMachineAuth>(CMsgClientUpdateMachineAuth.class, packetMsg);

		final UpdateMachineAuthCallback innerCallback = new UpdateMachineAuthCallback(machineAuth.getBody());
		final JobCallback<?> callback = new JobCallback<UpdateMachineAuthCallback>(new JobID(packetMsg.getSourceJobID()), innerCallback);
		getClient().postCallback(callback);
	}

	void handleSessionToken(IPacketMsg packetMsg) {
		final ClientMsgProtobuf<CMsgClientSessionToken> sessToken = new ClientMsgProtobuf<CMsgClientSessionToken>(CMsgClientSessionToken.class, packetMsg);

		final SessionTokenCallback callback = new SessionTokenCallback(sessToken.getBody());
		getClient().postCallback(callback);
	}

	void handleLoginKey(IPacketMsg packetMsg) {
		final ClientMsgProtobuf<CMsgClientNewLoginKey> loginKey = new ClientMsgProtobuf<CMsgClientNewLoginKey>(CMsgClientNewLoginKey.class, packetMsg);

		final ClientMsgProtobuf<CMsgClientNewLoginKeyAccepted> resp = new ClientMsgProtobuf<CMsgClientNewLoginKeyAccepted>(CMsgClientNewLoginKeyAccepted.class, EMsg.ClientNewLoginKeyAccepted);
		resp.getBody().uniqueId = loginKey.getBody().uniqueId;

		getClient().send(resp);

		final LoginKeyCallback callback = new LoginKeyCallback(loginKey.getBody());
		getClient().postCallback(callback);
	}

	void handleLogOnResponse(IPacketMsg packetMsg) {
		if (packetMsg.isProto()) {
			final ClientMsgProtobuf<CMsgClientLogonResponse> logonResp = new ClientMsgProtobuf<CMsgClientLogonResponse>(CMsgClientLogonResponse.class, packetMsg);

			final LoggedOnCallback callback = new LoggedOnCallback(logonResp.getBody());
			getClient().postCallback(callback);
		}
	}

	void handleAccountInfo(IPacketMsg packetMsg) {
		final ClientMsgProtobuf<CMsgClientAccountInfo> accInfo = new ClientMsgProtobuf<CMsgClientAccountInfo>(CMsgClientAccountInfo.class, packetMsg);

		final AccountInfoCallback callback = new AccountInfoCallback(accInfo.getBody());
		getClient().postCallback(callback);
	}

	void handleWalletInfo(IPacketMsg packetMsg) {
		final ClientMsgProtobuf<CMsgClientWalletInfoUpdate> walletInfo = new ClientMsgProtobuf<CMsgClientWalletInfoUpdate>(CMsgClientWalletInfoUpdate.class, packetMsg);

		final WalletInfoCallback callback = new WalletInfoCallback(walletInfo.getBody());
		getClient().postCallback(callback);
	}

	void handlePurchaseResponse(IPacketMsg packetMsg) {
		final ClientMsgProtobuf<CMsgClientPurchaseResponse> purchaseResp = new ClientMsgProtobuf<CMsgClientPurchaseResponse>(CMsgClientPurchaseResponse.class, packetMsg);

		final PurchaseResponseCallback callback = new PurchaseResponseCallback(purchaseResp.getBody());
		getClient().postCallback(callback);
	}
}
