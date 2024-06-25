import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class App {
    public static void ideal_scenario() throws InterruptedException {
        int maxPlanes = 4;
        BlockingQueue<Plane> refuelQueue = new LinkedBlockingQueue<Plane>();

        Tower tower = new Tower(refuelQueue);
        FuelTruck fuelTruck = new FuelTruck(refuelQueue);
        Thread fuelTruckThread = new Thread(fuelTruck);

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
    
    public static void main(String[] args) throws Exception {
        ideal_scenario();
    }
}
