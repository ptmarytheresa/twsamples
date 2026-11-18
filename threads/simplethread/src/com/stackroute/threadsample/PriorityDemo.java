package com.stackroute.threadsample;
class Task extends  Thread
{
    public void run()
    {
        System.out.println("Thread " + Thread.currentThread().getPriority());
    }
}
public class PriorityDemo {
    public static void main(String[] args) {
       Task t1=new Task();
        t1.setPriority(Thread.MIN_PRIORITY);
        Task t2=new Task();
        t2.setPriority(Thread.MAX_PRIORITY);
            t1.start();
        t2.start();
    }
}