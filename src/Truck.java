import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Truck implements Runnable {
    // Information from blocking queue:
    // https://www.baeldung.com/java-blocking-queue
    private BlockingQueue<Plane> queue;
    private String threadName;
    private Lock lock = new ReentrantLock();

    Truck(BlockingQueue<Plane> queue) {
        this.queue = queue;
    }

    public void run() {
        Thread.currentThread().setName("FuelTruckThread - ");
        threadName = Thread.currentThread().getName();
        System.out.println(threadName + "Fuel truck ready and waiting!");

        try {
            lock.lock();

            while (!queue.isEmpty()) {
                Plane plane = queue.poll();

                if (!plane.readyToRefuel.get()) {
                    // If the plane isn't ready, put it back into the queue
                    // to avoid checking it again. Then check the next plane.
                    queue.add(plane);
                    continue;
                }

                System.out.println(threadName + "Currently servicing Plane " + plane.id);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                System.out.println(threadName + "Done with Plane " + plane.id);
                plane.readyToRefuel.set(false); // To signal the plane has a full tank.
            }
        } finally {
            lock.unlock();
        }

        System.out.println(threadName + "No more planes to service.");
    }
}
