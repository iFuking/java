
public class Topological {
	
	public static int[][] DIR = { {-1, 1}, {0, 1}, {1, 1} };
	
	private boolean[][] marked;
	private Stack<Pixel> postReverse;
	private int width;
	private int height;
	
	public Topological(int width, int height) {
		
		this.width = width;
		this.height = height;
		postReverse = new Stack<Pixel>();
		
		marked = new boolean[width][height];
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				if (!marked[i][j]) dfs(i, j);
	}
	
	private void dfs(int x, int y) {
		
		marked[x][y] = true;
		for (int i = 0; i < DIR.length; i++)
			if (!outOfBound(x+DIR[i][0],y+DIR[i][1]) && !marked[x+DIR[i][0]][y+DIR[i][1]]) 
				dfs(x+DIR[i][0], y+DIR[i][1]);
		Pixel p = new Pixel(x, y);
		postReverse.push(p);
	}
	
	private boolean outOfBound(int x, int y) {
		
		if (x>=0 && x<width && y>=0 && y<height)
			return false;
		return true;
	}
	
	public Iterable<Pixel> order() {
		return postReverse;
	}
}
