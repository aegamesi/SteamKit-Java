package uk.co.thomasc.steamkit.steam3.handlers.steamworkshop;

import uk.co.thomasc.steamkit.base.ClientMsgProtobuf;
import uk.co.thomasc.steamkit.base.IPacketMsg;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgCREEnumeratePublishedFiles;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgCREEnumeratePublishedFilesResponse;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientUCMEnumeratePublishedFilesByUserAction;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientUCMEnumeratePublishedFilesByUserActionResponse;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientUCMEnumerateUserPublishedFiles;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientUCMEnumerateUserPublishedFilesResponse;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientUCMEnumerateUserSubscribedFiles;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientUCMEnumerateUserSubscribedFilesResponse;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EMsg;
import uk.co.thomasc.steamkit.steam3.handlers.ClientMsgHandler;
import uk.co.thomasc.steamkit.steam3.handlers.steamworkshop.callbacks.PublishedFilesCallback;
import uk.co.thomasc.steamkit.steam3.handlers.steamworkshop.callbacks.UserActionPublishedFilesCallback;
import uk.co.thomasc.steamkit.steam3.handlers.steamworkshop.callbacks.UserPublishedFilesCallback;
import uk.co.thomasc.steamkit.steam3.handlers.steamworkshop.callbacks.UserSubscribedFilesCallback;
import uk.co.thomasc.steamkit.steam3.handlers.steamworkshop.types.EnumerationDetails;
import uk.co.thomasc.steamkit.steam3.handlers.steamworkshop.types.EnumerationUserDetails;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.JobCallback;
import uk.co.thomasc.steamkit.types.JobID;

/**
 * This handler is used for requesting files published on the Steam Workshop.
 */
public final class SteamWorkshop extends ClientMsgHandler {
	/**
	 * Enumerates the list of published files for the current logged in user.
	 * Results are returned in a {@link UserPublishedFilesCallback} from a
	 * {@link JobCallback}.
	 *
	 * @param details
	 *            The specific details of the request.
	 * @return The Job ID of the request. This can be used to find the
	 *         appropriate {@link JobCallback}.
	 */
	public JobID enumerateUserPublishedFiles(EnumerationUserDetails details) {
		final ClientMsgProtobuf<CMsgClientUCMEnumerateUserPublishedFiles> enumRequest = new ClientMsgProtobuf<CMsgClientUCMEnumerateUserPublishedFiles>(CMsgClientUCMEnumerateUserPublishedFiles.class, EMsg.ClientUCMEnumerateUserPublishedFiles);
		enumRequest.setSourceJobID(getClient().getNextJobID());

		enumRequest.getBody().appId = details.appId;
		enumRequest.getBody().sortOrder = details.sortOrder;
		enumRequest.getBody().startIndex = details.startIndex;

		getClient().send(enumRequest);

		return enumRequest.getSourceJobID();
	}

	/**
	 * Enumerates the list of subscribed files for the current logged in user.
	 * Results are returned in a {@link UserSubscribedFilesCallback} from a
	 * {@link JobCallback}.
	 *
	 * @param details
	 *            The specific details of the request.
	 * @return The Job ID of the request. This can be used to find the
	 *         appropriate {@link JobCallback}.
	 */
	public JobID enumerateUserSubscribedFiles(EnumerationUserDetails details) {
		final ClientMsgProtobuf<CMsgClientUCMEnumerateUserSubscribedFiles> enumRequest = new ClientMsgProtobuf<CMsgClientUCMEnumerateUserSubscribedFiles>(CMsgClientUCMEnumerateUserSubscribedFiles.class, EMsg.ClientUCMEnumerateUserSubscribedFiles);
		enumRequest.setSourceJobID(getClient().getNextJobID());

		enumRequest.getBody().appId = details.appId;
		enumRequest.getBody().startIndex = details.startIndex;

		getClient().send(enumRequest);

		return enumRequest.getSourceJobID();
	}

	/**
	 * Enumerates the list of published files for the current logged in user
	 * based on user action. Results are returned in a
	 * {@link UserActionPublishedFilesCallback} from a {@link JobCallback}.
	 *
	 * @param details
	 *            The specific details of the request.
	 * @return The Job ID of the request. This can be used to find the
	 *         appropriate {@link JobCallback}.
	 */
	public JobID enumeratePublishedFilesByUserAction(EnumerationUserDetails details) {
		final ClientMsgProtobuf<CMsgClientUCMEnumeratePublishedFilesByUserAction> enumRequest = new ClientMsgProtobuf<CMsgClientUCMEnumeratePublishedFilesByUserAction>(CMsgClientUCMEnumeratePublishedFilesByUserAction.class, EMsg.ClientUCMEnumeratePublishedFilesByUserAction);
		enumRequest.setSourceJobID(getClient().getNextJobID());

		enumRequest.getBody().action = details.userAction.v();
		enumRequest.getBody().appId = details.appId;
		enumRequest.getBody().startIndex = details.startIndex;

		getClient().send(enumRequest);

		return enumRequest.getSourceJobID();
	}

	/**
	 * Enumerates the list of all published files on the Steam workshop. Results
	 * are returned in a {@link PublishedFilesCallback} from a
	 * {@link JobCallback}.
	 *
	 * @param details
	 *            The specific details of the request.
	 * @return The Job ID of the request. This can be used to find the
	 *         appropriate {@link JobCallback}.
	 */
	public JobID enumeratePublishedFiles(EnumerationDetails details) {
		final ClientMsgProtobuf<CMsgCREEnumeratePublishedFiles> enumRequest = new ClientMsgProtobuf<CMsgCREEnumeratePublishedFiles>(CMsgCREEnumeratePublishedFiles.class, EMsg.CREEnumeratePublishedFiles);
		enumRequest.setSourceJobID(getClient().getNextJobID());

		enumRequest.getBody().appId = details.appID;

		enumRequest.getBody().queryType = details.type.v();

		enumRequest.getBody().startIndex = details.startIndex;

		enumRequest.getBody().days = details.days;
		enumRequest.getBody().count = details.count;

		enumRequest.getBody().tags = details.getTags().toArray(new String[details.getTags().size()]);
		enumRequest.getBody().userTags = details.getUserTags().toArray(new String[details.getUserTags().size()]);

		getClient().send(enumRequest);

		return enumRequest.getSourceJobID();
	}

	/**
	 * Handles a client message. This should not be called directly.
	 */
	@Override
	public void handleMsg(IPacketMsg packetMsg) {
		switch (packetMsg.getMsgType()) {
			case CREEnumeratePublishedFilesResponse:
				handleEnumPublishedFiles(packetMsg);
				break;
			case ClientUCMEnumerateUserPublishedFilesResponse:
				handleEnumUserPublishedFiles(packetMsg);
				break;
			case ClientUCMEnumerateUserSubscribedFilesResponse:
				handleEnumUserSubscribedFiles(packetMsg);
				break;
			case ClientUCMEnumeratePublishedFilesByUserActionResponse:
				handleEnumPublishedFilesByAction(packetMsg);
				break;
			//case ClientUCMGetPublishedFileDetailsResponse:
			//	handlePublishedFileDetails(packetMsg);
			//	break;
		}
	}

	void handleEnumPublishedFiles(IPacketMsg packetMsg) {
		final ClientMsgProtobuf<CMsgCREEnumeratePublishedFilesResponse> response = new ClientMsgProtobuf<CMsgCREEnumeratePublishedFilesResponse>(CMsgCREEnumeratePublishedFilesResponse.class, packetMsg);

		final PublishedFilesCallback innerCallback = new PublishedFilesCallback(response.getBody());
		final JobCallback<?> callback = new JobCallback<PublishedFilesCallback>(response.getTargetJobID(), innerCallback);
		getClient().postCallback(callback);
	}

	void handleEnumUserPublishedFiles(IPacketMsg packetMsg) {
		final ClientMsgProtobuf<CMsgClientUCMEnumerateUserPublishedFilesResponse> response = new ClientMsgProtobuf<CMsgClientUCMEnumerateUserPublishedFilesResponse>(CMsgClientUCMEnumerateUserPublishedFilesResponse.class, packetMsg);

		final UserPublishedFilesCallback innerCallback = new UserPublishedFilesCallback(response.getBody());
		final JobCallback<?> callback = new JobCallback<UserPublishedFilesCallback>(response.getTargetJobID(), innerCallback);
		getClient().postCallback(callback);
	}

	void handleEnumUserSubscribedFiles(IPacketMsg packetMsg) {
		final ClientMsgProtobuf<CMsgClientUCMEnumerateUserSubscribedFilesResponse> response = new ClientMsgProtobuf<CMsgClientUCMEnumerateUserSubscribedFilesResponse>(CMsgClientUCMEnumerateUserSubscribedFilesResponse.class, packetMsg);

		final UserSubscribedFilesCallback innerCallback = new UserSubscribedFilesCallback(response.getBody());
		final JobCallback<?> callback = new JobCallback<UserSubscribedFilesCallback>(response.getTargetJobID(), innerCallback);
		getClient().postCallback(callback);
	}

	void handleEnumPublishedFilesByAction(IPacketMsg packetMsg) {
		final ClientMsgProtobuf<CMsgClientUCMEnumeratePublishedFilesByUserActionResponse> response = new ClientMsgProtobuf<CMsgClientUCMEnumeratePublishedFilesByUserActionResponse>(CMsgClientUCMEnumeratePublishedFilesByUserActionResponse.class, packetMsg);

		final UserActionPublishedFilesCallback innerCallback = new UserActionPublishedFilesCallback(response.getBody());
		final JobCallback<?> callback = new JobCallback<UserActionPublishedFilesCallback>(response.getTargetJobID(), innerCallback);
		getClient().postCallback(callback);
	}
}
