import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Passengers implements Runnable {
    private int number = new Random().nextInt(51);
    public Lock lock = new ReentrantLock();
    public Condition plane_is_ready = lock.newCondition();

    private void disembark() {
        for (int i = number; i != 0; i--) {
            try {
                Thread.sleep(1000); // each passenger takes 1 second
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void embark() {
        number = new Random().nextInt(51);
        for (int i = 0; i < number; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } // each passenger takes 1 second
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                plane_is_ready.await();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            break;
        }

        disembark();
        embark();
    }
}
