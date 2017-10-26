import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Stream;

public class ProducerConsumerBlocking {

    public static final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(5);

    public static class Producer implements Runnable
    {

        @Override
        public void run() {
            Stream.iterate(0, i -> i + 1)
                    .limit(20)
                    .forEach(i -> {
                        try {
                            queue.put(" x " + i); // waits for empty space (add would not work here)
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Produced " + i);
                    });
        }

    }

    public static class Consumer implements Runnable
    {

        @Override
        public void run() {
            while(true) {


                try {
                    System.out.println("Consumed " + queue.take()); // use take instaed of remove to wait for available elements
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) {
       new Thread( new Producer()).start();
       new Thread( new Consumer()).start();
    }
}
