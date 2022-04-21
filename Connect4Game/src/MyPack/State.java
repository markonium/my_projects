package MyPack;


import java.util.ArrayList;

public class State {
	char[][] state = new char[6][7];private int levelNum=1;
	ArrayList<data> neighbours = new ArrayList<data>();
	int AI_score = 0, Human_score = 0, index;
	private int colDiffFromParent = 0;
	
	private int rowDiffFromParent = 0;
	/**
	 * Constructor to construct the state
	 * @param state the current state array
	 * @param max Determines whether to generate neighbors for maximizer state or minimizer state
	 */
	public State(char state[][], Boolean max) {
		this.state = state;
		
		genNeighbours(max);
		score();
	}
	public State() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Generate neighbors for a maximizer or minimizer
	 * @param max Determine whether its a minimizer or a maximizer
	 */
	public void genNeighbours(boolean max) {
		for(int j = 0; j < 7; j++) {
			char[][] n = new char[6][7];
			copy(n);
			int i;
			for(i = 5; i >= 0; i--) {
				if(state[i][j] == 'e') {
					n[i][j] = 'x';
					if(max) {
						n[i][j] = 'o';
					}
					data d=new data(i, j, n);Heuristic h=new Heuristic();
					State s=new State();s.state=n;s.setColDiffFromParent(j);s.setRowDiffFromParent(i);s.setLevelNum(this.getLevelNum()+1);
					d.h=h.calculateHeoristic(s);
					neighbours.add(d);
					break;
				}
			}if(i == -1) {
				neighbours.add(null);
			}
		}
	}
	/**
	 * Helper method to copy state array values into destination array
	 * @param n The destination array
	 */
	private void copy(char[][] n) {
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				n[i][j] = state[i][j];
			}
		}
	}
	/**
	 * Determines the state after a human has played
	 * @param j The column the human played in
	 * @return the new State and the location in column j where human disc should be put
	 */
	public data humanPlayed(int j){
		char[][] n = new char[6][7];
		copy(n);
		int i;
		for(i = 5; i >= 0; i--) {
			if(state[i][j] == 'e') {
				n[i][j] = 'x';
				//Assumes that human could play in that column i.e column was not full
				break;
			}
		}
		return new data(new State(n, true), i);
	}
	/**
	 * Determines the state after the AI has played
	 * @param j The column the human played in
	 * @return the new State and the location in column j where AI disc should be put
	 */
	public data AIPlayed(int j){
		char[][] n = new char[6][7];
		copy(n);
		int i;
		for(i = 5; i >= 0; i--) {
			if(state[i][j] == 'e') {
				n[i][j] = 'o';
				break;
			}
		}
		return new data(new State(n, false), i);
	}
	/**
	 * Calculates current score of the game
	 */
	public void score() {
		//Check rows
		int a = 0, h = 0;
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				if(state[i][j] == 'x') {
					h++;
					if(a > 3) {
						AI_score += a - 3;
					}a = 0;
				}else if(state[i][j] == 'e') {
					if(a > 3) {
						AI_score += a - 3;	
					}if(h > 3) {
						Human_score += h - 3;
					}a = 0; h = 0;
				}
				else if(state[i][j] == 'o') {
					a++;
					if(h > 3) {
						Human_score += h - 3;						
					}h = 0;
				}
			}if(a > 3) {
				AI_score += a - 3;	
			}if(h > 3) {
				Human_score += h - 3;
			}a = 0; h = 0;
		}
		//Check columns
		a = 0; h = 0;
		for(int j = 0; j < 7; j++) {
			for(int i = 0; i < 6; i++) {
				if(state[i][j] == 'x') {
					h++;
					if(a > 3) {
						AI_score += a - 3;	
					}a = 0;
				}else if(state[i][j] == 'e') {
					if(a > 3) {
						AI_score += a - 3;
					}if(h > 3) {
						Human_score += h - 3;
					}a = 0; h = 0;
				}
				else if(state[i][j] == 'o') {
					a++;
					if(h > 3) {
						Human_score += h - 3;
					}h = 0;
				}
			}
			if(a > 3) {
				AI_score += a - 3;
			}if(h > 3) {
				Human_score += h - 3;
			}a = 0; h = 0;
		}
		//Check diagonals left to right
		int i = 0, j = 0;
		a = 0; h = 0;
		for(int k = 1; k <= 3; k++) {
			j = k;
			i = 0;
			while(i < 6 && j < 7) {
				if(state[i][j] == 'x') {
					h++;
					if(a > 3) {
						AI_score += a - 3;
					}a = 0;
				}else if(state[i][j] == 'e') {
					if(a > 3) {
						AI_score += a - 3;
					}if(h > 3) {
						Human_score += h - 3;
					}a = 0; h = 0;
				}else if(state[i][j] == 'o') {
					a++;
					if(h > 3) {
						Human_score += h - 3;
					}h = 0;
				}i++; j++;
			}if(a > 3) {
				AI_score += a - 3;
			}if(h > 3) {
				Human_score += h - 3;
			}a = 0; h = 0;
		}
		a = 0; h = 0;
		for(int k = 2; k >= 0; k--) {
			j = 0;
			i = k;
			while(i < 6 && j < 7) {
				if(state[i][j] == 'x') {
					h++;
					if(a > 3) {
						AI_score += a - 3;
					}a = 0;
				}else if(state[i][j] == 'e') {
					if(a > 3) {
						AI_score += a - 3;
					}if(h > 3) {
						Human_score += h - 3;
					}a = 0; h = 0;
				}else if(state[i][j] == 'o') {
					a++;
					if(h > 3) {
						Human_score += h - 3;
					}h = 0;
				}i++; j++;
			}if(a > 3) {
				AI_score += a - 3;
			}if(h > 3) {
				Human_score += h - 3;
			}a = 0; h = 0;
		}
		//Check diagonals right to left
		a = 0; h = 0;
		for(int k = 3; k <= 6; k++) {
			j = k;
			i = 0;
			while(i < 6 && j >= 0) {
				if(state[i][j] == 'x') {
					h++;
					if(a > 3) {
						AI_score += a - 3;
					}a = 0;
				}else if(state[i][j] == 'e') {
					if(a > 3) {
						AI_score += a - 3;
					}if(h > 3) {
						Human_score += h - 3;
					}a = 0; h = 0;
				}else if(state[i][j] == 'o') {
					a++;
					if(h > 3) {
						Human_score += h - 3;
					}h = 0;
				}i++; j--;
			}if(a > 3) {
				AI_score += a - 3;
			}if(h > 3) {
				Human_score += h - 3;
			}a = 0; h = 0;
		}
		a = 0; h = 0;
		for(int k = 1; k <= 2; k++) {
			j = 6;
			i = k;
			while(i < 6 && j >= 0) {
				if(state[i][j] == 'x') {
					h++;
					if(a > 3) {
						AI_score += a - 3;	
					}a = 0;
				}else if(state[i][j] == 'e') {
					if(a > 3) {
						AI_score += a - 3;
					}if(h > 3) {
						Human_score += h - 3;
					}a = 0; h = 0;
				}else if(state[i][j] == 'o') {
					a++;
					if(h > 3) {
						Human_score += h - 3;
					}h = 0;
				}i++; j--;
			}if(a > 3) {
				AI_score += a - 3;
			}if(h > 3) {
				Human_score += h - 3;
			}a = 0; h = 0;
		}	
	}
	/**
	 * Getter for AI score
	 * @return current AI score
	 */
	public int getAIScore() {
		return this.AI_score;
	}
	/**
	 * Getter for human score
	 * @return current human score
	 */
	public int getHumanScore() {
		return this.Human_score;
	}
	
	/**
	 * Getter for current state neighbour
	 * @return current state neighbour
	 */
	public ArrayList<data> getNeighbours() {
		return this.neighbours;
	}
	/**
	 * Prints the state to console
	 */
	public void print() {
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				System.out.print(state[i][j] + " ");
			}System.out.println();
		}//System.out.println("\n");
	}
	
	//------------------------------------------
	//Heuristic function to be put here, we call the function h
	public int h() {
		Heuristic h = new Heuristic();
		return h.calculateHeoristic(this);
	}
	//----------------------------------------------
	/**
	 * Getter for state matrix
	 * @return State matrix
	 */
	public char[][] getState() {
		return state;
	}
	/**
	 * Getter for column difference from state to its parent
	 * @return Column difference
	 */
	public int getColDiffFromParent() {
		return colDiffFromParent;
	}
	/**
	 * Setter for column difference
	 * @param colDiffFromParent
	 */
	public void setColDiffFromParent(int colDiffFromParent) {
		this.colDiffFromParent = colDiffFromParent;
	}
	/**
	 * Getter for row difference from state to its parent
	 * @return Row difference
	 */
	public int getRowDiffFromParent() {
		return rowDiffFromParent;
	}
	/**
	 * Setter for row difference
	 * @param rowDiffFromParent
	 */
	public void setRowDiffFromParent(int rowDiffFromParent) {
		this.rowDiffFromParent = rowDiffFromParent;
	}
	/**
	 * Check whether the state is terminal or not
	 * @return true if terminal, false otherwise
	 */
	public boolean isTerminal() {
		for(int j = 0; j < 7; j++) {
			if(state[0][j] == 'e') {
				return false;
			}
		}
		return true;
	}
	public void setState(char[][] state) {
		this.state = state;
	}
	public int getLevelNum() {
		return levelNum;
	}
	public void setLevelNum(int levelNum) {
		this.levelNum = levelNum;
	}
	
	
}
