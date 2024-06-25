public class Runway {
    private boolean occupied = false;

    Runway() {}
    
    public void occupy() {
        System.out.println("Occupying runway.");
        this.occupied = true;
    }

    public void free() {
        System.out.println("Runway freed.");
        this.occupied = false;
    }

    public boolean isOccupied() {
        return this.occupied;
    }
}