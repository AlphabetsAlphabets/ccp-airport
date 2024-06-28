import java.util.concurrent.BlockingQueue;

public class Truck implements Runnable {
    // Information from blocking queue:
    // https://www.baeldung.com/java-blocking-queue
    private BlockingQueue<Plane> queue;
    private String threadName;

    Truck(BlockingQueue<Plane> queue) {
        this.queue = queue;
    }

    public void run() {
        Thread.currentThread().setName("FuelTruckThread - ");
        threadName = Thread.currentThread().getName();
        System.out.println(threadName + "Fuel truck ready and waiting!");

        while (!queue.isEmpty()) {
            Plane plane = queue.peek(); // first check if plane has landed.
            if (plane.readyToRefuel.get() == true) {
                plane = queue.poll(); // if landed, then remove from the queue.
                System.out.println(threadName + "Currently servicing Plane " + plane.id);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println(threadName + "Done with Plane " + plane.id);
                plane.readyToRefuel.set(false); // To signal the plane has a full tank.
            }
        }

        System.out.println(threadName + "No more planes to service.");
    }
}
