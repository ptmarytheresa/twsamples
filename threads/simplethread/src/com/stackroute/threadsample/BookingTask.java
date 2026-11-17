package com.stackroute.threadsample;

class BookingTask implements Runnable {

    private String taskName;

    BookingTask(String taskName) {
        this.taskName = taskName;
    }

    public void run() {
        System.out.println(
                taskName + " started by " +
                        Thread.currentThread().getName()
        );
    }
}