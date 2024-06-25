import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class App {
    public static void ideal_scenario() throws InterruptedException {
        BlockingQueue<Plane> refuelQueue = new LinkedBlockingQueue<Plane>();

        Tower tower = new Tower(refuelQueue);
        FuelTruck fuelTruck = new FuelTruck(refuelQueue);
        Thread fuelTruckThread = new Thread(fuelTruck);

        fuelTruckThread.start();
        
        Plane planeOne = new Plane(1, tower);
        Plane planeTwo = new Plane(2, tower);
        Plane PlaneThree = new Plane(3, tower);        
        Plane PlaneFour = new Plane(4, tower);

        Thread PlaneOneThread = new Thread(planeOne);
        Thread PlaneTwoThread = new Thread(planeTwo);
        Thread PlaneThreeThread = new Thread(PlaneThree);
        Thread PlaneFourThread = new Thread(PlaneFour);

        PlaneOneThread.start();
        PlaneTwoThread.start();
        PlaneThreeThread.start();        
        PlaneFourThread.start();

        PlaneOneThread.join();
        PlaneTwoThread.join();
        PlaneThreeThread.join();
        PlaneFourThread.join();

        fuelTruckThread.join();
        
        System.out.println("Finished");
    }
    
    public static void main(String[] args) throws Exception {
        ideal_scenario();
    }
}
