package uk.co.thomasc.steamkit.steam3.handlers.steammasterserver;

import uk.co.thomasc.steamkit.base.ClientMsgProtobuf;
import uk.co.thomasc.steamkit.base.IPacketMsg;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgClientGMSServerQuery;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgGMSClientServerQueryResponse;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EMsg;
import uk.co.thomasc.steamkit.steam3.handlers.ClientMsgHandler;
import uk.co.thomasc.steamkit.steam3.handlers.steammasterserver.callbacks.QueryCallback;
import uk.co.thomasc.steamkit.steam3.handlers.steammasterserver.types.QueryDetails;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.JobCallback;
import uk.co.thomasc.steamkit.types.JobID;
import uk.co.thomasc.steamkit.util.util.NetHelpers;

/**
 * This handler is used for requesting server list details from Steam.
 */
public final class SteamMasterServer extends ClientMsgHandler {
	/**
	 * Requests a list of servers from the Steam game master server. Results are
	 * returned in a {@link QueryCallback} from a {@link JobCallback}.
	 *
	 * @param details
	 *            The details for the request.
	 * @return The Job ID of the request. This can be used to find the
	 *         appropriate {@link JobCallback}.
	 */
	public JobID serverQuery(QueryDetails details) {
		final ClientMsgProtobuf<CMsgClientGMSServerQuery> query = new ClientMsgProtobuf<CMsgClientGMSServerQuery>(CMsgClientGMSServerQuery.class, EMsg.ClientGMSServerQuery);
		query.setSourceJobID(getClient().getNextJobID());

		query.getBody().appId = details.appId;

		if (details.geoLocatedIP != null) {
			query.getBody().geoLocationIp = (int) NetHelpers.getIPAddress(details.geoLocatedIP);
		}

		query.getBody().filterText = details.filter;
		query.getBody().regionCode = details.region.v();

		query.getBody().maxServers = details.maxServers;

		getClient().send(query);

		return query.getSourceJobID();
	}

	/**
	 * Handles a client message. This should not be called directly.
	 */
	@Override
	public void handleMsg(IPacketMsg packetMsg) {
		switch (packetMsg.getMsgType()) {
			case GMSClientServerQueryResponse:
				handleServerQueryResponse(packetMsg);
				break;
		}
	}

	void handleServerQueryResponse(IPacketMsg packetMsg) {
		final ClientMsgProtobuf<CMsgGMSClientServerQueryResponse> queryResponse = new ClientMsgProtobuf<CMsgGMSClientServerQueryResponse>(CMsgGMSClientServerQueryResponse.class, packetMsg);

		final QueryCallback innerCallback = new QueryCallback(queryResponse.getBody());
		final JobCallback<?> callback = new JobCallback<QueryCallback>(queryResponse.getTargetJobID(), innerCallback);
		getClient().postCallback(callback);
	}

}
