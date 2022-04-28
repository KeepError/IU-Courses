import java.util.ArrayList;

/**
 * Character from FSA alphabet
 */
public class Character {
    private final String name;
    private final ArrayList<Transition> transitions;

    public Character(String name) {
        this.name = name;
        this.transitions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addTransition(Transition transition) {
        transitions.add(transition);
    }
}
