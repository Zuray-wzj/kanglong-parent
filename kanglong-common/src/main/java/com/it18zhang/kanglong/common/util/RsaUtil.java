package com.it18zhang.kanglong.common.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA工具类
 */
public class RsaUtil {
	//公钥串
	public static String publicKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgba/4/7yqyiqOpjk98Bito4P511oln/BHrUVFvuKMbTHQYcnG1T695S35A8wtgxMCj4PNlu253MrvfcqVFInvF046zONnoT7tWDT73vPxVIe2PzQ0F7MOKpRC8PTJERm9Idq87LheswrdHq3OmV/XukXuj/VFG6R78r6ZYHJWmwIDAQAB";

	//私钥串
	public static String privateKeyString = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAKBtr/j/vKrKKo6mOT3wGK2jg/nXWiWf8EetRUW+4oxtMdBhycbVPr3lLfkDzC2DEwKPg82W7bncyu99ypUUie8XTjrM42ehPu1YNPve8/FUh7Y/NDQXsw4qlELw9MkRGb0h2rzsuF6zCt0erc6ZX9e6Re6P9UUbpHvyvplgclabAgMBAAECgYEAkTv7rQ3H/U3cTRvKgnoTvU5ksg/1ek5atmj0Z3mxmrNX/3AnvStcC4monuCDPUiGMzE9fyd9u19ZEIrulRvs6ec5X3BWSmDQTBzcb4aNE4V8w9boLigYS1DIys/s64NyZgE0iN0TgowmPt/VIbl6xOdimxMbbPmP6aVAbNo8NnECQQDhy0x00dCtilx0viv5DKGlAxFRhDx06ll3n+7A/DBSJgIJpmkEYe5mBeD3UIaWGrv3UPwMoMDWqHUlj48PLPR/AkEAtePUv+TOm9ftLZutL2ZtKYg+6NrKu/9/lB66cjsmvMTmW2AqlOU+mpjXgMs+KeT0ZpzPatHUAyLUQJjXBz/f5QJBAKsHG6WM27znhbqj5ZjimXntBewx9r00WYpD6UviehNvWLHIi9lW+IXsxAdwxlDidSgS/qfuB8NQnFNUFy+OhK8CQQCzIZjiD5YG12XHN/0cpHQ3pe3G58sn0R21I3odi/G/kO8I6LGnCApauNHsDr2/BrRH5oFDwzhYilGc+Kk0g4ydAkEAy0NFqE27qGZy0lp9WDa+2XPB3cREJoFijLHXoi9OHmDj168T+0NteYl/tsw5Nu6O5oylT4QlDlgm+Y4qt0lpCw==";

	/**
	 * 生成公私秘钥对
	 */
	public static void genKeyPair() {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(1024, new SecureRandom());
			KeyPair keyPair = keyPairGen.generateKeyPair();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥

			String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
			String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
			System.out.println("publicKey:" + publicKeyString);
			System.out.println("privateKey:" + privateKeyString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 使用钥匙(公钥或私钥)加密
	 */
	public static String encryptWithKey(String src, String base64Key, boolean pub) {
		try {
			//对base64key进行解码
			byte[] decoded = Base64.decodeBase64(base64Key);
			//恢复成
			//如果是公钥
			if (pub) {
				RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(
						new X509EncodedKeySpec(decoded));
				Cipher cipher = Cipher.getInstance("RSA");
				cipher.init(Cipher.ENCRYPT_MODE, pubKey);
				String encBase64String = Base64.encodeBase64String(cipher.doFinal(src.getBytes("UTF-8")));
				return encBase64String;
			}
			//私钥加密
			else {
				RSAPrivateKey privateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(
						new PKCS8EncodedKeySpec(decoded));
				Cipher cipher = Cipher.getInstance("RSA");
				cipher.init(Cipher.ENCRYPT_MODE, privateKey);
				String encBase64String = Base64.encodeBase64String(cipher.doFinal(src.getBytes("UTF-8")));
				return encBase64String;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 */
	public static String decryptWithKey(String encbase64Str, String base64Key, boolean pub) {
		try {
			//解码64密文
			byte[] inputByte = Base64.decodeBase64(encbase64Str.getBytes("UTF-8"));
			//base64编码的私钥
			byte[] decoded2 = Base64.decodeBase64(base64Key);
			//公钥
			if (pub) {
				RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded2));
				//RSA解密
				Cipher cipher2 = Cipher.getInstance("RSA");
				cipher2.init(Cipher.DECRYPT_MODE, pubKey);
				String srcMsg = new String(cipher2.doFinal(inputByte));
				return srcMsg;
			} else {
				RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded2));
				//RSA解密
				Cipher cipher2 = Cipher.getInstance("RSA");
				cipher2.init(Cipher.DECRYPT_MODE, priKey);
				String srcMsg = new String(cipher2.doFinal(inputByte));

				return srcMsg;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
