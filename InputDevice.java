import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InputDevice {
    private Scanner scanner;
    private OutputDevice outputDevice;
    private Prison prison;
    private Dininghall dininghall;

    public InputDevice(InputStream inputStream, OutputDevice outputDevice, Prison prison, Dininghall dininghall) throws FileNotFoundException {
        if (inputStream == null) {
            throw new IllegalArgumentException("InputStream cannot be null");
        }
        this.scanner = new Scanner(inputStream);
        this.outputDevice = outputDevice;
        this.prison = prison;
        this.dininghall = dininghall;
    }

    public void processCommands() throws IOException, DuplicateEntryException {
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            handleCommand(command);
        }
    }

    private void handleCommand(String command) throws IOException, DuplicateEntryException {
        String[] parts = command.split(" ", 2);

        if (parts.length < 2) {
            outputDevice.writeMessage("Invalid command format.");
            return;
        }

        String action = parts[0].toLowerCase();
        String[] argsList;

        switch (action) {
            case "addprisoner":
                argsList = parts[1].split(",");
                if (argsList.length != 4) {
                    outputDevice.writeMessage("Invalid format for addprisoner. Expected: <name>,<prisonerID>,<sentence>,<cellNumber>");
                    return;
                }
                String prisonerName = argsList[0].replace("\"", "").trim();
                int prisonerID = Integer.parseInt(argsList[1].trim());
                int sentence = Integer.parseInt(argsList[2].trim());
                int cellNumber = Integer.parseInt(argsList[3].trim());
                prison.assignPrisonerToCell(prisonerName, prisonerID, sentence, cellNumber);
                break;

            case "removeprisoner":
                int prisonerIDToRemove = Integer.parseInt(parts[1].trim());
                prison.removePrisoner(prisonerIDToRemove);
                break;

            case "addguard":
                argsList = parts[1].split(",");
                if (argsList.length != 3) {
                    outputDevice.writeMessage("Invalid format for addguard. Expected: <name>,<guardID>,<clearanceLevel>");
                    return;
                }
                String guardName = argsList[0].replace("\"", "").trim();
                int guardID = Integer.parseInt(argsList[1].trim());
                int clearanceLevel = Integer.parseInt(argsList[2].trim());
                Guard guard = new Guard(guardName, guardID, clearanceLevel);
                prison.addGuard(guard);
                break;

            case "removeguard":
                int guardIDToRemove = Integer.parseInt(parts[1].trim());
                boolean removedGuard = prison.removeGuard(guardIDToRemove);
                if (removedGuard) {
                    outputDevice.writeMessage("Guard with ID " + guardIDToRemove + " has been removed.");
                } else {
                    outputDevice.writeMessage("Guard with ID " + guardIDToRemove + " not found.");
                }
                break;

            case "addmeal":
                String mealName = parts[1].replace("\"", "").trim();
                dininghall.add(mealName);
                outputDevice.writeMessage("Meal added: " + mealName);
                break;

            case "removemeal":
                String mealToRemove = parts[1].replace("\"", "").trim();
                boolean removedMeal = dininghall.remove(mealToRemove);
                if (removedMeal) {
                    outputDevice.writeMessage("Meal removed: " + mealToRemove);
                } else {
                    outputDevice.writeMessage("Meal not found: " + mealToRemove);
                }
                break;

            default:
                outputDevice.writeMessage("Unknown action: " + action);
                break;
        }
    }

    public void close() {
        scanner.close();
    }
}