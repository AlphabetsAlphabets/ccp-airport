import java.util.ArrayList;

public class Tower {
    private volatile ArrayList<Gate> gates = new ArrayList<>();
    private Runway runway;

    Tower() {
        this.gates.add(new Gate(1));
        this.gates.add(new Gate(2));
        this.gates.add(new Gate(3));

        this.runway = new Runway();
    }

    public void land(int plane_id) {
        int unoccupied_gate = this.find_unoccupied_gate();
        boolean can_land = unoccupied_gate != -1 && !this.runway.is_occupied();

        while(!can_land) {
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            unoccupied_gate = this.find_unoccupied_gate();
            can_land = unoccupied_gate != -1 && !this.runway.is_occupied();
        }
        
        synchronized (this.runway) {
            this.runway.occupy();

            System.out.println("Plane " + plane_id + " coasting to gate " + unoccupied_gate);
            synchronized (this.gates) {
                for (var gate : gates) {
                    if (gate.get_id() == unoccupied_gate) {
                        gate.occupy();
                    }
                }
            }
            this.runway.free();
        }
    }

    private int find_unoccupied_gate() {
        synchronized (this.gates) {
            for (var gate : gates) {
                if (!gate.is_occupied()) {
                    System.out.println("Unoccupied gate found.");
                    return gate.get_id();
                }
            }
        }

        return -1;
    }
}
