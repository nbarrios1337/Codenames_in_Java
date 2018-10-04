package eventhandlers;

import gui.CodenamesModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Action Listener for the clue submit button
 * <p>
 * checks for a legal clue and assigns it to the current clue in Model checks
 * for a legal count and assigns it to the current count in Model
 *
 * @author Veornica Vitale
 */
public class ClueSubmitHandler implements ActionListener {

    private CodenamesModel _model;
    private JTextField _clue;
    private JFrame _window;
    private JTextField _count;

    public ClueSubmitHandler(CodenamesModel m, JTextField c, JTextField c2, JFrame w) {
        _model = m;
        _clue = c;
        _window = w;
        _count = c2;

    }

    /**
     * Checks for a legal clue and count. Ends turn is clue isn't legal. Clears the
     * text of the clue and count box and assigns it to currentClue and current
     * count in the model otherwise
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean youDoneHecked = false;
        if (_model.clueInput(_clue.getText())) {
            _model.setClue(_clue.getText());

            _clue.setText("");

        } else {
            _clue.setText("");
            youDoneHecked = true;
            _model.endTurn();
        }
        int count = -1;
        try {
            count = Integer.parseInt(_count.getText());
        } catch (NumberFormatException excep) {
            JOptionPane.showMessageDialog(_window, "Please Enter a Valid Count!");
            youDoneHecked = true;
        }

        if (count >= 0 && count <= _model.getUnrevealedAgents(_model.getWhoseTurn())) {
            _model.setCount(count);
            _count.setText("");
        } else {
            JOptionPane.showMessageDialog(_window, "Please Enter a Valid Count!");
            youDoneHecked = true;
        }
        if (!youDoneHecked) {
            _model.endSpymasterTurn();
        }
    }

}
