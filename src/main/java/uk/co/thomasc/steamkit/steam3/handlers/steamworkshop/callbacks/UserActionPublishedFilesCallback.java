package uk.co.thomasc.steamkit.steam3.handlers.steamworkshop.callbacks;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientUCMEnumeratePublishedFilesByUserActionResponse;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientUCMEnumeratePublishedFilesByUserActionResponse.PublishedFileId;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EResult;
import uk.co.thomasc.steamkit.steam3.handlers.steamworkshop.SteamWorkshop;
import uk.co.thomasc.steamkit.steam3.handlers.steamworkshop.types.EnumerationUserDetails;
import uk.co.thomasc.steamkit.steam3.handlers.steamworkshop.types.FilePublished;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * This callback is received in response to calling
 * {@link SteamWorkshop#enumeratePublishedFilesByUserAction(EnumerationUserDetails)}
 * .
 */
public final class UserActionPublishedFilesCallback extends CallbackMsg {
    /**
     * Gets the result.
     */
    private final EResult result;
    /**
     * Gets the list of enumerated files.
     */
    private final List<FilePublished> files = new ArrayList<FilePublished>();
    /**
     * Gets the count of total results.
     */
    private final int totalResults;

    public UserActionPublishedFilesCallback(CMsgClientUCMEnumeratePublishedFilesByUserActionResponse msg) {
        result = EResult.f(msg.eresult);
        for (final PublishedFileId f : msg.publishedFiles) {
            files.add(new FilePublished(f));
        }
        totalResults = msg.totalResults;
    }

    /**
     * Gets the result.
     */
    public EResult getResult() {
        return this.result;
    }

    /**
     * Gets the list of enumerated files.
     */
    public List<FilePublished> getFiles() {
        return this.files;
    }

    /**
     * Gets the count of total results.
     */
    public int getTotalResults() {
        return this.totalResults;
    }
}
