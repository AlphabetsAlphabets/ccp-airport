import java.util.Random;

public class Passenger implements Runnable {
    public int max;
    public Plane plane;

    public Passenger(int max, Plane plane) {
        // Minmum 20 passengers, maximum of 50.
        this.max = max;
        this.plane = plane;
    }

    public void run() {
        Random random = new Random();
        Thread.currentThread().setName("Passenger Thread " + plane.id + " - ");
        String threadName = Thread.currentThread().getName();

        try {
            Thread.sleep(random.nextInt(1000, 5000));
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(threadName + "" + max + " passengers are disembarking from Plane " + plane.id);

        int newPassengers = random.nextInt(20, 51);
        plane.passengers = newPassengers;

        try {
            Thread.sleep(random.nextInt(1000, 5000));
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(threadName + "All passengers have disembarked. Embarking " + newPassengers + " new passengers into Plane " + plane.id);
    }   
}