package uk.co.thomasc.steamkit.steam3.handlers.steamapps.types;

import uk.co.thomasc.steamkit.steam3.handlers.steamapps.SteamApps;

/**
 * Represents app request details when calling {@link SteamApps#getAppInfo(int)}.
 */
public final class AppDetails {
    /**
     * Gets or sets the AppID for this request.
     */
    public int appID;
    /**
     * Gets or sets the section flags for this request.
     */
    public int sectionFlags = 65535; // request all sections by default
    /**
     * Gets the Section CRC list for this request.
     */
    private final int[] sectionCRC = null;

    /**
     * Initializes a new instance of the {@link AppDetails} class.
     */
    public AppDetails() {
    }

    /**
     * Gets the Section CRC list for this request.
     */
    public int[] getSectionCRC() {
        return this.sectionCRC;
    }
}
