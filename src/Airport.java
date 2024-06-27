import java.util.ArrayList;

public class Airport {
    public ArrayList<Gate> gates = new ArrayList<>();
    public Runway runway = new Runway();

    public Airport() {
        gates.add(new Gate(1));
        gates.add(new Gate(2));
        gates.add(new Gate(3));
    }
}
