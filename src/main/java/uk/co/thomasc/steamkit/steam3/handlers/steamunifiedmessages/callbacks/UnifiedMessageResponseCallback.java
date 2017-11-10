package uk.co.thomasc.steamkit.steam3.handlers.steamunifiedmessages.callbacks;

import com.google.protobuf.nano.MessageNano;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientServiceMethodResponse;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;

import java.lang.reflect.Method;

public class UnifiedMessageResponseCallback extends CallbackMsg {
    /**
     * Gets the method name of the response
     */
    private final String methodName;
    private final byte[] serializedMethodResponse;

    public UnifiedMessageResponseCallback(CMsgClientServiceMethodResponse resp) {
        this.methodName = resp.methodName;
        this.serializedMethodResponse = resp.serializedMethodResponse;
    }

    public <T extends MessageNano> T getProtobuf(Class<T> type) {
        try {
            Method method = type.getMethod("parseFrom", byte[].class);
            MessageNano messageNano = (MessageNano) method.invoke(null, serializedMethodResponse);
            return (T) messageNano;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the method name of the response
     */
    public String getMethodName() {
        return this.methodName;
    }
}
