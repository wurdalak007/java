import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int N = in.nextInt();
        int k = in.nextInt();

        System.out.print(count(N, k));

    }

    private static int count (int N, int k) {
        int ans = 1;
        for (int n = 2; n <= N; n++) {
            ans = (ans + k - 1) % n + 1;
        }

        return ans;
    }
}
