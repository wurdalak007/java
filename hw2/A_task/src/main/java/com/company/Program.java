package com.company;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Point A = new Point(scanner.nextInt(), scanner.nextInt());
        Point B = new Point(scanner.nextInt(), scanner.nextInt());
        Point C = new Point(scanner.nextInt(), scanner.nextInt());
        Point D = new Point(scanner.nextInt(), scanner.nextInt());

        Segment ABSegment = new Segment(A, B);
        Segment CDSegment = new Segment(C, D);

        Ray ABRay = new Ray(A, B);
        Ray CDRay = new Ray(C, D);

        Line ABLine = new Line(A, B);
        Line CDLine = new Line(C, D);

        //В названиях методов первые две буквы обозначают между чем и чем ищется дистанция
        // P - Point, S - Segment, R - Ray, L - Line

        System.out.printf("%.10f %n", DistanceFinder.PPDistance(A, C));
        System.out.printf("%.10f %n", DistanceFinder.PSDistance(A, CDSegment));
        System.out.printf("%.10f %n", DistanceFinder.PRDistance(A, CDRay));
        System.out.printf("%.10f %n", DistanceFinder.PLDistance(A, CDLine));

        System.out.printf("%.10f %n", DistanceFinder.PSDistance(C, ABSegment));
        System.out.printf("%.10f %n", DistanceFinder.SSDistance(ABSegment, CDSegment));
        System.out.printf("%.10f %n", DistanceFinder.SRDistance(ABSegment, CDRay));//
        System.out.printf("%.10f %n", DistanceFinder.SLDistance(ABSegment, CDLine));

        System.out.printf("%.10f %n", DistanceFinder.PRDistance(C, ABRay));
        System.out.printf("%.10f %n", DistanceFinder.SRDistance(CDSegment, ABRay));
        System.out.printf("%.10f %n", DistanceFinder.RRDistance(ABRay, CDRay));//
        System.out.printf("%.10f %n", DistanceFinder.RLDistance(ABRay, CDLine));//

        System.out.printf("%.10f %n", DistanceFinder.PLDistance(C, ABLine));
        System.out.printf("%.10f %n", DistanceFinder.SLDistance(CDSegment, ABLine));
        System.out.printf("%.10f %n", DistanceFinder.RLDistance(CDRay, ABLine));
        System.out.printf("%.10f %n", DistanceFinder.LLDistance(ABLine, CDLine));

        scanner.close();
    }
}
