// Generated by delombok at Wed Jun 15 22:04:53 CDT 2016
package uk.co.thomasc.steamkit.types;

import java.io.IOException;
import uk.co.thomasc.steamkit.types.keyvalue.KeyValue;
import uk.co.thomasc.steamkit.util.stream.BinaryReader;

public class MessageObject {
	protected KeyValue keyValues;

	public MessageObject() {
		this.keyValues = new KeyValue("MessageObject");
	}

	public MessageObject(KeyValue keyValues) {
		this.keyValues = keyValues;
	}

	public boolean readFromBinary(BinaryReader input) {
		if (keyValues == null) {
			return false;
		}
		try {
			return keyValues.readAsBinary(input);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public KeyValue getKeyValues() {
		return this.keyValues;
	}
}