package tests;

import code.Board;
import code.Location;
import code.Person;
import code.Team;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author Anthony Latoni
 * @author Nicolas Barrios
 * @author Vikram Singh
 */

public class TwoTeamBoardTests {

    private static final String NO_WINSTATE_AFTER_INIT = "Board should not be in a win state after initialization";

    @Test
    public void boardhas25() {
        Board board = new Board(2);
        Location[][] test = board.get_gameBoard();
        int sum = 0;
        for (Location[] locations : test) {
            for (Location location : locations) {
                sum++;
            }
        }
        assertTrue(sum == 25);
    }

    @Test
    public void test25CodeNames() {
        Board name = new Board(2);
        assertEquals(25, name.get_codeNames().size(), .001);
    }

    @Test
    public void testBlueSelectedAssassin() {
        // initialize board
        Board board = new Board(2);
        ArrayList<Location> locations = (ArrayList<Location>) board.getLocationAsList();

        for (Location loc : locations) {
            Person p = loc.getPerson();
            if (p == Person.ASSASSIN) {
                board.getLocationAt(loc.getCodeName()).setRevealed(true);
                board.getLocationAt(loc.getCodeName()).setRevealer(Team.BLUE);
            }
        }

        assertTrue("Board should be in a win state since the assassin was triggered", board.isWinState());
        assertFalse("Board should not be a winner for BLUE", Team.BLUE == board.getTeamWin());
        assertFalse("Board should not be a winner for BLUE", Team.BLUE == board.getAssassinWinTeam());
        assertEquals("Red should win if blue triggers the assassin", Team.RED, board.getTeamWin());
        assertEquals("Red should win if blue triggers the assassin", Team.RED, board.getAssassinWinTeam());

    }

    @Test
    public void testBlueWinState() {
        // Initialize board
        Board board = new Board(2);

        assertFalse(NO_WINSTATE_AFTER_INIT, board.isWinState());

        ArrayList<Location> locations = (ArrayList<Location>) board.getLocationAsList();
        for (Location location : locations) {
            Person p = location.getPerson();
            if (p == Person.B_AGENT) {
                location.setRevealed(true);
            }
        }

        assertTrue("Board should be in a win state for blue team", board.isWinState());
        assertEquals("Blue team should be winning the board", Team.BLUE, board.getTeamWin());

    }

    /*
     * Check if the board is initialized correctly Board needs 25 locations with a
     * codename, a not NONE Person and not revelaed It will also be red teams turn
     */
    @Test
    public void testBoardInitialization() {
        // initialize a test board
        Board board = new Board(2);
        assertTrue("When board is initialized, it should be red team's turn", board.getCurrentTeamsTurn() == Team.RED);

        // Get the locations as a list
        ArrayList<Location> locations = (ArrayList<Location>) board.getLocationAsList();
        ArrayList<String> totalWords = (ArrayList<String>) board.get_gameWords();

        // Check if there are 25 locations
        assertEquals("Board should have 25 locations when initialized", 25, locations.size(), 0.0);

        // Check properties
        for (Location l : locations) {
            assertTrue("Locations should not be null when initialized", l != null);
            assertTrue("Each location should have a non empty codename", !l.getCodeName().isEmpty());
            assertTrue("The location codename should be from the game words list",
                    totalWords.contains(l.getCodeName()));
            assertTrue(
                    "The person at this location should be either red agents, blue agents, assassin, or innocent bystander",
                    l.getPerson() != Person.NONE);
            assertTrue("The location should not be revealed when initialized", !l.getRevealed());
        }

    }

    @Test
    public void testClueIsEmpty() {
        Board empty = new Board(2);
        assertFalse("", empty.isLegalClue(""));

    }

    @Test
    public void testClueIsNull() {
        Board nulll = new Board(2);
        assertFalse(nulll.isLegalClue(null));

    }

    @Test
    public void testIsIllegalClue() {
        Board illegal = new Board(2);

        ArrayList<String> codeNames = (ArrayList<String>) illegal.get_codeNames();

        for (String s : codeNames) {
            if (!illegal.getLocationAt(s).getRevealed()) {
                assertFalse("A non-revealed location should not be a legal clue", illegal.isLegalClue(s));
            }
        }

    }

    @Test
    public void testIsLegalClue() {
        Board illegal = new Board(2);

        ArrayList<String> codeNames = (ArrayList<String>) illegal.get_codeNames();
        ArrayList<String> gameWords = (ArrayList<String>) illegal.get_gameWords();

        for (String s : gameWords) {
            if ((codeNames.contains(s) && illegal.getLocationAt(s).getRevealed()) || !codeNames.contains(s)) {

                assertTrue(
                        "If the clue is not in codenames or is in codenames, but is revealed, it should be a legal clue",
                        illegal.isLegalClue(s));

            }
        }

    }

    @Test
    public void testNoWinState() {
        // Initialize board
        Board board = new Board(2);
        assertFalse(NO_WINSTATE_AFTER_INIT, board.isWinState());

    }

    /*
     * Test to check whether or not the person assignment is random using
     * probability
     */
    @Test
    public void testRandomPersonAssignment() {
        // List to hold random lists
        ArrayList<ArrayList<Person>> personLists = new ArrayList<ArrayList<Person>>();
        // Create 10 random assignments of people
        // It is very unlikely that all 10 boards would
        // be the same after random assignments
        for (int i = 0; i < 10; i++) {
            personLists.add((ArrayList<Person>) new Board(2).get_listOfPerson());
        }

        // Check if all the lists are equal
        // Test passes if they aren't all equal
        boolean allEqual = true;
        for (int i = 0; i < personLists.size() - 1; i++) {
            for (int j = i + 1; j < personLists.size(); j++) {
                if (!personLists.get(i).equals(personLists.get(j))) {
                    allEqual = false;
                }
            }
        }

        assertFalse(
                "Although theoretically it is possible for 10 random arrangments of 25 people to all be equal, it is very unlikely.",
                allEqual);
    }

    @Test
    public void testReadGameWords() {
        Board board = new Board(2);
        ArrayList<String> words = (ArrayList<String>) board.readInGameWords("resources/GameWords1.txt");

        assertTrue(words.size() == 400);
        words = (ArrayList<String>) board.readInGameWords("resources/GameWords2.txt");
        assertTrue(words.size() == 50);
    }

    @Test
    public void testRedSelectedAssassin() {
        // initialize board
        Board board = new Board(2);
        ArrayList<Location> locations = (ArrayList<Location>) board.getLocationAsList();

        for (Location loc : locations) {
            Person p = loc.getPerson();
            if (p == Person.ASSASSIN) {
                board.getLocationAt(loc.getCodeName()).setRevealed(true);
                board.getLocationAt(loc.getCodeName()).setRevealer(Team.RED);
            }
        }

        assertTrue("Board should be in a win state since the assassin was triggered", board.isWinState());
        assertFalse("Board should not be a winner for RED", Team.RED == board.getTeamWin());
        assertFalse("Board should not be a winner for RED", Team.RED == board.getAssassinWinTeam());
        assertEquals("Blue should win if red triggers the assassin", Team.BLUE, board.getTeamWin());
        assertEquals("Blue should win if red triggers the assassin", Team.BLUE, board.getAssassinWinTeam());

    }

    @Test
    public void testRedWinState() {
        // Initialize board
        Board board = new Board(2);

        assertFalse(NO_WINSTATE_AFTER_INIT, board.isWinState());

        ArrayList<Location> locations = (ArrayList<Location>) board.getLocationAsList();
        for (Location location : locations) {
            Person p = location.getPerson();
            if (p == Person.R_AGENT) {
                location.setRevealed(true);
            }
        }

        assertTrue("Board should be in a win state for red team", board.isWinState());
        assertEquals("Red team should be winning the board", Team.RED, board.getTeamWin());

    }

    /*
     * Checks if each code name is unique
     */
    @Test
    public void testUniqueCodeNames() {
        Board b = new Board(2);
        ArrayList<String> test = (ArrayList<String>) b.get_codeNames();
        boolean unique = true;
        for (int i = 0; i < test.size(); i++) {
            for (int j = i + 1; j < test.size(); j++) {
                if (test.get(j).equals(test.get(i))) {
                    unique = false;
                }
            }
        }
        assertTrue(unique);
    }

    /*
     * the following FOUR tests test the updateLocation method
     */

    @Test
    public void testUpdateLocationNotRevealedDiffferentTeam() {
        Board board = new Board(2);
        Location loc = new Location();
        loc.setPerson(Person.B_AGENT);
        assertFalse(
                "updateLocation should return false if the Location is not revealed and the Person is different from the current Team",
                board.updateLocation(loc));
    }

    @Test
    public void testUpdateLocationNotRevealedSameTeam() {
        Board board = new Board(2);
        Location loc = new Location();
        loc.setPerson(Person.R_AGENT);
        assertTrue(
                "updateLocation should return true if the Location is not revealed and the Person is the same as the current Team",
                board.updateLocation(loc));
    }

    @Test
    public void testUpdateLocationRevealedDifferentTeam() {
        Board board = new Board(2);
        Location loc = new Location();
        loc.setRevealed(true);
        loc.setPerson(Person.B_AGENT);
        assertFalse(
                "updateLocation should return false if the Location is revealed and the Person is the same as the current Team",
                board.updateLocation(loc));

    }

    @Test
    public void testUpdateLocationRevealedSameTeam() {
        Board board = new Board(2);
        Location loc = new Location();
        loc.setRevealed(true);
        loc.setPerson(Person.R_AGENT);
        assertFalse(
                "updateLocation should return false if the Location is revealed and the Person is the same as the current Team",
                board.updateLocation(loc));
    }
}
