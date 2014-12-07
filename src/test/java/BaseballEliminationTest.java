import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseballEliminationTest {

    private BaseballElimination division;

    @Before
    public void setUp() throws Exception {
        division = new BaseballElimination("files/baseball/teams4.txt");
    }

    @Test
    public void testNumberOfTeams() throws Exception {
        Assert.assertEquals(4, division.numberOfTeams());
    }

    @Test
    public void testTeams() throws Exception {

        Iterable<String> iterable = division.teams();

        Assert.assertNotNull(iterable);

        for (String team: division.teams()) {
            System.out.println(team);
        }
    }

    @Test
    public void testIndex() throws Exception {

        Method indexMethod = BaseballElimination.class.getDeclaredMethod("findIndexOfTeam", String.class);
        indexMethod.setAccessible(true);
        int index = (Integer) indexMethod.invoke(division, "Atlanta");

        Assert.assertEquals(0, index);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIndexException() throws Throwable {

        try {
            Method indexMethod = BaseballElimination.class.getDeclaredMethod("findIndexOfTeam", String.class);
            indexMethod.setAccessible(true);
            int index = (Integer) indexMethod.invoke(division, "hede");

            Assert.assertEquals(0, index);
        }
        catch (InvocationTargetException targetException) {
            throw targetException.getCause();
        }

    }

    @Test
    public void testAtlantaWins() throws Exception {
        int wins = division.wins("Atlanta");

        Assert.assertEquals(83, wins);
    }

    @Test
    public void testMontrealLosses() throws Exception {
        int losses = division.losses("Montreal");

        Assert.assertEquals(82, losses);
    }

    @Test
    public void testRemainingNewYork() throws Exception {
        int remaining = division.remaining("New_York");

        Assert.assertEquals(6, remaining);
    }

    @Test
    public void testGamesAgainstPhiliVsMontreal() throws Exception {
        int gameAgainst = division.against("Philadelphia", "Montreal");

        Assert.assertEquals(2, gameAgainst);
    }
}