import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tower implements Runnable {
    private String threadName;

    public Airport airport;
    private Queue<Plane> queue = new LinkedList<>();

    private Semaphore runway = new Semaphore(1);

    private Lock gateLock = new ReentrantLock();

    public Tower(Airport airport, Queue<Plane> queue) {
        this.airport = airport;
        this.queue = queue;
    }

    public void run() {
        Thread.currentThread().setName("Tower thread: ");
        threadName = Thread.currentThread().getName();

        System.out.println(threadName + "Tower has started running.");
    }

    public void refuel(Plane plane) {
        airport.truck.refuel(plane);
    }

    public void land(Plane plane) {
        System.out.println(threadName + " Plane " + plane.id + " is requesting to land.");
        
        while (!runway.tryAcquire()) {
            System.out.println(threadName + "Plane " + plane.id + " request to land denied. Runway is not available.");
            try {
                Thread.sleep(1000); // Simulate pilot waiting a few minutes before requesting for landing.
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        System.out.println(threadName + "Plane " + plane.id + " landing request approved. Please coast to runway.");
            
        try {
            gateLock.lock();
            for (var gate : airport.gates) {
                if (gate.is_occupied()) continue;

                System.out.println(threadName + " Plane " + plane.id + " is cleared for landing. Please coast to Gate " + gate.get_id());
                gate.occupy(plane.id);

                runway.release();
                break;
            }
        } finally {
            gateLock.unlock();
        }
    }

    public void depart(Plane plane) {
        System.out.println(threadName + "Plane " + plane.id + " is requesting to depart.");
       
        while (!runway.tryAcquire()) {
            System.out.println(threadName + "Depature for Plane " + plane.id + " denied. Runway is not available.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        try {
            gateLock.lock();
            for (var gate : airport.gates) {
                if (gate.occupiedBy(plane.id)) {    
                    gate.free();
                    break;
                }
            }

            System.out.println(threadName + "Runway is now free. Plane " + plane.id + " is cleared for depature.");
            runway.release();
        } finally {
            gateLock.unlock();
        }
    }
}
