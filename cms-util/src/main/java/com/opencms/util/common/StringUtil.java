package com.opencms.util.common;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-12
 * Time: 23:16:54
 * To change this template use File | Settings | File Templates.
 */
public class StringUtil {

    public static String getCnASCII(String cnStr) {
        StringBuffer strBuf = new StringBuffer();
        char[] bGBK = cnStr.toCharArray();
        for (int i = 0; i < bGBK.length; i++) {
            if(java.lang.Character.toString(bGBK[i]).matches("[\\u4E00-\\u9FA5]+")){
                strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
            } else{
                strBuf.append(bGBK[i]);    
            }

        }
        return strBuf.toString();
    }

    public static void main(String[] args) {
        System.out.println(getCnASCII("中国lijing"));    
    }

}
