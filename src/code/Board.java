package code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Board holds the logical representation of the game board as a 2D array of
 * Location objects
 *
 * @author Vikram Singh
 * @author Christine Chen
 * @author Nicolas Barrios
 * @author anthonylatoni
 * @author Veronica Vitale
 * @see Location
 */
public class Board {

    /**
     * ROWS is the number of rows in the game board
     */
    public static final int ROWS = 5;
    /**
     * COLS is the number of columns in the game board
     */
    public static final int COLS = 5;
    /**
     * Logical representation of the game board as a 2D Array
     */
    private Location[][] _gameBoard;
    /**
     * The current teams turn
     */
    private Team _currentTeamsTurn;
    /**
     * number of codeNames that relate to the clue
     */
    private int _count;
    /**
     * The number of red agents in the current game
     */
    private int _numRedAgents;
    /**
     * The number of blue agents in the current game
     */
    private int _numBlueAgents;
    /**
     * The number of green agents in the current game
     */
    private int _numGreenAgents;
    /**
     * List of all words in GameWords1.txt
     */
    private ArrayList<String> _gameWords;
    /**
     * List of 25 random names generated from _gameWords
     */
    private ArrayList<String> _codeNames;
    /**
     * List of random locations for red agents, blue agents, innocent bystanders,
     * and assassin
     */
    private ArrayList<Person> _listOfPersons;

    /**
     * The number of teams in the current game
     */
    private int _numTeams;

    /**
     * Empty Constructor
     * <p>
     * Instantiates the game board to a 2D array of Locations with dimension sizes
     * <code>ROWS</code> X <code>COLS</code>.
     * <p>
     * Sets the current teams turn to none and the number of red agents and blue
     * agents to 0 initially
     */
    public Board(int numTeams) {
        _numTeams = numTeams;
        initBoard();
    }

    /**
     * Initializes the board such that the game board gets 25 random locations each
     * with a code name, person and not being revealed.
     * <p>
     * Sets the current number of red and blue agents to 9 and 8 respectively
     * <p>
     * Sets the turn count to 0;
     */
    private void initBoard() {
        // Initialize fields
        _gameBoard = new Location[ROWS][COLS];

        // _numRedAgents = 9;
        // _numBlueAgents = 8;
        _count = 0;

        _gameWords = new ArrayList<>();
        _codeNames = new ArrayList<>();
        _listOfPersons = new ArrayList<>();

        _currentTeamsTurn = Team.RED;

        // fills the gameWords, odeNames, and listOfPersons ArrayLists
        _gameWords = (ArrayList<String>) readInGameWords("resources/GameWords1.txt");
        _codeNames = (ArrayList<String>) generateCodeNames();
        _listOfPersons = (ArrayList<Person>) generatePersons();

        // Set each of the 25 Location instances: a code name, person, and not revealed
        setLocations();
    }

    private ArrayList<Person> generate2TeamPersons() {
        ArrayList<Person> persons = new ArrayList<>();
        for (int red = 0; red < 9; red++) {
            persons.add(Person.R_AGENT);
        }
        for (int blue = 0; blue < 8; blue++) {
            persons.add(Person.B_AGENT);
        }
        for (int inn = 0; inn < 7; inn++) {
            persons.add(Person.BYSTANDER);
        }
        persons.add(Person.ASSASSIN);
        _numRedAgents = 9;
        _numBlueAgents = 8;
        _numGreenAgents = 0;
        return persons;

    }

    private ArrayList<Person> generate3TeamPersons() {
        ArrayList<Person> persons = new ArrayList<>();
        for (int red = 0; red < 6; red++) {
            persons.add(Person.R_AGENT);
        }
        for (int blue = 0; blue < 5; blue++) {
            persons.add(Person.B_AGENT);
        }
        for (int green = 0; green < 5; green++) {
            persons.add(Person.G_AGENT);
        }
        for (int inn = 0; inn < 7; inn++) {
            persons.add(Person.BYSTANDER);
        }
        persons.add(Person.ASSASSIN);
        persons.add(Person.ASSASSIN);

        _numRedAgents = 6;
        _numBlueAgents = 5;
        _numGreenAgents = 5;

        return persons;
    }

    /**
     * Creates a list that generates 25 random "code names" from _gameWords
     *
     * @return _codeNames
     */
    public List<String> generateCodeNames() {

        ArrayList<String> randomized = new ArrayList<>(_gameWords);
        Collections.shuffle(randomized);

        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < ROWS * COLS; i++) {
            names.add(randomized.get(i));
        }

        return names;
    }

    /**
     * fills listOfPersons with 9 Red Agents, 8 Blue Agents, 7 Innocent Bystanders,
     * and an Assassin.
     *
     * @return _listOfPersons
     */
    public List<Person> generatePersons() {
        ArrayList<Person> persons = new ArrayList<>();
        if (_numTeams == 2) {
            persons = generate2TeamPersons();
        } else {
            persons = generate3TeamPersons();
        }

        Collections.shuffle(persons);

        return persons;
    }

    /**
     * @return the _codeNames
     */
    public List<String> get_codeNames() {
        return _codeNames;
    }

    /**
     * @return the _gameBoard
     */
    public Location[][] get_gameBoard() {
        return _gameBoard;
    }

    /**
     * @return the _gameWords
     */
    public List<String> get_gameWords() {
        return _gameWords;
    }

    /**
     * @return the _listOfPerson
     */
    public List<Person> get_listOfPerson() {
        return _listOfPersons;
    }

    /**
     * Returns which team won due to the assassin being revealed. If the assassin
     * has not been revealed yet it will return <code>Team.NONE</code>
     *
     * @return <code>Team.NONE</code> If the location of the assassin has not been
     * revealed.
     * <p>
     * <code>Team.RED</code> If the location of the assassin has been
     * revealed and the revealer for that location was
     * <code>Team.BLUE</code>.
     * <p>
     * <code>Team.BLUE</code> If the location of the assassin has been
     * revealed and the revealer for that location was
     * <code>Team.RED</code>.
     */
    public Team getAssassinWinTeam() {
        List<Team> teams = new LinkedList<Team>();
        teams.add(Team.RED);
        teams.add(Team.BLUE);
        if (_numTeams == 3) {
            teams.add(Team.GREEN);
        }

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Location loc = _gameBoard[row][col];
                if (loc.getPerson() == Person.ASSASSIN && loc.getRevealed()) {
                    teams.remove(loc.getRevealer());
                }
            }
        }
        if (teams.size() != 1) {
            return Team.NONE;
        }
        return teams.get(0);
    }

    /**
     * Returns _count which is the teams turn count
     *
     * @return _count
     */
    public int getCount() {
        return _count;
    }

    /**
     * Returns which team's turn it is.
     *
     * @return _currentTeamsTurn
     */
    public Team getCurrentTeamsTurn() {
        return _currentTeamsTurn;
    }

    /**
     * Returns all the locations on the game board as an <code>ArrayList</code> of
     * <code>Locations</code>
     * <p>
     * The ArrayList is added row by row left to right.
     *
     * @return <code>{@literal ArrayList<Location>}</code> with all 25 locations
     * from game board
     */
    public List<Location> getLocationAsList() {
        ArrayList<Location> returnLocation = new ArrayList<>();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                returnLocation.add(_gameBoard[row][col]);
            }
        }

        return returnLocation;
    }

    /**
     * Returns the location object at a given row and column from the game board.
     * Row and column are valid if they are less than ROWS and COLS respectively
     *
     * @param row the row you want to access in the game board
     * @param col the column you want to access in the game board
     * @return <code>Location</code> for a valid row and column.
     * <p>
     * <code>null</code> for a non valid row and column.
     */
    public Location getLocationAt(int row, int col) {
        if (row >= ROWS || col >= COLS) {
            return null;
        }
        return _gameBoard[row][col];
    }

    /**
     * Returns the location object found with the given code name
     *
     * @param codename The code name to find location at
     * @return <code>Location</code> instance if a location with the code name is
     * found
     * <p>
     * <code>null</code> is returned if code name could not be found
     */
    public Location getLocationAt(String codename) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Location loc = _gameBoard[row][col];
                if (loc.getCodeName().equals(codename)) {
                    return loc;
                }
            }
        }

        return null;
    }

    /**
     * Helper method to return the opposing team of a given team
     *
     * @param team1 The team you want the opposing team for
     * @return Opposite Team
     */
    private Team getOppositeTeam(Team team1) {
        if (team1 == Team.BLUE) {
            return Team.RED;
        } else {
            return Team.BLUE;
        }
    }

    /**
     * This method will return which team has won the current game board. If no team
     * is in a win state for the current board, this method will return
     * <code>Team.NONE</code>
     * <p>
     * First the board is checked if a win has occurred. <code>Team.NONE</code> is
     * returned if this check fails
     * <p>
     * Then the board is checked if the assassin has been revealed. If the check
     * passes, it will return the opposing team of the revealer of the assassin.
     * <p>
     * Then the board is checked for the number of red agents revealed. If the
     * number of red agents revealed equals the total number of red agents on the
     * board, the red team has won and will return <code>Team.Red</code>. Otherwise
     * the blue team must have won since it is the last state to be checked.
     *
     * @return <code>Team.BLUE</code> If the board is in a win state for the blue
     * team.
     * <p>
     * <code>Team.RED</code> If the board is in a win state for the red
     * team.
     * <p>
     * <code>Team.NONE</code> if the board is not in a win state for either
     * team.
     */
    public Team getTeamWin() {
        if (!isWinState()) {
            return Team.NONE;
        } else {

            // Check if the assassination win has occurred
            Team assassinationTeamWin = getAssassinWinTeam();
            if (assassinationTeamWin != Team.NONE) {
                return assassinationTeamWin;
            }

            /*
             * Since assassination win has not occurred Check if the red team has won. If
             * the red team has not won then the blue team must have won
             */

            // number of red agents currently revealed
            int numRedAgents = 0;
            int numBlueAgents = 0;
            int numGreenAgents = 0;

            boolean redAssassin = false;
            boolean blueAssassin = false;

            // Check how many have been revealed
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    Location loc = _gameBoard[row][col];

                    // If the location of the person is red and revealed, increment number
                    if (loc.getPerson() == Person.R_AGENT && loc.getRevealed()) {
                        numRedAgents++;
                    } else if (loc.getPerson() == Person.B_AGENT && loc.getRevealed()) {
                        numBlueAgents++;
                    } else if (loc.getPerson() == Person.G_AGENT && loc.getRevealed()) {
                        numGreenAgents++;
                    } else if (loc.getPerson() == Person.ASSASSIN && loc.getRevealed()) {
                        switch (loc.getRevealer()) {
                            case RED:
                                redAssassin = true;
                                break;
                            case BLUE:
                                blueAssassin = true;
                                break;
                        }
                    }

                }
            }

            // If all red revealed, red team wins
            // else all blue must be revealed, blue team wins
            if (numRedAgents == _numRedAgents && _numRedAgents != 0 && !redAssassin) {
                return Team.RED;
            } else if (numBlueAgents == _numBlueAgents && _numBlueAgents != 0 && !blueAssassin) {
                return Team.BLUE;
            } else {
                return Team.GREEN;
            }

        }
    }

    /**
     * Checks if a given string is a "legal" clue. Legal clues have many rules, but
     * the rule that is check is whether or not the clue contains any non-revealed
     * location code names. If a location is revealed, a string of the same code
     * name is legal.
     *
     * @param clue A one word string to check if its valid in the current game board
     * @return <code>true</code> if the clue is not equal to any of the non-revealed
     * location code names.
     * <p>
     * <code>false</code> otherwise.
     */
    public boolean isLegalClue(String clue) {

        if (clue == null || clue.isEmpty()) {
            return false;
        }
        if (clue.contains(" ")) {
            return false;
        }

        // For each location on the game board,
        // check if the given clue is one of the
        // non-revealed code names. This indicates
        // an illegal clue.
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Location loc = _gameBoard[row][col];
                if (loc.getCodeName().equalsIgnoreCase(clue) && !loc.getRevealed()) {
                    return false;
                }
            }
        }

        // Since there wasn't any code names
        // found, return that this is a legal clue
        return true;

    }

    /**
     * Check if a person and team are on the same side
     *
     * @param person Person to be checked
     * @param team   Team to be checked
     * @return <code>true</code> If the person is <code>Person.B_AGENT</code> and
     * the team is <code>Team.RED</code> or if the person is
     * <code>Person.R_AGENT</code> and the team is <code>Team.BLUE</code>.
     * <p>
     * <code>false</code> otherwise.
     */
    private boolean isPersonOnSameTeam(Person person, Team team) {

        switch (person) {
            case B_AGENT:
                return team == Team.BLUE;
            case R_AGENT:
                return team == Team.RED;
            case G_AGENT:
                return team == Team.GREEN;
            default:
                return false;
        }

    }

    /**
     * Detects whether or not the game board is currently in a win state.
     * <p>
     * Since there are two ways to win in this game and two teams there are four win
     * states. Two for each team.
     * <p>
     * The first win state is when all of ones team's agents are revealed. This
     * method counts the number of a team's agents that have been revealed and then
     * checks if it is equal to their team's total agents. If the check is true the
     * method will return true. This is done for both teams.
     * <p>
     * The second win state is when the assassin is revealed on the field. When the
     * assassin is revealed on the field this method will return true since the
     * revealer of the assassin is who lost the game.
     *
     * @return <code>true</code> if a win state is detected in the game board
     * <p>
     * <code>false</code> if a win state is not detected in the game board
     */
    public boolean isWinState() {

        // number of red, blue, green and Assassin currently revealed
        int numRedAgents = 0;
        int numBlueAgents = 0;
        int numGreenAgents = 0;
        int numAssassin = 0;

        boolean redAssassin = false;
        boolean blueAssassin = false;
        boolean greenAssassin = false;

        // whether or not the game board is in a win state
        boolean won = false;

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {

                Location l = _gameBoard[row][col];
                Person p = l.getPerson();

                if (p == Person.R_AGENT && l.getRevealed()) {
                    numRedAgents++;
                } else if (p == Person.B_AGENT && l.getRevealed()) {
                    numBlueAgents++;
                } else if (p == Person.G_AGENT && l.getRevealed()) {
                    numGreenAgents++;
                } else if (p == Person.ASSASSIN && l.getRevealed()) {
                    numAssassin++;
                    switch (l.getRevealer()) {
                        case RED:
                            redAssassin = true;
                            break;
                        case BLUE:
                            blueAssassin = true;
                            break;
                        case GREEN:
                            greenAssassin = true;
                            break;
                    }
                }
            }
        }

        if (numRedAgents == _numRedAgents && _numRedAgents != 0 && !redAssassin) {
            won = true;
        }
        if (numBlueAgents == _numBlueAgents && _numBlueAgents != 0 && !blueAssassin) {
            won = true;
        }
        if (numGreenAgents == _numGreenAgents && _numGreenAgents != 0 && !greenAssassin) {
            won = true;
        }
        if (numAssassin == _numTeams - 1) {
            won = true;
        }

        return won;

    }

    /**
     * Creates list of all words from GameWords1.txt
     *
     * @param filename Path to the file
     * @return an <code>Arraylist</code> containing the words
     */
    public List<String> readInGameWords(String filename) {
        ArrayList<String> words = new ArrayList<>();
        try {
            for (String word : Files.readAllLines(Paths.get(filename))) {
                words.add(word.toUpperCase());
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return words;
    }

    /**
     * Sets _count, which is the teams turn count to that of the given parameter
     *
     * @param count the count to be set
     */
    public void setCount(int count) {
        _count = count;
    }

    /**
     * Sets the current teams turn to that of <code>team</code>
     *
     * @param team the team to set the turn to
     */
    public void setCurrentTeamsTurn(Team team) {
        _currentTeamsTurn = team;
    }

    /**
     * Assigns each of 25 Location instances with a code name, a person, revealed to
     * false, and set current Team's turn to Red.
     */

    public void setLocation() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                for (int i = 0; i < _codeNames.size(); i++) {
                    _gameBoard[row][col] = new Location();
                    _gameBoard[row][col].setCodeName(_codeNames.get(i));
                    _gameBoard[row][col].setPerson(_listOfPersons.get(i));
                    _gameBoard[row][col].setRevealed(false);
                }
            }
        }
        _currentTeamsTurn = Team.RED;
    }

    /**
     * Sets the location at the given row and column to the location
     *
     * @param row      the row you want to access in the game board
     * @param col      the column you want to access in the game board
     * @param location the location object you want to set in the game board
     */
    public void setLocationAt(int row, int col, Location location) {
        _gameBoard[row][col] = location;
    }

    /**
     * Assigns each of 25 Location instances with a code name, and a Person value.
     */
    private void setLocations() {

        for (int i = 0; i < ROWS * COLS; i++) {
            int row = i / ROWS;
            int col = i % COLS;

            _gameBoard[row][col] = new Location();
            _gameBoard[row][col].setCodeName(_codeNames.get(i));
            _gameBoard[row][col].setPerson(_listOfPersons.get(i));

        }

    }

    /**
     * finds the number of unrevealed agents for the current team
     *
     * @param team the curernt team
     * @return the number of agents not revealed for the current team
     */
    public int unrevealedAgents(Team team) {
        int agent = 0;

        Person agents = Person.B_AGENT;
        if (team == Team.RED) {
            agents = Person.R_AGENT;
        } else if (team == Team.GREEN) {
            agents = Person.G_AGENT;
        }

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Location loc = _gameBoard[row][col];
                if (loc.getPerson() == agents && !loc.getRevealed()) {
                    agent++;
                }
            }
        }
        return agent;
    }

    /**
     * Given a location on the game board, that location will be updated
     *
     * @param location The location that will be updated
     * @return <code>true</code> If the location had not been revealed and the
     * location's person was on the same team as the current team's turn.
     * <p>
     * <code>false</code> otherwise.
     */
    public boolean updateLocation(Location location) {

        if (location == null) {
            return false;
        }

        // if the location had not been revealed, reveal it
        if (!location.getRevealed()) {
            location.setRevealed(true);
            location.setRevealer(_currentTeamsTurn);

            // Check if the person is on the same team
            Person p = location.getPerson();
            return isPersonOnSameTeam(p, _currentTeamsTurn);

        } else {
            // location has been already revealed, return false
            return false;
        }

    }

    /**
     * Given a row and column of the game board, the location that corresponds to
     * that spot will be updated.
     *
     * @param row The row on the gameBoard to update location for
     * @param col The col on the gameBoard to update location for
     * @return <code>true</code> If the location at the given coordinate had not
     * been revealed and the person at the location is on the same team as
     * the current team's turn.
     * <p>
     * <code>false</code> otherwise
     * @see updateLocation
     */
    public boolean updateLocationAt(int row, int col) {
        return updateLocation(getLocationAt(row, col));
    }

    /**
     * Given a code name for a location on the board, that location will be updated
     *
     * @param codeName the code name to update location at
     * @return <code>true</code> If the location at the given code name had not been
     * revealed and the person at the location is on the same team as the
     * current team's turn.
     * <p>
     * <code>false</code> otherwise
     * @see updateLocation
     */
    public boolean updateLocationAt(String codeName) {
        return updateLocation(getLocationAt(codeName));
    }

}
