public class Tower implements Runnable {
    private String threadName;

    public Airport airport;

    public Tower(Airport airport) {
        this.airport = airport;
    }

    public void run() {
        Thread.currentThread().setName("Tower thread: ");
        threadName = Thread.currentThread().getName();

        System.out.println(threadName + "Tower has started running.");
    }

    public void land(Plane plane) {
        System.out.println(threadName + " Plane " + plane.id + " is requesting to land.");

        while (!airport.gate.tryAcquire()) {
            System.out.println(threadName + "Plane " + plane.id + " No available gates. Please wait.");
            try {
                Thread.sleep(1000); // Simulate pilot waiting a few minutes before requesting for landing
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        while (!airport.runway.tryAcquire()) {
            System.out.println(threadName + "Plane " + plane.id + " request to land denied. Runway is not available.");
            try {
                Thread.sleep(1000); // Simulate pilot waiting a few minutes before requesting for landing.
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        System.out.println(threadName + "A gate and runway is free. Plane " + plane.id + " landing request approved. Please coast to gate " + (airport.gate.availablePermits() + 1));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        plane.readyToRefuel.set(true);
        System.out.println(threadName + "Plane " + plane.id + " has docked at assigned gate.");
        airport.runway.release();
    }

    public void depart(Plane plane) {
        System.out.println(threadName + "Plane " + plane.id + " is requesting to depart.");

       
        while (!airport.runway.tryAcquire()) {
            System.out.println(threadName + "Depature for Plane " + plane.id + " denied. Runway is not available.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        System.out.println(threadName + "Depature for Plane " + plane.id + " is approved. Please coast to runway.");
        airport.gate.release();
        System.out.println(threadName + "A gate " + (airport.gate.availablePermits() + 1) + " is now free.");
        airport.runway.release();
        System.out.println(threadName + "Plane " + plane.id + " has taken off successfully.");
    }
}
