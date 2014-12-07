import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
}