package cn.springlet.crypt.rsa;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * rsa 解密
 *
 * @author watermelon
 * @time 2021/11/29
 */
@Slf4j
public class RSAUtil {

    /**
     * 解密 秘钥 私钥
     */
    private static final String PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCgMEl9wljclROopLnF8WPh2CTrZwtCqckeHOdjpdxmSa5pNXfxqQwwp62bLmX6ug7VzByEIHlVtu5G99Dqu19GajYj4yJD4eseHpmjHuVUvecnAhqOgrawCZplobPYO/gQ7s20HFE9X/J+MhHK0S8gUUD79ZR/8L6rKiKyCUa0rny/pzu0NIHaVIgTKfnk/m3s0E0IWHC4fT3XPUOIeS0oAC2LJpVbEb/hdPQu1lOPmsvkjET0WBLKS4X7eF4g+UeTsQQdiHhEjR4AdJhE3pMgJmAOyIvlpk7A3zvVYPELN4WHu2VSyhvuE58aL6K49KcRtYWq7pD3BmafLnDDDm1xAgMBAAECggEAfbwJI2YV++YjzR6nFuDXZqESsZdrNkMwsqs7UHMS4VL6TGiEojHveI1U7lZoA3phRJ120Jrwwj/ZHelMmcLDEPEzV83jymac90JLk8cfePmqY2r6T1v4a4rOHvHiyVqg5yGaYsJLXiJeo4FHh+vT0skUefCURCxCbPWg0CAJ2K1S3nauVlhGFCrgUXhvIK+P5c8SHnVnlBNRNr9muSyTh/dEVdswjKo4/cqA50gMsE1ulCYO3rdFX3TV+8U5F19stiWHvgOL2uh8K6sc4gU4kaQssMIogO1C58qdmXcBEvX+7bWDymmNutTDkn3rWdSsLrP6wR16dEWLnWpfrWI08QKBgQDTFfKkBj7GpLCxvWvzKC7+d9xiksqCq2PSwvAy3SNpen7qzSqIC+vGK+c53yXunHrJFd+rZVyZmn4lYOM/dcvfdN2QVzJTRlpoDnRbfsBizAHE3BGXbmhvKEQ0cKKq8rt6Ief554xy/6pVNPzDOMGcNZZ01MThdSgMK70CsMgrPQKBgQDCRewgiQQG4ZIodObgRJzUpg/R4yBEEaIrC55reVa/CCtPDHALQnG8TFYe/QxbSxw7/YFu1K21f8twdMjNXk04tzeryxj/e3TwR6vXhlpAy/7AtPZcNQEwnU+SdWMLp+i6frs9rLIviSkQXqtkPLtZypukYGp5B6x6iP3g30o+RQKBgQDFo30mWrqYX0cZhFP4vZ0SocN1gB+grTyv79napfcsIYeH7UVvUEoXqK+9HdR0T4sfkB12a1eCcoa+FLNxjaevt8pkBWCXW/32A+KqcElNt9nIKo1GDhg2S6tgtj14dgFXXzvGXyi/2+XuFci8OP3+dqZDCZy1xG4UxzcXt9ITuQKBgDJiehQSFQZH2GU/tGUzxkzsaKvxsPuWay2ozMRo6tOOpdTRj/mLmP3R+n/89whyWXdBjyEUbZzl8PhQgYepznNIKVsXyHryJOjwXaP/B0dI4OBPzpkpcg6OrgV5BmAOgzBjD+K1+FyVwR2qv962VYy7HFSsYNDU6am5/8YjgxjVAoGBAKHYgCLqvPPgIEsG6buMWcivUKrb4t5Bn5Fd6J0ydCJEV07xcBAwlvdL65tQB4R3u4xEYz1wtvn/DlRf/6WtV7HlblShIOGIRd4UiPyS61h6mvK8DUWXPpWRtfSwXPn6F80WYRkMQ6+Y6+6Xf6sThWyctE3KnksEcN/PCfaBzTEm";
    private static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoDBJfcJY3JUTqKS5xfFj4dgk62cLQqnJHhznY6XcZkmuaTV38akMMKetmy5l+roO1cwchCB5VbbuRvfQ6rtfRmo2I+MiQ+HrHh6Zox7lVL3nJwIajoK2sAmaZaGz2Dv4EO7NtBxRPV/yfjIRytEvIFFA+/WUf/C+qyoisglGtK58v6c7tDSB2lSIEyn55P5t7NBNCFhwuH091z1DiHktKAAtiyaVWxG/4XT0LtZTj5rL5IxE9FgSykuF+3heIPlHk7EEHYh4RI0eAHSYRN6TICZgDsiL5aZOwN871WDxCzeFh7tlUsob7hOfGi+iuPSnEbWFqu6Q9wZmny5www5tcQIDAQAB";


    public static volatile RSA rsa;

    private static void init() {
        if (rsa == null) {
            synchronized (RSAUtil.class) {
                if (rsa == null) {
                    rsa = SpringUtil.getBean(RSAConfig.RSA_BEAN_NAME);
                }
            }
        }
    }

    /**
     * 加密
     *
     * @param text
     * @return
     */
    public static String encrypt(String text) {
        init();

        log.debug("encrypt：明文：{}", text);
        //使用 公钥 加密
        String encryptStr = rsa.encryptBase64(text, KeyType.PublicKey);
        log.debug("encrypt：密文：{}", encryptStr);
        return encryptStr;
    }

    /**
     * 解密
     *
     * @param cipherText
     * @return
     */
    public static String decrypt(String cipherText) {
        init();
        log.debug("decrypt：密文：{}", cipherText);
        //使用 私钥 解密
        String decryptStr = rsa.decryptStr(cipherText, KeyType.PrivateKey, CharsetUtil.CHARSET_UTF_8);
        log.debug("decrypt：明文：{}", decryptStr);
        return decryptStr;
    }

    /**
     * 加密
     *
     * @param text
     * @return
     */
    public static String encrypt(String text, String privateKey, String publicKey) {
        RSA rsa_ = new RSA(privateKey, publicKey);
        log.debug("encrypt：明文：{}", text);
        //使用 公钥 加密
        String encryptStr = rsa_.encryptBase64(text, KeyType.PublicKey);
        log.debug("encrypt：密文：{}", encryptStr);
        return encryptStr;
    }

    /**
     * 解密
     *
     * @param cipherText
     * @return
     */
    public static String decrypt(String cipherText, String privateKey, String publicKey) {
        RSA rsa_ = new RSA(privateKey, publicKey);
        log.debug("decrypt：密文：{}", cipherText);
        //使用 私钥 解密
        String decryptStr = rsa_.decryptStr(cipherText, KeyType.PrivateKey, CharsetUtil.CHARSET_UTF_8);
        log.debug("decrypt：明文：{}", decryptStr);
        return decryptStr;
    }


    public static void main(String[] args) {
        String str = "111111";
        decrypt(encrypt(str));
    }
}
