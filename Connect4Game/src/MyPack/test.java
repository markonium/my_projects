package MyPack;

public class test {

	public static void main(String[] args) {
		char[][] state = new char[6][7];
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				state[i][j] = 'e';
			}
		}
//		for(int i = 1; i < 6; i++) {
//			for(int j = 2; j < 7; j++) {
//				state[i][j] = 'o';
//			}
//		}
//		for(int i = 2; i < 6; i++) {
//			state[i][4] = 'x';
//		}state[3][4] = 'e';
//		state[1][4] = 'e';
		
		//-----------------------
//		for(int j = 0; j < 7; j++) {
//			state[0][j] = 'x';
//		}
		//-----------------------
//		state[0][3] = 'e';
//		state[5][1] = 'x';
//		state[5][2] = 'o';
//		state[5][3] = 'x';
		
//		
		State sAI = new State(state, true);
		sAI.setRowDiffFromParent(5);
		sAI.setColDiffFromParent(3);
		
		Minimax_Pruning m = new Minimax_Pruning();
		//Minimax m = new Minimax();
		data d = m.decision(sAI, 9);
		//data d1 = sAI.AIPlayed(1);
//		data d2 = sAI.AIPlayed(3);
//		data d3 = sAI.AIPlayed(5);
//		
//		
//		//State sHuman = new State(state, false);
		//data d4 = sAI.humanPlayed(0);
//		data d5 = sAI.humanPlayed(2);
//		data d6 = sAI.humanPlayed(4);
		
		System.out.println(d.i+" "+d.j);
	}
}
