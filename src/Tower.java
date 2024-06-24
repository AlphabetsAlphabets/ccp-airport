import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tower {
    public ArrayList<Gate> gates = new ArrayList<>();
    public Runway runway;
    public RefuellingTruck refuelling_truck;

    private Lock lock = new ReentrantLock();
    private Condition can_land = lock.newCondition();
    private Condition cannot_land = lock.newCondition();
    
    private Condition can_depart = lock.newCondition();
    private Condition cannot_depart = lock.newCondition();

    Tower() {
        gates.add(new Gate(1));
        gates.add(new Gate(2));
        gates.add(new Gate(3));

        runway = new Runway();
        refuelling_truck = new RefuellingTruck();
    }

    public boolean depart(Plane plane, int gate_id) {
        boolean can_depart = false;

        try {
            lock.lock();

            while (!(can_depart = plane_can_depart())) {
                try {
                    this.can_depart.await();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } finally {
            lock.unlock();
        }

        return can_depart;
    }

    private boolean plane_can_depart() {
        boolean can_depart = !runway.is_occupied();
        if (can_depart) {
            this.cannot_depart.signal();
        } else {
            this.can_depart.signal();
        }

        return can_depart;
    }

    public void request_refuel(Plane plane) {
        refuelling_truck.fuel_plane(plane);
    }

    public int land(Plane plane) {
        System.out.println(plane.id + "- Landing request.");
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

    /**
     * Checks if a plane can land.
     * @return -1 if can't land. Otherwise returns gate id.
     */
    private int plane_can_land() {
        int unoccupied_gate = this.find_unoccupied_gate();
        boolean occupied_runway = this.runway.is_occupied();
        boolean can_land = unoccupied_gate != -1 && !occupied_runway;

        if (can_land) {
            this.can_land.signal();
            return unoccupied_gate;
        }
        
        cannot_land.signal();
        return -1;
    }
}
