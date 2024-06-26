import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class App {
    public static void main(String[] args) throws Exception {
        // Congested scenario
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

        // 6 planes landing
        int maxPlanes = 6;
        for (int i = 0; i < maxPlanes; i++) {
            int delay = new Random().nextInt(1000, 6000);
            Thread.sleep(delay); // Randomized arrival for each plane.

            Plane plane;
            plane = new Plane(i + 1, tower);
            if (i == 2) {
                // 3rd plane has an emergency.
                plane.has_emergency();
            }

            Thread thread = new Thread(plane);
            threads.add(thread);
            thread.start();
        }
 
        for (var thread: threads) {
            thread.join();
        }

        System.out.println("Finished");
    }
}
