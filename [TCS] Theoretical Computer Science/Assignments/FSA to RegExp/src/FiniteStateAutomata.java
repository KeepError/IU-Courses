import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FSA Class
 */
public class FiniteStateAutomata {
    ArrayList<State> states;
    ArrayList<Character> alphabet;
    State startState;


    public FiniteStateAutomata(Scanner input) throws InputException {
        states = new ArrayList<>();
        alphabet = new ArrayList<>();

        readInputData(input);
    }

    /**
     * Returns regular expression for FSA
     *
     * @return regular expression string
     */
    public String getRegExp() {
        HashMap<String, String> kleeneStates = new HashMap<>();

        for (int i = 0; i < states.size(); i++) {
            for (int j = 0; j < states.size(); j++) {
                ArrayList<String> characters = getStatesTransitionsCharacters(states.get(i), states.get(j));
                if (i == j) characters.add("eps");
                if (characters.isEmpty()) characters.add("{}");
                kleeneStates.put(getPairKey(i, j), String.join("|", characters));
            }
        }

        for (int loopNumber = 0; loopNumber < states.size(); loopNumber++) {
            HashMap<String, String> newKleeneStates = new HashMap<>();
            for (String pair : kleeneStates.keySet()) {
                int i = getIFromPairKey(pair);
                int j = getJFromPairKey(pair);
                String ikValue = kleeneStates.get(getPairKey(i, loopNumber));
                String kkValue = kleeneStates.get(getPairKey(loopNumber, loopNumber));
                String kjValue = kleeneStates.get(getPairKey(loopNumber, j));
                String ijValue = kleeneStates.get(pair);
                String newValue = String.format("(%s)(%s)*(%s)|(%s)", ikValue, kkValue, kjValue, ijValue);
                newKleeneStates.put(getPairKey(i, j), newValue);
            }
            kleeneStates = newKleeneStates;
        }

        ArrayList<String> result = new ArrayList<>();
        int startStateNum = states.indexOf(startState);
        for (int stateNum = 0; stateNum < states.size(); stateNum++) {
            if (states.get(stateNum).isAccepting()) {
                result.add(kleeneStates.get(getPairKey(startStateNum, stateNum)));
            }
        }
        if (result.size() == 0) return "{}";
        return String.join("|", result);
    }

    /**
     * Returns pair key string for two elements
     *
     * @param i first element
     * @param j second element
     * @return pair string
     */
    private String getPairKey(int i, int j) {
        return i + "_" + j;
    }

    /**
     * Returns first element from pair string
     *
     * @param pairKey key string
     * @return I element
     */
    private Integer getIFromPairKey(String pairKey) {
        return Integer.valueOf(pairKey.split("_")[0]);
    }

    /**
     * Returns second element from pair string
     *
     * @param pairKey key string
     * @return J element
     */
    private Integer getJFromPairKey(String pairKey) {
        return Integer.valueOf(pairKey.split("_")[1]);
    }

    /**
     * Returns characters which belong to transition between two states
     *
     * @param q1 From state
     * @param q2 To state
     * @return transition characters list
     */
    private ArrayList<String> getStatesTransitionsCharacters(State q1, State q2) {
        ArrayList<String> result = new ArrayList<>();
        for (Transition transition : q1.getFromThisTransitions()) {
            if (transition.to.equals(q2)) {
                result.add(transition.character.getName());
            }
        }
        return result;
    }

    /**
     * Checks is FSA non-deterministic
     */
    private void checkForNonDeterministic() throws NondeterministicFSAException {
        for (State state : states) {
            ArrayList<Character> stateAlphabet = new ArrayList<>();

            for (Transition transition : state.getFromThisTransitions()) {
                if (stateAlphabet.contains(transition.character)) {
                    throw new NondeterministicFSAException();
                }
                stateAlphabet.add(transition.character);
            }
        }
    }

    /**
     * Checks if there are non-reachable states in FSA
     *
     * @return true if there are, false if not
     */
    private boolean checkForNonReachableStates() {
        ArrayList<State> passedStates = new ArrayList<>();
        for (State state : states) {
            if (state.equals(startState)) continue;
            if (!isStateReachable(passedStates, startState, state)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks recursively if state is reachable to another state
     *
     * @param passedStates states already passed and does not need to be checked
     * @param current      state which is needed to be checked
     * @param target       strive for state
     * @return true if reachable, false if not
     */
    @SuppressWarnings("unchecked")
    private boolean isStateReachable(ArrayList<State> passedStates, State current, State target) {
        boolean result = false;
        passedStates = (ArrayList<State>) passedStates.clone();
        passedStates.add(current);

        for (State state : current.getFromThisStates()) {
            if (passedStates.contains(state) || state.equals(current)) continue;
            if (state.equals(target)) {
                result = true;
                break;
            }
            if (isStateReachable(passedStates, state, target)) {
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * Returns state by its name or throws an exception if state is not in states list
     *
     * @param name state name
     * @return state
     */
    private State getStateByName(String name) throws StateNotInSetException {
        for (State state : states) {
            if (state.getName().equals(name)) {
                return state;
            }
        }
        throw new StateNotInSetException(name);
    }

    /**
     * Returns character by its name or throws an exception if character is not in alphabet
     *
     * @param name character name
     * @return character
     */
    private Character getCharacterByName(String name) throws TransitionNotPresentedInAlphabet {
        for (Character character : alphabet) {
            if (character.getName().equals(name)) {
                return character;
            }
        }
        throw new TransitionNotPresentedInAlphabet(name);
    }

    /**
     * @param line     input line
     * @param lineName "states", "alpha", "init.st", "fin.st" or "trans"
     * @return list of input value
     * @throws InputFileMalformedException if input file is malformed
     */
    private ArrayList<String> getValuesFromInputLine(String line, String lineName) throws InputFileMalformedException {
        String validatingRegex = "=\\[([\\w,>]*)\\]";

        Pattern r = Pattern.compile(lineName + validatingRegex);
        Matcher m = r.matcher(line);
        if (m.matches()) {
            String[] values = m.group(1).split(",");
            ArrayList<String> valuesList = new ArrayList<>(Arrays.asList(values));
            if (valuesList.size() == 1 && valuesList.get(0).equals("")) {
                valuesList.remove(0);
            }
            return valuesList;
        } else {
            throw new InputFileMalformedException();
        }
    }

    /**
     * Initializes states in format:
     * states=[s1,s2,...]	// s1 , s2, ... ∈ latin letters, words and numbers
     */
    private void initStates(ArrayList<String> values) throws InputFileMalformedException {
        for (String name : values) {
            State state = new State(name);
            states.add(state);
        }
        if (states.isEmpty()) {
            throw new InputFileMalformedException();
        }
    }

    /**
     * Initializes alphabet in format:
     * alpha=[a1,a2,...]	// a1 , a2, ... ∈ latin letters, words, numbers and character '_’(underscore)
     */
    private void initAlphabet(ArrayList<String> values) {
        for (String name : values) {
            Character character = new Character(name);
            alphabet.add(character);
        }
    }

    /**
     * Initializes start state in format:
     * init.st=[s]	// s ∈ states
     */
    private void initStartState(ArrayList<String> values) throws InputFileMalformedException, InitialStateNotDefinedException, StateNotInSetException {
        if (values.size() >= 2) {
            throw new InputFileMalformedException();
        }
        if (values.size() == 0) {
            throw new InitialStateNotDefinedException();
        }
        String stateName = values.get(0);
        startState = getStateByName(stateName);
    }

    /**
     * Initializes accepting states in format:
     * fin.st=[s1,s2,...]	// s1, s2 ∈ states
     */
    private void initAcceptingStates(ArrayList<String> values) throws StateNotInSetException {
        for (String stateName : values) {
            State state = getStateByName(stateName);
            state.setAccepting(true);
        }
    }

    /**
     * Initializes transitions in format:
     * trans=[s1>a>s2,...]	// s1,s2,...∈ states; a ∈ alpha
     */
    private void initTransitions(ArrayList<String> values) throws StateNotInSetException, TransitionNotPresentedInAlphabet {
        for (String transitionLine : values) {
            String[] transitionValues = transitionLine.split(">");
            State from = getStateByName(transitionValues[0]);
            State to = getStateByName(transitionValues[2]);
            Character character = getCharacterByName(transitionValues[1]);

            Transition transition = new Transition(from, to, character);
            from.addFromThisTransition(transition);
            to.addToThisTransition(transition);
            character.addTransition(transition);
        }
    }

    /**
     * Checks if there are disjoint states and throws exception if needed
     */
    private void checkDisjointStates() throws SomeStatesDisjointException {
        if (states.size() == 1) {
            return;
        }
        for (State state : states) {
            if (state.isDisjoint()) {
                throw new SomeStatesDisjointException();
            }
        }
    }

    /**
     * Reads input from file in format:
     * states=[s1,s2,...]
     * alpha=[a1,a2,...]
     * init.st=[s]
     * fin.st=[s1,s2,...]
     * trans=[s1>a>s2,...]
     *
     * @param input reader from file
     */
    private void readInputData(Scanner input) throws InputException {
        try {
            initStates(getValuesFromInputLine(input.nextLine(), "states"));
            initAlphabet(getValuesFromInputLine(input.nextLine(), "alpha"));
            initStartState(getValuesFromInputLine(input.nextLine(), "initial"));
            initAcceptingStates(getValuesFromInputLine(input.nextLine(), "accepting"));
            initTransitions(getValuesFromInputLine(input.nextLine(), "trans"));
            checkDisjointStates();
            checkForNonDeterministic();
        } catch (NoSuchElementException e) {
            throw new InputFileMalformedException();
        }
    }
}
