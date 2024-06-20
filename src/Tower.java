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
        String plane_info = "(" + plane_id + ") ";
        System.out.println(plane_info + "Landing request.");

        try {
            lock.lock();
            int unoccupied_gate = this.find_unoccupied_gate();
            boolean occupied_runway = this.runway.is_occupied();
            boolean can_land = unoccupied_gate != -1 && !occupied_runway;

            if (!can_land) {
                System.out.println(plane_info + "Cannot land. Reason:");
                String gate_status = (unoccupied_gate == -1) ? "No. All gates occupied." : "Yes. There is at least one free gate.";
                System.out.println(plane_info + "Is there a free gate? " + gate_status);
                System.out.println(plane_info + "Is runway occupied? " + occupied_runway);
                return;
            }

            System.out.println(plane_info + "landing on runway");
            try {
                Thread.sleep(1000); // landing takes time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.runway.occupy();
            System.out.println(plane_info + "Landed at runway successfully.");
            System.out.println(plane_info + "coasting to gate " + unoccupied_gate);
            
            try {
                Thread.sleep(1000); // Coasting to a gate takes time.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

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
