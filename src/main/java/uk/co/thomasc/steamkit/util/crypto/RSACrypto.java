package uk.co.thomasc.steamkit.util.crypto;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import uk.co.thomasc.steamkit.util.crypto.asnkeyparser.AsnKeyParser;
import uk.co.thomasc.steamkit.util.crypto.asnkeyparser.BerDecodeException;

public class RSACrypto {
	Cipher cipher;
	RSAPublicKey RSAkey;

	public RSACrypto(byte[] key) {
		try {
			final List<Byte> list = new ArrayList<Byte>();
			for (final byte b : key) {
				list.add(b);
			}
			final AsnKeyParser keyParser = new AsnKeyParser(list);
			final BigInteger[] keys = keyParser.parseRSAPublicKey();
			init(keys[0], keys[1], true);
		} catch (final BerDecodeException e) {
			uk.co.thomasc.steamkit.util.logging.DebugLog.writeLine("NEW_EX", "Exception: %s", e);
		}
	}

	public RSACrypto(BigInteger mod, BigInteger exp) {
		this(mod, exp, true);
	}

	public RSACrypto(BigInteger mod, BigInteger exp, boolean oaep) {
		init(mod, exp, oaep);
	}

	private void init(BigInteger mod, BigInteger exp, boolean oaep) {
		try {
			Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);

			final RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(mod, exp);

			final KeyFactory factory = KeyFactory.getInstance("RSA", "SC");
			RSAkey = (RSAPublicKey) factory.generatePublic(publicKeySpec);

			if (oaep) {
				cipher = Cipher.getInstance("RSA/None/OAEPWithSHA1AndMGF1Padding", "SC");
			} else {
				cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "SC");
			}
			cipher.init(Cipher.ENCRYPT_MODE, RSAkey);
		} catch (final NoSuchAlgorithmException e) {
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			e.printStackTrace(printWriter);
			String s = writer.toString();
			uk.co.thomasc.steamkit.util.logging.DebugLog.writeLine("NEW_EX", "Exception: %s", s);
		} catch (final NoSuchPaddingException e) {
			uk.co.thomasc.steamkit.util.logging.DebugLog.writeLine("NEW_EX", "Exception: %s", e);
		} catch (final InvalidKeyException e) {
			uk.co.thomasc.steamkit.util.logging.DebugLog.writeLine("NEW_EX", "Exception: %s", e);
		} catch (final InvalidKeySpecException e) {
			uk.co.thomasc.steamkit.util.logging.DebugLog.writeLine("NEW_EX", "Exception: %s", e);
		} catch (final NoSuchProviderException e) {
			uk.co.thomasc.steamkit.util.logging.DebugLog.writeLine("NEW_EX", "Exception: %s", e);
		}
	}

	public byte[] encrypt(byte[] input) {
		try {
			return cipher.doFinal(input);
		} catch (final IllegalBlockSizeException e) {
			uk.co.thomasc.steamkit.util.logging.DebugLog.writeLine("NEW_EX", "Exception: %s", e);
		} catch (final BadPaddingException e) {
			uk.co.thomasc.steamkit.util.logging.DebugLog.writeLine("NEW_EX", "Exception: %s", e);
		}
		return null;
	}
}
