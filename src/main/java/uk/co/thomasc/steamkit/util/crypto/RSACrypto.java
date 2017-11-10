package uk.co.thomasc.steamkit.util.crypto;

import uk.co.thomasc.steamkit.util.crypto.asnkeyparser.AsnKeyParser;
import uk.co.thomasc.steamkit.util.crypto.asnkeyparser.BerDecodeException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.List;

public class RSACrypto {
    public Cipher cipher;
    public RSAPublicKey RSAkey;

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
            e.printStackTrace();
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
        } catch (final NoSuchAlgorithmException |
                NoSuchPaddingException |
                InvalidKeyException |
                InvalidKeySpecException |
                NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    public byte[] encrypt(byte[] input) {
        try {
            return cipher.doFinal(input);
        } catch (final IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
