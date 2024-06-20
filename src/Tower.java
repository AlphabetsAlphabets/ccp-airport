import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tower {
    private volatile ArrayList<Gate> gates = new ArrayList<>();
    private Runway runway;

    private Lock lock = new ReentrantLock();

    Tower() {
        this.gates.add(new Gate(1));
        this.gates.add(new Gate(2));
        this.gates.add(new Gate(3));

        this.runway = new Runway();
    }

    public void land(int plane_id) {
        System.out.println(plane_id + " requesting to land.");
        try {
            lock.lock();
            int unoccupied_gate = this.find_unoccupied_gate();
            boolean occupied_runway = this.runway.is_occupied();
            boolean can_land = unoccupied_gate != -1 && !occupied_runway;

            if (!can_land) {
                System.out.println("(" + plane_id + ") Cannot land. Reason.");
                String gate_status = (unoccupied_gate == -1) ? "No. All gates occupied." : "Yes. There is at least one free gate.";
                System.out.println("(" + plane_id + ") Is there a free gate? " + gate_status);

                System.out.println("(" + plane_id + ") Is runway occupied? " + occupied_runway);
                return;
            }

            this.runway.occupy();
            System.out.println("Plane " + plane_id + " coasting to gate " + unoccupied_gate);

            for (var gate : gates) {
                if (gate.get_id() == unoccupied_gate) {
                    gate.occupy();
                }
            }

            this.runway.free();
        } finally {
            lock.unlock();
        }
    }

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
