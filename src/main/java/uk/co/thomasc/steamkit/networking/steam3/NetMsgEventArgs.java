package uk.co.thomasc.steamkit.networking.steam3;

import uk.co.thomasc.steamkit.util.cSharp.events.EventArgs;
import uk.co.thomasc.steamkit.util.cSharp.ip.IPEndPoint;

/**
 * Represents data that has been received over the network.
 */
public class NetMsgEventArgs extends EventArgs {
    private final byte[] data;
    private final IPEndPoint endPoint;

    public NetMsgEventArgs(byte[] data, IPEndPoint endPoint) {
        this.data = data;
        this.endPoint = endPoint;
    }

    public byte[] getData() {
        return this.data;
    }

    public IPEndPoint getEndPoint() {
        return this.endPoint;
    }
}
