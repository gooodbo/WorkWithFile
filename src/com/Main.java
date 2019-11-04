package com;


import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;


/*
1.      Сделать процесс скачивания романа из интернета автоматическим
2.      Прочитать его из файла (используя на выбор один из классов для чтения байтовой информации, а потом один из классов для чтения текстовой информации).
3.      Проанализировать его и записать в другой файл следующие результаты:
- самое часто встречающееся слово
- самое редко встречающееся слово
- самое длинное слово

*/



public class Main {
    public static void main(String[] args) {

        String s;
        String textFile = "";
        String URL = "https://drive.google.com/uc?authuser=0&id=1y2rnReNIUzpWzP1RWJLGbUY0CCzSRkqa&export=download";
        String path = "C:\\Users\\Avalakh\\Desktop\\voina-i-mir.txt";

        try {
            download(URL, path);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(
                                   path), StandardCharsets.UTF_8));
            while ((s = bufferedReader.readLine()) != null) {
                textFile = textFile + s + "\n";
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] textFileArray = textFile.split("\\s*([.?,!])\\s*|\\s");

        //System.out.println(textFile);

        System.out.println("Самое повторяющееся слово: \"" + theMostRepeatedWord(textFileArray) + "\".");
        System.out.println("Самое редко встречающееся: \"" + theRarestWord(textFileArray) + "\" .");
        System.out.println("Самое длинное слово \"" + theLongestWord(textFileArray) + "\".");


    }


    public static String theLongestWord(String[] m) {

        int i, size = m.length;
        int iMax = -1, iMaxLength = -1;
        for (i = 0; i < size; ++i)
            if (!"".equals(m[i]) && m[i].length() > iMaxLength) {
                iMax = i;
                iMaxLength = m[i].length();
            }

        return m[iMax];
    }


    public static String theRarestWord(String[] m) {
        HashMap<String, Integer> h = makeMap(m);

        String wordRarest = "";

        int p1 = 0;
        for (String w : h.keySet()) {
            wordRarest = w;
            if (p1 > h.get(w)) {
                p1 = h.get(w);
                wordRarest = w;
            }
        }
        return wordRarest;
    }

    public static HashMap<String, Integer> makeMap(String[] m) {
        HashMap<String, Integer> h = new HashMap<>();

        for (int i = 0; i < m.length; i++) {
            if (h.containsKey(m[i])) {
                h.replace(m[i], h.get(m[i]) + 1);
            } else {
                h.put(m[i], 1);
            }
        }
        return h;
    }

    public static String theMostRepeatedWord(String[] m) {
        HashMap<String, Integer> h = makeMap(m);


        String wordMostRepeated = null;
        int p = 0;
        for (String w : h.keySet()) {
            if (p < h.get(w)) {
                p = h.get(w);
                wordMostRepeated = w;
            }
        }
        return wordMostRepeated;
    }


    public static void download(String url, String fileName) throws Exception {
        try (InputStream in = URI.create(url).toURL().openStream()) {
            Files.copy(in, Paths.get(fileName));
        }
    }
}

