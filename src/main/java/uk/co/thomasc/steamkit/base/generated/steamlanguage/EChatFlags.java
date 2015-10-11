package uk.co.thomasc.steamkit.base.generated.steamlanguage;

import java.util.HashMap;

public enum EChatFlags {
	Locked(1), InvisibleToFriends(2), Moderated(4), Unjoinable(8);

	private int code;

	private EChatFlags(int code) {
		this.code = code;
	}

	public int v() {
		return code;
	}

	private static HashMap<Integer, EChatFlags> values = new HashMap<Integer, EChatFlags>();

	static {
		for (final EChatFlags type : EChatFlags.values()) {
			EChatFlags.values.put(type.v(), type);
		}
	}

	public static EChatFlags f(int code) {
		return EChatFlags.values.get(code);
	}
}
