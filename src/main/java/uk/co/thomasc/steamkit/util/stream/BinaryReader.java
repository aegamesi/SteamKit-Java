package uk.co.thomasc.steamkit.util.stream;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.InvalidProtocolBufferException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class BinaryReader {

	public static long LongMaxValue = 0xFFFFFFFFFFFFFFFFL;

	CodedInputStream reader;
	int len = 0;

	public BinaryReader(InputStream stream) {
		reader = CodedInputStream.newInstance(stream);
	}

	public BinaryReader(byte[] data) {
		reader = CodedInputStream.newInstance(data);
		len = data.length;
		try {
			reader.pushLimit(len);
		} catch (final InvalidProtocolBufferException e) {
			uk.co.thomasc.steamkit.util.logging.DebugLog.writeLine("NEW_EX", "Exception: %s", e);
		}
	}

	public long readLong() throws IOException {
		return getBuffer(8).getLong();
	}

	private ByteBuffer getBuffer(int size) throws IOException {
		final byte[] buffer = new byte[size];
		for (int i = 1; i <= size; i++) {
			buffer[size - i] = reader.readRawByte();
		}
		return ByteBuffer.wrap(buffer);
	}

	public int readInt() throws IOException {
		return getBuffer(4).getInt();
	}

	public int getPosition() {
		/*
		 * try { Field f =
		 * CodedInputStream.class.getDeclaredField("totalBytesRetired");
		 * f.setAccessible(true); return (int) f.get(reader); } catch
		 * (NoSuchFieldException | SecurityException | IllegalArgumentException
		 * | IllegalAccessException e) {
		 * uk.co.thomasc.steamkit.util.logging.DebugLog.writeLine("NEW_EX",
		 * "Exception: %s", e); } return 0;
		 */
		return reader.getTotalBytesRead();
	}

	public int getRemaining() {
		return len - getPosition();
	}

	public CodedInputStream getStream() {
		return reader;
	}

	public short readShort() throws IOException {
		return getBuffer(2).getShort();
	}

	public byte readByte() throws IOException {
		return reader.readRawByte();
	}

	public byte[] readBytes(int length) throws IOException {
		return reader.readRawBytes(length);
	}

	public boolean isAtEnd() throws IOException {
		return reader.isAtEnd();
	}

	public byte[] readBytes() throws IOException {
		return reader.readRawBytes(getRemaining());
	}

	public float readFloat() throws IOException {
		return getBuffer(4).getFloat();
	}

	public String readString() throws IOException {
		return reader.readString();
	}

	public String readNullTermString() throws IOException {
		int rb, i = 0;
		final byte[] res = new byte[2048];
		while ((rb = readByte()) != 0 && rb != -1) {
			res[i++] = (byte) rb;
		}
		return new String(res, 0, i);
	}

}
