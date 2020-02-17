package com.company;

public class DistanceFinder {

    public static double PPDistance(Point A, Point B) {
        return Math.hypot((A.x - B.x), (A.y - B.y));
    }

    public static double PSDistance(Point A, Segment B) {
        if (isOnSegment(A, B)) {
            return 0;
        }

        Point proj = findProjection(A, new Ray(B.start, B.end));
        return isOnSegment(proj, B) ? PPDistance(A, proj) : Math.min(PPDistance(A, B.start), PPDistance(A, B.end));
    }

    public static double PRDistance(Point A, Ray B) {
        if (isOnRay(A, B)) {
            return 0;
        }

        Point proj = findProjection(A, B);
        return isOnRay(proj, B) ? PPDistance(A, proj) : PPDistance(A, B.start);
    }

    public static double PLDistance(Point A, Line B) {
        if (isOnRay(A, new Ray(B.start, B.end)) || isOnRay(A, new Ray(B.end, B.start))) {
            return 0;
        }

        double lenghtAStart = PPDistance(A, B.start);
        double lenghtB = PPDistance(B.start, B.end);
        double lenghtAEnd = PPDistance(A, B.end);

        double s = (lenghtAStart + lenghtB + lenghtAEnd) / 2;
        double area = Math.sqrt(s * (s - lenghtAStart) * (s - lenghtB) * (s - lenghtAEnd));

        return (2 * area / lenghtB);
    }

    public static double SSDistance(Segment A, Segment B) {
        if (segmentIntersect(A, B)) {
            return 0;
        }

        double first = Math.min(PSDistance(A.start, B), PSDistance(A.end, B));
        double second = Math.min(PSDistance(B.start, A), PSDistance(B.end, A));
        return Math.min(first, second);

    }

    public static double SRDistance(Segment A, Ray B) {
        double answer = 0;
        Point intersectPoint = new Point(0, 0);
        double zn = findIntersection(A, B, intersectPoint);

        if (Math.abs(zn) > EPS) {
            answer = isOnRay(intersectPoint, B) && isOnSegment(intersectPoint, A) ? 0 : isOnRay(intersectPoint, B) ? Math.min(PRDistance(A.start, B), PRDistance(A.end, B)) : PSDistance(B.start, A);
        } else {
            answer = Math.min(PRDistance(A.start, B), PRDistance(A.end, B));
        }
        return answer;
    }

    public static double SLDistance(Segment A, Line B) {
        double answer = 0;
        Point intersectPoint = new Point(0, 0);
        double zn = findIntersection(A, B, intersectPoint);

        if (Math.abs(zn) > EPS) {
            answer = isOnSegment(intersectPoint, A) ? 0 : Math.min(PLDistance(A.start, B), PLDistance(A.end, B));
        } else {
            answer = PLDistance(A.start, B);
        }
        return answer;
    }


    public static double RRDistance(Ray A, Ray B) {
        double answer = 0;
        Point intersectPoint = new Point(0, 0);
        double zn = findIntersection(A, B, intersectPoint);

        if (Math.abs(zn) > EPS) {
            answer = isOnRay(intersectPoint, A) && isOnRay(intersectPoint, B) ? 0 : Math.min(PRDistance(A.start, B), PRDistance(B.start, A));
        } else {
            answer = Math.min(PRDistance(A.start, B), PRDistance(B.start, A));
        }
        return answer;
    }

    public static double RLDistance(Ray A, Line B) {
        double answer = 0;
        Point intersectPoint = new Point(0, 0);
        double zn = findIntersection(A, B, intersectPoint);

        if (Math.abs(zn) > EPS) {
            answer = isOnRay(intersectPoint, A) ? 0 : PLDistance(A.start, B);
        } else {
            answer = PLDistance(A.start, B);
        }
        return answer;
    }

    public static double LLDistance(Line A, Line B) {
        if (lineIntersect(A, B)) {
            return 0;
        }
        return PLDistance(A.start, B);
    }

    private static double Det(double A, double B, double C, double D) {
        return A * D - B * C;
    }

    private static double findIntersection(AbstractLine A, AbstractLine B, Point intetsectPoint) {
        double A1 = A.start.y - A.end.y,
                B1 = A.end.x - A.start.x,
                C1 = - A1 * A.start.x - B1 * A.start.y;
        double A2 = B.start.y - B.end.y,
                B2 = B.end.x - B.start.x,
                C2 = - A2 * B.start.x - B2 * B.start.y;

        double zn = Det(A1, B1, A2, B2);

        if (Math.abs(zn) > EPS) {
            intetsectPoint.x = - Det(C1, B1, C2, B2) / zn;
            intetsectPoint.y = - Det(A1, C1, A2, C2) / zn;
        } else {
            zn = 0;
        }

        return zn;
    }

    private static boolean segmentIntersect(Segment A, Segment B) {
        Point intersectPoint = new Point(0, 0);
        double zn = findIntersection(A, B, intersectPoint);

        if (Math.abs(zn) > EPS) {
            return isOnSegment(intersectPoint, A) && isOnSegment(intersectPoint, B);
        } else {
            return isOnSegment(A.start, B) || isOnSegment(A.end, B) ||
                    isOnSegment(B.start, A) || isOnSegment(B.end, A);
        }
    }

    private static boolean lineIntersect(Line A, Line B) {
        double A1 = A.start.y - A.end.y, B1 = A.end.x - A.start.x;
        double A2 = B.start.y - B.end.y, B2 = B.end.x - B.start.x;

        double zn = Det(A1, B1, A2, B2);

        return Math.abs(zn) > EPS;
    }

    private static boolean isOnRay(Point A, Ray B) {
        double vectorBX = B.end.x - B.start.x;
        double vectorBY = B.end.y - B.start.y;

        double ADist = (A.x - B.start.x) * vectorBY;
        double BDist = vectorBX * (A.y - B.start.y);
        return ((Math.abs(ADist - BDist) < EPS * 1000) &&
                ((A.x - B.start.x) * vectorBX > -EPS * 1000) &&
                ((A.y - B.start.y) * vectorBY > -EPS * 1000));
    }

    private static boolean isOnSegment(Point A, Segment B) {
        return (isOnRay(A, new Ray(B.start, B.end)) &&
                isOnRay(A, new Ray(B.end, B.start)));
    }

    private static Point findProjection(Point A, Ray B) {
        if (isOnRay(A, B)) {
            return A;
        }
        if (B.start.x == B.end.x) {
            return new Point(B.start.x, A.y);
        }

        double x1 = B.start.x,
                x2 = B.end.x,
                x3 = A.x,
                x4;
        double y1 = B.start.y,
                y2 = B.end.y,
                y3 = A.y,
                y4;

        double d = Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2);

        if (d == 0) {
            x4 = 0;
        } else {
            x4 = ((x2 - x1) * (y2 - y1) * (y3 - y1) + x1 * Math.pow(y2 - y1, 2) + x3 * Math.pow(x2 - x1, 2)) / d;
        }
        y4 = (y2 - y1) * (x4 - x1) / (x2 - x1) + y1;

        return new Point(x4, y4);
    }

    final private static double EPS = 10e-11;
}
