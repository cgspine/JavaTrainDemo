import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by cgspine on 16/7/21.
 */
public class ThreadDemo {
    static class MyThread extends Thread {
        public MyThread(String name) {
            setName(name);
        }

        @Override
        public void run() {
            System.out.println("Thread name is " +Thread.currentThread().getName());
        }
    }


    public static void main(String[] args){
        new MyThread("Custom").start();




//
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Thread name is " + Thread.currentThread().getName());
//            }
//        };
//        new Thread(runnable).start();


//        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(100);
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 60 * 60 * 1000, TimeUnit.MILLISECONDS, queue);
//        for(int i=0; i<500;i++){
//            executor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println("Thread name is " +Thread.currentThread().getName());
//                }
//            });
//        }
//        System.out.println("Task add finished");

    }
}
