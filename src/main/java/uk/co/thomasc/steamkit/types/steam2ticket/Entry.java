package uk.co.thomasc.steamkit.types.steam2ticket;

import uk.co.thomasc.steamkit.util.stream.BinaryReader;

import java.io.IOException;

/**
 * Represents a single data entry within the ticket container.
 */
public final class Entry {
    /**
     * Gets the magic.
     */
    private short magic; // 0x0400, probably entry magic? idk
    /**
     * Gets the index of this entry.
     */
    private int index;
    /**
     * Gets the data of this entry.
     */
    private byte[] data;

    public void deSerialize(BinaryReader stream) throws IOException {
        magic = stream.readShort();
        final int length = stream.readInt();
        index = stream.readInt();
        data = stream.readBytes(length);
    }

    /**
     * Gets the magic.
     */
    public short getMagic() {
        return this.magic;
    }

    /**
     * Gets the index of this entry.
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * Gets the data of this entry.
     */
    public byte[] getData() {
        return this.data;
    }
}
