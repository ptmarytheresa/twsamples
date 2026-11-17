package com.stackroute.threadsample;

public class Kitchen {
    public static void main(String[] args) throws InterruptedException {
        Thread cookRice = new Thread(() -> {
            try { Thread.sleep(2000); }
            catch (InterruptedException e) {}
            System.out.println(" Rice ready");
        });
        Thread cookCurry = new Thread(() -> {
            try { Thread.sleep(3000); }
            catch (InterruptedException e) {}
            System.out.println(" Curry ready");
        });

        cookRice.start();
        cookCurry.start();

        cookRice.join();   // main waits for rice
        cookCurry.join();  // main waits for curry

        System.out.println("Serve dinner!");  // always prints LAST
    }
}