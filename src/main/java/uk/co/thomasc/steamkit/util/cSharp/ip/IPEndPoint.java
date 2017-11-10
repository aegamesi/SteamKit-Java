package uk.co.thomasc.steamkit.util.cSharp.ip;

import java.net.InetAddress;

public class IPEndPoint {
    private final String ipAddress;
    private int port = 0;

    /**
     * Constructor
     *
     * @param ipAddress ip address
     * @param port      port number
     */
    public IPEndPoint(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public IPEndPoint(InetAddress ipAddress, short port) {
        this.ipAddress = ipAddress.getHostAddress();
        this.port = port;
    }

    public static IPEndPoint fromString(String ipAddressAndPort) {
        if (ipAddressAndPort == null) return null;
        String[] components = ipAddressAndPort.split(":");
        if (components.length != 2) return null;
        try {
            String ipAddress = components[0];
            int port = Integer.parseInt(components[1]);
            return new IPEndPoint(ipAddress, port);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        final StringBuffer result = new StringBuffer();
        result.append("[").append(ipAddress).append(":").append(port).append("]");
        return result.toString();
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public int getPort() {
        return this.port;
    }
}
