package com.company;
abstract class AbstractLine {
    Point start, end;

    AbstractLine(Point a, Point b) {
        this.start = a;
        this.end = b;
    }
}
