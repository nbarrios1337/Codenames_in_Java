package gui;

import eventhandlers.GameHandler;
import eventhandlers.KonamiCodeHander;
import eventhandlers.WindowCloseHandler;

import javax.swing.*;

/**
 * Driver is the main entry point into this application
 *
 * @author Vikram Singh
 */
public class Driver implements Runnable {

    /**
     * Enum for deciding which game is current loaded
     *
     * @author Vikram Singh
     */
    public enum Games {
        Codenames2Teams, Codenames3Teams, TicTacToe, None
    }

    /**
     * Main entry point into the program
     *
     * @param args main's shenanigans
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Driver());
    }

    /**
     * Instance of model to represent the games's state
     */
    private GameModel _model;

    /**
     * The game's current GUI
     */
    private GameGui _gui;

    /*
     * The instance of the window
     */
    private JFrame _window;

    /*
     * Reference to the main content pane
     */
    private JPanel _mainPanel;

    /**
     * Current game loaded
     */
    private Games _currentGame;

    /**
     * Constructor
     * <p>
     * Sets the model of the window
     */
    public Driver() {
        _model = null;
        _gui = null;
        _currentGame = Games.None;
    }

    /**
     * Creates an ImageIcon for an image at the path
     *
     * @param path - path to the image
     * @return <code>ImageIcon</code> when path is valid
     * <p>
     * <code>null</code> when path is invalid
     */
    private ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find the file: " + path);
            return null;
        }
    }

    @Override
    /**
     * Loop that is run in this thread
     */
    public void run() {
        switchGame(Games.Codenames2Teams);
    }

    /**
     * Initializes the menu for the program.
     * <p>
     * Creates a file menu which houses the menu items for closing the window and
     * starting a new game
     */
    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();

        // add file to menu
        JMenu file = new JMenu("File");
        JMenuItem menuItem;

        // New Codewords
        menuItem = new JMenuItem("New: Codewords Game (2 Teams)");
        menuItem.addActionListener(new GameHandler(this, Games.Codenames2Teams));
        file.add(menuItem);

        // New Codewords3Teams
        menuItem = new JMenuItem("New: Codewords Game (3 Teams)");
        menuItem.addActionListener(new GameHandler(this, Games.Codenames3Teams));
        file.add(menuItem);

        // New TicTacToe
        menuItem = new JMenuItem("New: Tic Tac Toe Game");
        menuItem.addActionListener(new GameHandler(this, Games.TicTacToe));
        file.add(menuItem);

        // Separate games options from exit
        file.addSeparator();

        // Close the window
        menuItem = new JMenuItem("Exit");
        menuItem.addActionListener(new WindowCloseHandler(_window));
        file.add(menuItem);

        menuBar.add(file);
        _window.setJMenuBar(menuBar);

    }

    /**
     * Sets up the window with the name "codenames" and sets the menu
     */
    private void setupWindow() {

        String title;
        switch (_currentGame) {
            case Codenames2Teams:
                title = "Codenames";
                break;
            case Codenames3Teams:
                title = "Codenames";
                break;
            case TicTacToe:
                title = "Tic Tac Toe";
                break;
            default:
                title = "Choose a game";
                break;
        }

        boolean windowExists = (_window != null);

        if (!windowExists) {
            _window = new JFrame(title);
            _mainPanel = new JPanel();
            _window.getContentPane().add(_mainPanel);

            ImageIcon image = createImageIcon("../Images/codenamesbasic.png");
            _window.setIconImage(image.getImage());

        } else {
            _window.setTitle(title);
            _mainPanel.removeAll();
        }

        setupMenu();

        _window.setVisible(true);
        _window.pack();

        if (!windowExists) {
            _window.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            _window.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    ImageIcon hero = createImageIcon("../Images/hero.jpg");
                    JOptionPane.showMessageDialog(_window, "Goodbye World!", "Goodbye world!",
                            JOptionPane.PLAIN_MESSAGE, hero);
                    e.getWindow().dispose();
                }
            });
            _window.addKeyListener(new KonamiCodeHander(_window));
        }

    }

    /**
     * Switch the current game
     *
     * @param game - The game that we are switching to
     */
    public void switchGame(Games game) {
        _currentGame = game;

        switch (game) {
            case Codenames2Teams:
                setupWindow();
                _model = new TwoTeamModel(2);
                _gui = new TwoTeamGUI((CodenamesModel) _model, _mainPanel, _window, this);
                break;
            case Codenames3Teams:
                setupWindow();
                _model = new ThreeTeamModel(3);
                _gui = new ThreeTeamGUI((CodenamesModel) _model, _mainPanel, _window, this);
                break;
            case TicTacToe:
                setupWindow();
                _model = new TicTacToeModel();
                _gui = new TicTacToeGui((TicTacToeModel) _model, _mainPanel, _window, this);
                break;
            default:
                break;
        }
    }

    /**
     * Redraws the JFrame
     * <p>
     * Meant to be called outside of this class
     */
    public void updateJFrame() {
        _window.pack();
        _window.repaint();
    }

}
