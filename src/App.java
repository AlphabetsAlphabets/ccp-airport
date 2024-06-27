import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class App {
    public static void main(String[] args) throws Exception {
        BlockingQueue<Plane> planeQueue = new LinkedBlockingQueue<>();

        RefuelTruck truck = new RefuelTruck();


        Airport airport = new Airport();
        Tower tower = new Tower(airport, planeQueue);

        Plane p1 = new Plane(1, tower);
        Plane p2 = new Plane(2, tower);
        Plane p3 = new Plane(3, tower);
        Plane p4 = new Plane(4, tower);
        
        planeQueue.add(p1);
        planeQueue.add(p2);
        // planeQueue.add(p3);
        // planeQueue.add(p4);

        Thread refuThread = new Thread(truck);
        Thread towerThread = new Thread(tower);        

        Thread p1Thread = new Thread(p1);
        Thread p2Thread = new Thread(p2);

        refuThread.start();

        towerThread.start();
        p1Thread.start();
        p2Thread.start();

        towerThread.join();

        System.out.println("Finished");
    }
}
