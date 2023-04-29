package com.liu.getOffBusReminderFinal.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author liushuaibiao
 * @date 2023/4/27 17:57
 */
public class MD5Utils {

    /**
     * 对字符串进行md5加密
     * @param strValue
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getMD5Str(String strValue) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        String newStr = Base64.getEncoder().encodeToString(strValue.getBytes());
        return newStr;
    }


    public static void main(String[] args) {
        String liu = null;
        try {
            liu = getMD5Str("liu");
            System.out.println(liu);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
