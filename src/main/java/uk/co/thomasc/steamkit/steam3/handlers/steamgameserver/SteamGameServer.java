package uk.co.thomasc.steamkit.steam3.handlers.steamgameserver;

import java.security.InvalidParameterException;

import uk.co.thomasc.steamkit.base.ClientMsgProtobuf;
import uk.co.thomasc.steamkit.base.IPacketMsg;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientLogOff;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientLogon;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientTicketAuthComplete;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgGSStatusReply;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EAccountType;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EMsg;
import uk.co.thomasc.steamkit.base.generated.steamlanguageinternal.msg.MsgClientLogon;
import uk.co.thomasc.steamkit.steam3.handlers.ClientMsgHandler;
import uk.co.thomasc.steamkit.steam3.handlers.steamgameserver.callbacks.StatusReplyCallback;
import uk.co.thomasc.steamkit.steam3.handlers.steamgameserver.callbacks.TicketAuthCallback;
import uk.co.thomasc.steamkit.steam3.handlers.steamgameserver.types.LogOnDetails;
import uk.co.thomasc.steamkit.steam3.handlers.steamuser.callbacks.LoggedOffCallback;
import uk.co.thomasc.steamkit.steam3.handlers.steamuser.callbacks.LoggedOnCallback;
import uk.co.thomasc.steamkit.types.steamid.SteamID;
import uk.co.thomasc.steamkit.util.util.NetHelpers;
import uk.co.thomasc.steamkit.util.util.Utils;

/**
 * This handler is used for interacting with the Steam network as a game server.
 */
public final class SteamGameServer extends ClientMsgHandler {
    /**
     * Logs onto the Steam network as a persistent game server. The client
     * should already have been connected at this point. Results are return in a
     * {@link LoggedOnCallback}.
     *
     * @param details
     *            The details to use for logging on.
     */
    public void logOn(LogOnDetails details, String machineID) {
        if (details == null) {
            throw new InvalidParameterException("details");
        }

        if (details.username == null || details.username.length() == 0 || details.password == null || details.password.length() == 0) {
            throw new InvalidParameterException("LogOn requires a username and password to be set in 'details'.");
        }

        final ClientMsgProtobuf<CMsgClientLogon> logon = new ClientMsgProtobuf<CMsgClientLogon>(CMsgClientLogon.class, EMsg.ClientLogon);

        final SteamID gsId = new SteamID(0, 0, getClient().getConnectedUniverse(), EAccountType.GameServer);

        logon.getProtoHeader().clientSessionid = 0;
        logon.getProtoHeader().steamid = gsId.convertToLong();

        final int localIp = (int) NetHelpers.getIPAddress(getClient().getLocalIP());
        logon.getBody().obfustucatedPrivateIp = localIp ^ MsgClientLogon.ObfuscationMask;

        logon.getBody().protocolVersion = MsgClientLogon.CurrentProtocol;

        logon.getBody().clientOsType = Utils.getOSType().v();
        logon.getBody().gameServerAppId = details.appId;
        logon.getBody().machineId = machineID.getBytes();

        logon.getBody().accountName = details.username;
        logon.getBody().password = details.password;

        getClient().send(logon);
    }

    /**
     * Logs the client into the Steam3 network as an anonymous game server. The
     * client should already have been connected at this point. Results are
     * returned in a {@link LoggedOnCallback}.
     *
     * @param appId
     *            The AppID served by this game server, or 0 for the default.
     */
    public void logOnAnonymous(int appId, String machineID) {
        final ClientMsgProtobuf<CMsgClientLogon> logon = new ClientMsgProtobuf<CMsgClientLogon>(CMsgClientLogon.class, EMsg.ClientLogon);

        final SteamID gsId = new SteamID(0, 0, getClient().getConnectedUniverse(), EAccountType.AnonGameServer);

        logon.getProtoHeader().clientSessionid = 0;
        logon.getProtoHeader().steamid = gsId.convertToLong();

        final int localIp = (int) NetHelpers.getIPAddress(getClient().getLocalIP());
        logon.getBody().obfustucatedPrivateIp = localIp ^ MsgClientLogon.ObfuscationMask;

        logon.getBody().protocolVersion = MsgClientLogon.CurrentProtocol;

        logon.getBody().clientOsType = Utils.getOSType().v();
        logon.getBody().gameServerAppId = appId;
        logon.getBody().machineId = machineID.getBytes();

        getClient().send(logon);
    }

    public void logOnAnonymous(String machineID) {
        logOnAnonymous(0, machineID);
    }

    /**
     * Logs the game server off of the Steam3 network. This method does not
     * disconnect the client. Results are returned in a
     * {@link LoggedOffCallback}.
     */
    public void logOff() {
        final ClientMsgProtobuf<CMsgClientLogOff> logOff = new ClientMsgProtobuf<CMsgClientLogOff>(CMsgClientLogOff.class, EMsg.ClientLogOff);
        getClient().send(logOff);
    }

    /**
     * Handles a client message. This should not be called directly.
     */
    @Override
    public void handleMsg(IPacketMsg packetMsg) {
        switch (packetMsg.getMsgType()) {
        case GSStatusReply:
            handleStatusReply(packetMsg);
            break;
        case ClientTicketAuthComplete:
            handleAuthComplete(packetMsg);
            break;
        }
    }

    void handleStatusReply(IPacketMsg packetMsg) {
        final ClientMsgProtobuf<CMsgGSStatusReply> statusReply = new ClientMsgProtobuf<CMsgGSStatusReply>(CMsgGSStatusReply.class, packetMsg);

        final StatusReplyCallback callback = new StatusReplyCallback(statusReply.getBody());
        getClient().postCallback(callback);
    }

    void handleAuthComplete(IPacketMsg packetMsg) {
        final ClientMsgProtobuf<CMsgClientTicketAuthComplete> authComplete = new ClientMsgProtobuf<CMsgClientTicketAuthComplete>(CMsgClientTicketAuthComplete.class, packetMsg);

        final TicketAuthCallback callback = new TicketAuthCallback(authComplete.getBody());
        getClient().postCallback(callback);
    }
}
