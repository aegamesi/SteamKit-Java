package uk.co.thomasc.steamkit.steam3.handlers.steamworkshop.callbacks;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientUCMEnumerateUserSubscribedFilesResponse;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientUCMEnumerateUserSubscribedFilesResponse.PublishedFileId;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EResult;
import uk.co.thomasc.steamkit.steam3.handlers.steamworkshop.SteamWorkshop;
import uk.co.thomasc.steamkit.steam3.handlers.steamworkshop.types.EnumerationUserDetails;
import uk.co.thomasc.steamkit.steam3.handlers.steamworkshop.types.FileSubscribed;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * This callback is received in response to calling
 * {@link SteamWorkshop#enumerateUserSubscribedFiles(EnumerationUserDetails)}.
 */
public final class UserSubscribedFilesCallback extends CallbackMsg {
    /**
     * Gets the result.
     */
    private final EResult result;
    /**
     * Gets the list of enumerated files.
     */
    private final List<FileSubscribed> files = new ArrayList<FileSubscribed>();
    /**
     * Gets the count of total results.
     */
    private final int totalResults;

    public UserSubscribedFilesCallback(CMsgClientUCMEnumerateUserSubscribedFilesResponse msg) {
        result = EResult.f(msg.eresult);
        for (final PublishedFileId f : msg.subscribedFiles) {
            files.add(new FileSubscribed(f));
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
    public List<FileSubscribed> getFiles() {
        return this.files;
    }

    /**
     * Gets the count of total results.
     */
    public int getTotalResults() {
        return this.totalResults;
    }
}
