public class Plane implements Runnable {
    public int id;
    private Tower tower;

    Plane(int id, Tower tower) {
        this.id = id;
        this.tower = tower;
    }

    @Override
    public void run() {
        this.tower.land(this);
    }

    public void land(int gate_id) {
        this.land_at_runway();
        this.dock_at_gate(gate_id);
    }

    private void land_at_runway() {
        System.out.println(id + " - landing on runway");
        try {
            Thread.sleep(1000); // landing takes time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tower.runway.occupy();
    }

    private void dock_at_gate(int gate_id) {
        System.out.println(id + " - landed at runway successfully.");
        System.out.println(id + " - coasting to gate " + gate_id);
        
        try {
            Thread.sleep(1000); // Coasting to a gate takes time.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (var gate : tower.gates) {
            if (gate.get_id() == gate_id) {
                gate.occupy();
            }
        }

        tower.runway.free(); // Once plane is at gate, runway is free
    }
}
