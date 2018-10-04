package code;

/**
 * @author Vikram Singh
 * <p>
 * Enum which dictates which color the team is on, RED for red team BLUE
 * for blue team, and NONE for no team
 */
public enum Team {

    NONE, RED, BLUE, GREEN;

    @Override
    /**
     * Overrode the to string to make the values nicer to look at
     */
    public String toString() {
        switch (this) {
            case NONE:
                return "";
            case RED:
                return "Red";
            case BLUE:
                return "Blue";
            case GREEN:
                return "Green";
            default:
                throw new IllegalArgumentException();
        }
    }

}
