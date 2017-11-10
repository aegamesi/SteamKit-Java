package uk.co.thomasc.steamkit.steam3.handlers.steamworkshop.types;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientUCMEnumeratePublishedFilesByUserActionResponse;

import java.util.Date;

/**
 * Represents the details of a single published file.
 */
public class FilePublished extends File {
    /**
     * Gets the timestamp of this file.
     */
    private final Date timeStamp;

    public FilePublished(CMsgClientUCMEnumeratePublishedFilesByUserActionResponse.PublishedFileId file) {
        super(file.publishedFileId);
        timeStamp = new Date(file.rtimeTimeStamp);
    }

    /**
     * Gets the timestamp of this file.
     */
    public Date getTimeStamp() {
        return this.timeStamp;
    }
}
