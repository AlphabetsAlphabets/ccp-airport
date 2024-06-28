import java.util.Random;

public class Plane implements Runnable {
    public int id;
    private Tower tower;
    public int passengers;

    private String threadName;

    public Plane(int id, Tower tower) {
        passengers = new Random().nextInt(20, 51);
        this.id = id;
        this.tower = tower; // One tower shared across multiple
                            // plane threads
    }

    public void run() {
        Thread.currentThread().setName("Plane " + id + " thread: ");
        threadName = Thread.currentThread().getName();

        land();

        postLandingTasks();

        depart();
    }

    private void land() {
        System.out.println(threadName + "requesting tower to land.");
        tower.land(this);
    }

    private void depart() {
        tower.depart(this);
    }

    private void postLandingTasks() {
        handlePassengers();
        tower.refuel(this); // This is probably wrong, this runs concurrently.
    }

    private void handlePassengers() {
        Passenger passenger = new Passenger(passengers, this);
        Thread passThread = new Thread(passenger);

        passThread.start();
        try {
            passThread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
