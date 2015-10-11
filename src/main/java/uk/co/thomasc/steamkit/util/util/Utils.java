package uk.co.thomasc.steamkit.util.util;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import uk.co.thomasc.steamkit.base.generated.steamlanguage.EOSType;
import uk.co.thomasc.steamkit.util.crypto.CryptoHelper;

public class Utils {

	public static EOSType getOSType() {
		return EOSType.Win7;
	}

	private static byte[] generateMachineID() {
		// Java can't really do much here :/
		// TODO: Make this better?

		try {
			final Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			do {
				final NetworkInterface n = interfaces.nextElement();
				if (n.getHardwareAddress() != null && n.getHardwareAddress().length > 0) {
					return CryptoHelper.SHAHash(n.getHardwareAddress());
				}
			} while (NetworkInterface.getNetworkInterfaces().hasMoreElements());
		} catch (final SocketException e) {
			uk.co.thomasc.steamkit.util.logging.DebugLog.writeLine("NEW_EX", "Exception: %s", e);
		}
		return null;
	}

}
