package com.stackroute.threadsample;

class RoomTemperatureMonitor extends Thread {

    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(
                    "Monitoring room temperature: " + i
            );
        }
    }
}

public class HotelApp {
    public static void main(String[] args) {

        RoomTemperatureMonitor monitor =
                new RoomTemperatureMonitor();

        monitor.start();

        System.out.println("Reception is serving customers");

//        Runnable invoice =
//                new BookingTask("Generate Invoice");
//
//        Runnable email =
//                new BookingTask("Send Confirmation");
//
//        Runnable loyalty =
//                new BookingTask("Update Loyalty Points");
//
//        new Thread(invoice).start();
//        new Thread(email).start();
//        new Thread(loyalty).start();
    }
}