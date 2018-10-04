package tests;

import code.Board;
import code.Location;
import code.Person;
import code.Team;
import gui.ThreeTeamModel;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Veronica Vitale
 */
public class ThreeTeamTest {
    Board a;
    Board b;

    @Before
    public void setUpBoards() {
        a = new Board(3);
        b = new Board(3);
    }

    @Test
    public void testAssassinWin() {
        Board c = new Board(3);
        boolean firstAssassin = false;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (c.getLocationAt(i, j).getPerson() == Person.ASSASSIN) {
                    if (!firstAssassin) {
                        Location l = new Location();
                        l.setPerson(Person.ASSASSIN);
                        l.setRevealed(true);
                        l.setRevealer(Team.BLUE);
                        c.setLocationAt(i, j, l);
                    } else {
                        Location l = new Location();
                        l.setPerson(Person.ASSASSIN);
                        l.setRevealed(true);
                        l.setRevealer(Team.GREEN);
                        c.setLocationAt(i, j, l);
                    }
                    firstAssassin = true;
                }
            }
        }
        assertTrue(c.isWinState());
        assertEquals(Team.RED, c.getAssassinWinTeam());
    }

    @Test
    public void testBlueTeamVictory() {
        Board c = new Board(3);
        for (Location l : c.getLocationAsList()) {
            if (l.getPerson() == Person.B_AGENT) {
                l.setRevealed(true);
            }
        }
        assertTrue(c.isWinState());
        assertTrue(c.getTeamWin() == Team.BLUE);
        Board b2 = new Board(3);
        int thereCanOnlyBe1 = 0;
        for (Location l : b2.getLocationAsList()) {
            if (l.getPerson() == Person.B_AGENT) {
                l.setRevealed(true);
            }
            if (l.getPerson() == Person.ASSASSIN && thereCanOnlyBe1 == 0) {
                thereCanOnlyBe1++;
                l.setRevealed(true);
                l.setRevealer(Team.BLUE);
            }
        }
        assertFalse(b2.isWinState());

    }

    @Test
    public void testCorrectAgentRatio() {
        List<Person> l1 = b.get_listOfPerson();
        int as = 0;
        int b = 0;
        int by = 0;
        int g = 0;
        int r = 0;
        for (Person p : l1) {
            if (p == Person.ASSASSIN) {
                as++;
            }
            if (p == Person.B_AGENT) {
                b++;
            }
            if (p == Person.BYSTANDER) {
                by++;
            }
            if (p == Person.G_AGENT) {
                g++;
            }
            if (p == Person.R_AGENT) {
                r++;
            }
        }
        assertEquals(2, as);
        assertTrue(b == 5);
        assertTrue(by == 7);
        assertTrue(g == 5);
        assertTrue(r == 6);
    }

    @Test
    public void testGreenTeamVictory() {
        Board g = new Board(3);
        for (Location l : g.getLocationAsList()) {
            if (l.getPerson() == Person.G_AGENT) {
                l.setRevealed(true);
            }
        }
        assertTrue(g.isWinState());
        assertTrue(g.getTeamWin() == Team.GREEN);
        Board g2 = new Board(3);
        int thereCanOnlyBe1 = 0;
        for (Location l : g2.getLocationAsList()) {
            if (l.getPerson() == Person.G_AGENT) {
                l.setRevealed(true);
            }
            if (l.getPerson() == Person.ASSASSIN && thereCanOnlyBe1 == 0) {
                thereCanOnlyBe1++;
                l.setRevealed(true);
                l.setRevealer(Team.GREEN);
            }
        }
        assertFalse(g2.isWinState());

    }

    @Test
    public void testInitWinless() {
        assertFalse(a.isWinState());
    }

    @Test
    public void testRandomAssignments() {
        List<Person> l1 = a.get_listOfPerson();
        List<Person> l2 = b.get_listOfPerson();
        int count = 0;
        for (int i = 0; i < 25; i++) {
            if (l1.get(i) == l2.get(i)) {
                count++;
            }
        }
        assertTrue(count < 25);
    }

    @Test
    public void testRedTeamVictory() {
        Board r = new Board(3);
        for (Location l : r.getLocationAsList()) {
            if (l.getPerson() == Person.R_AGENT) {
                l.setRevealed(true);
            }
        }
        assertTrue(r.isWinState());
        assertTrue(r.getTeamWin() == Team.RED);
        Board r2 = new Board(3);
        int thereCanOnlyBe1 = 0;
        for (Location l : r2.getLocationAsList()) {
            if (l.getPerson() == Person.R_AGENT) {
                l.setRevealed(true);
            }
            if (l.getPerson() == Person.ASSASSIN && thereCanOnlyBe1 == 0) {
                thereCanOnlyBe1++;
                l.setRevealed(true);
                l.setRevealer(Team.RED);
            }
        }
        assertFalse(r2.isWinState());

    }

    @Test
    public void testTurns() {
        ThreeTeamModel m = new ThreeTeamModel(3);
        m.startGame();
        m.endTurn();
        assertEquals(Team.BLUE, m.getWhoseTurn());
        boolean found = false;
        Location[][] l = m.getGameBoardLocations();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (l[i][j].getPerson() == Person.ASSASSIN && !found) {
                    found = true;
                    Location l1 = new Location();
                    l1.setCodeName(l[i][j].getCodeName());
                    l1.setPerson(Person.ASSASSIN);
                    l1.setRevealed(true);
                    l1.setRevealer(Team.BLUE);
                    m.updateLocationAt(l1);
                }
            }
        }
        m.endTurn();
        m.endTurn();
        m.endTurn();
        assertEquals(Team.GREEN, m.getWhoseTurn());
    }
}
