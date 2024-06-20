public class App {
    public static void main(String[] args) throws Exception {
        Tower tower = new Tower();
        
        Plane plane_one = new Plane(1, tower);
        Thread t_plane_one = new Thread(plane_one);
        
        Plane plane_two = new Plane(2, tower);
        Thread t_plane_two = new Thread(plane_two);

        t_plane_one.start();
        t_plane_two.start();

        t_plane_one.join();
        t_plane_two.join();
        
        System.out.println("Finished");
    }
}
