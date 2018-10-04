package eventhandlers;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.LinkedList;

/**
 * @author Nicolas Barrios
 */
public class KonamiCodeHander implements KeyListener {

    private LinkedList<Key> konami;

    private int index;
    private boolean allCorrect;
    private JFrame window;

    public KonamiCodeHander(JFrame window) {
        super();
        this.window = window;
        konami = new LinkedList<>();
        index = 0;
        allCorrect = true;

        refill();
    }

    /**
     * Checks each input, incrementing the index and comparing the input to the
     * correct key.
     *
     * @param input the key input from the user
     */
    private void checkInput(KeyEvent input) {
        if (index < konami.size() && input.getKeyCode() == konami.get(index).getValue()) { // if you input the correct
            // code at that index
            konami.get(index).setCorrect(true);
            index++;
            return;
        } else if (index < konami.size() && input.getKeyCode() != konami.get(index).getValue()) { // if you input the
            // wrong code at
            // that index
            index++;
            return;
        } else { // if index == konami.size()
            for (Key k : konami) {
                if (!k.isCorrect()) { // if any of the keys weren't input correctly, allCorrect is false
                    allCorrect = false;
                }
            }
            if (!allCorrect) { // if the wrong code sequence is entered
                int optionChoice = JOptionPane.showConfirmDialog(window, "Try Again?", "You Failed",
                        JOptionPane.OK_CANCEL_OPTION);
                if (optionChoice == JOptionPane.OK_OPTION) { // if they want to try again
                    refill();
                    return;
                }
            } else { // if the input sequence is correct, reset and show correct messeage
                display();
                refill();
                return;
            }
        }
    }

    /**
     * Displays the secret message after a correct code sequence input
     */
    private void display() {
        URL imgURL = getClass().getResource("../Images/meme.jpg");
        if (imgURL != null) {
            ImageIcon image = new ImageIcon(imgURL);
            JOptionPane.showMessageDialog(window, null, "The Secret Has Been Revealed", JOptionPane.ERROR_MESSAGE,
                    image);
        }

    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        // nothing
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        checkInput(arg0);
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // nothing
    }

    /**
     * Resets the code for the next time it's inputted
     */
    public void refill() {
        konami.clear();
        index = 0;
        allCorrect = true;
        konami.add(new Key(KeyEvent.VK_UP, false));
        konami.add(new Key(KeyEvent.VK_UP, false));
        konami.add(new Key(KeyEvent.VK_DOWN, false));
        konami.add(new Key(KeyEvent.VK_DOWN, false));
        konami.add(new Key(KeyEvent.VK_LEFT, false));
        konami.add(new Key(KeyEvent.VK_RIGHT, false));
        konami.add(new Key(KeyEvent.VK_LEFT, false));
        konami.add(new Key(KeyEvent.VK_RIGHT, false));
        konami.add(new Key(KeyEvent.VK_A, false));
        konami.add(new Key(KeyEvent.VK_B, false));

    }

}
