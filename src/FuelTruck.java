import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

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
            // If there are no planes within 5 seconds then the fuel truck retires.
            while ((plane = queue.poll(5L, TimeUnit.SECONDS)) != null) {
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
}
