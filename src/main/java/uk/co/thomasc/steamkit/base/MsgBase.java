package uk.co.thomasc.steamkit.base;

import uk.co.thomasc.steamkit.base.generated.steamlanguage.EMsg;
import uk.co.thomasc.steamkit.base.generated.steamlanguageinternal.ISteamSerializableHeader;
import uk.co.thomasc.steamkit.types.JobID;
import uk.co.thomasc.steamkit.types.steamid.SteamID;

import java.io.IOException;

/**
 * This is the abstract base class for all available client messages. It's used
 * to maintain packet payloads and provide a header for all client messages.
 *
 * @param <T> The header type for this client message.
 */
public abstract class MsgBase<T extends ISteamSerializableHeader> extends AMsgBase implements IClientMsg {
    /**
     * true if this instance is protobuf backed; otherwise, false.
     */
    private boolean isProto;
    /**
     * The network message type.
     */
    private EMsg msgType;
    /**
     * The session id.
     */
    protected int sessionID;
    /**
     * The {@link SteamID}
     */
    protected SteamID steamID;
    /**
     * The target job id.
     */
    protected JobID targetJobID;
    /**
     * The source job id.
     */
    protected JobID sourceJobID;
    /**
     * Gets the header for this message type.
     */
    protected T header;

    public MsgBase(Class<T> clazz) {
        this(clazz, 0);
    }

    /**
     * Initializes a new instance of the {@link MsgBase} class.
     *
     * @param payloadReserve The number of bytes to initialize the payload capacity to.
     */
    public MsgBase(Class<T> clazz, int payloadReserve) {
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
     * true if this instance is protobuf backed; otherwise, false.
     */
    public boolean isProto() {
        return this.isProto;
    }

    /**
     * The network message type.
     */
    public EMsg getMsgType() {
        return this.msgType;
    }

    /**
     * The session id.
     */
    public int getSessionID() {
        return this.sessionID;
    }

    /**
     * The {@link SteamID}
     */
    public SteamID getSteamID() {
        return this.steamID;
    }

    /**
     * The target job id.
     */
    public JobID getTargetJobID() {
        return this.targetJobID;
    }

    /**
     * The source job id.
     */
    public JobID getSourceJobID() {
        return this.sourceJobID;
    }

    /**
     * Gets the header for this message type.
     */
    public T getHeader() {
        return this.header;
    }
}
