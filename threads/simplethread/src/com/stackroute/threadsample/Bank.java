package com.stackroute.threadsample;

// Shared resource
class BankAccount {
    private int balance = 1000;

      synchronized void withdraw(String who, int amount) {
        if (balance >= amount) {
            System.out.println(who + " is withdrawing " + amount);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            balance = balance - amount;
            System.out.println(who + " done. Balance = " + balance);
        } else {
            System.out.println(who + " - insufficient funds. Balance = " + balance);
        }
    }
}

// The job each thread will do
class WithdrawTask implements Runnable {
    private BankAccount account;
    private String name;
    private int amount;

    WithdrawTask(BankAccount account, String name, int amount) {
        this.account = account;
        this.name = name;
        this.amount = amount;
    }

    public void run() {
        account.withdraw(name, amount);
    }
}

// Main class
public class Bank {
    public static void main(String[] args) {
        BankAccount acc = new BankAccount();   // ONE shared account

        Thread t1 = new Thread(new WithdrawTask(acc, "Husband", 800));
        Thread t2 = new Thread(new WithdrawTask(acc, "Wife", 800));

        t1.start();
        t2.start();
    }
}