import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tower implements Runnable {
    private String threadName;

    public Airport airport;
    private Queue<Plane> queue = new LinkedList<>();

    private Lock runwayLock = new ReentrantLock();
    private Condition runwayIsFree = runwayLock.newCondition();

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
        try {
            runwayLock.lock();
            while (airport.runway.isOccupied()) {
                try {
                    System.out.println(threadName + "Runway is occupied. Plane " + plane.id + " is not allowed to land.");
                    runwayIsFree.await();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            try {
                gateLock.lock();
                for (var gate : airport.gates) {
                    if (gate.is_occupied()) continue;

                    System.out.println(threadName + " Plane " + plane.id + " is cleared for landing. Please coast to Gate " + gate.get_id());
                    airport.runway.occupy();
                    gate.occupy(plane.id);

                    airport.runway.free();
                    runwayIsFree.signal();
                    break;
                }
            } finally {
                gateLock.unlock();
            }
        } finally {
            runwayLock.unlock();
        }
    }

    public void depart(Plane plane) {
        System.out.println(threadName + "Plane " + plane.id + " is requesting to depart.");
        try {
            runwayLock.lock();
            while (airport.runway.isOccupied()) {
                try {
                    System.out.println(threadName + "Runway is occupied. Plane " + plane.id + " is not allowed to depart.");
                    runwayIsFree.await();
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
                airport.runway.free();
                runwayIsFree.signal();
            } finally {
                gateLock.unlock();
            }
        } finally {
            runwayLock.unlock();
        }
    }
}
