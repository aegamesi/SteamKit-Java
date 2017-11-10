package uk.co.thomasc.steamkit.steam3.handlers.steamfriends.callbacks;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientAddFriendResponse;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EResult;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;
import uk.co.thomasc.steamkit.types.steamid.SteamID;

/**
 * This callback is fired in response to adding a user to your friends list.
 */
public final class FriendAddedCallback extends CallbackMsg {
    /**
     * Gets the result of the request.
     */
    private final EResult result;
    /**
     * Gets the SteamID of the friend that was added.
     */
    private final SteamID steamID;
    /**
     * Gets the persona name of the friend.
     */
    private final String personaName;

    public FriendAddedCallback(CMsgClientAddFriendResponse msg) {
        result = EResult.f(msg.eresult);
        steamID = new SteamID(msg.steamIdAdded);
        personaName = msg.personaNameAdded;
    }

    /**
     * Gets the result of the request.
     */
    public EResult getResult() {
        return this.result;
    }

    /**
     * Gets the SteamID of the friend that was added.
     */
    public SteamID getSteamID() {
        return this.steamID;
    }

    /**
     * Gets the persona name of the friend.
     */
    public String getPersonaName() {
        return this.personaName;
    }
}
