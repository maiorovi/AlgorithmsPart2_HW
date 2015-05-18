import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BaseballElimination {
    private final int NAME = 0;
    private final int WINS = 1;
    private final int LOSSES = 2;
    private final int LEFT = 3;
    private final int SCHEDULE_PARSE_POSITION = 4;

    private Map<String, TeamInfo> statistic = new HashMap<String, TeamInfo>();
    private Map<String, ArrayList<String>> certificates = new HashMap<String, ArrayList<String>>();
    private TeamInfo leaderTeam;
    private int[][] againstTable;

    private int numberOfTeams;

    public BaseballElimination(String filename) {
        processInputFile(filename);
    }

    private void processInputFile(String filename) {
        In in = new In(filename);
        int id = 0;
        if (in.hasNextLine()) {
            numberOfTeams = Integer.parseInt(in.readLine());
            againstTable = new int[numberOfTeams][numberOfTeams];
        }
        while(in.hasNextLine()) {
            String line = in.readLine();
            createTeamInfoAndPutItToMap(line, id++);
        }
    }

    private void createTeamInfoAndPutItToMap(String line, int id) {
        String[] result = line.split("\\s+");

        TeamInfo teamInfo = new TeamInfo();
        teamInfo.setWins(Integer.parseInt(result[WINS]));
        teamInfo.setTeamName(result[NAME]);
        teamInfo.setLosses(Integer.parseInt(result[LOSSES]));
        teamInfo.setLeft(Integer.parseInt(result[LEFT]));
        teamInfo.setSchedule(parseSchedule(result, id));
        teamInfo.setId(id);

        if (id == 0)
            leaderTeam = teamInfo;

        statistic.put(result[NAME], teamInfo);
    }

    private int[] parseSchedule(String[] result, int id) {
        int length =  result.length - SCHEDULE_PARSE_POSITION;
        int[] schedule = new int[length];

        for (int i = SCHEDULE_PARSE_POSITION; i < result.length; i++) {
            againstTable[id][i-SCHEDULE_PARSE_POSITION] = Integer.parseInt(result[i]);
        }

        return schedule;
    }

    public int numberOfTeams() {
        return numberOfTeams;
    }

    public Iterable<String> teams() {
        return statistic.keySet();
    }

    public int wins(String team) {
        return statistic.get(team).getWins();
    }

    public int losses(String team) {
        return statistic.get(team).losses;
    }

    public int remaining(String team) {
        return statistic.get(team).left;
    }

    public int against(String team1, String team2) {
        int enemyId = statistic.get(team2).getId();
        return statistic.get(team1).getSchedule()[enemyId];
    }

    public boolean isEliminated(String team) {
        boolean result = true;
        if (isSimplyEliminated(team)) {
            result = true;
        } else {
            result = resolveElimination(team);
        }

        return result;
    }

    private Boolean resolveElimination(String team) {
        int numberOfGamesToPlay = numberOfTeams * (numberOfTeams - 1) / 2;
        int graphSize = 2 + numberOfTeams + numberOfGamesToPlay;
        FlowNetwork flowNetwork = new FlowNetwork(graphSize);
        int wins = statistic.get(team).wins;
        int left = statistic.get(team).left;
        int maxflow = 0;
        //connect zero with games
        for (int i = 0, k = 1; i < numberOfTeams - 1; i++) {
            for (int j = i + 1; j < numberOfTeams; j++) {
                flowNetwork.addEdge(new FlowEdge(0, k++, againstTable[i][j]));
                maxflow += againstTable[i][j];
            }
        }

        //connect games with teams
        for (int i = numberOfGamesToPlay + 1, z = 1; i < graphSize - 1; i++ ) {
            for (int j = i - numberOfGamesToPlay, q = i + 1 ; j < numberOfTeams; j++) {
                flowNetwork.addEdge(new FlowEdge(z, i, Double.POSITIVE_INFINITY));
                flowNetwork.addEdge(new FlowEdge(z++, q++, Double.POSITIVE_INFINITY));
            }
        }

        //connect teams to target
        for (int i = numberOfGamesToPlay + 1; i < graphSize - 1; i++ ) {
            flowNetwork.addEdge(new FlowEdge(i,graphSize -1, wins + left));
        }

        FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork, 0, graphSize - 1);
        if (maxflow > fordFulkerson.value()) {
            return true;
        }

        return false;
    }
    private boolean isSimplyEliminated(String team) {
        int wins = statistic.get(team).wins;
        int left = statistic.get(team).left;
        int totalGames = wins + left;

        for (String teamName : teams()) {
            ArrayList<String> teams = new ArrayList<String>();
            if (statistic.get(teamName).getWins() > totalGames)
                teams.add(teamName);
            if (!teams.isEmpty()) {
                certificates.put(team, teams);
                return true;
            }
        }
        return false;
    }

    public Iterable<String> certificateOfElimination(String team) {
        return certificates.get(team);
    }

    private class TeamInfo {
        private String teamName;
        private Integer wins;
        private Integer losses;
        private Integer left;
        private int[] schedule;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        private int id;

        public int[] getSchedule() {
            return schedule;
        }

        public void setSchedule(int[] schedule) {
            this.schedule = schedule;
        }

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public Integer getWins() {
            return wins;
        }

        public void setWins(Integer wins) {
            this.wins = wins;
        }

        public Integer getLosses() {
            return losses;
        }

        public void setLosses(Integer losses) {
            this.losses = losses;
        }

        public Integer getLeft() {
            return left;
        }

        public void setLeft(Integer left) {
            this.left = left;
        }
    }

    public static void main(String[] args) {
        BaseballElimination baseball = new BaseballElimination("teams4.txt");
        System.out.println(baseball.wins("Atlanta"));
        System.out.println(baseball.losses("Philadelphia"));
        System.out.println(baseball.remaining("New_York"));
        System.out.println(baseball.numberOfTeams());
        System.out.println(baseball.against("Atlanta", "Montreal"));
        System.out.println(baseball.isEliminated("Philadelphia"));
        System.out.println(baseball.isEliminated("New_York"));
        System.out.println(baseball.isEliminated("Montreal"));
    }
}
