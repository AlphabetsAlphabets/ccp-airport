import java.util.Random;


public class Plane implements Runnable {
    public int id;
    // 50 max passengers, range is 0 to 50
    private int passengers;
    private int maxPassengers;

    private Tower tower;

    Plane(int id, Tower tower) {
        passengers  = new Random().nextInt(1, 51);
        maxPassengers = passengers;
        
        this.id = id;
        this.tower = tower;
    }

    @Override
    public void run() {
        int gate_id = tower.land(this);
        tower.fuelTruck.refuel(this);
        unloadPassengers();
        loadPassengers();
        tower.depart(this, gate_id);
    }

    public int land(int gate_id) {
        this.landAtRunway();
        this.dockAtGate(gate_id);

        return gate_id;
    }

    private void landAtRunway() {
        System.out.println(Thread.currentThread().getName() + " - Plane " + id + " is landing on runway");
        try {
            Thread.sleep(1000); // landing takes time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tower.runway.occupy();
    }

    private void dockAtGate(int gate_id) {
        System.out.println(Thread.currentThread().getName()  + " - Plane " + id + " has landed at runway successfully.");
        System.out.println(Thread.currentThread().getName() + " - Plane " + id + " is coasting to gate " + gate_id);

        try {
            Thread.sleep(1000); // Coasting to a gate takes time.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (var gate : tower.gates) {
            if (gate.get_id() == gate_id) {
                gate.occupy(id);
            }
        }

        tower.runway.free(); // Once plane is at gate, runway is free
    }

    private void unloadPassengers() {
        System.out.println(Thread.currentThread().getName() + " - Passengers are currently disembarking from plane " + id + "...");

        while (maxPassengers != 0) {
            try {
                Thread.sleep(500); // simulating passengers leaving the plane.
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            maxPassengers--;
        }
        
        System.out.println(Thread.currentThread().getName() + " - Plane is now empty.");
    }

    private void loadPassengers() {
        System.out.println(Thread.currentThread().getName() + " - Passengers are curently embarking to plane " + id + "...");
        passengers = new Random().nextInt(1, 51);
        for (int i = 0; i < passengers; i++) {
            try {
                Thread.sleep(500); // simluating passengers embarking onto the plane.
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " - All passengers are on board plane " + id + ".");
    }

}
