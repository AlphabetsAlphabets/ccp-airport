public class App {
    // Currently, the program will enter a deadlock because there isn't a mechanism in place
    // which makes the planes leave the gate.
    public static void main(String[] args) throws Exception {
        Tower tower = new Tower();
        RefuellingTruck refuelling_truck = new RefuellingTruck();
        // Thread refuThread = new Thread(refuelling_truck);
        
        Plane plane_one = new Plane(1, tower);
        Plane plane_two = new Plane(2, tower);
        Plane plane_three = new Plane(3, tower);
        // Thread t_plane_four = new Thread(plane_four);

        Thread t_plane_one = new Thread(plane_one);
        Thread t_plane_two = new Thread(plane_two);
        Thread t_plane_three = new Thread(plane_three);
        
        // Plane plane_four = new Plane(4, tower);


        t_plane_one.start();
        t_plane_two.start();
        // t_plane_three.start();        
        // t_plane_four.start();

        // refuThread.start();

        t_plane_one.join();
        t_plane_two.join();
        // t_plane_three.join();     
        // t_plane_four.join();

        // refuThread.join();
        
        System.out.println("Finished");
    }
}
