package uk.co.thomasc.steamkit.steam3.handlers.steamworkshop.callbacks;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgCREEnumeratePublishedFilesResponse;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgCREEnumeratePublishedFilesResponse.PublishedFileId;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EResult;
import uk.co.thomasc.steamkit.steam3.handlers.steamworkshop.SteamWorkshop;
import uk.co.thomasc.steamkit.steam3.handlers.steamworkshop.types.EnumerationDetails;
import uk.co.thomasc.steamkit.steam3.handlers.steamworkshop.types.FileInfo;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * This callback is received in response to calling
 * {@link SteamWorkshop#enumeratePublishedFiles(EnumerationDetails)}.
 */
public final class PublishedFilesCallback extends CallbackMsg {
    /**
     * Gets the result.
     */
    private final EResult result;
    /**
     * Gets the list of enumerated files.
     */
    private final List<FileInfo> files = new ArrayList<FileInfo>();
    /**
     * Gets the count of total results.
     */
    private final int totalResults;

    public PublishedFilesCallback(CMsgCREEnumeratePublishedFilesResponse msg) {
        result = EResult.f(msg.eresult);
        for (final PublishedFileId file : msg.publishedFiles) {
            files.add(new FileInfo(file));
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
    public List<FileInfo> getFiles() {
        return this.files;
    }

    /**
     * Gets the count of total results.
     */
    public int getTotalResults() {
        return this.totalResults;
    }
}
