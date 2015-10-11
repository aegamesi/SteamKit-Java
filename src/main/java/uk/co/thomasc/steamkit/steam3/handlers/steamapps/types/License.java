package uk.co.thomasc.steamkit.steam3.handlers.steamapps.types;

import java.util.Date;

import lombok.Getter;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientLicenseList;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.ELicenseFlags;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.ELicenseType;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EPaymentMethod;

/**
 * Represents a granted license (steam3 subscription) for one or more games.
 */
public final class License {
	/**
	 * Gets the package ID used to identify the license.
	 */
	@Getter
	private final int packageID;

	/**
	 * Gets the last change number for this license.
	 */
	@Getter
	private final int lastChangeNumber;

	/**
	 * Gets the time the license was created.
	 */
	@Getter
	private final Date timeCreated;

	/**
	 * Gets the next process time for the license.
	 */
	@Getter
	private final Date timeNextProcess;

	/**
	 * Gets the minute limit of the license.
	 */
	@Getter
	private final int minuteLimit;

	/**
	 * Gets the minutes used of the license.
	 */
	@Getter
	private final int minutesUsed;

	/**
	 * Gets the payment method used when the license was created.
	 */
	@Getter
	private final EPaymentMethod paymentMethod;

	/**
	 * Gets the license flags.
	 */
	@Getter
	private final ELicenseFlags licenseFlags;

	/**
	 * Gets the two letter country code where the license was purchased.
	 */
	@Getter
	private final String purchaseCountryCode;

	/**
	 * Gets the type of the license.
	 */
	@Getter
	private final ELicenseType licenseType;

	/**
	 * Gets the territory code of the license.
	 */
	@Getter
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
}
