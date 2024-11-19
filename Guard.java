import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class Guard implements Identifiable, Comparable<Guard> {
    private String name;
    private int guardID;
    private int clearanceLevel;
    private ArrayList<Guard> guards;

    public Guard(String name, int guardID, int clearanceLevel) {
        this.name = name;
        this.guardID = guardID;
        this.clearanceLevel = clearanceLevel;
    }

    /*public boolean add(Guard guard) {
        guards.add(guard);
        return true;
    }

    public boolean remove(int guardID) {
        for (Guard guard : guards) {
            if (guard.getID() == guardID) {
                guards.remove(guard);
                return true;  // Guard removed successfully
            }
        }
        return false;  // Guard with that ID not found
    }*/

    public String getName() {
        return name;
    }

    public int getID() {
        return guardID;
    }

    public int getClearanceLevel() {
        return clearanceLevel;
    }

    public int compareTo(Guard other) {
        // First compare by clearance level in descending order
        if (this.clearanceLevel != other.clearanceLevel) {
            return Integer.compare(other.clearanceLevel, this.clearanceLevel);
        } else {
            // If clearance levels are equal, compare by ID in ascending order
            return Integer.compare(this.guardID, other.guardID);
        }
    }

}