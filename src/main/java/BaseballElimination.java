import java.io.File;
import java.util.Arrays;

/**
 * Created by ERKIN on 07/12/14.
 */
public class BaseballElimination {

    private final int numberOfTeams;

    private int[] wins;
    private int[] losses;
    private int[] remaining;
    private int[][] gamesAgaints;
    private String[] teams;

    public BaseballElimination(String filename) {
        // create a baseball division from given filename in format specified below
        //4
        //Atlanta       83 71  8  0 1 6 1
        //Philadelphia  80 79  3  1 0 0 2
        //New_York      78 78  6  6 0 0 0
        //Montreal      77 82  3  1 2 0 0

        In in = new In(new File(filename));

        numberOfTeams = in.readInt();
        wins = new int[numberOfTeams];
        losses = new int[numberOfTeams];
        remaining = new int[numberOfTeams];
        gamesAgaints = new int[numberOfTeams][numberOfTeams];
        teams = new String[numberOfTeams];

        for (int i = 0; i < numberOfTeams; i++) {
            teams[i] = in.readString();
            wins[i] = in.readInt();
            losses[i] = in.readInt();
            remaining[i] = in.readInt();

            for (int j = 0; j < numberOfTeams; j++) {
                gamesAgaints[i][j] = in.readInt();
            }
        }
    }
    public int numberOfTeams() {
        return numberOfTeams;
    }
    public Iterable<String> teams() {
            // all teams
        return Arrays.asList(teams);
    }
    public int wins(String team) {
        // number of wins for given team
        int index = findIndexOfTeam(team);

        return wins[index];
    }

    public int losses(String team) {
            // number of losses for given team
        throw new IllegalStateException("not implemented yet");
    }
    public int remaining(String team) {
            // number of remaining games for given team
        throw new IllegalStateException("not implemented yet");
    }
    public int against(String team1, String team2) {
            // number of remaining games between team1 and team2
        throw new IllegalStateException("not implemented yet");
    }
    public boolean isEliminated(String team) {
            // is given team eliminated?
        throw new IllegalStateException("not implemented yet");
    }
    public Iterable<String> certificateOfElimination(String team) {
            // subset R of teams that eliminates given team; null if not eliminated
        throw new IllegalStateException("not implemented yet");
    }

    public static void main(String[] args) {
        StdOut.println("printing teams");

        BaseballElimination division = new BaseballElimination("files/baseball/teams4.txt");

        for (String team : division.teams()) {

            StdOut.println(team);

//           if (division.isEliminated(team)) {
//                StdOut.print(team + " is eliminated by the subset R = { ");
//                for (String t : division.certificateOfElimination(team)) {
//                    StdOut.print(t + " ");
//                }
//                StdOut.println("}");
//            }
//            else {
//                StdOut.println(team + " is not eliminated");
//            }
        }
    }

    private int findIndexOfTeam(String team) {
        for (int i = 0; i < numberOfTeams; i++) {
            if (teams[i].equals(team)) {
                return i;
            }
        }

        throw new IllegalArgumentException("team not valid");
    }
}
