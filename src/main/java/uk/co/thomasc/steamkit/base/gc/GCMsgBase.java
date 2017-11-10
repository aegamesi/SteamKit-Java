package uk.co.thomasc.steamkit.base.gc;

import uk.co.thomasc.steamkit.base.AMsgBase;
import uk.co.thomasc.steamkit.base.generated.steamlanguageinternal.IGCSerializableHeader;
import uk.co.thomasc.steamkit.types.JobID;

import java.io.IOException;

/**
 * This is the abstract base class for all available game coordinator messages.
 * It's used to maintain packet payloads and provide a header for all gc
 * messages.
 *
 * @param <T> The header type for this gc message.
 */
public abstract class GCMsgBase<T extends IGCSerializableHeader> extends AMsgBase implements IClientGCMsg {
    /**
     * true if this instance is protobuf backed; otherwise, false
     */
    private boolean isProto;
    /**
     * The network message type.
     */
    private int msgType;
    /**
     * The target job id.
     */
    public JobID targetJobID;
    /**
     * The source job id.
     */
    public JobID sourceJobID;
    /**
     * Gets the header for this message type.
     */
    private T header;

    /**
     * Initializes a new instance of the {@link GCMsgBase} class.
     *
     * @param payloadReserve The number of bytes to initialize the payload capacity to.
     */
    public GCMsgBase(Class<T> clazz, int payloadReserve) {
        super(payloadReserve);
        try {
            header = clazz.newInstance();
        } catch (final InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * serializes this client message instance to a byte array.
     *
     * @return Data representing a client message.
     */
    @Override
    public abstract byte[] serialize() throws IOException;

    /**
     * Initializes this client message by deserializing the specified data.
     *
     * @param data The data representing a client message.
     * @throws IOException
     */
    @Override
    public abstract void deSerialize(byte[] data) throws IOException;

    /**
     * true if this instance is protobuf backed; otherwise, false
     */
    public boolean isProto() {
        return this.isProto;
    }

    /**
     * The network message type.
     */
    public int getMsgType() {
        return this.msgType;
    }

    /**
     * Gets the header for this message type.
     */
    public T getHeader() {
        return this.header;
    }
}
