public class Plane implements Runnable {
    public int id;
    private Tower tower;

    private String threadName;

    public Plane(int id, Tower tower) {
        this.id = id;
        this.tower = tower; // One tower shared across multiple
                            // plane threads
    }

    public void run() {
        Thread.currentThread().setName("Plane " + id + " thread: ");
        threadName = Thread.currentThread().getName();

        land();
        unload_passengers();
        load_passengers();
        depart();
    }

    private void depart() {
        tower.depart(this);
    }

    private void load_passengers() {
        
    }

    private void unload_passengers() {
        
    }

    private void land() {
        System.out.println(threadName + "requesting tower to land.");
        tower.land(this);
    }
}
