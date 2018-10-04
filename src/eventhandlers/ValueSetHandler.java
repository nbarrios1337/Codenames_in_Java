package eventhandlers;

import gui.TicTacToeModel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ValueSetHandler implements MouseListener {

    TicTacToeModel model;
    int x;
    int y;

    public ValueSetHandler(TicTacToeModel model, int x, int y) {
        super();
        this.model = model;
        this.x = x;
        this.y = y;
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        model.setValueAt(x, y);

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // nothing

    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // nothing

    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        // nothing

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // nothing

    }

}
