package uk.co.thomasc.steamkit.steam3.handlers.steamapps.types;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver.CMsgClientPackageInfoResponse;
import uk.co.thomasc.steamkit.types.keyvalue.KeyValue;
import uk.co.thomasc.steamkit.util.stream.BinaryReader;

import java.io.IOException;

/**
 * Represents a single package in this response.
 */
public final class Package {
    /**
     * Gets the status of this package.
     */
    private final PackageStatus status;
    /**
     * Gets the PackageID for this package.
     */
    private final int packageID;
    /**
     * Gets the last change number for this package.
     */
    private int changeNumber;
    /**
     * Gets a hash of the package data for caching purposes.
     */
    private byte[] hash;
    /**
     * Gets the data for this package.
     */
    private KeyValue data;

    public Package(CMsgClientPackageInfoResponse.Package pack, PackageStatus status) {
        this.status = status;
        packageID = pack.packageId;
        changeNumber = pack.changeNumber;
        hash = pack.sha;
        data = new KeyValue();
        final BinaryReader is = new BinaryReader(pack.buffer);
        try {
            is.readInt(); // unknown uint at the beginning of the buffer
            data.readAsBinary(is);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public Package(int packageId, PackageStatus status) {
        this.status = status;
        packageID = packageId;
    }

    /**
     * Gets the status of this package.
     */
    public PackageStatus getStatus() {
        return this.status;
    }

    /**
     * Gets the PackageID for this package.
     */
    public int getPackageID() {
        return this.packageID;
    }

    /**
     * Gets the last change number for this package.
     */
    public int getChangeNumber() {
        return this.changeNumber;
    }

    /**
     * Gets a hash of the package data for caching purposes.
     */
    public byte[] getHash() {
        return this.hash;
    }

    /**
     * Gets the data for this package.
     */
    public KeyValue getData() {
        return this.data;
    }
}
