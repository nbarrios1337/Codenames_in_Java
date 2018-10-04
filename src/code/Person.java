package code;

/**
 * @author Nicolas Barrios
 */
public enum Person {
    NONE, R_AGENT, B_AGENT, G_AGENT, BYSTANDER, ASSASSIN;

    @Override
    /**
     * Overrode the toString method to make the values nicer to look at.
     */
    public String toString() {
        switch (this) {
            case NONE:
                return "";
            case R_AGENT:
                return "Red Agent";
            case B_AGENT:
                return "Blue Agent";
            case G_AGENT:
                return "Green Agent";
            case BYSTANDER:
                return "Bystander";
            case ASSASSIN:
                return "Assassin";
            default:
                throw new IllegalArgumentException();
        }
    }

}
