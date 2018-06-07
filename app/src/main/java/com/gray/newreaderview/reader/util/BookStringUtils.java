package com.gray.newreaderview.reader.util;

/**
 * @author wjy on 2018/6/7.
 */
public class BookStringUtils {

    //文章和段落格式化
    private static String formattingJudgment(String word) {
        int i = word.lastIndexOf("\n");
        if (i != word.length() - 1) {
            word += "\n";
            return word;
        } else if (i > -1) {
            word = word.substring(0, word.length() - 1);
            return formattingJudgment(word);
        } else {
            return word;
        }
    }

    public static String formateContent(String content) {
        return formattingJudgment(content.replace("\r", "")
                .replaceAll(" ", "")
                .replace("\n", "\n  "));
    }


}
