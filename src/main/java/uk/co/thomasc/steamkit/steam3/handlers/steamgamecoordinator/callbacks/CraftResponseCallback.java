package uk.co.thomasc.steamkit.steam3.handlers.steamgamecoordinator.callbacks;

import uk.co.thomasc.steamkit.base.gc.tf2.GCMsgCraftItemResponse;
import uk.co.thomasc.steamkit.steam3.steamclient.callbackmgr.CallbackMsg;

import java.util.HashSet;
import java.util.Set;

/**
 * This callback is fired in response to an attempt to craft some items.
 */
public final class CraftResponseCallback extends CallbackMsg {
    /**
     * Gets the result of ignoring a friend.
     */
    private final Set<Long> items = new HashSet<Long>();

    public CraftResponseCallback(GCMsgCraftItemResponse response) {
        for (Long item : response.items) {
            items.add(item);
        }
    }

    /**
     * Gets the result of ignoring a friend.
     */
    public Set<Long> getItems() {
        return this.items;
    }
}
