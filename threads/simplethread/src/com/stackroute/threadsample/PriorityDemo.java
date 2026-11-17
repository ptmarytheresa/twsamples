package com.stackroute.threadsample;

public class PriorityDemo {
    public static void main(String[] args) {
        Thread low  = new Thread(() -> System.out.println("Low priority task"));
        Thread high = new Thread(() -> System.out.println("High priority task"));

        low.setPriority(Thread.MIN_PRIORITY);   // 1
        high.setPriority(Thread.MAX_PRIORITY);  // 10  (default is 5)

        low.start();
        high.start();
    }
}