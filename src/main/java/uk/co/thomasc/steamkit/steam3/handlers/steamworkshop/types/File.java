// Generated by delombok at Wed Jun 15 22:04:53 CDT 2016
package uk.co.thomasc.steamkit.steam3.handlers.steamworkshop.types;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientUCMEnumerateUserPublishedFilesResponse;

/**
 * Represents the details of a single published file.
 */
public class File {
	/**
	 * Gets the file ID.
	 */
	private final long fileId;

	public File(CMsgClientUCMEnumerateUserPublishedFilesResponse.PublishedFileId file) {
		fileId = file.publishedFileId;
	}

	public File(long fileId) {
		this.fileId = fileId;
	}

	/**
	 * Gets the file ID.
	 */
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public long getFileId() {
		return this.fileId;
	}
}
