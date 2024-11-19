import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;


public class Main {
    public static void main(String[] args) throws IOException, DuplicateEntryException
    {
        FileInputStream fileInputStream = new FileInputStream("input.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("output.txt");
        OutputDevice outputDevice = new OutputDevice(fileOutputStream);
        Prison prison = new Prison(10, outputDevice);
        Dininghall dininghall = new Dininghall(outputDevice);

        InputDevice inputDevice = new InputDevice(fileInputStream, outputDevice, prison, dininghall);
        inputDevice.processCommands();
        inputDevice.close();

        // Display final status of prison and dining hall after processing commands
        prison.displayGuards();
        outputDevice.writeMessage("\nPrison status:");
        prison.displayPrisonStatus();
        dininghall.displayItems();


        // Display sorted prisoners and guards
        prison.displayGuardsByClearance();
        prison.displayGuardsByID();
        prison.displayPrisonersBySentence();
        prison.displayPrisonersByID();

        /*if (args.length == 0) {
            outputDevice.writeMessage("No arguments provided. Please specify 'add', 'display' or 'serve'.");
            return;
        }

        String action = args[0]; // The first argument will determine the action

        switch (action) {
            case "addprisoner":
                if (args.length != 5) {
                    outputDevice.writeMessage("Usage: add <name> <prisonerID> <sentenceInYears> <cellNumber>");
                    break;
                }
                String name = args[1];
                int prisonerID;

                try {
                    prisonerID = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    outputDevice.writeMessage("prisonerID must be an integer.");
                    break;
                }

                int sentence;

                try {
                    sentence = Integer.parseInt(args[3]);
                } catch (NumberFormatException e) {
                    outputDevice.writeMessage("Sentence must be an integer.");
                    break;
                }

                int cellNumber;

                try {
                    cellNumber = Integer.parseInt(args[4]);
                } catch (NumberFormatException e) {
                    outputDevice.writeMessage("Cell number must be an integer.");
                    break;
                }

                // Attempt to add the prisoner
                if (prison.assignPrisonerToCell(name, prisonerID, sentence, cellNumber)) {
                    outputDevice.writeMessage("Prisoner " + name + " with ID: " + prisonerID + " and sentence of "
                            + sentence + " years added to cell " + cellNumber + ".");
                } else {
                    outputDevice.writeMessage("Failed to add prisoner to cell " + cellNumber + ".");
                }
                break;

            case "removeprisoner":
                if (args.length != 2) {
                    outputDevice.writeMessage("Usage: removeprisoner <prisonerID>");
                    break;
                }
                int prisonerID2;
                try {
                    prisonerID2 = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    outputDevice.writeMessage("prisonerID must be an integer.");
                    break;
                }
                // Attempt to add the prisoner
                prison.removePrisoner(prisonerID2);
                break;


            case "display":
                prison.displayPrisonStatus();
                break;

            case "addmeal":
                if (args.length != 2) {
                    outputDevice.writeMessage("Usage: addmeal <meal>");
                    break;
                }
                String mealToAdd = args[1];
                dininghall.add(mealToAdd);
                outputDevice.writeMessage("Meal added: " + mealToAdd + ".");
                break;

            case "removemeal":
                if (args.length != 2) {
                    outputDevice.writeMessage("Usage: removemeal <meal>");
                }
                String mealToRemove = args[1];
                if(dininghall.remove(mealToRemove)) outputDevice.writeMessage("Meal removed: " + mealToRemove + ".");
                else outputDevice.writeMessage("Meal not found: " + mealToRemove + ".");
                break;



            case "serve":
                dininghall.displayItems();
                break;

            default:
                outputDevice.writeMessage("Unknown action: " + action);
                break;
        }*/
    }
}