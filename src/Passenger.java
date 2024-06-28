import java.util.Random;

public class Passenger implements Runnable {
    public int max;

    public Passenger() {
        // Minmum 20 passengers, maximum of 50.
        max = new Random().nextInt(20, 51);
    }

    public void run() {
        
    }   
}
