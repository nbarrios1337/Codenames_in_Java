package gui;

import java.util.ArrayList;

/**
 * Base class for all model classes
 *
 * @author Vikram Singh
 */
public abstract class GameModel {

    /**
     * List of all Observers (GUIs)
     */
    private ArrayList<Observer> _observers;

    /**
     * Empty Constructor
     * <p>
     * Instantiates a new ArrayList for the observers
     */
    public GameModel() {
        _observers = new ArrayList<>();
    }

    /**
     * Adds a new observer to the list
     *
     * @param observer - new observer to add
     */
    public void addObserver(Observer observer) {
        _observers.add(observer);
    }

    /**
     * Notify (call update) on all observers
     */
    public void notifyObservers() {
        for (Observer ob : _observers) {
            ob.update();
        }
    }

}
