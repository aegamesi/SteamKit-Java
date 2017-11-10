package uk.co.thomasc.steamkit.base;

import uk.co.thomasc.steamkit.util.stream.BinaryReader;
import uk.co.thomasc.steamkit.util.stream.BinaryWriter;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * This class provides a payload backing to client messages.
 */
public abstract class AMsgBase {
    private BinaryReader payload;
    private final BinaryWriter outputStream;

    /**
     * Initializes a new instance of the {@link AMsgBase} class.
     *
     * @param payloadReserve The number of bytes to initialize the payload capacity to.
     */
    public AMsgBase(int payloadReserve) {
        outputStream = new BinaryWriter(payloadReserve);
    }

    public AMsgBase() {
        this(0);
    }

    /**
     * Writes a single 32bit integer to the message payload.
     *
     * @param data The integer.
     * @throws IOException
     */
    public void write(int data) throws IOException {
        outputStream.write(data);
    }

    /**
     * Writes a single 64bit long to the message payload.
     *
     * @param data The long.
     * @throws IOException
     */
    public void write(long data) throws IOException {
        outputStream.write(data);
    }

    /**
     * Writes the specified byte array to the message payload.
     *
     * @param data The byte array.
     * @throws IOException
     */
    public void write(byte[] data) throws IOException {
        outputStream.write(data);
    }

    /**
     * Writes the specified string to the message payload using the specified
     * encoding. This function does not write a terminating null character.
     *
     * @param data     The string to write.
     * @param encoding The encoding to use.
     * @throws IOException
     */
    public void write(String data, Charset encoding) throws IOException {
        if (data == null) {
            return;
        }
        outputStream.write(data.getBytes(encoding));
    }

    /**
     * Writes the specified string and a null terminator to the message payload
     * using the specified encoding.
     *
     * @param data     The string to write.
     * @param encoding The encoding to use.
     * @throws IOException
     */
    public void writeNullTermString(String data, Charset encoding) throws IOException {
        write(data, encoding);
        write("\000", encoding);
    }

    public void setPayload(final BinaryReader payload) {
        this.payload = payload;
    }

    public BinaryReader getPayload() {
        return this.payload;
    }

    public BinaryWriter getOutputStream() {
        return this.outputStream;
    }
}
