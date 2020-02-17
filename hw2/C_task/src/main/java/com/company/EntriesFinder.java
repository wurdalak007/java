package com.company;

import java.util.ArrayList;

public class EntriesFinder {

    EntriesFinder(String text, String pattern) {
        this.text = text;
        this.count = 0;
        int textSize = text.length();
        int patternSize = pattern.length();

        this.bohr = new Bohr();

        C = new int[textSize];
        for (int i = 0; i < textSize; i++) {
            C[i] = 0;
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < patternSize; i++) {
            int tmp_len = stringBuilder.length();
            if (pattern.charAt(i) == '?') {
                if (tmp_len != 0) {
                    bohr.addString(stringBuilder.toString(), i - tmp_len);
                    count++;
                    stringBuilder = new StringBuilder();
                }
            } else {
                stringBuilder.append(pattern.charAt(i));
            }

            tmp_len = stringBuilder.length();
            if (tmp_len != 0 && i == patternSize - 1) {
                bohr.addString(stringBuilder.toString(), i - tmp_len + 1);
                count++;
            }
        }

        this.patternSize = patternSize;
    }

    public void findAllEntries() {
        int u = 0;
        for (int i = 0; i < text.length(); i++){
            u = bohr.getMove(u, whereGo(text.charAt(i)));
            bohr.canGoToTheRoot(u, i + 1, C);
        }
    }

    public ArrayList<Integer> getAllEntries() {
        ArrayList<Integer> answer = new ArrayList<Integer>(0);

        Integer textSize = text.length();
        for (int i = 0; i < textSize; i++) {
            if (C[i] == count) {
                if (i + patternSize - 1 < textSize) {
                    answer.add(i);
                }

            }
        }

        return answer;
    }

    private char whereGo (char c) {
        return (char) (c - 'a');
    }

    private int patternSize;
    private int count;
    private int[] C;
    private String text;
    private Bohr bohr;
}
