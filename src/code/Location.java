package code;

/**
 * Location holds the details of a specific spot on the board
 *
 * @author Veronica Vitale
 */
public class Location {

    private String _codeName;
    private Person _person;
    private boolean _revealed;
    private Team _revealer;

    public Location() {

        _revealed = false;
        _revealer = Team.NONE;
        _codeName = null;
        _person = Person.NONE;
    }

    /**
     * Gets the code name for the person at this location
     *
     * @return a string containing the code name
     */
    public String getCodeName() {
        return _codeName;
    }

    /**
     * Gets the person at the current location
     *
     * @return the person at the current location
     */
    public Person getPerson() {
        return _person;
    }

    /**
     * Gets weather or not the location is revealed
     *
     * @return a boolean that is true if the location is revealed
     */
    public boolean getRevealed() {
        return _revealed;
    }

    /**
     * Tells which team revealed this location
     *
     * @return the Team wich revealed the spot (team is none if location isn't
     * revealed)
     */
    public Team getRevealer() {
        return _revealer;
    }

    /**
     * sets the codename
     *
     * @param s is a string containing the code name which will be set
     */
    public void setCodeName(String s) {
        _codeName = s;
    }

    /**
     * sets the person variable
     *
     * @param p is the person which the variable will be set to
     */
    public void setPerson(Person p) {
        _person = p;
    }

    /**
     * sets wether or not the location is reavled (is false by default)
     *
     * @param r a boolean which is true if the location is revealed
     */
    public void setRevealed(boolean r) {
        _revealed = r;
    }

    /**
     * sets the team which revealed the location (is none by default)
     *
     * @param r a Team which tells who reavled this location
     */
    public void setRevealer(Team r) {
        _revealer = r;
    }

}
