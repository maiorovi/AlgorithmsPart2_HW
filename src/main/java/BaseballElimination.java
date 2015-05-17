import java.util.HashMap;
import java.util.Map;

public class BaseballElimination {
    private final int NAME = 0;
    private final int WINS = 1;
    private final int LOSSES = 2;
    private final int LEFT = 3;
    private final int SCHEDULE_PARSE_POSITION = 4;

    private Map<String, TeamInfo> statistic = new HashMap<String, TeamInfo>();
    private TeamInfo leaderTeam;

    private int numberOfTeams;

    public BaseballElimination(String filename) {
        processInputFile(filename);
    }

    private void processInputFile(String filename) {
        In in = new In(filename);
        int id = 0;
        if (in.hasNextLine())
            numberOfTeams = Integer.parseInt(in.readLine());
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
        teamInfo.setSchedule(parseSchedule(result));
        teamInfo.setId(id);

        if (id == 0)
            leaderTeam = teamInfo;

        statistic.put(result[NAME], teamInfo);
    }

    private int[] parseSchedule(String[] result) {
        int length =  result.length - SCHEDULE_PARSE_POSITION;
        int[] schedule = new int[length];

        for (int i = SCHEDULE_PARSE_POSITION; i < result.length; i++) {
            schedule[i-SCHEDULE_PARSE_POSITION] = Integer.parseInt(result[i]);
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
        if (isSimplyEliminated(team)) {
            return true;
        }

        return false;
    }

    private boolean isSimplyEliminated(String team) {
        int wins = statistic.get(team).wins;
        int left = statistic.get(team).left;
        int leaderWins = leaderTeam.getWins();

        return wins + left < leaderWins;
    }

    public Iterable<String> certificateOfElimination(String team) {
        return null;
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
