// Generated by delombok at Wed Jun 15 22:04:52 CDT 2016
package uk.co.thomasc.steamkit.steam3.handlers.steamcloud.callbacks;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientUFSGetUGCDetailsResponse;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EResult;
import uk.co.thomasc.steamkit.steam3.handlers.steamcloud.SteamCloud;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;
import uk.co.thomasc.steamkit.types.steamid.SteamID;

/**
 * This callback is recieved in response to calling
 * {@link SteamCloud#requestUGCDetails(long)}.
 */
public final class UGCDetailsCallback extends CallbackMsg {
	/**
	 * Gets the result of the request.
	 */
	private final EResult result;
	/**
	 * Gets the App ID the UGC is for.
	 */
	private final int appID;
	/**
	 * Gets the App ID the UGC is for.
	 */
	private final SteamID creator;
	/**
	 * Gets the URL that the content is located at.
	 */
	private final String URL;
	/**
	 * Gets the name of the file.
	 */
	private final String fileName;
	/**
	 * Gets the size of the file.
	 */
	private final int fileSize;

	public UGCDetailsCallback(CMsgClientUFSGetUGCDetailsResponse msg) {
		result = EResult.f(msg.eresult);
		appID = msg.appId;
		creator = new SteamID(msg.steamidCreator);
		URL = msg.url;
		fileName = msg.filename;
		fileSize = msg.fileSize;
	}

	/**
	 * Gets the result of the request.
	 */
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public EResult getResult() {
		return this.result;
	}

	/**
	 * Gets the App ID the UGC is for.
	 */
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int getAppID() {
		return this.appID;
	}

	/**
	 * Gets the App ID the UGC is for.
	 */
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public SteamID getCreator() {
		return this.creator;
	}

	/**
	 * Gets the URL that the content is located at.
	 */
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getURL() {
		return this.URL;
	}

	/**
	 * Gets the name of the file.
	 */
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getFileName() {
		return this.fileName;
	}

	/**
	 * Gets the size of the file.
	 */
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int getFileSize() {
		return this.fileSize;
	}
}
