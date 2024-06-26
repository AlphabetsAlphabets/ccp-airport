import java.util.concurrent.BlockingQueue;

public class FuelTruck implements Runnable {
    // Information from blocking queue:
    // https://www.baeldung.com/java-blocking-queue
    private BlockingQueue<Plane> queue;
    private String threadName;

    FuelTruck(BlockingQueue<Plane> queue) {
        this.queue = queue;
    }

    public void run() {
        Thread.currentThread().setName("FuelTruckThread");
        threadName = Thread.currentThread().getName();
        System.out.println(threadName + " - Fuel truck ready and waiting!");

        Plane plane;

        try {
            while (true) {
                // multiple threads need access to the queue to check it's state.
                // make sure it is owned to ensure that what it reads is accurate.
                synchronized(queue) {
                    while(queue.isEmpty()) {
                        queue.wait();
                    }
                
                    plane = queue.take();
                    refuel(plane);
                }
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(threadName + " - No more planes. Fuel truck shutting down.");
    }

    public void refuel(Plane plane) {
        System.out.println(threadName + " - Currently refueling plane " + plane.id);
        try {
            Thread.sleep(2000); // Refueling takes time.
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(threadName + " - Plane " + plane.id + " is now full!");
    }

    public void add(Plane plane) {
        // multiple threads will run this function. Ensure that only
        // one thread owns the queue as the queue's data will be modified.
        synchronized(queue) {
            queue.add(plane);
            queue.notify();
        }

    }
}
