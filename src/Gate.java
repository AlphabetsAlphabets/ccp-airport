public class Gate {
    private int id;
    private boolean occupied = false;
    // -1 means not occupied
    private int occupiedByPlane = -1;

    Gate(int id) {
        this.id = id;
    }

    public boolean occupiedBy(int plane_id) {
        return occupiedByPlane == plane_id;
    }

    public void occupy(int plane_id) {
        System.out.println("Gate " + id + " is now occupied.");
        occupiedByPlane = plane_id;
        occupied = true;
    }

    public void free() {
        System.out.println("Gate " + id + " is now free.");
        occupied = false;
    }

    public synchronized boolean is_occupied() {
        return occupied;
    }

    public synchronized int get_id() {
        return id;
    }
}