package uk.co.thomasc.steamkit.steam3.handlers.steamworkshop.types;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientUCMEnumerateUserSubscribedFilesResponse;

import java.util.Date;

/**
 * Represents the details of a single published file.
 */
public class FileSubscribed extends File {
    /**
     * Gets the time this file was subscribed to.
     */
    private final Date timeSubscribed;

    public FileSubscribed(CMsgClientUCMEnumerateUserSubscribedFilesResponse.PublishedFileId file) {
        super(file.publishedFileId);
        timeSubscribed = new Date(file.rtime32Subscribed);
    }

    /**
     * Gets the time this file was subscribed to.
     */
    public Date getTimeSubscribed() {
        return this.timeSubscribed;
    }
}
