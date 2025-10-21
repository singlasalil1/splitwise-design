package org.design.models;

import lombok.Builder;
import lombok.Data;
import org.design.enums.ExpenseType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Expense {
    User userWhoPaid;
    Float totalAmount;
    Integer numUsersInvolved;
    ExpenseType expenseType;
    List<User> usersInvolved = new ArrayList<>();
    Map<String, Float> percentByUser = new HashMap<>();
    Map<String, Float> exactValByUser =  new HashMap<>();

    public Expense(String expense) {
        String[] expenseStrSplit = expense.split(" ");
        this.userWhoPaid = User.builder().name(expenseStrSplit[1]).build();
        this.totalAmount = Float.parseFloat(expenseStrSplit[2]);
        this.numUsersInvolved = Integer.parseInt(expenseStrSplit[3]);
        for(int i=0; i<numUsersInvolved; i++) {
            this.usersInvolved.add(User.builder()
                    .name(expenseStrSplit[4+i])
                    .build());
        }
        this.expenseType = ExpenseType.valueOf(expenseStrSplit[4+numUsersInvolved]);

        switch (expenseType) {
            case EXACT: {
                for (int i = 0; i < numUsersInvolved; i++) {
                    exactValByUser.put(expenseStrSplit[4+i],
                            Float.parseFloat(expenseStrSplit[5+numUsersInvolved+i])
                    );
                }
            }
            break;

            case PERCENT:
                for (int i = 0; i < numUsersInvolved; i++) {
                    percentByUser.put(expenseStrSplit[4+i],
                            Float.parseFloat(expenseStrSplit[5+numUsersInvolved+i])
                    );
                }
                break;
        }
    }

    public void split(Balances balances) {
        Map<String, Map<String, Double>> balancesOwed = balances.getUserToBalancesOwed();
        System.out.println(expenseType);
        switch (expenseType) {
            case EXACT -> {
                if (balancesOwed.containsKey(userWhoPaid.getName())) {
                    for (User user : usersInvolved) {
                        double userCurBal = balancesOwed.get(userWhoPaid.getName()).get(
                                user.getName());
                        balancesOwed.get(userWhoPaid.getName()).put(
                                user.getName(),
                                userCurBal + exactValByUser.get(user.getName()));

                    }
                }
            }

            case PERCENT -> {
                if (balancesOwed.containsKey(userWhoPaid.getName())) {
                    for (User user : usersInvolved) {
                        double userCurBal = balancesOwed.get(userWhoPaid.getName()).get(
                                user.getName());
                        balancesOwed.get(userWhoPaid.getName()).put(
                                user.getName(),
                                userCurBal + (totalAmount * percentByUser.get(user.getName())) / 100);

                    }
                }
            }

            case EQUAL -> {
                if (balancesOwed.containsKey(userWhoPaid.getName())) {
                    for (User user : usersInvolved) {
                        double userCurBal = balancesOwed.get(userWhoPaid.getName()).get(
                                user.getName());
                        balancesOwed.get(userWhoPaid.getName()).put(
                                user.getName(),
                                userCurBal + (totalAmount / numUsersInvolved));
                    }
                }
            }
        }
        balances.setUserToBalancesOwed(balancesOwed);
    }
}
