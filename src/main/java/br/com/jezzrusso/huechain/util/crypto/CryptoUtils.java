package br.com.jezzrusso.huechain.util.crypto;

import java.math.BigInteger;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import org.apache.commons.codec.digest.DigestUtils;

import br.com.jezzrusso.huechain.util.crypto.beans.Signature;
import br.com.jezzrusso.huechain.util.crypto.infra.Secp256k1;

public final class CryptoUtils {

	private static Encoder encoder = Base64.getUrlEncoder().withoutPadding();
	private static Decoder decoder = Base64.getUrlDecoder();

	private CryptoUtils() {

	}

	public static String hash(Long timestamp, String lastHash, String data, Long nonce, Integer difficulty) {
		return DigestUtils.sha256Hex(timestamp.toString().concat(lastHash).concat(data).concat(nonce.toString())
				.concat(difficulty.toString()));
	}

	public static String hash(String data) {
		return DigestUtils.sha256Hex(data);
	}

	public static String generatePrivateKey() {
		return encoder.encodeToString(Secp256k1.generatePrivateKey());
	}

	public static String getPublicKey(final String privateKey) {
		return encoder.encodeToString(Secp256k1.getPublicKey(decoder.decode(privateKey)));
	}

	public static Signature signTransaction(final String data, final String privateKey) {
		byte[][] signTransaction = Secp256k1.signTransaction(data.getBytes(), decoder.decode(privateKey));

		BigInteger r = new BigInteger(signTransaction[0]);
		BigInteger s = new BigInteger(signTransaction[1]);
		Integer recoveryParam = new Byte(signTransaction[2][0]).intValue();

		return new Signature(encoder.encodeToString(r.toByteArray()), encoder.encodeToString(s.toByteArray()),
				recoveryParam);

	}

	public static Boolean verifySignature(Signature signature, String publicKey, String data) {

		return Secp256k1.verifySignature(decoder.decode(signature.getR()), decoder.decode(signature.getS()),
				decoder.decode(publicKey), data.getBytes());
	}

}
