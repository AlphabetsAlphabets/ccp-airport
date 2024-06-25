import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class FuelTruck implements Runnable {
    // Information from blocking queue:
    // https://www.baeldung.com/java-blocking-queue
    private BlockingQueue<Plane> queue;
    private String threadName;
    private volatile boolean running = true;

    FuelTruck(BlockingQueue<Plane> queue) {
        this.queue = queue;
    }

    public void run() {
        Thread.currentThread().setName("FuelTruckThread");
        threadName = Thread.currentThread().getName();
        System.out.println(threadName + " - Fuel truck ready and waiting!");

        try {
            Plane plane;
            // If there are no more planes within the last 10 seconds
            // and the queue is empty, then the truck will shutdown.
            while ((plane = queue.poll(10L, TimeUnit.SECONDS)) != null && queue.isEmpty()) {
                fuelPlane(plane);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(threadName + " - No more planes. Fuel truck shutting down.");
    }

    public void fuelPlane(Plane plane) {
        System.out.println(threadName + " - Currently refueling plane " + plane.id);
        try {
            Thread.sleep(1000); // Refueling takes time.
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(threadName + " - Plane " + plane.id + " is now full!");
    }

    public void stop() {
        running = false;
    }
}
