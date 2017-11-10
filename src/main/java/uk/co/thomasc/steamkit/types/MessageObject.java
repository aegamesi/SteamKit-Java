package uk.co.thomasc.steamkit.types;

import uk.co.thomasc.steamkit.types.keyvalue.KeyValue;
import uk.co.thomasc.steamkit.util.stream.BinaryReader;

import java.io.IOException;

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

    public KeyValue getKeyValues() {
        return this.keyValues;
    }
}
