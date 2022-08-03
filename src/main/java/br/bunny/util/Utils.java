package br.bunny.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class Utils {

    public static String removeAccentsWord(String word) {
        String normalizer = Normalizer.normalize(word, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizer).replaceAll("");
    }
}
