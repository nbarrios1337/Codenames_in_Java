package eventhandlers;

import gui.Driver;
import gui.Driver.Games;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Action Listener for the new TicTacToe menu item
 * <p>
 * Starts a new game by calling <code>Driver.switchGame</code>
 *
 * @author Vikram Singh
 */
public class NewTicTacToeGameHandler implements ActionListener {

    /**
     * Reference to the game's state
     */
    private Driver _driver;

    /**
     * Sets the reference to the game's driver
     *
     * @param driver - reference to the game's driver
     */
    public NewTicTacToeGameHandler(Driver driver) {
        _driver = driver;
    }

    @Override
    /**
     * Starts a new game
     */
    public void actionPerformed(ActionEvent arg0) {
        _driver.switchGame(Games.TicTacToe);
    }

}
