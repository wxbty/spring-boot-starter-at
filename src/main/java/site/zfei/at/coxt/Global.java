package site.zfei.at.coxt;

import java.util.HashSet;
import java.util.Set;

public class Global {

    public static boolean printCurl = true;
    public static boolean printResult = true;

    public static Set<String> excludeCurlPaths = new HashSet<>();

    public static boolean isPrintCurl(String path) {
        return printCurl && !excludeCurlPaths.contains(path);
    }
}
