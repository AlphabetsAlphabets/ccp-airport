import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class ATCManager implements Runnable {
    private BlockingQueue<Plane> queue;
    public ArrayList<Long> waitTimes = new ArrayList<>();
    public int passengersServed = 0;
    public Tower tower;

    public ATCManager(BlockingQueue<Plane> queue) {
        this.queue = queue;
    }

    public ArrayList<Long> statistics() {
        ArrayList<Long> timeInfo = new ArrayList<>();

        return timeInfo;
    }

    public void run() {
        while (!queue.isEmpty()) {} // make sure the queue is empty.

        long longestWaitTime = 0;
        long shortestWaitTime = 0;
        long totalWaitTime = 0;
        
        for(var waitTime : waitTimes) {
            if (longestWaitTime == 0) {
                longestWaitTime = waitTime;
            }
            if (waitTime > longestWaitTime) {
                longestWaitTime = waitTime;
            }
            
            if (shortestWaitTime == 0) {
                shortestWaitTime = waitTime;
            }

            if (shortestWaitTime > waitTime) {
                shortestWaitTime = waitTime;
            }

            totalWaitTime += waitTime;
        }

        long averageWaitTime = totalWaitTime / waitTimes.size();

        System.out.println("\nSTATISTICS\n");
        System.out.println("All gates are free? " + (tower.airport.gate.availablePermits() == 3));
        
        System.out.println("Number of planes served: " + waitTimes.size());
        System.out.println("Total number of passengers: " + passengersServed);

        System.out.println("Time shown in seconds.");
        System.out.println("Longest wait time: " + longestWaitTime / 1000.0);
        System.out.println("Average wait time: " + averageWaitTime / 1000.0);
        System.out.println("Shortest wait time: " + shortestWaitTime / 1000.0);
    }
}
