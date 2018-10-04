package eventhandlers;

import gui.Driver;
import gui.Driver.Games;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Action Listener for the new codewords menu item
 * <p>
 * Starts a new game by calling <code>Driver.switchGame</code>
 *
 * @author Vikram Singh
 */
public class GameHandler implements ActionListener {

    /**
     * Reference to the game's state
     */
    private Driver _driver;
    private Games _game;

    /**
     * Sets the reference to the game driver
     *
     * @param driver - reference to the game driver
     */
    public GameHandler(Driver driver, Driver.Games game) {
        _driver = driver;
        _game = game;
    }

    @Override
    /**
     * Starts a new game
     */
    public void actionPerformed(ActionEvent e) {
        _driver.switchGame(_game);

    }

}
