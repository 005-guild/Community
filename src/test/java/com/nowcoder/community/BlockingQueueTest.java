package com.nowcoder.community;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class BlockingQueueTest {

    public static void main(String[] args) {
        BlockingQueue<String> queue = new LinkedBlockingDeque<>(1);
        Producer p = new Producer(queue);
        Consumer c = new Consumer(queue);
        new Thread(p,"producer").start();
        new Thread(c,"consumer").start();
    }

}

class Consumer implements Runnable {
    private BlockingQueue<String> queue;
    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }
    @Override
    public void run() {
        try {
            while(true){
                //Thread.sleep(20);
                System.out.println("消费者消费了：" + queue.take());
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
class Producer implements Runnable{
    private BlockingQueue<String> queue;
    public Producer(BlockingQueue<String> queue){
        this.queue = queue;
    }
    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                String tmp = "a product " + i + " from:" + Thread.currentThread().getName();
                System.out.println("生产者生产了：" + tmp);
                queue.put(tmp);
                //Thread.sleep(20);
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
