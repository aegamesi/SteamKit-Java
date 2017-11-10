package uk.co.thomasc.steamkit.steam3.handlers.steamuser.types;

/**
 * Represents the details required to log into Steam3 as a user.
 */
public final class LogOnDetails {
    /**
     * Gets or sets the username.
     */
    public String username = "";
    /**
     * Gets or sets the password.
     */
    public String password = "";
    /**
     * Gets or sets the Steam Guard auth code used to login. This is the code
     * sent to the user's email.
     */
    public String authCode = "";
    /**
     * The two factor auth code from the mobile app
     */
    public String twoFactorCode = "";
    /**
     * Gets or sets the sentry file hash for this logon attempt, or null if no
     * sentry file is available.
     */
    public byte[] sentryFileHash = null;
    /**
     * Gets or sets the account instance. 1 for the PC instance or 2 for the
     * Console (PS3) instance.
     */
    public int accountInstance = 1; // use the default pc steam instance
    /**
     * Gets or sets a value indicating whether to request the Steam2 ticket.
     * This is an optional request only needed for Steam2 content downloads.
     */
    public boolean requestSteam2Ticket = false;
    /**
     * Tells Steam if we should remember the password by getting a valid loginkey for future logons.
     */
    public boolean shouldRememberPassword = false;
    /**
     * The loginkey to use, for passwordless authentication
     */
    public String loginkey = "";

    /**
     * Used to identify the logon session.
     */
    public Integer loginId = null;

    /**
     * Initializes a new instance of the {@link LogOnDetails} class.
     */
    public LogOnDetails() {
    }

    /**
     * Gets or sets the username.
     * @return this
     */
    public LogOnDetails username(final String username) {
        this.username = username;
        return this;
    }

    /**
     * Gets or sets the password.
     * @return this
     */
    public LogOnDetails password(final String password) {
        this.password = password;
        return this;
    }

    /**
     * Gets or sets the Steam Guard auth code used to login. This is the code
     * sent to the user's email.
     * @return this
     */
    public LogOnDetails authCode(final String authCode) {
        this.authCode = authCode;
        return this;
    }

    /**
     * The two factor auth code from the mobile app
     * @return this
     */
    public LogOnDetails twoFactorCode(final String twoFactorCode) {
        this.twoFactorCode = twoFactorCode;
        return this;
    }
}
