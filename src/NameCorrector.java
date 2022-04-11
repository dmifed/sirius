import java.util.regex.Pattern;

/**
 * @author dmifed
 */
public class NameCorrector {
    public static String correct(String s){
        int startIndex = s.indexOf("_") + 1;
        String res = s.substring(startIndex, s.length()-4);
        res = res.replaceAll("Евро-Керамика ", "");
        res = res.replaceAll("-2", " 2");
        res = res.replaceAll("140х103_", "");
        res = res.replaceAll("[_]", " ");
        return res;

    }
}
