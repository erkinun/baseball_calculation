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
        return losses[findIndexOfTeam(team)];
    }
    public int remaining(String team) {
            // number of remaining games for given team
        return remaining[findIndexOfTeam(team)];
    }
    public int against(String team1, String team2) {
            // number of remaining games between team1 and team2
        return gamesAgaints[findIndexOfTeam(team1)][findIndexOfTeam(team2)];
    }
    public boolean isEliminated(String team) {
        // is given team eliminated?
        //don't forget the trivial elimination

        //construct the flow network
        FlowNetwork flow = constructFlowNetwork(team);

        //find the max flow
        FordFulkerson alg = new FordFulkerson(flow, 0, flow.V()-1);
        StdOut.print(alg.value());

        for (FlowEdge edge : flow.adj(0)) {
            if (edge.residualCapacityTo(edge.to()) != 0) {
                return true;
            }
        }

        return false;
    }

    public Iterable<String> certificateOfElimination(String team) {
            // subset R of teams that eliminates given team; null if not eliminated
        throw new IllegalStateException("not implemented yet");
    }

    public static void main(String[] args) {
        StdOut.println("printing teams");

        BaseballElimination division = new BaseballElimination("files/baseball/teams4.txt");

        StdOut.println(division.isEliminated("Atlanta"));

//        for (String team : division.teams()) {
//
//            StdOut.println(team);
//
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
//        }
    }

    private int findIndexOfTeam(String team) {
        for (int i = 0; i < numberOfTeams; i++) {
            if (teams[i].equals(team)) {
                return i;
            }
        }

        throw new IllegalArgumentException("team not valid");
    }

    private FlowNetwork constructFlowNetwork(String team) {
        //how many vertices?
        //1 for source, 1 for target
        //teams.len - 1 for other teams

        FlowNetwork flow = new FlowNetwork(1 + 1 + teams.length + numberOfTeams*numberOfTeams);

        //add edges
        //add game edges

        //add game -> team edges
        //add team -> target edges
        int teamIndex = findIndexOfTeam(team);
        int possibleWinsForTeam = wins[teamIndex] + remaining[teamIndex];
        int gameIndex = 1;
        for (int i = 0; i < gamesAgaints.length; i++) {
            for (int j = i+1; j < gamesAgaints[i].length; j++) {

                if (i == teamIndex || j == teamIndex) {
                    continue;
                }

                if (i == j) {
                    continue;
                }

                if (gamesAgaints[i][j] == 0) {
                    continue;
                }

                //add edge from source to game
                gameIndex = calculateGameIndex(i, j);

                //team 1
                flow.addEdge(new FlowEdge(0, gameIndex, gamesAgaints[i][j]));

                int team1Ix = i + 1;
                flow.addEdge(new FlowEdge(gameIndex, team1Ix , Double.POSITIVE_INFINITY));

                addEdge(flow, possibleWinsForTeam, i, team1Ix);

                //team 2
                int team2Ix = j + 1;
                flow.addEdge(new FlowEdge(gameIndex, team2Ix, Double.POSITIVE_INFINITY));

                addEdge(flow, possibleWinsForTeam, j, team2Ix);

                gameIndex++; //switch to other game if exists

            }
        }
        System.out.println(flow.toString());

        return flow;
    }

    private void addEdge(FlowNetwork flow, int possibleWinsForTeam, int i, int team1Ix) {
        int cap1 = possibleWinsForTeam - wins[i];
        if (cap1 < 0) {
            cap1 = 0;
        }

        FlowEdge edgeToAdd = new FlowEdge(team1Ix, flow.V() - 1, cap1);
        for (FlowEdge edge : flow.adj(team1Ix)) {
            if (edge.from() == edgeToAdd.from() && edge.to() == edgeToAdd.to()) {
                return;
            }
        }

        flow.addEdge(edgeToAdd);
    }

    private int calculateGameIndex(int i, int j) {
        return numberOfTeams + ((i+1) * (j+1));
    }

}
