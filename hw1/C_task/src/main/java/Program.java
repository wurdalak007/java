import java.util.ArrayList;
import java.util.Scanner;

public class Program {

    public static void main (String[] args) {
        Scanner in = new Scanner(System.in);

        ArrayList<Integer> A = new ArrayList<Integer>();
        ArrayList<Integer> B = new ArrayList<Integer>();

        int n = in.nextInt();
        for (int i = 0; i < n; i++) {
            int value = in.nextInt();
            A.add(value);
        }


        int m = in.nextInt();
        for (int i = 0; i < m; i++) {
            int value = in.nextInt();
            B.add(value);
        }

        int K = in.nextInt();
        in.close();

        int j = m-1;
        int numOfPairs = 0;
        for (int i = 0; i < n; i++) {

            while (j >= 0 && A.get(i) + B.get(j) > K) {
                j--;
            }

            if (j >= 0 && A.get(i) + B.get(j) == K) {
                numOfPairs++;
                j--;
            }

        }

        System.out.print(numOfPairs);
    }


}
