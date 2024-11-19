import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;

public class Prison {
    private transient OutputDevice outputDevice;
    private Cell[] cells;
    private ArrayList<Guard> guards;

    public Prison(int numberOfCells, OutputDevice outputDevice) {
        cells = new Cell[numberOfCells];
        for (int i = 0; i < numberOfCells; i++) {
            cells[i] = new Cell(i, outputDevice); // Each cell belongs to this prison
        }
        this.outputDevice = outputDevice;
        this.guards = new ArrayList<>();
    }

    private ArrayList<Guard> getGuards() {
        return guards;
    }

    public List<Prisoner> getAllPrisoners() {
        List<Prisoner> allPrisoners = new ArrayList<>();
        for (Cell cell : cells) {
            for (Prisoner prisoner : cell.getPrisoners()) {
                if (prisoner != null) {
                    allPrisoners.add(prisoner);
                }
            }
        }
        return allPrisoners;
    }

    /*public List<Prisoner> getAllPrisoners() {
        return prisoners;
    }*/

    public void displayPrisonersBySentence() throws IOException {
        List<Prisoner> prisoners = getAllPrisoners(); // Get the list of prisoners

        // Sort using the natural order defined by compareTo()
        Collections.sort(prisoners);

        outputDevice.writeMessage("\nPrisoners sorted by sentence (descending), and ID (descending if sentences are the same):");

        // Display sorted prisoners
        for (Prisoner prisoner : prisoners) {
            outputDevice.writeMessage(" - " + prisoner.getName() + " (ID: " + prisoner.getID() + ", Sentence: "
                    + prisoner.getSentence() + " years)");
        }
    }

    public void displayPrisonersByID() throws IOException {
        List<Prisoner> prisoners = getAllPrisoners();  // Get the list of prisoners

        // Sort by prisoner ID using a Comparator
        Collections.sort(prisoners, Comparator.comparingInt(Prisoner::getID));

        outputDevice.writeMessage("\nPrisoners sorted by ID (ascending):");

        // Display sorted prisoners
        for (Prisoner prisoner : prisoners) {
            outputDevice.writeMessage(" - " + prisoner.getName() + " (ID: " + prisoner.getID() + ", Sentence: "
                    + prisoner.getSentence() + " years)");
        }
    }


    // Methods to add and remove guards
    public void addGuard(Guard guard) throws IOException, DuplicateEntryException {
        for (Guard existingGuard : guards) {
            if (existingGuard.getID() == guard.getID()) {
                throw new DuplicateEntryException("Guard with ID " + guard.getID() + " already exists.");
            }
        }
        guards.add(guard);
        outputDevice.writeMessage("Guard " + guard.getName() + " (ID: " + guard.getID() + ") added.");
    }

    public boolean removeGuard(int guardID) throws IOException {
        for (Guard guard : guards) {
            if (guard != null && guard.getID() == guardID) {
                guards.remove(guard);
                outputDevice.writeMessage("Guard with ID " + guardID + " has been removed from the prison.");
                return true;
            }
        }
        outputDevice.writeMessage("Guard with ID " + guardID + " not found.");
        return false;
    }

    // Display all guards
    public void displayGuards() throws IOException {
        outputDevice.writeMessage("\nGuards on duty:");
        for (Guard guard : guards) {
            outputDevice.writeMessage(" - " + guard.getName() + " (ID: " + guard.getID() + ")");
        }
    }

    public void displayGuardsByClearance() throws IOException {
        List<Guard> guards = new ArrayList<>(getGuards()); // Assume `getAllGuards()` returns a list of guards

        // Sort the list (natural ordering defined in `compareTo`)
        Collections.sort(guards);

        outputDevice.writeMessage("\nGuards sorted by Clearance Level (descending), and by ID (ascending if Clearance Levels are the same):");
        for (Guard guard : guards) {
            outputDevice.writeMessage(" - " + guard.getName() + " (ID: " + guard.getID() + ", Clearance Level: "
                    + guard.getClearanceLevel() + ")");
        }
    }

    public void displayGuardsByID() throws IOException {
        List<Guard> guards = getGuards();  // Get the list of guards

        // Sort guards by ID in ascending order
        Collections.sort(guards, Comparator.comparingInt(Guard::getID));

        outputDevice.writeMessage("\nGuards sorted by ID (ascending):");

        // Display sorted guards
        for (Guard guard : guards) {
            outputDevice.writeMessage(" - " + guard.getName() + " (ID: " + guard.getID() + ", Clearance Level: "
                    + guard.getClearanceLevel() + ")");
        }
    }

    /*public boolean assignPrisonerToCell(String name, int prisonerID, int sentence, int cellNumber) throws IOException, DuplicateEntryException {
        for (Prisoner existingPrisoner : prisoners) {
            if (existingPrisoner.getID() == prisonerID) {
                throw new DuplicateEntryException("Prisoner with ID " + prisonerID + " already exists.");
            }
        }
        Prisoner prisoner = new Prisoner(name, prisonerID, sentence);
        return assignPrisonerToCell(cellNumber, prisoner); // Call existing method
    }*/

    public boolean assignPrisonerToCell(String name, int prisonerID, int sentence, int cellNumber) throws IOException, DuplicateEntryException {
        // Check if the prisoner already exists in the main list of prisoners
        for (Prisoner existingPrisoner : getAllPrisoners()) {
            if (existingPrisoner.getID() == prisonerID) {
                throw new DuplicateEntryException("Prisoner with ID " + prisonerID + " already exists in the prison.");
            }
        }

        // Check if the prisoner already exists in any of the cells
        for (Cell cell : cells) {
            for (Prisoner prisoner : cell.getPrisoners()) {
                if (prisoner != null && prisoner.getID() == prisonerID) {
                    throw new DuplicateEntryException("Prisoner with ID " + prisonerID + " already exists in cell.");
                }
            }
        }

        // Create a new prisoner and assign to a cell
        Prisoner prisoner = new Prisoner(name, prisonerID, sentence);
        return assignPrisonerToCell(cellNumber, prisoner); // Call the existing method to assign the prisoner
    }

    // Assign a prisoner to a specified cell
    public boolean assignPrisonerToCell(int cellNumber, Prisoner prisoner) throws IOException {
        if (cellNumber < 0 || cellNumber >= cells.length) {
            outputDevice.writeMessage("Invalid cell number.");
            return false;
        }

        boolean added = cells[cellNumber].addPrisoner(prisoner);
        if (!added) {
            outputDevice.writeMessage("Failed to add prisoner to cell " + cellNumber + ". The cell is full.");
        }
        return added; // Returns false if cell is full
    }

    public boolean removePrisoner(int prisonerID) throws IOException {
        for (Cell cell : cells) {
            if (cell != null && cell.removePrisoner(prisonerID)) {
                outputDevice.writeMessage("Prisoner with ID " + prisonerID + " has been removed from the prison.");
                return true; // Prisoner successfully found and removed
            }
        }
        outputDevice.writeMessage("Prisoner with ID " + prisonerID + " not found in any cell.");
        return false; // Prisoner not found
    }


    // Display status of all cells in the prison
    public void displayPrisonStatus() throws IOException {
        for (int i = 0; i < cells.length; i++) {
            outputDevice.writeMessage("Cell " + i + ":");
            cells[i].displayItems();
        }
    }

}