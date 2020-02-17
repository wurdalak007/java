import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        
        int x_prev = in.nextInt();
        int y_prev = in.nextInt();
        int x_start = x_prev;
        int y_start = y_prev;


        double answer = 0;
        for (int i = 1; i < num; i++) {
            int x_cur = in.nextInt();
            int y_cur = in.nextInt();

            answer += (y_cur + y_prev) * (x_cur - x_prev);

            x_prev = x_cur;
            y_prev = y_cur;
        }
        in.close();

        answer += (y_start + y_prev) * (x_start - x_prev);

        answer /= 2;
        System.out.print(Math.abs(answer));


    }


}