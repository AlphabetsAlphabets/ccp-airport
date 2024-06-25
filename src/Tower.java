import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tower {
    public ArrayList<Gate> gates = new ArrayList<>();
    public Runway runway;
    public FuelTruck refuelling_truck;

    private Lock lock = new ReentrantLock();
    private Condition can_land = lock.newCondition();
    
    private Condition can_depart = lock.newCondition();
    private Condition cannot_depart = lock.newCondition();

    Tower() {
        gates.add(new Gate(1));
        gates.add(new Gate(2));
        gates.add(new Gate(3));

        runway = new Runway();
        refuelling_truck = new FuelTruck();
    }

    public void depart(Plane plane, int gate_id) {
        try {
            lock.lock();
            // To depart only one condition is met: the runway must be unoccupied.
            while (!plane_can_depart()) {
                try {
                    this.can_depart.await();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            for (var gate: gates) {
                if (gate.occupied_by(plane.id)) {
                    gate.free();
                    break;
                }
            }

            System.out.println(Thread.currentThread().getName() + " - Plane " + plane.id + " is departing.");
            System.out.println(Thread.currentThread().getName() + " - is moving to the runway and is now departing.");
            // cannot_depart.signalAll();
            runway.occupy();
            try {
                Thread.sleep(2000); // simulate moving to runway and departing.
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            runway.free();
            can_depart.signalAll(); // tells other planes that are waiting to depart that they can.
            can_land.signalAll(); // tells planes waiting to land that they can land as at this point
                                  // a gate and a runway is free.
        } finally {
            lock.unlock();
        }
    }

    /**
     * Checks if the runway is occupied. Sends the cannot_depart signal if it is.
     * Sends the can_depart signal if it isn't.
     * 
     * This function is used in isolation specifically for plane depatures.
     * @return can_depart (bool)
     */
    private boolean plane_can_depart() {
        boolean can_depart = !runway.is_occupied();
        if (can_depart) {
            // this.cannot_depart.signalAll();
        } else {
            this.can_depart.signalAll();
        }

        return can_depart;
    }

    public void request_refuel(Plane plane) {
        refuelling_truck.fuel_plane(plane);
    }

    public int land(Plane plane) {
        System.out.println(Thread.currentThread().getName() + " - Plane " + plane.id + " requesting to land.");
        int gate_id = -1;

        try {
            lock.lock();
            while ((gate_id = plane_can_land()) == -1) { // -1 means there are no free gates
                try {
                    can_land.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
  
            plane.land(gate_id);
        } finally {
            lock.unlock();
        }

        return gate_id;
    }


    /**
     * Checks if a plane can land.
     * @return -1 if can't land. Otherwise returns gate id.
     */
    private int plane_can_land() {
        int unoccupied_gate = this.find_unoccupied_gate();
        boolean occupied_runway = this.runway.is_occupied();
        boolean can_land = unoccupied_gate != -1 && !occupied_runway;

        if (can_land) {
            this.can_land.signalAll();
            return unoccupied_gate;
        }
        
        return -1;
    }

    /**
     * Finds an unoccupied gate and returns its id.
     * @return gate id (int)
     */
    private int find_unoccupied_gate() {
        for (var gate : gates) {
            if (!gate.is_occupied()) {
                System.out.println("Unoccupied gate found.");
                return gate.get_id();
            }
        }

        return -1;
    }

}
