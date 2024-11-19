import java.util.ArrayList;

public class Prisoner implements Identifiable, Comparable<Prisoner> {
    private int sentence;
    private String name;
    private int prisonerID;
    private ArrayList<Prisoner> prisoners;

    public Prisoner(String name, int prisonerID, int sentence) {
        this.name = name;
        this.prisonerID = prisonerID;
        this.sentence = sentence;
        this.prisoners = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public int getID() {
        return prisonerID;
    }

    public int getSentence() {
        return sentence;
    }

    public int compareTo(Prisoner other) {
        if (this.sentence != other.sentence) {
            return Integer.compare(other.sentence, this.sentence); // Descending by sentence
        } else {
            return Integer.compare(this.prisonerID, other.prisonerID); // Ascending by ID if sentences are the same
        }
    }

}