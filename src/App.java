import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class App {
    private static void generatePlanes(int numPlanes, Tower tower, BlockingQueue<Plane> queue) {
        ArrayList<Thread> planes = new ArrayList<>();

        for (int i = 0; i < numPlanes; i++) {
            Plane plane = new Plane(i + 1, tower);
            Thread t = new Thread(plane);
            queue.add(plane);
            planes.add(t);
            t.start();
        }

        for (var thread: planes) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        BlockingQueue<Plane> planeQueue = new LinkedBlockingQueue<>();

        Truck truck = new Truck(planeQueue);
        Thread truckThread = new Thread(truck);
        truckThread.start();

        Airport airport = new Airport();
        
        ATCManager atcManager = new ATCManager(planeQueue);
        Tower tower = new Tower(airport, planeQueue, atcManager);
        Thread towerThread = new Thread(tower);

        towerThread.start();


        int numPlanes = 4;
        generatePlanes(numPlanes, tower, planeQueue);

        atcManager.tower = tower;
        Thread statsThread = new Thread(atcManager);
        statsThread.start();

        towerThread.join();
        truckThread.join();
        statsThread.join();

        System.out.println("Finished");
    }
}
