package eventhandlers;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Action Listener for the Exit menu item in the window
 * <p>
 * Closes the window by sending a Java.Awt Event
 *
 * @author Vikram Singh
 */
public class WindowCloseHandler implements ActionListener {

    /**
     * Reference to the window we are trying to close
     */
    private JFrame _window;

    /**
     * Constructor that sets the reference of the window
     *
     * @param window - reference to the window to be closed
     */
    public WindowCloseHandler(JFrame window) {
        _window = window;
    }

    @Override
    /**
     * Closes the menu by sending a close event
     */
    public void actionPerformed(ActionEvent arg0) {
        _window.dispatchEvent(new WindowEvent(_window, WindowEvent.WINDOW_CLOSING));
    }

}
