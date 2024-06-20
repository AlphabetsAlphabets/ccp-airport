public class Gate {
    private int id;
    private boolean occupied = false;

    Gate(int id) {
        this.id = id;
    }

    public void occupy() {
        System.out.println("Gate " + this.id + " is now occupied.");
        this.occupied = true;

    }

    public void free() {
        this.occupied = false;

    }

    public synchronized boolean is_occupied() {
        return this.occupied;
    }

    public synchronized int get_id() {
        return this.id;
    }
}
