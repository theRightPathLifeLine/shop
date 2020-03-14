package com.shop.list.util;

public class MyTest extends Thread {
    public static int i = 0;
    public static Object obj = new Object();

    String s = "";
    int c = 0;

    public MyTest(String s, int c) {
        this.s = s;
        this.c = c;
    }

    @Override
    public void run() {
        while (true)
            synchronized (obj){
                while (i % 3 != c){
                    try {
                        System.out.println("wait:" + i + ":" + s + ":" + c);
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(s);
                i++;
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                obj.notifyAll();
            }
    }
}
