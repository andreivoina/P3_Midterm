import java.io.IOException;
import java.io.Serializable;

public class Cell implements Manageable {

    private OutputDevice outputDevice;
    private int cellNumber;
    private Prisoner[] prisoners;

    public Cell(int cellNumber, OutputDevice outputDevice) {
        this.cellNumber = cellNumber;
        this.prisoners = new Prisoner[2];
        this.outputDevice = outputDevice; // Each cell can hold up to 2 prisoners
    }

    public int getCellNumber() {
        return cellNumber;
    }

    public boolean addPrisoner(Prisoner prisoner) { // Accepts a Prisoner object
        for (int i = 0; i < prisoners.length; i++) {
            if (prisoners[i] == null) {
                prisoners[i] = prisoner;
                return true; // Successfully added prisoner
            }
        }
        return false; // No space left in the cell
    }

    public boolean removePrisoner(int prisonerID) {
        for (int i = 0; i < prisoners.length; i++) {
            if (prisoners[i] != null && prisoners[i].getID() == prisonerID) {
                prisoners[i] = null; // Remove the prisoner by setting the slot to null
                return true; // Successfully removed
            }
        }
        return false; // Prisoner not found
    }


     public void displayItems() throws IOException {
         //outputDevice.writeMessage("Prisoners in cell " + cellNumber + ":");
         for (Prisoner prisoner : prisoners) {
             if (prisoner != null) {
                 outputDevice.writeMessage(" - " + prisoner.getName() + " (ID: " + prisoner.getID() + ")");
             }
             else outputDevice.writeMessage(" - Empty spot");

         }
    }

    public Prisoner[] getPrisoners() {
        return prisoners;
    }
}