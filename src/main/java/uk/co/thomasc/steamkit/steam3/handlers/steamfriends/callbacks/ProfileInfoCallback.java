package uk.co.thomasc.steamkit.steam3.handlers.steamfriends.callbacks;

import java.util.Date;

import lombok.Getter;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EResult;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;
import uk.co.thomasc.steamkit.types.steamid.SteamID;

/**
 * This callback is fired in response to requesting profile info for a user.
 */
public final class ProfileInfoCallback extends CallbackMsg {
	/**
	 * Gets the result of requesting profile info.
	 */
	@Getter
	private final EResult result;

	/**
	 * Gets the SteamID this info belongs to.
	 */
	@Getter
	private final SteamID steamID;

	/**
	 * Gets the time this account was created.
	 */
	@Getter
	private final Date timeCreated;

	/**
	 * Gets the real name.
	 */
	@Getter
	private final String realName;

	/**
	 * Gets the name of the city.
	 */
	@Getter
	private final String cityName;
	/**
	 * Gets the name of the state.
	 */
	@Getter
	private final String stateName;
	/**
	 * Gets the name of the country.
	 */
	@Getter
	private final String countryName;

	/**
	 * Gets the headline.
	 */
	@Getter
	private final String headline;

	/**
	 * Gets the summary.
	 */
	@Getter
	private final String summary;

	public ProfileInfoCallback(SteammessagesClientserver.CMsgClientFriendProfileInfoResponse response) {
		result = EResult.f(response.eresult);
		steamID = new SteamID(response.steamidFriend);

		timeCreated = new Date((long)response.timeCreated * 1000L);

		realName = response.realName;

		cityName = response.cityName;
		stateName = response.stateName;
		countryName = response.countryName;

		headline = response.headline;

		summary = response.summary;
	}
}
