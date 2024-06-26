import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class App {
    public static void main(String[] args) throws Exception {
        int maxPlanes = 4;
        BlockingQueue<Plane> refuelQueue = new LinkedBlockingQueue<Plane>();

        FuelTruck fuelTruck = new FuelTruck(refuelQueue);
        Tower tower = new Tower(fuelTruck);

        Thread fuelTruckThread = new Thread(fuelTruck);
        
        // This thread serves the planes. Once the planes have finished their tasks.
        // The thread will end. Which is why it is set as a daemon thread.
        // Source: https://www.baeldung.com/java-daemon-thread
        fuelTruckThread.setDaemon(true);
        fuelTruckThread.start();

        ArrayList<Thread> threads = new ArrayList<>();

        for (int i = 0; i < maxPlanes; i++) {
            Plane plane = new Plane(i + 1, tower);
            Thread thread = new Thread(plane);
            thread.start();
            threads.add(thread);
        }

        for (var thread : threads) {
            thread.join();
        }
        
        System.out.println("Finished");
    }
}
