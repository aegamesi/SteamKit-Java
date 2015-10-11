package uk.co.thomasc.steamkit.util.cSharp.ip;

import java.net.InetAddress;

import lombok.Getter;

public class IPEndPoint {

	@Getter
	private final String ipAddress;
	@Getter
	private int port = 0;

	/**
	 * Constructor
	 * 
	 * @param ipAddress
	 *            ip address
	 * @param port
	 *            port number
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
		if(ipAddressAndPort == null)
			return null;

		String[] components = ipAddressAndPort.split(":");

		if(components.length != 2)
			return null;

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
}
