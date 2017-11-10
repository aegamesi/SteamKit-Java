package uk.co.thomasc.steamkit.base;

import uk.co.thomasc.steamkit.base.generated.steamlanguage.EMsg;
import uk.co.thomasc.steamkit.base.generated.steamlanguageinternal.MsgHdr;
import uk.co.thomasc.steamkit.util.stream.BinaryReader;

import java.io.IOException;

/**
 * Represents a packet message with basic header information.
 */
public final class PacketMsg implements IPacketMsg {
    /**
     * Gets a value indicating whether this packet message is protobuf backed.
     * This type of message is never protobuf backed.
     */
    @Override
    public boolean isProto() {
        return false;
    }

    /**
     * Gets the network message type of this packet message.
     */
    private final EMsg msgType;
    /**
     * Gets the target job id for this packet message.
     */
    private long targetJobID;
    /**
     * Gets the source job id for this packet message.
     */
    private long sourceJobID;
    byte[] payload;

    /**
     * Initializes a new instance of the {@link PacketMsg} class.
     *
     * @param eMsg The network message type for this packet message.
     * @param data The data.
     */
    public PacketMsg(EMsg eMsg, byte[] data) {
        msgType = eMsg;
        payload = data;
        final MsgHdr msgHdr = new MsgHdr();
        // deserialize the header to get our hands on the job ids
        final BinaryReader is = new BinaryReader(data);
        try {
            msgHdr.deSerialize(is);
            targetJobID = msgHdr.targetJobID;
            sourceJobID = msgHdr.sourceJobID;
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the underlying data that represents this client message.
     */
    @Override
    public byte[] getData() {
        return payload;
    }

    /**
     * Gets the network message type of this packet message.
     */
    public EMsg getMsgType() {
        return this.msgType;
    }

    /**
     * Gets the target job id for this packet message.
     */
    public long getTargetJobID() {
        return this.targetJobID;
    }

    /**
     * Gets the source job id for this packet message.
     */
    public long getSourceJobID() {
        return this.sourceJobID;
    }
}
