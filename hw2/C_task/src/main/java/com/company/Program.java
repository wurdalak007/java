package com.company;
import java.util.ArrayList;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String  pattern = scanner.nextLine();
        String text = scanner.nextLine();

        EntriesFinder entriesFinderFind = new EntriesFinder(text, pattern);
        entriesFinderFind.findAllEntries();
        ArrayList<Integer> ans = entriesFinderFind.getAllEntries();
        for (Integer el: ans) {
            System.out.print(String.valueOf(el) + " ");
        }

        scanner.close();
    }
}
