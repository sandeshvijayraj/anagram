package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
public class AnagramDictionary {
    private static final int MIN_NUM_ANAGRAMS = 5;
    private static int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random=new Random();
    public ArrayList<String> result = new ArrayList<String>();
    private HashMap<Integer, ArrayList<String>> wordset = new HashMap<>();


    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        ArrayList<String> wordlist = new ArrayList<String>();
        while ((line = in.readLine()) != null) {
            String word = line.trim();
            wordlist.add(word);
        }
        int size = wordlist.size();
        for (int i = DEFAULT_WORD_LENGTH; i <= MAX_WORD_LENGTH+2; i++) {
            ArrayList<String> temps = new ArrayList<String>();
            for (int j = 0; j < size; j++) {
                if (wordlist.get(j).length() == i) {
                    temps.add(wordlist.get(j));
                }
            }
            ArrayList<String>[] temp = new ArrayList[7];
            temp[DEFAULT_WORD_LENGTH]=new ArrayList<>(temps);
            wordset.put(i, temp[DEFAULT_WORD_LENGTH]);
            temps.clear();
        }
    }


    public boolean isGoodWord(String word, String base) {
        int z = result.size();
        for (int i = 0; i < z; i++) {
            if (result.get(i).equals(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }


    public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> ne=new ArrayList<String>();
        String x=sort(targetWord);
        String y;
        for(int i=0;i<wordset.get(targetWord.length()).size();i++){
            y=sort(wordset.get(targetWord.length()).get(i));
            if(y.equals(x)){
                ne.add(wordset.get(targetWord.length()).get(i));
            }
        }
        return ne;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        result.clear();
        char a;
        int si;
        ArrayList<String> thi;
        String s;
        for(a='a';a<='z';a++){
            s=a+word;
            thi=getAnagrams(s);
            si=thi.size();
            for(int i=0;i<si;i++){
                if(!thi.get(i).contains(word))
                result.add(thi.get(i));
            }
        }
        return result;
    }

    public String pickGoodStarterWord(int d) {
        int count=0;
        while (count<=1) {
            int y = 0;
            int x = random.nextInt(wordset.get(DEFAULT_WORD_LENGTH).size());
            if (d == 1) {
                if (getAnagramsWithOneMoreLetter(wordset.get(DEFAULT_WORD_LENGTH).get(x)).size() >= MIN_NUM_ANAGRAMS) {
                    y = DEFAULT_WORD_LENGTH;
                    if (DEFAULT_WORD_LENGTH < MAX_WORD_LENGTH) {
                        DEFAULT_WORD_LENGTH++;
                    }else DEFAULT_WORD_LENGTH=3;
                    return wordset.get(y).get(x);
                }
            } else {
                if (anagramwithtwoword(wordset.get(DEFAULT_WORD_LENGTH).get(x)).size() >= MIN_NUM_ANAGRAMS) {
                    y = DEFAULT_WORD_LENGTH;
                    if (DEFAULT_WORD_LENGTH < MAX_WORD_LENGTH) {
                        DEFAULT_WORD_LENGTH++;
                    }else DEFAULT_WORD_LENGTH=3;
                    return wordset.get(y).get(x);
                }else count++;
            }
        }
        return "badge";
    }
    public ArrayList<String> anagramwithtwoword(String word) {
        result.clear();
        ArrayList<String> res=new ArrayList<String>();
        char a,b;
        int si;
        ArrayList<String> thi=new ArrayList<String>();
        String s;
        for(a='a';a<'z';a++) {
            for (b = 'a'; b <= 'z'; b--) {
                s = b + word;
                thi = getAnagramsWithOneMoreLetter(s);
                si = thi.size();
                for (int i = 0; i < si; i++) {
                    res.add(thi.get(i));
                    if (res.size() >= 10) {
                        result.clear();
                        result.addAll(res);
                        return result;
                    }
                }
            }
        }
        result.clear();
        result.addAll(res);
        return result;
    }


    private String sort(String word) {
        char a[]=word.toLowerCase().toCharArray();
        Arrays.sort(a);
        return new String(a);

    }
}

