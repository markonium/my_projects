package MyPack;

public class data {
	State s;
	int i;
	int j;
	int h;
	char[][] sArr = new char[6][7];
	public data(State s, int i) {
		this.s = s;
		this.i = i;
	}public data(int i, int j, State s){
		this.i = i;
		this.j = j;
		this.s = s;
	}
	
	//---------------------------------------
	/**
	 * Constructor
	 * @param j Column
	 * @param h Heuristic value
	 */
	public data(int j, int h){
		this.h = h;
		this.j = j;
	}
	/**
	 * Help DS
	 * @param i the RowDifference
	 * @param j the ColDefference
	 * @param sArr
	 */
	public data(int i, int j, char[][] sArr) {
		this.i = i;
		this.j = j;
		this.sArr = sArr;
	}
}
