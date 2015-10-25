
public class TeamInfo {
	
	private final String team;
	private final int teamId;
	private int wins;               // team i has w[i] wins
	private int loses;              // l[i] losses
	private int remaining;          // r[i] remaining games
	private int[] gamesLeft;        // g[i][j] games left to play against team j
	
	public TeamInfo(String team, int id, int w, int l, int r, int g[]) {
		
		this.team = team;
		teamId = id;
		wins = w;
		loses = l;
		remaining = r;
		
		gamesLeft = new int[g.length];
		for (int i = 0; i < g.length; i++)
			gamesLeft[i] = g[i];
	}
	
	public String Team() {
		return team;
	}
	
	public int TeamID() {
		return teamId;
	}
	
	public int Wins() {
		return wins;
	}
	
	public int Loses() {
		return loses;
	}
	
	public int Remaining() {
		return remaining;
	}
	
	public int[] GamesLeft() {
		return gamesLeft;
	}
}
