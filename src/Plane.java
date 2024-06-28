import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Plane implements Runnable {
    public int id;
    private Tower tower;
    public int passengers;
    public AtomicBoolean readyToRefuel = new AtomicBoolean(false);

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
        Thread passThread = handlePassengers();
        Thread crewThread = cleanUp();

        passThread.start();
        crewThread.start();

        try {
            passThread.join();
            crewThread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // Wait for the plane to be done refuelling.
        while(readyToRefuel.get() == true) {}
    }

    private Thread cleanUp() {
        CleanUpCrew crew = new CleanUpCrew(id);
        Thread crewThread = new Thread(crew);

        return crewThread;
    }

    private Thread handlePassengers() {
        Passenger passenger = new Passenger(passengers, this);
        Thread passThread = new Thread(passenger);

        return passThread;
    }
}
