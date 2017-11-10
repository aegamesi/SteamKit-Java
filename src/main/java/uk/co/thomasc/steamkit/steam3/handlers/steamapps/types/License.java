package uk.co.thomasc.steamkit.steam3.handlers.steamapps.types;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientLicenseList;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.ELicenseFlags;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.ELicenseType;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EPaymentMethod;

import java.util.Date;

/**
 * Represents a granted license (steam3 subscription) for one or more games.
 */
public final class License {
    /**
     * Gets the package ID used to identify the license.
     */
    private final int packageID;
    /**
     * Gets the last change number for this license.
     */
    private final int lastChangeNumber;
    /**
     * Gets the time the license was created.
     */
    private final Date timeCreated;
    /**
     * Gets the next process time for the license.
     */
    private final Date timeNextProcess;
    /**
     * Gets the minute limit of the license.
     */
    private final int minuteLimit;
    /**
     * Gets the minutes used of the license.
     */
    private final int minutesUsed;
    /**
     * Gets the payment method used when the license was created.
     */
    private final EPaymentMethod paymentMethod;
    /**
     * Gets the license flags.
     */
    private final ELicenseFlags licenseFlags;
    /**
     * Gets the two letter country code where the license was purchased.
     */
    private final String purchaseCountryCode;
    /**
     * Gets the type of the license.
     */
    private final ELicenseType licenseType;
    /**
     * Gets the territory code of the license.
     */
    private final int territoryCode;

    public License(CMsgClientLicenseList.License license) {
        packageID = license.packageId;
        lastChangeNumber = license.changeNumber;
        timeCreated = new Date(license.timeCreated);
        timeNextProcess = new Date(license.timeNextProcess);
        minuteLimit = license.minuteLimit;
        minutesUsed = license.minutesUsed;
        paymentMethod = EPaymentMethod.f(license.paymentMethod);
        licenseFlags = ELicenseFlags.f(license.flags);
        purchaseCountryCode = license.purchaseCountryCode;
        licenseType = ELicenseType.f(license.licenseType);
        territoryCode = license.territoryCode;
    }

    /**
     * Gets the package ID used to identify the license.
     */
    public int getPackageID() {
        return this.packageID;
    }

    /**
     * Gets the last change number for this license.
     */
    public int getLastChangeNumber() {
        return this.lastChangeNumber;
    }

    /**
     * Gets the time the license was created.
     */
    public Date getTimeCreated() {
        return this.timeCreated;
    }

    /**
     * Gets the next process time for the license.
     */
    public Date getTimeNextProcess() {
        return this.timeNextProcess;
    }

    /**
     * Gets the minute limit of the license.
     */
    public int getMinuteLimit() {
        return this.minuteLimit;
    }

    /**
     * Gets the minutes used of the license.
     */
    public int getMinutesUsed() {
        return this.minutesUsed;
    }

    /**
     * Gets the payment method used when the license was created.
     */
    public EPaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    /**
     * Gets the license flags.
     */
    public ELicenseFlags getLicenseFlags() {
        return this.licenseFlags;
    }

    /**
     * Gets the two letter country code where the license was purchased.
     */
    public String getPurchaseCountryCode() {
        return this.purchaseCountryCode;
    }

    /**
     * Gets the type of the license.
     */
    public ELicenseType getLicenseType() {
        return this.licenseType;
    }

    /**
     * Gets the territory code of the license.
     */
    public int getTerritoryCode() {
        return this.territoryCode;
    }
}
