import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Tower implements Runnable {
    private String threadName;

    public Airport airport;
    private Queue<Plane> queue = new LinkedList<>();

    public Tower(Airport airport, Queue<Plane> queue) {
        this.airport = airport;
        this.queue = queue;
    }

    public void run() {
        Thread.currentThread().setName("Tower thread: ");
        threadName = Thread.currentThread().getName();

        System.out.println(threadName + " tower has started running.");

        ArrayList<Thread> threads = new ArrayList<>();

        while (!queue.isEmpty()) { // meaning there are no planes.
            Plane plane = queue.poll();

            Thread thread = new Thread(plane);
            
            threads.add(thread);
            thread.start();
        }
    
        for (var thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void land(Plane plane) {
        System.out.println(threadName + " Plane " + plane.id + " is requesting to land.");

        Gate freeGate = null;
        while (freeGate == null) { // If a plane can't land, it needs to keep waiting until it can.
            synchronized(airport.runway) {
                while (airport.runway.isOccupied()) {
                    try {
                        System.out.println(threadName + " Plane " + plane.id + " not allowed to land. Runway is occupied.");
                        wait();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                synchronized(airport.gates) {
                    for (var gate : airport.gates) {
                        if (gate.is_occupied()) continue;
                        freeGate = gate;
                    }
                    
                    if (freeGate == null) {
                        System.out.println(threadName + "Plane " + plane.id + " is not allowed to land." + "Runway is free but no gates are free.");
                        try {
                            Thread.sleep(2000); // Simulate pilot waiting a few minutes before requesting to land.
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        continue;
                    }
                    
                    System.out.println(threadName + "Plane " + plane.id + " is cleared for landing. Please coast to Gate " + freeGate.get_id());
                    airport.runway.occupy();
                    try {
                        Thread.sleep(1000); // Plane coasting on runway to gate.
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    airport.runway.free();
                    notifyAll();
                    freeGate.occupy(plane.id);
                }
            }
        }
    }

    public void depart(Plane plane) {
        System.out.println(threadName + "Plane " + plane.id + " is requesting to depart.");
        synchronized(airport.runway) {
            while (airport.runway.isOccupied()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            System.out.println(threadName + "Plane " + plane.id + " cleared for depature.");
            synchronized(airport.gates) {
                for (var gate: airport.gates) {
                    if (gate.occupiedBy(plane.id)) gate.free();
                }
            }

            airport.runway.occupy();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            airport.runway.free();
            System.out.println(threadName + "Plane " + plane.id + " has successfully taken off.");
            notifyAll();
        }

    }
}
