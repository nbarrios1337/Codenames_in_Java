package gui;

import code.Board;
import code.Location;
import code.Person;
import code.Team;

import java.util.ArrayList;

public class ThreeTeamModel extends CodenamesModel {

    private ArrayList<Team> teams;
    private boolean assassinTriggered;

    public ThreeTeamModel(int numTeams) {
        currentClue = "";
        currentCount = 0;
        _numTeams = numTeams;

    }

    @Override
    void endTurnLogic() {
        currentRole = role.Spymaster;
        if (!assassinTriggered) {
            teams.add(teams.get(0));
        }
        teams.remove(0);
        boardInstance.setCurrentTeamsTurn(teams.get(0));

        assassinTriggered = false;

    }

    @Override
    public void startGame() {
        boardInstance = new Board(_numTeams);
        currentRole = role.Spymaster;
        boardInstance.setCurrentTeamsTurn(Team.RED);

        teams = new ArrayList<>();
        teams.add(Team.RED);
        teams.add(Team.BLUE);
        teams.add(Team.GREEN);

        assassinTriggered = false;

        notifyObservers();
    }

    @Override
    public boolean updateLocationAt(Location location) {
        boolean success = boardInstance.updateLocation(location);
        if (success) {
            decrementCount();
        } else {
            if (location.getPerson() == Person.ASSASSIN) {
                assassinTriggered = true;
            }
        }
        return success;
    }

}
