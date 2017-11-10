package uk.co.thomasc.steamkit.base.generated.steamlanguage;

import java.util.HashMap;

public enum EClanPermission {
    Nobody(0), Owner(1), Officer(2), OwnerAndOfficer(1 | 2), Member(4), Moderator(8), OwnerOfficerModerator(1 | 2 | 8), AllMembers(1 | 2 | 4 | 8), OGGGameOwner(16), NonMember(128), MemberAllowed(128 | 4), ModeratorAllowed(128 | 4 | 8), OfficerAllowed(128 | 4 | 8 | 2), OwnerAllowed(128 | 4 | 8 | 2 | 1), Anybody(128 | 4 | 8 | 2 | 1);

    private int code;

    private EClanPermission(int code) {
        this.code = code;
    }

    public int v() {
        return code;
    }

    private static HashMap<Integer, EClanPermission> values = new HashMap<Integer, EClanPermission>();

    static {
        for (final EClanPermission type : EClanPermission.values()) {
            EClanPermission.values.put(type.v(), type);
        }
    }

    public static EClanPermission f(int code) {
        return EClanPermission.values.get(code);
    }
}
