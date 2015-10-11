package uk.co.thomasc.steamkit.steam3.handlers.steamchat.types;

import java.util.ArrayList;
import java.util.List;

import uk.co.thomasc.steamkit.types.steamid.SteamID;

public class Chat {
	public SteamID steamId;

	public List<ChatMemberInfo> members = new ArrayList<ChatMemberInfo>();
}
