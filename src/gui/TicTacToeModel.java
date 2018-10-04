package gui;

/**
 * TicTacToe game's state
 *
 * @author Vikram Singh
 */
public class TicTacToeModel extends GameModel {

    /**
     * Constant value for rows
     */
    public static final int ROWS = 3;

    /**
     * Constant value for cols
     */
    public static final int COLS = 3;

    /**
     * Constant value for the space character
     */
    public static final char SPACE = ' ';

    /**
     * Logical representation of the tic tac toe board as a 3 x 3 character array
     */
    private char[][] _gameBoard;

    /**
     * Current team's turn
     * <p>
     * Represented with a character to the team's name
     */
    private char _currentTeam;

    /**
     * Empty Constructor
     */
    public TicTacToeModel() {
        _gameBoard = new char[ROWS][COLS];

        notifyObservers();

    }

    /**
     * Change current team
     */
    private void changeTeam() {
        if (_currentTeam == 'X')
            _currentTeam = 'O';
        else
            _currentTeam = 'X';
    }

    /**
     * @return <code>'X'</code> when team X wins
     * <p>
     * <code>'O'</code> when team O wins
     * <p>
     * <code>' '</code> when no one has won
     */
    public char getTeamWin() {
        if (!isFinished())
            return SPACE;

        if (_gameBoard[0][0] == _gameBoard[0][1] && _gameBoard[0][1] == _gameBoard[0][2]) {
            return _gameBoard[0][0];
        } else if (_gameBoard[1][0] == _gameBoard[1][1] && _gameBoard[1][1] == _gameBoard[1][2]) {
            return _gameBoard[1][0];
        } else if (_gameBoard[2][0] == _gameBoard[2][1] && _gameBoard[2][1] == _gameBoard[2][2]) {
            return _gameBoard[2][0];
        } else if (_gameBoard[0][0] == _gameBoard[1][0] && _gameBoard[1][0] == _gameBoard[2][0]) {
            return _gameBoard[2][0];
        } else if (_gameBoard[0][1] == _gameBoard[1][1] && _gameBoard[1][1] == _gameBoard[2][1]) {
            return _gameBoard[0][1];
        } else if (_gameBoard[0][2] == _gameBoard[1][2] && _gameBoard[1][2] == _gameBoard[2][2]) {
            return _gameBoard[0][2];
        } else if (_gameBoard[0][0] == _gameBoard[1][1] && _gameBoard[1][1] == _gameBoard[2][2]) {
            return _gameBoard[1][1];
        } else if (_gameBoard[2][0] == _gameBoard[1][1] && _gameBoard[1][1] == _gameBoard[0][2]) {
            return _gameBoard[1][1];
        } else {
            return SPACE;
        }

    }

    /**
     * @return <code>true</code> when at least one of the board win states has
     * occurred
     * <p>
     * <code>false</code> when none of the board win states has occured
     */
    public boolean isFinished() {
        return (_gameBoard[0][0] == _gameBoard[0][1] && _gameBoard[0][1] == _gameBoard[0][2]
                && _gameBoard[0][0] != SPACE)
                || (_gameBoard[1][0] == _gameBoard[1][1] && _gameBoard[1][1] == _gameBoard[1][2]
                && _gameBoard[1][0] != SPACE)
                || (_gameBoard[2][0] == _gameBoard[2][1] && _gameBoard[2][1] == _gameBoard[2][2]
                && _gameBoard[2][0] != SPACE)
                || (_gameBoard[0][0] == _gameBoard[1][0] && _gameBoard[1][0] == _gameBoard[2][0]
                && _gameBoard[2][0] != SPACE)
                || (_gameBoard[0][1] == _gameBoard[1][1] && _gameBoard[1][1] == _gameBoard[2][1]
                && _gameBoard[2][1] != SPACE)
                || (_gameBoard[0][2] == _gameBoard[1][2] && _gameBoard[1][2] == _gameBoard[2][2]
                && _gameBoard[2][2] != SPACE)
                || (_gameBoard[0][0] == _gameBoard[1][1] && _gameBoard[1][1] == _gameBoard[2][2]
                && _gameBoard[0][0] != SPACE)
                || (_gameBoard[2][0] == _gameBoard[1][1] && _gameBoard[1][1] == _gameBoard[0][2]
                && _gameBoard[2][0] != SPACE
                || (_gameBoard[0][0] != SPACE && _gameBoard[0][1] != SPACE && _gameBoard[0][2] != SPACE
                && _gameBoard[1][0] != SPACE && _gameBoard[1][1] != SPACE && _gameBoard[1][2] != SPACE
                && _gameBoard[2][0] != SPACE && _gameBoard[2][1] != SPACE
                && _gameBoard[2][2] != SPACE));
    }

    /**
     * Sets the value at (row, col) to the current team
     *
     * @param row - the row to change
     * @param col - the column to change
     */
    public void setValueAt(int row, int col) {

        _gameBoard[row][col] = _currentTeam;
        changeTeam();

        notifyObservers();

    }

    /**
     * Sets up a new game
     */
    public void startGame() {
        _currentTeam = 'X';

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                _gameBoard[row][col] = SPACE;
            }
        }

        notifyObservers();
    }

    /**
     * Get's the character at the given row and column
     *
     * @param row - row of the game board
     * @param col - column of the game board
     * @return <code>char</code> - the character at the given coordinates
     */
    public char valueAt(int row, int col) {
        return _gameBoard[row][col];
    }

}
