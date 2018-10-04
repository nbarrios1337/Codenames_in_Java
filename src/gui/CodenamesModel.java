package gui;

import code.Board;
import code.Location;
import code.Team;

/**
 * The logical representation of Codename's game state.
 *
 * @author Vikram Singh
 * @author Nicolas Barrios
 * @author Veronica Vitale
 */
public abstract class CodenamesModel extends GameModel {

    /**
     * @author Vikram Singh
     * <p>
     * Enum to abstract the roles of the game between spymaster and agent
     */
    public enum role {
        Spymaster, Agent
    }

    /**
     * Reference to the game board
     */
    protected Board boardInstance;
    /**
     * The current clue for the current team's turn
     */
    protected String currentClue;
    /**
     * The current count for the clue for the current team's turn
     */
    protected int currentCount;
    /**
     * The number of teams for the current game
     */
    protected int _numTeams;
    /**
     * Representation of the current turn's role <code>role.Spymaster</code> - for
     * choosing the clue and count <code>role.Agent</code> - for guessing the valid
     * agents
     */
    protected role currentRole;

    /**
     * Determines whether or not a clue is legal
     *
     * @param clue String containing the clue entered
     * @return <code>Board.isLegalClue</code>
     */
    public boolean clueInput(String clue) {
        return boardInstance.isLegalClue(clue);
    }

    /**
     * Decrements the current count, if the count is less than 0 end the turn for
     * the team
     */
    public void decrementCount() {
        currentCount--;
        if (currentCount < 0) {
            endTurn();
        }
    }

    /**
     * Ends the spymasters turn and switches to agent
     */
    public void endSpymasterTurn() {
        currentRole = role.Agent;
        notifyObservers();
    }

    /**
     * Ends the turn for the current team
     * <p>
     * Called when the count is equal to 0 or when turn is skipped
     */
    public void endTurn() {
        endTurnLogic();
        setClue("");
        setCount(0);

        notifyObservers();

    }

    abstract void endTurnLogic();

    /**
     * Gets what the current clue is
     *
     * @return String containing the current clue
     */
    public String getClue() {
        return currentClue;
    }

    /**
     * Gets the current count.
     *
     * @return integer of the current count.
     */
    public int getCount() {
        return currentCount;
    }

    /**
     * Gets the current role for the team
     *
     * @return <code>currentRole</code>
     */
    public role getCurrentRole() {
        return currentRole;
    }

    /**
     * Return wrapper for get_gameBoard in Board.java
     *
     * @return <code>Board.get_gameBaord</code>
     */
    public Location[][] getGameBoardLocations() {
        return boardInstance.get_gameBoard();
    }

    /**
     * Return wrapper for getLocationAt in Board.java
     *
     * @param x row index
     * @param y column index
     * @return the Location instance at [x][y]
     */
    public Location getLocation(int x, int y) {
        return boardInstance.getLocationAt(x, y);
    }

    /**
     * Return wrapper for getTeamWin() in Board.java
     *
     * @return <code>Team</code>
     */
    public Team getTeamWin() {
        return boardInstance.getTeamWin();
    }

    /**
     * Return wrapper for unrevealedAgents() in Board
     *
     * @param team
     * @return <code>unrevealedAgents()</code>
     */
    public int getUnrevealedAgents(Team team) {
        return boardInstance.unrevealedAgents(team);
    }

    /**
     * Return wrapper for getCurrentTeamsTurn in Board.java
     *
     * @return <code>Board.getCurrentTeamsTurn()</code>
     */
    public Team getWhoseTurn() {
        return boardInstance.getCurrentTeamsTurn();
    }

    /**
     * Return wrapper for isWinState() in Board.java
     *
     * @return <code>Board.isWinState()</code>
     */
    public boolean isGameFinished() {
        return boardInstance.isWinState();
    }

    /**
     * Sets the value of the current clue.
     *
     * @param clue String containing what the current clue will be set to
     */
    public void setClue(String clue) {
        currentClue = clue;
    }

    /**
     * Sets the value of the current count. Ends turn if count is Less than 0
     *
     * @param count integer that will be the currentCount
     */
    public void setCount(int count) {
        currentCount = count;
        if (currentCount < 0) {
            endTurn();
        }
    }

    /**
     * Starts game. Creates a new Board instance and sets turn to RED
     */
    public void startGame() {
        boardInstance = new Board(_numTeams);
        currentRole = role.Spymaster;
        boardInstance.setCurrentTeamsTurn(Team.RED);
        notifyObservers();
    }

    /**
     * Updates the location at the given row and column
     *
     * @param row    - the row of the location to update
     * @param column - the column of the location to update
     */
    public void updateLocation(int row, int column) {
        this.getLocation(row, column);

    }

    /**
     * Updates the location at the given location
     *
     * @param location - the location o be updated
     * @return <code>true</code> if the location has the agent for the current team
     * <code>false</code> otherwise
     */
    public boolean updateLocationAt(Location location) {
        boolean success = boardInstance.updateLocation(location);
        if (success) {
            decrementCount();
        }
        return success;
    }

}
