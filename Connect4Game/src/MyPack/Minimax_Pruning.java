package MyPack;

import java.util.ArrayList;
import java.util.Collections;

public class Minimax_Pruning {
	ArrayList <State> arr=new ArrayList<State>();
	/**
	 * Find the child state with the highest heuristic value
	 * @param s The state to find maximum child of
	 * @return The child index
	 */
	private data max(State s, int k, float alpha, float beta) {
		if(arr.contains(s)==false) {
			arr.add(s);
		}
		if(k == 0) {

			return new data(0, s.h());
		}
		int maxh = Integer.MIN_VALUE;
		ArrayList<data> n = s.getNeighbours();
		int j = 0;
		for(int i = 0; i < 7; i++) {
			if(n.get(i) != null) {
				State ns = new State(n.get(i).sArr, false);
				ns.setRowDiffFromParent(n.get(i).i);
				ns.setColDiffFromParent(n.get(i).j);ns.setLevelNum(s.getLevelNum()+1);
				data d = min(ns, k-1, alpha, beta);
				int h = d.h;
				if(h > maxh) {
					maxh = h;
					j = i;
				}
				if(maxh >= beta) {
					break;
				}
				if(maxh > alpha) {
					alpha = maxh;
				}
			}
		}
		return new data(j, maxh);
	}
	/**
	 * Find the child state with the lowest heuristic value
	 * @param s The state to find minimum child of
	 * @return The child index
	 */
	private data min(State s, int k, float alpha, float beta) {
		if(arr.contains(s)==false) {
			arr.add(s);
		}
		if(k == 0) {
			return new data(0, s.h());
		}
		int minh = Integer.MAX_VALUE;
		ArrayList<data> n = s.getNeighbours();
		int j = 0;
		for(int i = 0; i < 7; i++) {
			if(n.get(i) != null) {
				State ns = new State(n.get(i).sArr, true);
				ns.setRowDiffFromParent(n.get(i).i);ns.setLevelNum(s.getLevelNum()+1);
				ns.setColDiffFromParent(n.get(i).j);
				data d  = max(ns, k-1, alpha, beta);
				int h = d.h;
				if(h < minh) {
					minh = h;
					j = i;
				}
				if(minh <= alpha) {
					break;
				}
				if(minh < beta) {
					beta = minh;
				}
			}
		}
		return new data(j, minh);
	}
	/**
	 * Find child with highest heuristic value for the current state
	 * @param s The current state
	 * @param k The maximum depth level
	 * @return The index of the child
	 */
	public data decision(State s, int k) {
		float beta = Float.POSITIVE_INFINITY;
        float alpha = Float.NEGATIVE_INFINITY;
        long startTime = System.currentTimeMillis();
		data d1 = max(s, k, alpha, beta);
		long endTime   = System.currentTimeMillis();
		long timeElapsed   = endTime - startTime;
		data d = s.AIPlayed(d1.j);
		//sort by state level
				myComparator comp=new myComparator();
				Collections.sort(arr, comp);
				int x=0;int lev=1;
				int lastLevel=arr.get(arr.size()-1).getLevelNum();System.out.println("Level : "+lev);
				while(x<arr.size()) {
					if(arr.get(x).getLevelNum()==lev) {
						arr.get(x).print();
						if(arr.get(x).getLevelNum()==lastLevel) {
							System.out.println(arr.get(x).h());System.out.println();
						}else {
							System.out.println();
						}
					}else {
						lev=arr.get(x).getLevelNum();
						System.out.println("Level : "+lev);
						arr.get(x).print();
						if(arr.get(x).getLevelNum()==lastLevel) {
							System.out.println(arr.get(x).h());System.out.println();
						}else {
							System.out.println();
						}
					}
					x++;
				}
		System.out.println("\nTime elapsed: " + timeElapsed + "ms");
		System.out.println("Nodes expanded: " + arr.size());
		return new data(d.i, d1.j, d.s);
	}
}
