package cn.springlet.crypt.strategy;

import cn.springlet.crypt.CryptStrategy;
import cn.springlet.crypt.rsa.RSAUtil;

/**
 * rsa
 *
 * @author watermelon
 * @since 2022/7/11
 */
public class RSAStrategy implements CryptStrategy {
    @Override
    public String encrypt(String str) {
        return RSAUtil.encrypt(str);
    }

    @Override
    public String decrypt(String cipherStr) {
        return RSAUtil.decrypt(cipherStr);
    }

    @Override
    public String encrypt(String str, String privateKey, String publicKey) {
        return RSAUtil.encrypt(str, privateKey, publicKey);
    }

    @Override
    public String decrypt(String cipherStr, String privateKey, String publicKey) {
        return RSAUtil.decrypt(cipherStr, privateKey, publicKey);
    }
}
