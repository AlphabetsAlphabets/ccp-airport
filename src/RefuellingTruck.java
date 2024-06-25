import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import java.util.concurrent.locks.Condition;

public class RefuellingTruck {
    private boolean occupied;
    private Lock lock = new ReentrantLock();
    private Condition servicing_plane = lock.newCondition();
    private Condition not_servicing_pane = lock.newCondition();

    RefuellingTruck() {
        occupied = false;
    }

    public boolean is_occupied() {
        if (occupied)
            servicing_plane.signal();
        else
            not_servicing_pane.signal();

        return occupied;
    }

    private void occupy(Plane plane) {
        System.out.println(Thread.currentThread().getName()  + " - Refuelling truck is now servicing plane " + plane.id);
        occupied = true;
        servicing_plane.signal();
    }

    private void free(Plane plane) {
        System.out.println(Thread.currentThread().getName()  + " - Refuelling truck is done with plane " + plane.id);
        occupied = false;
        not_servicing_pane.signal();
    }

    public void fuel_plane(Plane plane) {
        try {
            lock.lock();
            while (occupied) {
                try {
                    not_servicing_pane.await();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            occupy(plane);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            free(plane);
        } finally {
            lock.unlock();
        }
    }
}
