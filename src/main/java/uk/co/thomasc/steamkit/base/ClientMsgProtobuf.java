package uk.co.thomasc.steamkit.base;

import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import uk.co.thomasc.steamkit.base.generated.SteammessagesBase.CMsgProtoBufHeader;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EMsg;
import uk.co.thomasc.steamkit.base.generated.steamlanguageinternal.MsgHdrProtoBuf;
import uk.co.thomasc.steamkit.types.JobID;
import uk.co.thomasc.steamkit.types.steamid.SteamID;
import uk.co.thomasc.steamkit.util.logging.Debug;
import uk.co.thomasc.steamkit.util.stream.BinaryReader;
import uk.co.thomasc.steamkit.util.stream.BinaryWriter;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Represents a protobuf backed client message.
 *
 * @param <U> The builder for T
 */
public final class ClientMsgProtobuf<U extends MessageNano> extends MsgBase<MsgHdrProtoBuf> {
    /**
     * Client messages of this type are always protobuf backed.
     */
    @Override
    public boolean isProto() {
        return true;
    }

    /**
     * Gets the network message type of this client message.
     */
    @Override
    public EMsg getMsgType() {
        return getHeader().msg;
    }

    /**
     * Gets the session id for this client message.
     */
    @Override
    public int getSessionID() {
        return getProtoHeader().clientSessionid;
    }

    /**
     * Sets the session id for this client message.
     */
    @Override
    public void setSessionID(int sessionID) {
        getProtoHeader().clientSessionid = sessionID;
    }

    /**
     * Gets the {@link SteamID} for this client message.
     */
    @Override
    public SteamID getSteamID() {
        return new SteamID(getProtoHeader().steamid);
    }

    /**
     * Sets the {@link SteamID} for this client message.
     */
    @Override
    public void setSteamID(SteamID steamID) {
        getProtoHeader().steamid = steamID.convertToLong();
    }

    /**
     * Gets or sets the target job id for this client message.
     */
    @Override
    public JobID getTargetJobID() {
        return new JobID(getProtoHeader().jobidTarget);
    }

    /**
     * Sets the target job id for this client message.
     */
    @Override
    public void setTargetJobID(JobID JobID) {
        getProtoHeader().jobidTarget = JobID.getValue();
    }

    /**
     * Gets or sets the target job id for this client message.
     */
    @Override
    public JobID getSourceJobID() {
        return new JobID(getProtoHeader().jobidSource);
    }

    /**
     * Sets the target job id for this client message.
     */
    @Override
    public void setSourceJobID(JobID JobID) {
        getProtoHeader().jobidSource = JobID.getValue();
    }

    /**
     * Shorthand accessor for the protobuf header.
     */
    public CMsgProtoBufHeader getProtoHeader() {
        return getHeader().proto;
    }

    /**
     * Gets the body structure of this message.
     */
    private U body;
    private Class<? extends MessageNano> clazz;

    public ClientMsgProtobuf(Class<? extends MessageNano> clazz, EMsg eMsg) {
        this(clazz, eMsg, 64);
    }

    /**
     * Initializes a new instance of the {@link ClientMsgProtobuf} class. This
     * is a client send constructor.
     *
     * @param eMsg           The network message type this client message represents.
     * @param clazz          The class of T
     * @param payloadReserve The number of bytes to initialize the payload capacity to.
     */
    @SuppressWarnings("unchecked")
    public ClientMsgProtobuf(Class<? extends MessageNano> clazz, EMsg eMsg, int payloadReserve) {
        super(MsgHdrProtoBuf.class, payloadReserve);
        this.clazz = clazz;
        try {
            body = (U) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // set our emsg
        getHeader().msg = eMsg;
    }

    public ClientMsgProtobuf(Class<? extends MessageNano> clazz, EMsg eMsg, MsgBase<MsgHdrProtoBuf> msg) {
        this(clazz, eMsg, msg, 64);
    }

    /**
     * Initializes a new instance of the {@link ClientMsgProtobuf} class. This a
     * reply constructor.
     *
     * @param eMsg           The network message type this client message represents.
     * @param msg            The message that this instance is a reply for.
     * @param payloadReserve The number of bytes to initialize the payload capacity to.
     */
    public ClientMsgProtobuf(Class<? extends MessageNano> clazz, EMsg eMsg, MsgBase<MsgHdrProtoBuf> msg, int payloadReserve) {
        this(clazz, eMsg, payloadReserve);
        // our target is where the message came from
        getHeader().proto.jobidTarget = msg.getHeader().proto.jobidSource;
    }

    /**
     * Initializes a new instance of the {@link ClientMsgProtobuf} class. This
     * is a recieve constructor.
     *
     * @param msg The packet message to build this client message from.
     */
    public ClientMsgProtobuf(Class<? extends MessageNano> clazz, IPacketMsg msg) {
        this(clazz, msg.getMsgType());
        Debug.Assert(msg.isProto(), "ClientMsgProtobuf used for non-proto message!");
        try {
            deSerialize(msg.getData());
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * serializes this client message instance to a byte array.
     *
     * @throws IOException
     */
    @Override
    public byte[] serialize() throws IOException {
        final BinaryWriter ms = new BinaryWriter();
        getHeader().serialize(ms);
        ms.write(MessageNano.toByteArray(body));
        ms.write(getOutputStream().toByteArray());
        return ms.toByteArray();
    }

    /**
     * Initializes this client message by deserializing the specified data.
     *
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    @Override
    public void deSerialize(byte[] data) throws IOException {
        final BinaryReader is = new BinaryReader(data);
        getHeader().deSerialize(is);
        try {
            body = (U) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        final int startPos = is.getPosition();
        CodedInputByteBufferNano byteBuffer = CodedInputByteBufferNano.newInstance(data, startPos, is.getRemaining());
        body.mergeFrom(byteBuffer);
        // the rest of the data is the payload
        final int payloadOffset = byteBuffer.getPosition() + startPos;
        final int payloadLen = data.length - payloadOffset;
        setPayload(new BinaryReader(new ByteArrayInputStream(copyOfRange(data, payloadOffset, payloadOffset + payloadLen))));
    }

    /**
     * XXX TODO CAUSES EXCEPTION **
     */
    public static byte[] copyOfRange(byte[] from, int start, int end) {
        int length = end - start;
        byte[] result = new byte[length];
        System.arraycopy(from, start, result, 0, length);
        return result;
    }

    /**
     * Gets the body structure of this message.
     */
    public U getBody() {
        return this.body;
    }
}
