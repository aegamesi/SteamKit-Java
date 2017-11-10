package uk.co.thomasc.steamkit.steam3.handlers.steamfriends.callbacks;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientPersonaState;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EPersonaState;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;
import uk.co.thomasc.steamkit.types.gameid.GameID;
import uk.co.thomasc.steamkit.types.steamid.SteamID;
import uk.co.thomasc.steamkit.util.util.NetHelpers;

import java.net.InetAddress;
import java.util.Date;

/**
 * This callback is fired in response to someone changing their friend details
 * over the network.
 */
public final class PersonaStateCallback extends CallbackMsg {
    /**
     * Gets the status flags. This shows what has changed.
     */
    private final int statusFlags;
    /**
     * Gets the friend ID.
     */
    private final SteamID friendID;
    /**
     * Gets the state.
     */
    private final EPersonaState state;
    /**
     * Gets the game app ID.
     */
    private final int gameAppID;
    /**
     * Gets the game ID.
     */
    private final GameID gameID;
    /**
     * Gets the name of the game.
     */
    private final String gameName;
    /**
     * Gets the game server IP.
     */
    private final InetAddress gameServerIP;
    /**
     * Gets the game server port.
     */
    private final int gameServerPort;
    /**
     * Gets the query port.
     */
    private final int queryPort;
    /**
     * Gets the source steam ID.
     */
    private final SteamID sourceSteamID;
    /**
     * Gets the game data blob.
     */
    private final byte[] gameDataBlob;
    /**
     * Gets the name.
     */
    private final String name;
    /**
     * Gets the avatar hash.
     */
    private final byte[] avatarHash;
    /**
     * Gets the last log off.
     */
    private final Date lastLogOff;
    /**
     * Gets the last log on.
     */
    private final Date lastLogOn;
    /**
     * Gets the clan rank.
     */
    private final int clanRank;
    /**
     * Gets the clan tag.
     */
    private final String clanTag;
    /**
     * Gets the online session instances.
     */
    private final int onlineSessionInstances;
    /**
     * Gets the published session ID.
     */
    private final int publishedSessionID;

    public PersonaStateCallback(CMsgClientPersonaState.Friend friend) {
        statusFlags = friend.personaStateFlags;
        friendID = new SteamID(friend.friendid);
        state = EPersonaState.f(friend.personaState);
        gameAppID = friend.gamePlayedAppId;
        gameID = new GameID(friend.gameid);
        gameName = friend.gameName;
        gameServerIP = NetHelpers.getIPAddress(friend.gameServerIp);
        gameServerPort = friend.gameServerPort;
        queryPort = friend.queryPort;
        sourceSteamID = new SteamID(friend.steamidSource);
        gameDataBlob = friend.gameDataBlob;
        name = friend.playerName;
        avatarHash = friend.avatarHash;
        lastLogOff = new Date(friend.lastLogoff);
        lastLogOn = new Date(friend.lastLogon);
        clanRank = friend.clanRank;
        clanTag = friend.clanTag;
        onlineSessionInstances = friend.onlineSessionInstances;
        publishedSessionID = friend.publishedInstanceId;
    }

    /**
     * Gets the status flags. This shows what has changed.
     */
    public int getStatusFlags() {
        return this.statusFlags;
    }

    /**
     * Gets the friend ID.
     */
    public SteamID getFriendID() {
        return this.friendID;
    }

    /**
     * Gets the state.
     */
    public EPersonaState getState() {
        return this.state;
    }

    /**
     * Gets the game app ID.
     */
    public int getGameAppID() {
        return this.gameAppID;
    }

    /**
     * Gets the game ID.
     */
    public GameID getGameID() {
        return this.gameID;
    }

    /**
     * Gets the name of the game.
     */
    public String getGameName() {
        return this.gameName;
    }

    /**
     * Gets the game server IP.
     */
    public InetAddress getGameServerIP() {
        return this.gameServerIP;
    }

    /**
     * Gets the game server port.
     */
    public int getGameServerPort() {
        return this.gameServerPort;
    }

    /**
     * Gets the query port.
     */
    public int getQueryPort() {
        return this.queryPort;
    }

    /**
     * Gets the source steam ID.
     */
    public SteamID getSourceSteamID() {
        return this.sourceSteamID;
    }

    /**
     * Gets the game data blob.
     */
    public byte[] getGameDataBlob() {
        return this.gameDataBlob;
    }

    /**
     * Gets the name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the avatar hash.
     */
    public byte[] getAvatarHash() {
        return this.avatarHash;
    }

    /**
     * Gets the last log off.
     */
    public Date getLastLogOff() {
        return this.lastLogOff;
    }

    /**
     * Gets the last log on.
     */
    public Date getLastLogOn() {
        return this.lastLogOn;
    }

    /**
     * Gets the clan rank.
     */
    public int getClanRank() {
        return this.clanRank;
    }

    /**
     * Gets the clan tag.
     */
    public String getClanTag() {
        return this.clanTag;
    }

    /**
     * Gets the online session instances.
     */
    public int getOnlineSessionInstances() {
        return this.onlineSessionInstances;
    }

    /**
     * Gets the published session ID.
     */
    public int getPublishedSessionID() {
        return this.publishedSessionID;
    }
}
