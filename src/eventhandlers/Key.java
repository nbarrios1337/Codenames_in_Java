package eventhandlers;

public class Key {

    private final int value;
    private boolean correct;

    /**
     * Data class for the Konami code easter egg
     *
     * @param value
     * @param correct
     */
    public Key(int value, boolean correct) {
        this.value = value;
        this.correct = correct;
    }

    /**
     * @return the value of the key
     */
    public int getValue() {
        return value;
    }

    /**
     * @return whether the correct key was pressed
     */
    public boolean isCorrect() {
        return correct;
    }

    /**
     * @param correct switches correct from false to true
     */
    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

}
