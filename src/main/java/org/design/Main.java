package org.design;

import org.design.models.Balances;
import org.design.models.Expense;
import org.design.models.Request;

import java.util.List;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> usersList = List.of("u1", "u2", "u3", "u4");
        Balances balances = new Balances(usersList);

        while(true) {
            String line = sc.nextLine();
            if (line.equalsIgnoreCase("exit")) break;

            Request request = new Request(line);
            request.performRequest(balances);
        }
    }
}