package org.design.models;

public class Request {
    String requestCommand;

    public Request(String requestCommand) {
        this.requestCommand = requestCommand;
    }

    public void performRequest(Balances balances) {
        String[] requestSplit = requestCommand.split(" ");
        switch (requestSplit[0]) {
            case "SHOW":
                if (requestSplit.length > 1) {
                    balances.showForAUser(requestSplit[1]);
                } else {
                    balances.showAll();
                }
                break;

            case "EXPENSE":
                Expense expense = new Expense(requestCommand);
                expense.split(balances);
                break;
        }
    }
}
