package org.design.models;

import lombok.Builder;
import lombok.Data;

import java.util.*;

@Data
public class Balances {
    List<String> usersList;
    Map<String, Map<String, Double>> userToBalancesOwed = new HashMap<>();

    public Balances(List<String> usersList) {
        this.usersList = usersList;
        for(int i=0; i<usersList.size(); i++) {
            Map<String, Double> owedBalancesByUser = new HashMap<>();
            for (String s : usersList) {
                owedBalancesByUser.put(s.toLowerCase(), 0.0);
            }
            this.userToBalancesOwed.put(usersList.get(i).toLowerCase(), owedBalancesByUser);
        }
    }

    public void showAll() {
        boolean zerosOnly = true;
        for(String s : usersList) {
            Map<String, Double> balancesOwedByUser = userToBalancesOwed.get(s.toLowerCase());
            for (String key: balancesOwedByUser.keySet()) {
                if (balancesOwedByUser.get(key) != 0) {
                    if (key.equals(s)) {
                        continue;
                    }
                    System.out.println(key + " owes " + s + " : " + balancesOwedByUser.get(key));
                    zerosOnly = false;
                }
            }
        }

        if (zerosOnly) {
            System.out.println("No balances");
        }
    }

    public void showForAUser(String user) {
        boolean zerosOnly = true;
        Map<String, Double> balancesOwedByUser = userToBalancesOwed.get(user);
        for (String key: balancesOwedByUser.keySet()) {
            if (balancesOwedByUser.get(key) > 0) {
                if (key.equals(user)) {
                    continue;
                }
                System.out.println(key + " owes " + user + " : " + balancesOwedByUser.get(key));
                zerosOnly = false;
            }
        }

        if (zerosOnly) {
            System.out.println("No balances");
        }
    }
}
