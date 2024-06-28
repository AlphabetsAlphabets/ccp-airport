import java.util.Random;

public class CleanUpCrew implements Runnable {
    public int plane_id;

    public CleanUpCrew(int plane_id) {
        this.plane_id = plane_id;
    }

    public void run() {
        Random random = new Random();
        Thread.currentThread().setName("CleanUpCrew Thread " + plane_id + " - ");
        String threadName = Thread.currentThread().getName();

        System.out.println(threadName + "Cleaning up Plane " + plane_id);
        try {
            Thread.sleep(random.nextInt(1000, 3000));
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(threadName + "Clean up for Plane " + plane_id + " is done.");
    }
}
