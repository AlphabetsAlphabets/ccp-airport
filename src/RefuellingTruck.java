import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import java.util.concurrent.locks.Condition;

public class RefuellingTruck implements Runnable {
    // read only public variable
    private boolean occupied = false;
    private Lock lock = new ReentrantLock();
    private Condition service_requested = lock.newCondition();

    RefuellingTruck() {}

    public void run() {
        try {
            lock.lock();
        } finally {
            lock.unlock();
        }
    }

    public boolean is_occupied() {
        return this.occupied;
    }

    public void fuel_plane(int id) {
        System.out.println(id + " - Refuelling truck is now servicing plane " + id);
        occupied = true;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        System.out.println(id + " - Refuelling truck is done with plane " + id);
        occupied = false;
    }

}
