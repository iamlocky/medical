package model;
;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;



import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/**
 *
 * @Project : NettyDemo
 * @Package : com.sondon.NettyDemo
 * @Class : AESTool.java
 * @Company 广州讯动网络科技有限公司
 * @Author : 蔡文锋
 * @DateTime：2015年4月16日 上午9:38:31
 * @Blog：http://blog.csdn.net/caiwenfeng_for_23
 * @Description : {
 *     AESTool工具
 *    }
 */
public class AESUtil {

    private byte[] initVector = {0x32, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31,
            0x38, 0x27, 0x36, 0x35, 0x33, 0x23, 0x32, 0x31};

    /**
     * FIXME For demo only, should rewrite this method in your product environment!
     *
     * @param appid
     * @return
     */
    public static String findKeyById(String appid) {
        // Fake key.
        String key = "12345678901234567890123456789012";
        return key;
    }

    // 加密
    public static String encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());

        return new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    // 解密
    public static String decrypt(String sSrc, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("UTF-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec("0102030405060708"
                    .getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original);
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
}