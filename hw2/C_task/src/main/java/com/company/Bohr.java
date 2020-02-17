package com.company;
import java.util.ArrayList;

public class Bohr {
    final static int noEdge = -1; // -1 - признак отсутствия ребра
    private ArrayList<Vertex> bohr;
    private ArrayList<String> pattern;
    private ArrayList<Integer> wherePatternStarts;

    Bohr() {
        bohr = new ArrayList<Vertex>(0);
        pattern = new ArrayList<String>(0);
        wherePatternStarts = new ArrayList<Integer>(0);
        bohr.add(new Vertex(0, (char) -1));
    }

    public void addString (String str, int pos) {
        int num = 0;
        int n = str.length();
        for (int i = 0; i < n; i++) {
            char ch = (char) (str.charAt(i) - 'a');

            if (bohr.get(num).nextVertex[ch] == noEdge) {
                bohr.add(new Vertex(num, ch));
                bohr.get(num).nextVertex[ch] = (short) (bohr.size() - 1);
            }
            num = bohr.get(num).nextVertex[ch];
        }
        bohr.get(num).isTerminal = true;
        pattern.add(str);
        wherePatternStarts.add(pos);
        bohr.get(num).patNums.add((short) (pattern.size() - 1));
    }

    void canGoToTheRoot(int v, int index, int[] entriesArray) {
        for (int u = v; u != 0; u = getCoolSuffLink(u)) {
            if (bohr.get(u).isTerminal) {
                for (int k = 0; k < bohr.get(u).patNums.size(); k++) {
                    int whichPattern = bohr.get(u).patNums.get(k);
                    int wherePat = wherePatternStarts.get(whichPattern);
                    int tmpLenght = pattern.get(bohr.get(u).patNums.get(k)).length();
                    int entryIndex = index - tmpLenght - wherePat;
                    if (entryIndex > -1)
                        entriesArray[entryIndex] += 1;
                }
            }
        }
    }

    private int getSimpleSuffLink(int v) {
        if (bohr.get(v).simpleSuffLink == -1) {
            if (v == 0 || bohr.get(v).parent == 0) {
                bohr.get(v).simpleSuffLink = 0;
            } else {
                bohr.get(v).simpleSuffLink = getMove(getSimpleSuffLink(bohr.get(v).parent), bohr.get(v).symb);
            }
        }
        return bohr.get(v).simpleSuffLink;
    }

    int getMove(int v, char ch) {
        if (bohr.get(v).autoMove[ch] == -1) {
            if (bohr.get(v).nextVertex[ch] != -1) {
                bohr.get(v).autoMove[ch] = bohr.get(v).nextVertex[ch];
            } else {
                if (v == 0) {
                    bohr.get(v).autoMove[ch] = 0;
                } else {
                    bohr.get(v).autoMove[ch] = (short) getMove(getSimpleSuffLink(v), ch);
                }
            }
        }
        return bohr.get(v).autoMove[ch];
    }

    private int getCoolSuffLink(int v) {
        if (bohr.get(v).coolSuffLink == -1) {
            int u = getSimpleSuffLink(v);
            if (u == 0) {
                bohr.get(v).coolSuffLink = 0;
            } else {
                if (bohr.get(u).isTerminal) {
                    bohr.get(v).coolSuffLink = u;
                } else {
                    bohr.get(v).coolSuffLink = getCoolSuffLink(u);
                }
            }
        }
        return bohr.get(v).coolSuffLink;
    }
}
