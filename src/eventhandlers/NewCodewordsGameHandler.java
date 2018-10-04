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
public class NewCodewordsGameHandler implements ActionListener {

    /**
     * Reference to the game's state
     */
    private Driver _driver;

    /**
     * Sets the reference to the game driver
     *
     * @param driver - reference to the game driver
     */
    public NewCodewordsGameHandler(Driver driver) {
        _driver = driver;
    }

    @Override
    /**
     * Starts a new game
     */
    public void actionPerformed(ActionEvent e) {
        _driver.switchGame(Games.Codenames2Teams);

    }

}
