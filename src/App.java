import java.util.LinkedList;
import java.util.Queue;

public class App {
    public static void main(String[] args) throws Exception {
        Airport airport = new Airport();

        Queue<Plane> planeQueue = new LinkedList<>();
        Tower tower = new Tower(airport, planeQueue);
        Plane p1 = new Plane(1, tower);
        Plane p2 = new Plane(2, tower);
        Plane p3 = new Plane(3, tower);
        Plane p4 = new Plane(4, tower);
        
        planeQueue.add(p1);
        planeQueue.add(p2);
        // planeQueue.add(p3);
        // planeQueue.add(p4);

        Thread towerThread = new Thread(tower);


        towerThread.start();

        
        Thread p1Thread = new Thread(p1);
        Thread p2Thread = new Thread(p2);

        p1Thread.start();
        p2Thread.start();

        towerThread.join();

        System.out.println("Finished");
    }
}
