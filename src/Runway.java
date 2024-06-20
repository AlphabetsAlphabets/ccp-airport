public class Runway {
    private boolean occupied = false;

    Runway() {}
    
    public synchronized void occupy() {
        System.out.println("Occupying runway.");
        this.occupied = true;
    }

    public void free() {
        System.out.println("Runway freed.");
        this.occupied = false;
    }

    public synchronized boolean is_occupied() {
        return this.occupied;
    }
}
