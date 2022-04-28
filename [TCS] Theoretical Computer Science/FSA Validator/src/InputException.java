/**
 * Exception for input errors
 */
public abstract class InputException extends Exception {
    InputException(String s) {
        super(s);
    }
}

class StateNotInSetException extends InputException {
    public StateNotInSetException(String stateName) {
        super("E1: A state '" + stateName + "' is not in the set of states");
    }
}

class SomeStatesDisjointException extends InputException {
    public SomeStatesDisjointException() {
        super("E2: Some states are disjoint");
    }
}

class TransitionNotPresentedInAlphabet extends InputException {
    public TransitionNotPresentedInAlphabet(String characterName) {
        super("E3: A transition '" + characterName + "' is not represented in the alphabet");
    }
}

class InitialStateNotDefinedException extends InputException {
    public InitialStateNotDefinedException() {
        super("E4: Initial state is not defined");
    }
}

class InputFileMalformedException extends InputException {
    public InputFileMalformedException() {
        super("E5: Input file is malformed");
    }
}
