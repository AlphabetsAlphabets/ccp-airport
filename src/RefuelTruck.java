public class RefuelTruck implements Runnable {
    private String threadName;

    public RefuelTruck() {
    }

    public void run() {
        Thread.currentThread().setName("FuelTruckThread: ");
        threadName = Thread.currentThread().getName();

        System.out.println(threadName + "Fuel truck is ready and waiting!");
    }

    public void refuel(Plane plane) {
        System.out.println(threadName + "Plane " + plane.id + " is currently being serviced.");
        System.out.println(threadName + "Plane " + plane.id + " has a full tank.");
    }
}
