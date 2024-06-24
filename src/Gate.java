public class Gate {
    private int id;
    private boolean occupied = false;
    // -1 means not occupied
    private int occupied_by_plane = -1;

    Gate(int id) {
        this.id = id;
    }

    public boolean occupied_by(int plane_id) {
        return occupied_by_plane == plane_id;
    }

    public void occupy(int plane_id) {
        System.out.println("Gate " + this.id + " is now occupied.");
        occupied_by_plane = plane_id;
        this.occupied = true;
    }

    public void free() {
        System.out.println("Gate " + this.id + " is now free.");
        this.occupied = false;
    }

    public synchronized boolean is_occupied() {
        return this.occupied;
    }

    public synchronized int get_id() {
        return this.id;
    }
}