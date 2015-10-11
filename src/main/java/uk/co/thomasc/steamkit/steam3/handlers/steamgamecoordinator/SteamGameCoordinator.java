package uk.co.thomasc.steamkit.steam3.handlers.steamgamecoordinator;

import java.io.IOException;

import uk.co.thomasc.steamkit.base.ClientMsgProtobuf;
import uk.co.thomasc.steamkit.base.IPacketMsg;
import uk.co.thomasc.steamkit.base.gc.ClientGCMsg;
import uk.co.thomasc.steamkit.base.gc.EGCMsgBase;
import uk.co.thomasc.steamkit.base.gc.IClientGCMsg;
import uk.co.thomasc.steamkit.base.gc.tf2.ECraftingRecipe;
import uk.co.thomasc.steamkit.base.gc.tf2.GCMsgCraftItem;
import uk.co.thomasc.steamkit.base.gc.tf2.GCMsgCraftItemResponse;
import uk.co.thomasc.steamkit.base.generated.SteammessagesClientserver2.CMsgGCClient;
import uk.co.thomasc.steamkit.base.generated.steamlanguage.EMsg;
import uk.co.thomasc.steamkit.steam3.handlers.ClientMsgHandler;
import uk.co.thomasc.steamkit.steam3.handlers.steamgamecoordinator.callbacks.CraftResponseCallback;
import uk.co.thomasc.steamkit.steam3.handlers.steamgamecoordinator.callbacks.MessageCallback;
import uk.co.thomasc.steamkit.util.util.MsgUtil;

/**
 * This handler handles all game coordinator messaging.
 */
public final class SteamGameCoordinator extends ClientMsgHandler {

	/**
	 * Tells the game coordinator to craft items together
	 * 
	 * @param recipe
	 *            The crafting recipe to use
	 * @param items
	 *            The items to craft together
	 */
	public void craft(int appId, ECraftingRecipe recipe, long... items) {
		final ClientGCMsg<GCMsgCraftItem> msg = new ClientGCMsg<GCMsgCraftItem>(GCMsgCraftItem.class);

		msg.getBody().recipe = recipe;
		for (long item : items) {
			msg.getBody().items.add(item);
		}

		send(msg, appId);
	}

	public void craft(ECraftingRecipe recipe, long... items) {
		craft(440, recipe, items);
	}

	/**
	 * Sends a game coordinator message for a specific appid.
	 * 
	 * @param msg
	 *            The GC message to send.
	 * @param appId
	 *            The app id of the game coordinator to send to.
	 */
	public void send(IClientGCMsg msg, int appId) {
		final ClientMsgProtobuf<CMsgGCClient> clientMsg = new ClientMsgProtobuf<CMsgGCClient>(CMsgGCClient.class, EMsg.ClientToGC);

		clientMsg.getBody().msgtype = MsgUtil.makeGCMsg(msg.getMsgType(), msg.isProto());
		clientMsg.getBody().appid = appId;

		try {
			clientMsg.getBody().payload = msg.serialize();

			getClient().send(clientMsg);
		} catch (final IOException e) {
		}
	}

	/**
	 * Handles a client message. This should not be called directly.
	 * 
	 * @param packetMsg
	 *            The packet message that contains the data.
	 */
	@Override
	public void handleMsg(IPacketMsg packetMsg) {
		if (packetMsg.getMsgType() == EMsg.ClientFromGC) {
			final ClientMsgProtobuf<CMsgGCClient> msg = new ClientMsgProtobuf<CMsgGCClient>(CMsgGCClient.class, packetMsg);

			final MessageCallback callback = new MessageCallback(msg.getBody());
			getClient().postCallback(callback);

			if (callback.getEMsg() == EGCMsgBase.CraftResponse) {
				final ClientGCMsg<GCMsgCraftItemResponse> craftMsg = new ClientGCMsg<GCMsgCraftItemResponse>(GCMsgCraftItemResponse.class);
				try {
					craftMsg.deSerialize(msg.getBody().payload);

					final CraftResponseCallback craftCallback = new CraftResponseCallback(craftMsg.getBody());
					getClient().postCallback(craftCallback);
				} catch (IOException e) {
					uk.co.thomasc.steamkit.util.logging.DebugLog.writeLine("NEW_EX", "Exception: %s", e);
				}
			}
		}
	}
}
