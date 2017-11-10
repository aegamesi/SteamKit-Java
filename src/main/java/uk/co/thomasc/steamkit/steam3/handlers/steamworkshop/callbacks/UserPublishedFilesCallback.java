package uk.co.thomasc.steamkit.steam3.handlers.steamworkshop.callbacks;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientUCMEnumerateUserPublishedFilesResponse;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientUCMEnumerateUserPublishedFilesResponse.PublishedFileId;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EResult;
import uk.co.thomasc.steamkit.steam3.handlers.steamworkshop.SteamWorkshop;
import uk.co.thomasc.steamkit.steam3.handlers.steamworkshop.types.EnumerationUserDetails;
import uk.co.thomasc.steamkit.steam3.handlers.steamworkshop.types.File;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;

import java.util.List;

/**
 * This callback is received in response to calling
 * {@link SteamWorkshop#enumerateUserPublishedFiles(EnumerationUserDetails)}.
 */
public final class UserPublishedFilesCallback extends CallbackMsg {
    /**
     * Gets the result.
     */
    private final EResult result;
    /**
     * Gets the list of enumerated files.
     */
    private List<File> files;
    /**
     * Gets the count of total results.
     */
    private final int totalResults;

    public UserPublishedFilesCallback(CMsgClientUCMEnumerateUserPublishedFilesResponse msg) {
        result = EResult.f(msg.eresult);
        for (final PublishedFileId f : msg.publishedFiles) {
            files.add(new File(f));
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
    public List<File> getFiles() {
        return this.files;
    }

    /**
     * Gets the count of total results.
     */
    public int getTotalResults() {
        return this.totalResults;
    }
}
