public class Plane implements Runnable {
    private int id;
    private Tower tower;

    Plane(int id, Tower tower) {
        this.id = id;
        this.tower = tower;
    }

    @Override
    public void run() {
        this.tower.land(this.id);
    }
}
