package MyPack;

import java.util.Comparator;

public class myComparator implements  Comparator<State>{

	@Override
	public int compare(State o1, State o2) {
		// TODO Auto-generated method stub
		return o1.getLevelNum()-o2.getLevelNum();
	}
	

}
