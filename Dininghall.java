import java.util.ArrayList;
import java.io.IOException;

public class Dininghall {
    private OutputDevice outputDevice;
    private ArrayList<String> mealsServed;

    public Dininghall(OutputDevice outputDevice) {
        mealsServed = new ArrayList<>();
        this.outputDevice = outputDevice;
    }

    public boolean add(String meal) throws DuplicateEntryException {
        // Check if the meal already exists
        if (mealsServed.contains(meal)) {
            throw new DuplicateEntryException("Meal '" + meal + "' already exists in the dining hall.");
        }
        mealsServed.add(meal);
        return true;
    }

    public boolean remove(String meal) {
        if (mealsServed.contains(meal)) {
            mealsServed.remove(meal);
            return true;
        }
        return false;
    }

    public void displayItems() throws IOException {
        outputDevice.writeMessage("\nMeals served:");
        for (String meal : mealsServed) {
            outputDevice.writeMessage(" - " + meal);
        }
    }
}