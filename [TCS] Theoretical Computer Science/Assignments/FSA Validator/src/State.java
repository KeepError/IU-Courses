import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * State from Finite State Automata
 */
public class State {
    private final String name;
    private boolean accepting;
    private final ArrayList<Transition> toThis;
    private final ArrayList<Transition> fromThis;

    public State(String name) {
        this.name = name;
        accepting = false;
        toThis = new ArrayList<>();
        fromThis = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setAccepting(boolean accepting) {
        this.accepting = accepting;
    }

    public void addFromThisTransition(Transition transition) {
        fromThis.add(transition);
    }

    public void addToThisTransition(Transition transition) {
        toThis.add(transition);
    }

    public ArrayList<Transition> getFromThisTransitions() {
        return fromThis;
    }

    public ArrayList<State> getFromThisStates() {
        ArrayList<State> result = new ArrayList<>();
        for (Transition transition : fromThis) {
            result.add(transition.to);
        }
        return result;
    }

    public boolean isDisjoint() {
        return (filteredTransitions(toThis).size() == 0 && filteredTransitions(fromThis).size() == 0);
    }

    public boolean hasFullAlphabetTransitions(ArrayList<Character> alphabet) {
        return (hasFullAlphabetForGivenTransitions(alphabet, fromThis));
    }

    public boolean isAccepting() {
        return accepting;
    }

    @SuppressWarnings("unchecked")
    private boolean hasFullAlphabetForGivenTransitions(ArrayList<Character> alphabet, ArrayList<Transition> transitions) {
        ArrayList<Character> nonUsedAlphabet = (ArrayList<Character>) alphabet.clone();
        for (Transition transition : transitions) {
            nonUsedAlphabet.remove(transition.character);
        }
        return nonUsedAlphabet.size() == 0;
    }

    private ArrayList<Transition> filteredTransitions(ArrayList<Transition> transitions) {
        return transitions.stream().filter(transition -> !transition.from.equals(transition.to)).collect(Collectors.toCollection(ArrayList::new));
    }
}
