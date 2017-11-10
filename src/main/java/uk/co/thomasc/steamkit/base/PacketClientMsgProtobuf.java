package uk.co.thomasc.steamkit.base;

import uk.co.thomasc.steamkit.base.generated.steamlanguage.EMsg;
import uk.co.thomasc.steamkit.base.generated.steamlanguageinternal.MsgHdrProtoBuf;
import uk.co.thomasc.steamkit.util.stream.BinaryReader;

import java.io.IOException;

/**
 * Represents a protobuf backed packet message.
 */
public final class PacketClientMsgProtobuf implements IPacketMsg {
    /**
     * This type of message is always protobuf backed.
     */
    @Override
    public boolean isProto() {
        return true;
    }

    /**
     * The message type.
     */
    private EMsg msgType;
    private final long targetJobID;
    private final long sourceJobID;
    byte[] payload;

    /**
     * Initializes a new instance of the {@link PacketClientMsgProtobuf} class.
     *
     * @param eMsg The network message type for this packet message.
     * @param data The data.
     */
    public PacketClientMsgProtobuf(EMsg eMsg, byte[] data) {
        msgType = eMsg;
        payload = data;
        final MsgHdrProtoBuf protobufHeader = new MsgHdrProtoBuf();
        // we need to pull out the job ids, so we deSerialize the protobuf header
        final BinaryReader ms = new BinaryReader(data);
        try {
            protobufHeader.deSerialize(ms);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        targetJobID = protobufHeader.proto.jobidTarget;
        sourceJobID = protobufHeader.proto.jobidSource;
    }

    /**
     * Gets the underlying data that represents this client message.
     */
    @Override
    public byte[] getData() {
        return payload;
    }

    /**
     * The message type.
     */
    public EMsg getMsgType() {
        return this.msgType;
    }

    /**
     * The message type.
     */
    public void setMsgType(final EMsg msgType) {
        this.msgType = msgType;
    }

    public long getTargetJobID() {
        return this.targetJobID;
    }

    public long getSourceJobID() {
        return this.sourceJobID;
    }
}
