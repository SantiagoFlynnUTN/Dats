package sample.Model;

import java.util.Arrays;

public class StringUtils {

    public static boolean compareIgnoreCaseAndSpecialCharacters(String str1, String str2){

        return Arrays.stream(removeSpecialCharacters(str2.toLowerCase()).split(" ")).allMatch(s -> removeSpecialCharacters(str1.toLowerCase()).contains(s));
    }

    private static String removeSpecialCharacters(String s) {
        s = s.replace("á", "a");
        s = s.replace("é", "e");
        s = s.replace("í", "i");
        s = s.replace("ó", "o");
        s = s.replace("ú", "u");
        s = s.replace("ñ", "n");

        return s;
    }
}
