import java.util.Random;

public class Plane implements Runnable {
    public int id;
    // 50 max passengers, range is 0 to 50
    private int passengers = new Random().nextInt(51);

    private Tower tower;

    Plane(int id, Tower tower) {
        this.id = id;
        this.tower = tower;
    }

    @Override
    public void run() {
        this.tower.land(this);
        this.disembark_then_embark_passengers();
    }

    public void land(int gate_id) {
        this.land_at_runway();
        this.dock_at_gate(gate_id);
    }

    public void disembark_then_embark_passengers() {
        this.unload_passengers();
        this.load_passengers();
    }

    private synchronized void unload_passengers() {
        System.out.println(this.id + " - Disembarking passengers...");
        for (int i = 0; i < passengers; i++) {
            try {
                Thread.sleep(500); // simulating passengers leaving the plane.
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            passengers--;
        }
        System.out.println(this.id + " - Plane is now empty.");
    }

    private synchronized void load_passengers() {
        System.out.println(this.id + " - Embarking passengers...");
        passengers = new Random().nextInt(50);
        for (int i = 0; i < passengers; i++) {
            try {
                Thread.sleep(500); // simluating passengers embarking onto the plane.
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println(this.id + " - All passengers are on board.");
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
