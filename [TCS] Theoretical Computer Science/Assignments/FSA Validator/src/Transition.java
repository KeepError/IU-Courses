/**
 * Transition from FSA which contains FROM state, TO state and character
 */
public class Transition {
    State from;
    State to;
    Character character;

    public Transition(State from, State to, Character character) {
        this.from = from;
        this.to = to;
        this.character = character;
    }
}
