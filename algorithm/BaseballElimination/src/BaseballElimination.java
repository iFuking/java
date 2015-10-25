import java.util.TreeMap;

public class BaseballElimination {

	private TreeMap<String, TeamInfo> teamsInfo;
	private int[][] g;
	private final int N;
	
	
	public BaseballElimination(String filenames) {
		
		teamsInfo = new TreeMap<String, TeamInfo>();
		
		In in = new In(filenames);
		N = in.readInt();
		
		int teamId = 0, lines = N;
		g = new int[N][N];
		while (lines-- > 0) {
			
			String team = in.readString();
			int wins = in.readInt();
			int loses = in.readInt();
			int remaining = in.readInt();
			int[] gamesLeft = new int[N];
			for (int i = 0; i < N; i++) {
				gamesLeft[i] = in.readInt();
				g[teamId][i] = gamesLeft[i];
			}
			
			TeamInfo teaminfo = new TeamInfo(team, teamId++, wins, loses, remaining, gamesLeft);
			teamsInfo.put(team, teaminfo);
		}
	}
	
	public int numberOfTeams() {
		return teamsInfo.keySet().size();
	}
	
	public Iterable<String> teams() {
		return teamsInfo.keySet();
	}
	
	public int wins(String team) {
		
		if (team == null || !teamsInfo.containsKey(team))
			throw new IllegalArgumentException();
		return teamsInfo.get(team).Wins();
	}
	
	public int loses(String team) {
		
		if (team == null || !teamsInfo.containsKey(team))
			throw new IllegalArgumentException();
		return teamsInfo.get(team).Loses();
	}
	
	public int remaining(String team) {
		
		if (team == null || !teamsInfo.containsKey(team))
			throw new IllegalArgumentException();
		return teamsInfo.get(team).Remaining();
	}
	
	public int against(String team1, String team2) {
		
		if (team1 == null || !teamsInfo.containsKey(team1) || team2 == null || !teamsInfo.containsKey(team2))
			throw new IllegalArgumentException();
		
		int[] teamsGamesLeft = teamsInfo.get(team1).GamesLeft();
		int team2Id = teamsInfo.get(team2).TeamID();
		
		return teamsGamesLeft[team2Id];
	}
	
	private void GenerateFlowNetwork(String team) {
		
		int gameV = (N-1)*(N-2)/2, teamV = N-1;
		GameVertice[] gameVertice = new GameVertice[gameV];
		
		int V = 2 + gameV + teamV;
		FlowNetwork graph = new FlowNetwork(V);
		
		for (int i = 0; i < gameV; i++) {
			// gameVertice = new GameVertice(i+1, );
			// FlowEdge fe = new FlowEdge(0, i, );
		}
	}
	
	public boolean isEliminated(String team) {
		
		if (team == null || !teamsInfo.containsKey(team))
			throw new IllegalArgumentException();
		return true;
	}
	
	public Iterable<String> certificateOfElimination(String team) {
		
		if (team == null || !teamsInfo.containsKey(team))
			throw new IllegalArgumentException();
		return null;
	}
	
	public static void main(String[] args) {
		
	    BaseballElimination division = new BaseballElimination(args[0]);
	    for (String team : division.teams()) {
	        if (division.isEliminated(team)) {
	            StdOut.print(team + " is eliminated by the subset R = { ");
	            for (String t : division.certificateOfElimination(team)) {
	                StdOut.print(t + " ");
	            }
	            StdOut.println("}");
	        }
	        else {
	            StdOut.println(team + " is not eliminated");
	        }
	    }
	}
}
