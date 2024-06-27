public class Plane implements Runnable {
    public int id;
    private Tower tower;

    public Plane(int id, Tower tower) {
        this.id = id;
        this.tower = tower; // One tower shared across multiple
                            // plane threads
    }

    public void run() {
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
        tower.land(this);
        
    }
}
