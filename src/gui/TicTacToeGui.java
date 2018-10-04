package gui;

import eventhandlers.ValueSetHandler;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Gui for the TicTacToe game
 *
 * @author Vikram Singh
 * @author Nicolas Barrios
 */
public class TicTacToeGui extends GameGui {

    /**
     * Reference to the tic tac toe model
     */
    private TicTacToeModel _model;

    /**
     * Reference to the window's content pane
     */
    private JPanel _mainPanel;

    /**
     * Reference to the main window
     */
    private JFrame _window;

    /**
     * Reference to the game driver
     */
    private Driver _windowHolder;

    /**
     * Sets up all the references
     *
     * @param model     - TicTacToe model's reference
     * @param mainPanel - ContentPane of the main window reference
     * @param window    - Main window reference
     * @param driver    - Game's driver reference
     */
    public TicTacToeGui(TicTacToeModel model, JPanel mainPanel, JFrame window, Driver driver) {
        _model = model;
        _mainPanel = mainPanel;
        _window = window;
        _windowHolder = driver;

        _model.addObserver(this);

        _model.startGame();

    }

    /**
     * renders the gui
     */
    private void render() {
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(3, 3));
        _mainPanel.add(gamePanel);
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                JPanel box = new JPanel();
                setUpBox(box);
                gamePanel.add(box);
                String labelString = Character.toString(_model.valueAt(x, y));
                box.add(new JLabel(labelString));
                ValueSetHandler handler = new ValueSetHandler(_model, x, y);
                box.addMouseListener(handler);
                if (!labelString.equals(Character.toString(TicTacToeModel.SPACE))) {
                    box.removeMouseListener(handler);
                }
            }
        }
        JLabel secret = new JLabel("Press the Enter key after entering the code...");
        secret.setFont(new java.awt.Font(null, 99999, 9));
        _mainPanel.add(secret);
        secret.setHorizontalAlignment(SwingConstants.CENTER);
    }

    /**
     * Helper method of the <code>render</code> method decorates each cardPanel with
     * a rounded Border
     *
     * @param boxPanel the card to apply the changes to
     */
    private void setUpBox(JPanel boxPanel) {
        boxPanel.setVisible(true);
        Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true);
        boxPanel.setBorder(border);
    }

    @Override
    /**
     * updates the Gui when observer is notified
     */
    public void update() {
        _mainPanel.removeAll();

        if (_model.isFinished()) {
            winDialog();
            return;
        }

        render();
        _windowHolder.updateJFrame();

    }

    /**
     * Popup if game has been won
     */
    private void winDialog() {
        char team = _model.getTeamWin();
        String message;
        if (team != TicTacToeModel.SPACE) {
            message = team + " wins!";
        } else {
            message = "Tie Game!";
        }

        JOptionPane.showMessageDialog(_window, message);
        _model.startGame();

    }

}
