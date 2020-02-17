package com.company;
import java.util.ArrayList;

public class Vertex {
    boolean isTerminal;
    char symb; // символ на ребре от parent к этой вершине
    int parent; // вершина-отец в дереве
    int simpleSuffLink;
    int coolSuffLink;
    short[] nextVertex;
    short[] autoMove; // запоминание перехода автомата
    ArrayList<Short> patNums; // шаблоны, оканчивающийся в данной вершине

    Vertex() {
        char alphabet = 26;
        nextVertex = new short[alphabet];
        autoMove = new short[alphabet];
        patNums = new ArrayList<Short>();

        for (int i = 0; i < alphabet; i++) {
            nextVertex[i] = -1;
            autoMove[i] = -1;
        }
    }

    Vertex (int parent, char ch) {
        this.isTerminal = false;
        this.simpleSuffLink = -1;
        this.coolSuffLink = -1;
        this.parent = parent;
        this.symb = ch;
    }

}
