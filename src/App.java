import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class App {
    private static void generatePlanes(int numPlanes, Tower tower) {
        ArrayList<Thread> planes = new ArrayList<>();

        for (int i = 0; i < numPlanes; i++) {
            Plane plane = new Plane(i + 1, tower);
            Thread t = new Thread(plane);
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

        RefuelTruck truck = new RefuelTruck();

        Airport airport = new Airport();
        Tower tower = new Tower(airport, planeQueue);

        Thread refuThread = new Thread(truck);
        Thread towerThread = new Thread(tower);        

        refuThread.start();
        towerThread.start();

        int numPlanes = 4;
        generatePlanes(numPlanes, tower);

        towerThread.join();

        System.out.println("Finished");
    }
}
