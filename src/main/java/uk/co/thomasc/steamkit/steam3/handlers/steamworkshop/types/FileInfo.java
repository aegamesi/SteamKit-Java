// Generated by delombok at Wed Jun 15 22:04:53 CDT 2016
package uk.co.thomasc.steamkit.steam3.handlers.steamworkshop.types;

import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgCREEnumeratePublishedFilesResponse;

/**
 * Represents the details of a single published file.
 */
public final class FileInfo extends File {
	/**
	 * Gets the number of reports for this file.
	 */
	private final int reports;
	/**
	 * Gets the score of this file, based on up and down votes.
	 */
	private final float score;
	/**
	 * Gets the total count of up votes.
	 */
	private final int upVotes;
	/**
	 * Gets the total count of down votes.
	 */
	private final int downVotes;

	public FileInfo(CMsgCREEnumeratePublishedFilesResponse.PublishedFileId file) {
		super(file.publishedFileId);
		reports = file.reports;
		score = file.score;
		upVotes = file.votesFor;
		downVotes = file.votesAgainst;
	}

	/**
	 * Gets the number of reports for this file.
	 */
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int getReports() {
		return this.reports;
	}

	/**
	 * Gets the score of this file, based on up and down votes.
	 */
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public float getScore() {
		return this.score;
	}

	/**
	 * Gets the total count of up votes.
	 */
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int getUpVotes() {
		return this.upVotes;
	}

	/**
	 * Gets the total count of down votes.
	 */
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int getDownVotes() {
		return this.downVotes;
	}
}
