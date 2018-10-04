package gui;

import code.Team;

public class TwoTeamModel extends CodenamesModel {

    /**
     * Empty Constructor Sets the current clue to an empty string and the count to 0
     */
    public TwoTeamModel(int numTeams) {
        currentClue = "";
        currentCount = 0;
        _numTeams = numTeams;
    }

    /**
     *
     */
    @Override
    void endTurnLogic() {
        currentRole = role.Spymaster;
        Team currentTeam = boardInstance.getCurrentTeamsTurn();
        if (currentTeam == Team.RED) {
            boardInstance.setCurrentTeamsTurn(Team.BLUE);
        } else {
            boardInstance.setCurrentTeamsTurn(Team.RED);
        }
    }

}
