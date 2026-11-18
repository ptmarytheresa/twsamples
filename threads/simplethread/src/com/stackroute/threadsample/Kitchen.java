package com.stackroute.threadsample;

import java.util.Date;

public class Kitchen {
    public static void main(String[] args) throws InterruptedException {
        Thread cookRice = new Thread(() -> {
            try {
                Thread.sleep(2000);
            }
            catch (InterruptedException e) {}
            System.out.println(" Rice ready" + new Date());

        });
        Thread cookCurry = new Thread(() -> {
            try {
                Thread.sleep(3000);
            }
            catch (InterruptedException e) {}

            System.out.println(" Curry ready" +  new Date());
        });

        System.out.println("before " + new Date());
        cookRice.start();
        cookCurry.start();

        cookRice.join();   // main waits for rice
        cookCurry.join();  // main waits for curry

        System.out.println("Serve dinner!" + new Date());  // always prints LAST
    }
}