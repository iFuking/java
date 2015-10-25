
public class GameVertice {

	private int id;
	private int thisv;
	private int thatv;
	
	public GameVertice(int id, int thisv, int thatv) {
		
		this.id = id;
		this.thisv = thisv;
		this.thatv = thatv;
	}
	
	public int Id() {
		return id;
	}
	
	public int ThisV() {
		return thisv;
	}
	
	public int ThatV() {
		return thatv;
	}
}
