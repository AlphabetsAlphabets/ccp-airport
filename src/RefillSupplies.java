import java.util.Random;

public class RefillSupplies implements Runnable {
    public int plane_id;

    public RefillSupplies(int plane_id) {
        this.plane_id = plane_id;
    }

    public void run() {
        Random random = new Random();
        Thread.currentThread().setName("RefillSupply Thread " + plane_id + " - ");
        String threadName = Thread.currentThread().getName();

        System.out.println(threadName + "Restocking supplies for Plane " + plane_id);
        try {
            Thread.sleep(random.nextInt(1000, 3000));
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(threadName + "Plane " + plane_id + "'s supplies are replenished done.");
    }
}
