import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tower {
    public ArrayList<Gate> gates = new ArrayList<>();
    public Runway runway;

    private Lock lock = new ReentrantLock();
    private Condition can_land = this.lock.newCondition();
    private Condition cannot_land = this.lock.newCondition();

    Tower() {
        this.gates.add(new Gate(1));
        this.gates.add(new Gate(2));
        this.gates.add(new Gate(3));

        this.runway = new Runway();
    }

    public void land(Plane plane) {
        String plane_info = "(" + plane.id + ") ";
        System.out.println(plane_info + "Landing request.");
        int gate_id = -1;

        try {
            lock.lock();
            while ((gate_id = this.plane_can_land()) == -1) { // -1 means there are no free gates
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
    }

    public void request_refuel() {

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
