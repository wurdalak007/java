import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int num = in.nextInt();

        ArrayList<Integer> A = new ArrayList<Integer>();
        ArrayList<Integer> B = new ArrayList<Integer>();

        for (int i = 0; i < num; i++) {
            int value = in.nextInt();
            A.add(value);
        }

        for (int i = 0; i < num; i++) {
            int value = in.nextInt();
            B.add(value);
        }
        in.close();

        Pair<Integer, Integer> ans = findIndexes(A, B);

        System.out.print(ans.getKey() + " " + ans.getValue());

    }

    private static Pair<Integer, Integer> findIndexes (ArrayList<Integer> A, ArrayList<Integer> B) {
        int i0 = 0;
        int j0 = 0;
        int n = A.size();

        int candidateMaxA = 0;

        for (int j = 0; j < n; j++) {
            if (A.get(j) > A.get(candidateMaxA)) {
                candidateMaxA = j;
            }

            if (A.get(candidateMaxA) + B.get(j) > A.get(i0) + B.get(j0)) {
                i0 = candidateMaxA;
                j0 = j;
            }
        }

        Pair<Integer, Integer> answer = new Pair<Integer, Integer>(i0, j0);

        return answer;
    }



}