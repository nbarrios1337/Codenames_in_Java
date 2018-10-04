package eventhandlers;

import code.Location;
import code.Team;
import gui.CodenamesModel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CardRevealHandler implements MouseListener {

    /**
     * The current team
     */
    Team team;

    /**
     * The location to be updated
     */
    Location location;

    /**
     * The logical representation of the game's state
     */
    CodenamesModel model;

    /**
     * MouseListener for each cardPanel in the gameBoard
     *
     * @param model    reference to the game's current state
     * @param team     the Team currently taking their turn
     * @param location the <code>Location</code> the cardPanel is associated with
     */
    public CardRevealHandler(CodenamesModel model, Team team, Location location) {
        this.team = team;
        this.location = location;
        this.model = model;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (!model.updateLocationAt(location)) {
            model.endTurn();
        }
        model.notifyObservers();

    }

    @Override
    /**
     * mouseEntered event handler
     */
    public void mouseEntered(MouseEvent e) {
        // nothing
    }

    @Override
    /**
     * mouseExited event handler
     */
    public void mouseExited(MouseEvent e) {
        // nothing
    }

    @Override
    /**
     * mousePressed event handler
     */
    public void mousePressed(MouseEvent e) {
        // nothing
    }

    @Override
    /**
     * mouseReleased event handler
     */
    public void mouseReleased(MouseEvent e) {
        // nothing
    }

}
