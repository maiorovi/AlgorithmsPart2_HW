
public class BaseballElimination {

    public BaseballElimination(String filename) {
        In in = new In(filename);
    }

    public int numberOfTeams() {
        return 0;
    }

    public Iterable<String> teams() {
        return null;
    }

    public int wins(String team) {
        return 0;
    }

    public int losses(String team) {
        return 0;
    }

    public int remaining(String team) {
        return 0;
    }

    public int against(String team1, String team2) {
        return 0;
    }

    public boolean isEliminated(String team) {
        return true;
    }

    public Iterable<String> certificateOfElimination(String team) {
        return null;
    }
}
