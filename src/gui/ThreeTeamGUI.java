package gui;

import code.Person;
import code.Team;
import eventhandlers.ClueSubmitHandler;
import eventhandlers.SkipTurnHandler;

import javax.swing.*;
import java.awt.*;

public class ThreeTeamGUI extends CodenamesGUI {

    /*
     * thank you Papa Alphonce
     */
    public ThreeTeamGUI(CodenamesModel model, JPanel mainPanel, JFrame window, Driver driver) {
        _window = window;
        _model = model;
        _windowHolder = driver;
        _mainPanel = mainPanel;

        _model.addObserver(this);
        _model.startGame();

    }

    @Override
    protected void renderStatPanel(JPanel statPanel) {
        renderCard(statPanel);
        statPanel.setLayout(new BoxLayout(statPanel, BoxLayout.Y_AXIS));

        JLabel numRedAgents = new JLabel(
                "Number of Red Agents Left: " + Integer.toString(_model.getUnrevealedAgents(Team.RED)));
        numRedAgents.setForeground(COLOR_RED);

        JLabel numBlueAgents = new JLabel(
                "Number of Blue Agents Left: " + Integer.toString(_model.getUnrevealedAgents(Team.BLUE)));
        numBlueAgents.setForeground(COLOR_BLUE);

        JLabel numGreenAgents = new JLabel(
                "Number of Green Agents Left: " + Integer.toString(_model.getUnrevealedAgents(Team.GREEN)));
        numGreenAgents.setForeground(COLOR_GREEN);

        JLabel countLabel = new JLabel("Count of Words Related to Clue: " + Integer.toString(_model.getCount()));
        JLabel clueLabel = new JLabel("Clue Given: " + _model.getClue());

        switch (_model.getWhoseTurn()) {
            case BLUE:
                countLabel.setForeground(COLOR_BLUE);
                clueLabel.setForeground(COLOR_BLUE);
                break;
            case RED:
                countLabel.setForeground(COLOR_RED);
                clueLabel.setForeground(COLOR_RED);
                break;
            case GREEN:
                countLabel.setForeground(COLOR_GREEN);
                clueLabel.setForeground(COLOR_GREEN);
            default:
                break;
        }

        JLabel teamLabel = new JLabel("Your Team: " + _model.getWhoseTurn().toString());

        statPanel.add(numRedAgents);
        statPanel.add(numBlueAgents);
        statPanel.add(numGreenAgents);
        statPanel.add(new JLabel(" "));
        statPanel.add(countLabel);
        statPanel.add(clueLabel);
        statPanel.add(teamLabel);

    }

    @Override
    protected void renderSubmitPanel(JPanel submitPanel) {
        renderCard(submitPanel);
        submitPanel.setLayout(new BoxLayout(submitPanel, BoxLayout.Y_AXIS));

        JLabel clueMessage = new JLabel("Enter Clue: ");
        JTextField clues = new JTextField(20);
        JButton submit = new JButton("Submit");
        JLabel countMessage = new JLabel("Enter Count: ");
        JTextField counts = new JTextField(5);
        submit.addActionListener(new ClueSubmitHandler(_model, clues, counts, _window));

        submitPanel.add(clueMessage);
        submitPanel.add(clues);
        submitPanel.add(countMessage);
        submitPanel.add(counts);

        submitPanel.add(submit);

    }

    /**
     * Helper method of the <code>spyMasterRender</code> and
     * <code>agentRender</code> methods. Sets the background color of the panel
     * parameter based on the Person type at Location[x][y]
     *
     * @param cardPanel the card to apply the changes to
     * @param x         x-coordinate of the Location[][] array
     * @param y         y-coordinate of the Location[][] array
     */
    @Override
    protected void setColor(JPanel cardPanel, int x, int y) {
        if (cards[x][y].getPerson().equals(Person.R_AGENT)) {
            cardPanel.setBackground(COLOR_RED);
        } else if (cards[x][y].getPerson().equals(Person.B_AGENT)) {
            cardPanel.setBackground(COLOR_BLUE);
        } else if (cards[x][y].getPerson().equals(Person.G_AGENT)) {
            cardPanel.setBackground(COLOR_GREEN);
        } else if (cards[x][y].getPerson().equals(Person.ASSASSIN)) {
            cardPanel.setBackground(Color.GRAY);
        } else if (cards[x][y].getPerson().equals(Person.BYSTANDER)) {
            cardPanel.setBackground(COLOR_YELLOW);
        }

    }

    @Override
    protected JPanel setUpGameView(boolean isSpyMaster) {
        _mainPanel.setLayout(new BoxLayout(_mainPanel, BoxLayout.Y_AXIS));
        JPanel gameBoardPanel = new JPanel();
        JPanel submitPanel = new JPanel();
        JPanel statPanel = new JPanel();
        JPanel subAndStatGroup = new JPanel();

        JButton skipTurn = new JButton("End Turn");

        gameBoardPanel.setLayout(new GridLayout(5, 5));

        _mainPanel.add(gameBoardPanel);
        if (isSpyMaster) {

            renderSubmitPanel(submitPanel);
            subAndStatGroup.add(submitPanel);
        }

        renderStatPanel(statPanel);
        subAndStatGroup.add(statPanel);

        _mainPanel.add(subAndStatGroup);

        if (!isSpyMaster) {
            skipTurn.addActionListener(new SkipTurnHandler(_model));
            _mainPanel.add(skipTurn);
        }

        JLabel secret = new JLabel("Press the Enter key after entering the code...");
        secret.setFont(new java.awt.Font(null, 99999, 9));
        _mainPanel.add(secret);
        return gameBoardPanel;
    }

    private void turnStartUpdateDialog() {
        boolean gameFinished = _model.isGameFinished();
        String message;
        ImageIcon image;

        if (gameFinished) {
            Team t = _model.getTeamWin();
            if (t == Team.RED) {
                message = "Red Team wins";
                image = createImageIcon("../Images/codenames_red.png");
            } else if (t == Team.BLUE) {
                message = "Blue Team wins";
                image = createImageIcon("../Images/codenames_blue.png");
            } else {
                message = "Green Team wins";
                image = createImageIcon("../Images/codenames_green.png");
            }
            JOptionPane.showMessageDialog(_window, message, "Winner Winner Chicken Dinner!",
                    JOptionPane.INFORMATION_MESSAGE, image);
        } else {
            Team t = _model.getWhoseTurn();
            if (t == Team.RED) {
                message = "It is Red Team's turn";
                image = createImageIcon("../Images/codenames_red.png");
            } else if (t == Team.BLUE) {
                message = "It is Blue Team's turn";
                image = createImageIcon("../Images/codenames_blue.png");
            } else {
                message = "It is Green Team's turn";
                image = createImageIcon("../Images/codenames_green.png");
            }
            JOptionPane.showMessageDialog(_window, message, "Next Turn", JOptionPane.INFORMATION_MESSAGE, image);
        }

        if (gameFinished) {
            _model.startGame();
        }

    }

    @Override
    public void update() {
        cards = _model.getGameBoardLocations(); // creates a local Location[][] array to reference, alias of the Board
        turnStartUpdateDialog();

        _mainPanel.removeAll();
        render(_model.getWhoseTurn(), (_model.getCurrentRole() == CodenamesModel.role.Spymaster));

        _windowHolder.updateJFrame();

    }

}
