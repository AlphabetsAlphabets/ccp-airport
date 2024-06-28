import java.util.concurrent.Semaphore;

public class Airport {
    public Semaphore runway = new Semaphore(1);
    public Semaphore gate = new Semaphore(3);

    public RefuelTruck truck;

    public Airport() {
        truck = new RefuelTruck();
    }
}
