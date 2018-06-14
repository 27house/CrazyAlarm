package com.sunn.xhui.crazyalarm.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 处理字符串
 *
 * @author XHui.sun
 *         created at 2017/9/15 0015  11:02
 */
@SuppressWarnings("unused")
public class StringUtils {

    public static List<String> splitString(@NonNull String splitChar, @NonNull String str) {
        //split后长度为1有一个空串对象
        String[] tmpHistory = str.split(splitChar);
        List<String> stringList = new ArrayList<>(Arrays.asList(tmpHistory));
        //如果没有搜索记录，split之后第0位是个空串的情况下
        if (stringList.size() == 1 && "".equals(stringList.get(0))) {
            //清空集合，这个很关键
            stringList.clear();
        }
        return stringList;
    }

    public static String appendString(@NonNull String splitChar, @NonNull String inputText, List<String> oldList) {
        if (oldList.size() > 0) {
            //移除之前重复添加的元素
            for (int i = 0; i < oldList.size(); i++) {
                if (inputText.equals(oldList.get(i))) {
                    oldList.remove(i);
                    break;
                }
            }
            if (!TextUtils.isEmpty(inputText)) {
                //将新输入的文字添加集合的第0位也就是最前面
                oldList.add(0, inputText);
            }
            if (oldList.size() > 10) {
                //最多保存10条搜索记录 删除最早搜索的那一项
                oldList.remove(oldList.size() - 1);
            }
            //逗号拼接
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < oldList.size(); i++) {
                sb.append(oldList.get(i)).append(splitChar);
            }
            return sb.toString();
        } else {
            //之前未添加过
            return inputText + splitChar;
        }
    }

    private final static char KOREAN_UNICODE_START = '가';
    private final static char KOREAN_UNICODE_END = '힣';
    private final static char KOREAN_UNIT = '까' - '가';
    private final static char[] KOREAN_INITIAL = {'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ'
            , 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};

    private final static char[] SPECIAL_CHAR = {'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+', '。', '，', '！', '!',
            '{', '}', '[', ']', '|', '\\', '"', '?', '/', ':', ';', '<', '>', ',', '.', '"'};

    public static String getURLEncoderString(String content) throws UnsupportedEncodingException {

        StringBuilder newContent = new StringBuilder();
        char[] chars = content.toCharArray();
        for (char aChar : chars) {
            if (isChinese(aChar)) {
                newContent.append(URLEncoder.encode(String.valueOf(aChar), "UTF-8"));
            } else {
                newContent.append(aChar);
            }
        }

        return newContent.toString();
    }

    public static boolean match(String value, String keyword) {
        if (value == null || keyword == null) {
            return false;
        }
        if (keyword.length() > value.length()) {
            return false;
        }

        int i = 0, j = 0;
        do {
            if (isKorean(value.charAt(i)) && isInitialSound(keyword.charAt(j))) {
                if (keyword.charAt(j) == getInitialSound(value.charAt(i))) {
                    i++;
                    j++;
                } else if (j > 0) {
                    break;
                } else {
                    i++;
                }
            } else {
                if (keyword.charAt(j) == value.charAt(i)) {
                    i++;
                    j++;
                } else if (j > 0) {
                    break;
                } else {
                    i++;
                }
            }
        } while (i < value.length() && j < keyword.length());

        return j == keyword.length();
    }

    private static boolean isKorean(char c) {
        return c >= KOREAN_UNICODE_START && c <= KOREAN_UNICODE_END;
    }

    private static boolean isInitialSound(char c) {
        for (char i : KOREAN_INITIAL) {
            if (c == i) {
                return true;
            }
        }
        return false;
    }

    private static char getInitialSound(char c) {
        return KOREAN_INITIAL[(c - KOREAN_UNICODE_START) / KOREAN_UNIT];
    }

    /**
     * 判断是否为特殊字符
     *
     * @param c 首字母
     * @return boolean
     */
    public static boolean isSpecialChar(char c) {
        for (char cc : SPECIAL_CHAR) {
            if (cc == c) {
                return true;
            }
        }
        return false;
    }

    // 完整的判断中文汉字和符号
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (char c : ch) {
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据Unicode编码完美的判断中文汉字和符号
     *
     * @param c char
     * @return isChinese
     */
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION;
    }

    public static String unescapeJava(String unicodeStr) {
        StringBuilder retBuf = new StringBuilder();
        try {
            if (unicodeStr == null) {
                return null;
            }
            int maxLoop = unicodeStr.length();
            for (int i = 0; i < maxLoop; i++) {
                if (unicodeStr.charAt(i) == '\\') {
                    if ((i < maxLoop - 5)
                            && ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr
                            .charAt(i + 1) == 'U')))
                        try {
                            retBuf.append((char) Integer.parseInt(
                                    unicodeStr.substring(i + 2, i + 6), 16));
                            i += 5;
                        } catch (NumberFormatException localNumberFormatException) {
                            retBuf.append(unicodeStr.charAt(i));
                        }
                    else
                        retBuf.append(unicodeStr.charAt(i));
                } else {
                    retBuf.append(unicodeStr.charAt(i));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return retBuf.toString();
    }
}
