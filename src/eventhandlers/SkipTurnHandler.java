package eventhandlers;

import gui.CodenamesModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Vikram Singh
 * <p>
 * Event handler that skips the turn for the current team.
 * <p>
 * Typically used to end the guessing phase of the game
 */
public class SkipTurnHandler implements ActionListener {

    /**
     * Reference to the model for Codenames
     */
    private CodenamesModel model;

    public SkipTurnHandler(CodenamesModel m) {
        model = m;
    }

    @Override
    /**
     * Calls <code>Model.endTurn</code> to end the team's turn
     */
    public void actionPerformed(ActionEvent e) {
        model.endTurn();
    }

}
