package uk.co.thomasc.steamkit.steam3.handlers.steamunifiedmessages;

import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import uk.co.thomasc.steamkit.base.ClientMsg;
import uk.co.thomasc.steamkit.base.ClientMsgProtobuf;
import uk.co.thomasc.steamkit.base.IPacketMsg;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientServiceMethod;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientServiceMethodResponse;
import uk.co.thomasc.steamkit.base.generated.SteammessagesTwofactorSteamclient;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EMsg;
import uk.co.thomasc.steamkit.base.generated.SteammessagesTwofactorSteamclient.CTwoFactor_AddAuthenticator_Response;
import uk.co.thomasc.steamkit.steam3.handlers.ClientMsgHandler;
import uk.co.thomasc.steamkit.steam3.handlers.steamunifiedmessages.callbacks.UnifiedMessageResponseCallback;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.JobCallback;
import uk.co.thomasc.steamkit.types.JobID;
import uk.co.thomasc.steamkit.types.gameid.GameID;

/**
 * This handler handles Steam unified messages
 */
public final class SteamUnifiedMessages extends ClientMsgHandler {
    /**
     * Sends a unified message
     */
    public void sendUnifiedMessage(String methodName, MessageNano proto, boolean notification) {
        final ClientMsgProtobuf<CMsgClientServiceMethod> msg = new ClientMsgProtobuf<>(CMsgClientServiceMethod.class, EMsg.ClientServiceMethod);
        msg.setSourceJobID(getClient().getNextJobID());
        msg.getBody().serializedMethod = MessageNano.toByteArray(proto);
        msg.getBody().methodName = methodName;
        msg.getBody().isNotification = notification;
        getClient().send(msg);
    }

    /**
     * Handles a client message. This should not be called directly.
     */
    @Override
    public void handleMsg(IPacketMsg packetMsg) {
        switch (packetMsg.getMsgType()) {
            case ClientServiceMethodResponse:
                handleClientServiceMethodResponse(packetMsg);
            break;
        }
    }

    public void handleClientServiceMethodResponse(IPacketMsg packetMsg) {
        final ClientMsgProtobuf<CMsgClientServiceMethodResponse> response = new ClientMsgProtobuf<>(CMsgClientServiceMethodResponse.class, packetMsg);


        final UnifiedMessageResponseCallback callback = new UnifiedMessageResponseCallback(response.getBody());
        getClient().postCallback(callback);
    }

    public interface UnifiedMessageCallback<T extends MessageNano> {
        void receive(T response);
        Class<T> getResponseClass();
    }
}
