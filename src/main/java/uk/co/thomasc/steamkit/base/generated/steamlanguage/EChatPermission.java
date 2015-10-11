package uk.co.thomasc.steamkit.base.generated.steamlanguage;

import java.util.HashMap;

public enum EChatPermission {
	Close(1), Invite(2), Talk(8), Kick(16), Mute(32), SetMetadata(64), ChangePermissions(128), Ban(256), ChangeAccess(512), EveryoneNotInClanDefault(8), EveryoneDefault(8 | 2), MemberDefault(256 | 16 | 8 | 2), OfficerDefault(256 | 16 | 8 | 2), OwnerDefault(512 | 256 | 64 | 32 | 16 | 8 | 2 | 1), Mask(1019);

	private int code;

	private EChatPermission(int code) {
		this.code = code;
	}

	public int v() {
		return code;
	}

	private static HashMap<Integer, EChatPermission> values = new HashMap<Integer, EChatPermission>();

	static {
		for (final EChatPermission type : EChatPermission.values()) {
			EChatPermission.values.put(type.v(), type);
		}
	}

	public static EChatPermission f(int code) {
		return EChatPermission.values.get(code);
	}
}
