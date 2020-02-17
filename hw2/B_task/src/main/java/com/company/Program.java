package com.company;
import java.util.Scanner;

public class Program {

    static public void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();

        String[] array = input.split(" ");
        VeryLong result = new VeryLong(array[0]);
        if (array.length == 1) {
            if (array[0].contains("++")) {
                Integer pos = array[0].indexOf("++");
                if (pos == 0) {
                    result = VeryLong.add(new VeryLong(array[0].substring(2)), new VeryLong(1));
                } else {
                    result = VeryLong.add(new VeryLong(array[0].substring(0, pos)), new VeryLong(1));
                }
            }
            if (array[0].contains("--")) {
                Integer pos = array[0].indexOf("--");
                if (pos == 0) {
                    result = VeryLong.minus(new VeryLong(array[0].substring(2)), new VeryLong(1));
                } else {
                    result = VeryLong.minus(new VeryLong(array[0].substring(0, pos)), new VeryLong(1));
                }
            }
        }

        for(int i = 1; i < array.length; i++) {
            String tmp = array[i];
            if ("+".equals(tmp) || "+=".equals(tmp)) {
                result = VeryLong.add(result, new VeryLong(array[i+1]));
                i++;
            } else if ("-".equals(tmp) || "-=".equals(tmp)) {
                result = VeryLong.minus(result, new VeryLong(array[i+1]));
                i++;
            } else if ("*".equals(tmp) || "*=".equals(tmp)) {
                result = VeryLong.multiply(result, new VeryLong(array[i+1]));
                i++;
            } else if ("/".equals(tmp) || "/=".equals(tmp)) {
                result = VeryLong.div(result, new VeryLong(array[i+1]));
                i++;
            } else if ("%".equals(tmp) || "/=".equals(tmp)) {
                result = VeryLong.residue(result, new VeryLong(array[i+1]));
                i++;
            } else if ("--".equals(tmp)) {
                result = VeryLong.minus(result, new VeryLong(1));
            } else if ("++".equals(tmp)) {
                result = VeryLong.add(result, new VeryLong(1));
            } else if (">=".equals(tmp)) {
                System.out.println(result.greaterEq(new VeryLong(array[i+1])));
                return;
            } else if (">".equals(tmp)) {
                System.out.println(result.greater(new VeryLong(array[i+1])));
                return;
            } else if ("<=".equals(tmp)) {
                System.out.println(result.lessEq(new VeryLong(array[i+1])));
                return;
            } else if ("<".equals(tmp)) {
                System.out.println(result.less(new VeryLong(array[i+1])));
                return;
            } else if ("==".equals(tmp)) {
                System.out.println(result.equal(new VeryLong(array[i+1])));
                return;
            } else if ("!=".equals(tmp)) {
                System.out.println(result.notEq(new VeryLong(array[i + 1])));
                return;
            }
        }

        System.out.println(result.toString());

        scanner.close();
    }

}
