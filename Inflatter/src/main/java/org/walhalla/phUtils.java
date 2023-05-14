package com.walhalla.sdk.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

//import okhttp3.HttpUrl;

/**
 * Created by combo on 20.06.2017.
 */

public class phUtils {


    public static String allupperCase(String inputVal) {
        if (inputVal.length() == 0) return inputVal;
        if (inputVal.length() == 1) return inputVal.toUpperCase();

        StringBuilder sb = new StringBuilder();
        for (char c : inputVal.toCharArray()) {
            c = Character.toUpperCase(c);
            sb.append(c);
        }

        return sb.toString().replace("model", "");
    }

    public static String properCase(String inputVal) {
        if (inputVal.length() == 0) return inputVal;
        if (inputVal.length() == 1) return inputVal.toLowerCase();

        StringBuilder sb = new StringBuilder();
        for (char c : inputVal.toCharArray()) {
            c = Character.toLowerCase(c);
            sb.append(c);
        }

        return sb.toString().replace("model", "");
    }

    public static String bigFirstLetter(String inputVal) {
        if (inputVal.length() == 0) return inputVal;
        if (inputVal.length() == 1) return inputVal.toUpperCase();

        StringBuilder sb = new StringBuilder();
        char[] array = inputVal.toCharArray();

        for (int i = 0; i < array.length; i++) {
            if (i < 1) {
                array[i] = Character.toUpperCase(array[i]);
            }
            sb.append(array[i]);
        }

        return sb.toString().replace("model", "");
    }

    public static String toCamelCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;
        input = input.substring(0, 1).toUpperCase()
                .concat(input.substring(1));

        for (char c : input.toCharArray()) {
            if (c == '_') {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString().replace("_", "").replace("tbl_", "");
    }

    public static String cammelCase(String inputVal) {
        if (inputVal.length() == 0) return inputVal;
        if (inputVal.length() == 1) return inputVal.toLowerCase();

        StringBuilder sb = new StringBuilder();

        boolean upper = false;


        for (char c : inputVal.toCharArray()) {

            if (upper || Character.toUpperCase(c) == c) {
                c = Character.toUpperCase(c);
            } else {
                c = Character.toLowerCase(c);
            }

            if (c == ' ' || c == '_') {
                upper = true;
            } else {
                upper = false;
                sb.append(c);
            }

        }

        return sb.toString().replace("model", "");
    }

    public static String methodCase(String inputVal) {
        if (inputVal.length() == 0) return inputVal;
        if (inputVal.length() == 1) return inputVal.toLowerCase();

        StringBuilder sb = new StringBuilder();

        boolean upper = false;

        char[] array = inputVal.toCharArray();

        for (int i = 0; i < array.length; i++) {

            char c = array[i];

            if (i < 1) {
                c = Character.toLowerCase(array[i]);
            }

            if (upper || Character.toUpperCase(c) == c) {
                c = Character.toUpperCase(c);
            } else {
                c = Character.toLowerCase(c);
            }

            if (c == ' ' || c == '_') {
                upper = true;
            } else {
                upper = false;
                sb.append(c);
            }

        }
        return sb.toString().replace("model", "");
    }

    public static String constantaCase(String string) {
        if (string.length() == 0) return string;
        if (string.length() == 1) return string.toLowerCase();

        StringBuilder sb = new StringBuilder();

        for (char c : string.toCharArray()) {

            c = (c == ' ') ? c = '_' : Character.toUpperCase(c);
            sb.append(c);
        }
        return sb.toString().replace("model", "");
    }

//    public static String cammelCase(String className) {
//        return bigFirstLetter(methodCase(className));
//    }
    public static String d(String... arr) {
        String div = "@@@@@@ ";
        StringBuilder sb = new StringBuilder(div);
        for (String s : arr) {
            sb.append(s)
                    .append(" ")
                    .append("@@")
                    .append(" ");
        }

        String res = sb.toString();
        System.out.println(res);
        return res;
    }


//    public static String classNameFromUrl(HttpUrl targetUrl) {
//        System.out.println(targetUrl);
//        URL url;
//        try {
//            url = new URL(targetUrl.toString().replaceAll("/\\d", ""));
//            String[] arr = url.getPath().split("/");
//            return cammelCase(arr[arr.length - 1]);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//            return "";
//        }
//
//    }


    public static void main(String[] args) {

        String[] demo = {
                "information_schema_ALL_PLUGINS.json",
                "et_dateCreated",
                "Oello oorld",

                "___Lol-;plol"
        };


        for (String s : demo) {
            printHeader(
                    //toResId(s), methodCase(s), allupperCase(s), className(s), properCase(s),
                    //        bigFirstLetter(s),toCamelCase(s), cammelCase(s), constantaCase(s),toResName(s)
                    toClassName(s)
            );
        }


    }


    private static Character[] spaces = {
            ' ', '_', '-'
    };


    //Convert string to Class CamelCaseName
    public static String toClassName(String inputVal) {
        inputVal = clear(inputVal);
        boolean nextUp = true;

        if (inputVal.length() == 0) return inputVal;
        if (inputVal.length() == 1) return inputVal.toUpperCase();

        StringBuilder sb = new StringBuilder();
        char[] array = inputVal.toCharArray();

        for (int i = 0; i < array.length; i++) {
            //if (i < 1) {

            char c = array[i];

            if (nextUp) {
                nextUp = false;
                array[i] = Character.toUpperCase(array[i]);
            } else {
                array[i] = Character.toLowerCase(array[i]);
            }

            if (Arrays.asList(spaces).contains(array[i])) {//(c == ' ' || c == '_')
                nextUp = true;
            }


            //}
            if (!nextUp) sb.append(array[i]);
        }

        return sb.toString()
                .replace("model", "")
                .replace(".json", "");
    }


    private static String clear(String inputVal) {
        return inputVal.replace("information_schema_", "");
    }


    public static String toResName(String string) {
        StringBuilder titleCase = new StringBuilder();
        int j = 0;
        char[] arr = string.toCharArray();
        for (char c : arr) {
            titleCase.append(c);
            ++j;
            if (j < arr.length) {
                if (Character.toTitleCase(arr[j]) == arr[j]) {
                    titleCase.append(" ");
                }
            }
        }
        return titleCase.length() == 0 ? "null" : titleCase.toString();
    }

    public static String toResId(String string) {
        StringBuilder titleCase = new StringBuilder();
        int j = 0;
        char[] arr = string.toCharArray();
        for (char c : arr) {
            titleCase.append(Character.toLowerCase(c));
            ++j;
            if (j < arr.length) {
                if (Character.toTitleCase(arr[j]) == arr[j]) {
                    titleCase.append("_");
                }
            }
        }
        return titleCase.length() == 0 ? "null" : titleCase.toString();
    }

    public static String printHeader(Object... obj) {
        StringBuilder sb = new StringBuilder();

        String liner = "\t";
        String data = "";

        for (Object o : obj) {
            String q;
            q = ((o instanceof String)) ? (String) o : String.valueOf(o);
            liner = (o != null) ? liner + new String(new char[q.length()]).replace("\0", "=") : q;
            data = data + "\t" + o;
        }

        sb.append(liner);
        sb.append("\n");
        sb.append(data);
        sb.append("\n");
        sb.append(liner);

        System.out.println(sb.toString());
        return sb.toString();
    }


    public static String jsonFromUrl(String target) throws Exception {
        StringBuilder sb = new StringBuilder();

        URL source = new URL(target);
        URLConnection yc = source.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            sb.append(inputLine);
        }

        in.close();
        return sb.toString();
    }
}