package uk.co.thomasc.steamkit.steam3.handlers.steamworkshop.types;

import lombok.Getter;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientUCMEnumerateUserPublishedFilesResponse;

/**
 * Represents the details of a single published file.
 */
public class File {
	/**
	 * Gets the file ID.
	 */
	@Getter
	private final long fileId;

	public File(CMsgClientUCMEnumerateUserPublishedFilesResponse.PublishedFileId file) {
		fileId = file.publishedFileId;
	}

	public File(long fileId) {
		this.fileId = fileId;
	}
}
