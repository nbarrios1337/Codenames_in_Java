package gui;

import code.Location;
import code.Team;
import eventhandlers.CardRevealHandler;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public abstract class CodenamesGUI extends GameGui {

    protected static final Color COLOR_RED = new Color(244, 67, 54);
    protected static final Color COLOR_GREEN = new Color(76, 175, 80);
    protected static final Color COLOR_BLUE = new Color(33, 150, 243);
    protected static final Color COLOR_YELLOW = new Color(255, 235, 59);
    protected CodenamesModel _model;
    protected Driver _windowHolder;
    protected JPanel _mainPanel;
    protected JFrame _window;
    protected Location[][] cards;

    /**
     * ~~Taken from Driver.java~~ Creates an ImageIcon for an image at the path
     *
     * @param path - path to the image
     * @return <code>ImageIcon</code> when path is valid
     * <p>
     * <code>null</code> when path is invalid
     */
    protected ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find the file: " + path);
            return null;
        }
    }

    /**
     * Instantiates and fills a new gameBoardPanel to add to the
     * <code>_mainPanel</code>, then loops through a 5x5 array, creating new
     * cardPanels for each Location. The cards details are displayed depending on
     * whether the player is a Spymaster or an Agent, by using different rendering
     * methods for each one.
     *
     * @param team        the Team whose taking the turn
     * @param isSpyMaster whether the player is a Spymaster
     */
    public void render(Team team, boolean isSpyMaster) {
        JPanel gameBoardPanel = setUpGameView(isSpyMaster);

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                JPanel cardPanel = new JPanel();
                renderCard(cardPanel);
                gameBoardPanel.add(cardPanel);
                JLabel codename = new JLabel(cards[x][y].getCodeName());
                JLabel person = new JLabel(cards[x][y].getPerson().toString());
                if (isSpyMaster) {
                    renderSpymasterBoard(cardPanel, codename, person, x, y);
                } else {
                    renderAgentBoard(cardPanel, codename, person, team, x, y);
                }
            }
        }
    }

    /**
     * Helper method of the <code>render()</code> method
     * <ul>
     * <li>fills in the codename and person type for the parameter cardPanel</li>
     * <li>inserts a MouseListener to the cardPanel, allowing the panel to be
     * clickable without a button</li>
     * <li>If the card is revealed:</li>
     * <ul>
     * <li>set its color based on the <code>Person</code> type, show only the person
     * JLabel, and de-clickify the panel.</li>
     * <li>else, show only the codename JLabel.</li>
     * </ul>
     * </ul>
     *
     * @param cardPanel the card to apply the changes to
     * @param codename  the codename of the Location at (<code>x,y</code>)
     * @param person    the Person type of the Location
     * @param team      the Team who's taking their turn
     * @param x         x-coordinate of the Location[][] array
     * @param y         y-coordinate of the Location[][] array
     */
    private void renderAgentBoard(JPanel cardPanel, JLabel codename, JLabel person, Team team, int x, int y) {
        cardPanel.add(codename);
        cardPanel.add(person);
        CardRevealHandler handler = new CardRevealHandler(_model, team, cards[x][y]);
        cardPanel.addMouseListener(handler);
        if (cards[x][y].getRevealed()) {
            setColor(cardPanel, x, y);
            person.setVisible(true);
            codename.setVisible(false);
            cardPanel.removeMouseListener(handler);
        } else {
            person.setVisible(false);
            codename.setVisible(true);
        }
    }

    /**
     * Helper method of the <code>render</code> method decorates each cardPanel with
     * a rounded Border
     *
     * @param cardPanel the card to apply the changes to
     */
    protected void renderCard(JPanel cardPanel) {
        cardPanel.setVisible(true);
        Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true);
        cardPanel.setBorder(border);
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
    }

    /**
     * Helper method of the <code>render()</code> method.
     * <ul>
     * <li>fills in the codename and person type for the parameter cardPanel</li>
     * <li>sets the card's color based on the Person type</li>
     * <li>If the card is revealed:</li>
     * <ul>
     * <li>show only the person JLabel.</li>
     * <li>else, show both the codename and the person JLabels.</li>
     * </ul>
     * </ul>
     *
     * @param cardPanel the card to apply the changes to
     * @param codename  the codename of the Location at (<code>x,y</code>)
     * @param person    the Person type of the Location
     * @param x         x-coordinate of the Location[][] array
     * @param y         y-coordinate of the Location[][] array
     */
    private void renderSpymasterBoard(JPanel cardPanel, JLabel codename, JLabel person, int x, int y) {
        cardPanel.add(codename);
        cardPanel.add(person);
        setColor(cardPanel, x, y);
        if (cards[x][y].getRevealed()) {
            person.setVisible(true);
            codename.setVisible(false);
        } else {
            codename.setVisible(true);
            person.setVisible(true);
        }
    }

    /**
     * Helper method to allow the players to see the current state of the game
     *
     * @param statPanel the panel where all the stats are to be stored
     */
    protected abstract void renderStatPanel(JPanel statPanel);

    /**
     * Helper method to allow the spy master to submit a clue
     *
     * @param submitPanel the panel that holds the clue and count inputs and the submit
     *                    button
     */
    protected abstract void renderSubmitPanel(JPanel submitPanel);

    /**
     * Sets the card paramtere's color based on which Person it holds
     *
     * @param cardPanel the card to colorize
     * @param x         x-coordinate of the Location[][] array
     * @param y         y-coordinate of the Location[][] array
     */
    abstract void setColor(JPanel cardPanel, int x, int y);

    /**
     * Helper method for render() fills in everything but the gameBoard aka: the
     * submit, stats, and end turn elements
     *
     * @param isSpyMaster whether the current player is the Spymaster
     * @return the set-up gameBoardPanel
     */
    protected abstract JPanel setUpGameView(boolean isSpyMaster);
}
