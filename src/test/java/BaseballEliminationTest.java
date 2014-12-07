import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
}