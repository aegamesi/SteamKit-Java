package uk.co.thomasc.steamkit.steam3.handlers.steamfriends.callbacks;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EResult;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;
import uk.co.thomasc.steamkit.types.steamid.SteamID;

import java.util.Date;

/**
 * This callback is fired in response to requesting profile info for a user.
 */
public final class ProfileInfoCallback extends CallbackMsg {
    /**
     * Gets the result of requesting profile info.
     */
    private final EResult result;
    /**
     * Gets the SteamID this info belongs to.
     */
    private final SteamID steamID;
    /**
     * Gets the time this account was created.
     */
    private final Date timeCreated;
    /**
     * Gets the real name.
     */
    private final String realName;
    /**
     * Gets the name of the city.
     */
    private final String cityName;
    /**
     * Gets the name of the state.
     */
    private final String stateName;
    /**
     * Gets the name of the country.
     */
    private final String countryName;
    /**
     * Gets the headline.
     */
    private final String headline;
    /**
     * Gets the summary.
     */
    private final String summary;

    public ProfileInfoCallback(SteammessagesClientserver.CMsgClientFriendProfileInfoResponse response) {
        result = EResult.f(response.eresult);
        steamID = new SteamID(response.steamidFriend);
        timeCreated = new Date((long) response.timeCreated * 1000L);
        realName = response.realName;
        cityName = response.cityName;
        stateName = response.stateName;
        countryName = response.countryName;
        headline = response.headline;
        summary = response.summary;
    }

    /**
     * Gets the result of requesting profile info.
     */
    public EResult getResult() {
        return this.result;
    }

    /**
     * Gets the SteamID this info belongs to.
     */
    public SteamID getSteamID() {
        return this.steamID;
    }

    /**
     * Gets the time this account was created.
     */
    public Date getTimeCreated() {
        return this.timeCreated;
    }

    /**
     * Gets the real name.
     */
    public String getRealName() {
        return this.realName;
    }

    /**
     * Gets the name of the city.
     */
    public String getCityName() {
        return this.cityName;
    }

    /**
     * Gets the name of the state.
     */
    public String getStateName() {
        return this.stateName;
    }

    /**
     * Gets the name of the country.
     */
    public String getCountryName() {
        return this.countryName;
    }

    /**
     * Gets the headline.
     */
    public String getHeadline() {
        return this.headline;
    }

    /**
     * Gets the summary.
     */
    public String getSummary() {
        return this.summary;
    }
}
