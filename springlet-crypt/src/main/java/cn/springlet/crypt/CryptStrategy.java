package cn.springlet.crypt;

/**
 * @author watermelon
 * @since 2022/7/11
 */
public interface CryptStrategy {

    String encrypt(String str);

    String encrypt(String str, String privateKey, String publicKey);

    String decrypt(String cipherStr);

    String decrypt(String cipherStr, String privateKey, String publicKey);
}
