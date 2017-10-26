import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;


public class SemaphoresTest
{
    @Test
    public void threadLocal()
    {
        // todo: ThreadLocal<String> tl = new ThreadLocal<>(); // storage dla watków
    }

    @Test
    public void testCountDownLatch()
    {
        int n = 200;
        CountDownLatch startSignal = new CountDownLatch(1);

        // set up thread and make them wait for signal to start
        for (int i = 0; i < n; i++) {

            new Thread(() -> {
                try{
                    startSignal.await();
                    System.out.println("Thread started: " + Thread.currentThread().getName());
                }
                catch (InterruptedException e){}
            }).start();
        }


        System.out.println("Ready to go");
        // signal to start
        startSignal.countDown();
        //CyclicBarrier
    }

    // Reentrant lock : lock & release

    @Test
    public void ThreadSafeCollectionsTest()
    {
        Map<Integer, String> hashMap =  new ConcurrentHashMap<Integer, String>();//new HashMap<Integer, String>();
        int n = 500;
        CountDownLatch start = new CountDownLatch(n);

        for (int i = 0; i < n; i++) {
            int finalI = i;
            new Thread(() -> {

                for (int j = 0; j < 10; j++) {
                    hashMap.putIfAbsent(finalI*100000 + j, Thread.currentThread().getName() + " " + finalI + " of " + j);
                }
                start.countDown();

            }).start();
        }

        try {
            start.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Map size is " + hashMap.size()); // expected 5000
        //Assert.assertArrayEquals();
    }

    // synchornized collection
    @Test
    public void synchronizedCollection()
    {
        HashMap<Integer, String> hashMap = new HashMap<Integer, String>();

        // Ways to synchronize
        ConcurrentHashMap<Integer, String> conchashMap;
        Map<Integer, String> ts;
        ts = Collections.synchronizedMap(hashMap); // wolniejsze niz CuncurrentHashMap
        Semaphore s = new Semaphore(4);

        /*
            Blokady:

            ReentrantLock
            Sempaphore
            CountDownLatch
            CyclicBarrier (rozszerzenie CountDownLatch o mozliwosc reset-u)

            Kolekcje:

            Collections.synchronizedXXX
            ConcurrentHashMap
            CopyOnUnitArrayList
            CopyOnUnitArraySet
            ArrayBlockingDeque
            LinkedBlockingDeque
         */
    }
}
