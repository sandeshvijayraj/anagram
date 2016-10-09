package com.google.engedu.anagrams;

import android.annotation.TargetApi;
import android.os.Build;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class       AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    ArrayList<String> wordlist = new ArrayList<String>();
    ArrayList<String>[] temp=new ArrayList[7];
    private int size;
    ArrayList<String> temps=new ArrayList<String>();
    ArrayList<String> result = new ArrayList<String>();
    HashMap<Integer, ArrayList<String>> wordset = new HashMap<>();
    String s, d;


    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while ((line = in.readLine()) != null) {
            String word = line.trim();
            wordlist.add(word);
        }
        size = wordlist.size();
        for (int i = DEFAULT_WORD_LENGTH; i <= MAX_WORD_LENGTH; i++) {
            for (int j = 0; j < size; j++) {
                if (wordlist.get(j).length() == i) {
                    temps.add(wordlist.get(j));
                }
            }
            temp[DEFAULT_WORD_LENGTH]=new ArrayList<>(temps);
            wordset.put(i,temp[DEFAULT_WORD_LENGTH]);
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
        ArrayList<String> thi=new ArrayList<String>();
        String s;
        for(a='a';a<='z';a++){
            s=a+word;
            thi=getAnagrams(s);
            si=thi.size();
            for(int i=0;i<si;i++){
                result.add(thi.get(i));
            }
        }
        return result;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public String pickGoodStarterWord() {
        while(true) {
            int y=0;
            int x = ThreadLocalRandom.current().nextInt(wordset.get(DEFAULT_WORD_LENGTH).size());
            if(getAnagramsWithOneMoreLetter(wordset.get(DEFAULT_WORD_LENGTH).get(x)).size()>=MIN_NUM_ANAGRAMS){
                y=DEFAULT_WORD_LENGTH;
                if(DEFAULT_WORD_LENGTH<=MAX_WORD_LENGTH) {
                    DEFAULT_WORD_LENGTH++;
                } return wordset.get(y).get(x);
            }
        }
    }

    public String sort(String word) {
        char a[]=word.toLowerCase().toCharArray();
        Arrays.sort(a);
        String s=new String(a);
        return s;

    }
}

