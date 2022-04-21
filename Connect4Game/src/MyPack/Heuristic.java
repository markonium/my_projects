package MyPack;


import java.awt.Point;

public class Heuristic {
	
	public Point checkConnected3(State state ,int row, int col){
		int i=row;
		int j = col-1;
		int numberOfThreesC=0;
		int numberOfThreesH=0;
		int count=1;
		int c=1;//number of numbers I took with me
		//check 3 in a row containing this added number for computer
		int index=col;
		while(j>=0) {
			if((state.getState()[i][j]=='o')&&c<4) {
				if(c+1<4) {
					index=j;
				}
				j--;count++;
				
			}else if(state.getState()[i][j]=='e'&&c<4) {
				if(c+1<4) {
					index=j;
				}
				j--;
			}
			else {
				break;
			}
			c++;
		}
		if(count==3&&c==4) {
			numberOfThreesC++;
		}
				while(index<=col) {//begin counting connected 3 from here and so on
					int y=0;int counter=0;
					int start=index;
					while(y<4&&index<7) {
						if(state.getState()[i][index]=='o'||index==col) {
							counter++;y++;
						}else if(state.getState()[i][index]=='e'){
							y++;
						}else 
						{
							break;
						}
						index++;
					}
					if(counter==3&&y==4) {
						numberOfThreesC++;
					}
					index=start+1;
				}
			
		
		
		
		//check 3 in a row containing this added number for human
		index=col;
		j=col-1;
		i=row;
		count=1;c=1;
		while(j>=0) {
			if((state.getState()[i][j]=='x')&&c<4) {
				if(c+1<4) {
					index=j;
				}
				j--;count++;
				
			}else if(state.getState()[i][j]=='e'&&c<4) {
				if(c+1<4) {
					index=j;
				}
				j--;
			}
			else {
				break;
			}
			c++;
		}
		if(count==3&&c==4) {
			numberOfThreesH++;
		}
				while(index<=col) {//begin counting connected 3 from here and so on
					int y=0;int counter=0;
					int start=index;
					while(y<4&index<7) {
						if(state.getState()[i][index]=='x'||index==col){
							counter++;y++;
						}else if(state.getState()[i][index]=='e'){
							y++;
						}else 
						{
							break;
						}
						index++;
					}
					if(counter==3&&y==4) {
						numberOfThreesH++;
					}
					index=start+1;
				}
			
		
		
		
		////////////////////////////////////////////////////////////////
		
		//check 3 in a col containing this number added for computer
		
		i=row-1;j=col;
		count=1;c=1;
		index=row;
		while(i>=0) {
			if(state.getState()[i][j]=='o'&&c<4) {
				if(c+1<4) {
					index=i;
				}
				i--;count++;
			}else if(state.getState()[i][j]=='e'&&c<4) {
				if(c+1<4) {
					index=i;
				}
				i--;
			}
			else {
				break;
			}
			c++;
		}
		if(count==3&&c==4) {
			numberOfThreesC++;
		}
				while(index<=row) {
					int y=0;int counter=0;
					int start =index;
					while(y<4&&index<6) {
						if(state.getState()[index][j]=='o'||index==row) {
							counter++;y++;
						}else if(state.getState()[index][j]=='e') {
							y++;
						}else {
							break;
						}
						index++;
					}
					index=start+1;
					if(counter==3&&y==4) {
						numberOfThreesC++;
					}
				}
				
			
		
		
		
		//check 3 in a col containing this number added for Human
		i=row-1;j=col;
		count=1;c=1;
		index=row;
		while(i>=0) {
			if(state.getState()[i][j]=='x'&&c<4) {
				if(c+1<4) {
					index=i;
				}
				i--;count++;
			}else if(state.getState()[i][j]=='e'&&c<4) {
				if(c+1<4) {
					index=i;
				}
				i--;
			}
			else {
				break;
			}
			c++;
		}
		if(count==3&&c==4) {
			numberOfThreesH++;
		}
				while(index<=row) {
					int y=0;int counter=0;
					int start =index;
					while(y<4&&index<6) {
						if(state.getState()[index][j]=='x'||index==row) {
							counter++;y++;
						}else if(state.getState()[index][j]=='e') {
							y++;
						}else {
							break;
						}
						index++;
					}
					index=start+1;
					if(counter==3&&y==4) {
						numberOfThreesH++;
					}
				}
				
			
		
		
		////////////////////////////////////////////////////////////////
		//check diagonal up connected 3 for computer 
		i=row-1;c=1;int indexI=row;int indexJ=col;
		j=col-1;count=1;
		while(i>=0&&j>=0) {
			if(state.getState()[i][j]=='o'&&c<4) {
				if(c+1<4) {
					indexI=i;indexJ=j;
				}
				i--;j--;
				count++;
			}else if(state.getState()[i][j]=='e'&&c<4) {
				if(c+1<4) {
					indexI=i;indexJ=j;
				}
				i--;j--;
			}
			else {
				break;
			}
			c++;
		}
		if(count==3&&c==4) {
			numberOfThreesC++;
		}
				while(indexI<=row&&indexJ<=col){
					int startI=indexI;
					int startJ=indexJ;
					int y=0;int counter=0;
					while(y<4&&indexI<6&&indexJ<7) {
						if(state.getState()[indexI][indexJ]=='o'||(indexI==row&&indexJ==col)) {
							y++;counter++;
						}else if(state.getState()[indexI][indexJ]=='e') {
							y++;
						}else {
							break;
						}
						indexI++;indexJ++;
					}
					indexI=startI+1;
					indexJ=startJ+1;
					if(counter==3&&y==4) {
						numberOfThreesC++;
					}
				}
			
		
		//check diagonal up connected 3 for human 
		i=row-1;c=1;indexI=row;indexJ=col;
		j=col-1;count=1;
		while(i>=0&&j>=0) {
			if(state.getState()[i][j]=='x'&&c<4) {
				if(c+1<4) {
					indexI=i;indexJ=j;
				}
				i--;j--;
				count++;
			}else if(state.getState()[i][j]=='e'&&c<4) {
				if(c+1<4) {
					indexI=i;indexJ=j;
				}
				i--;j--;
			}
			else {
				break;
			}
			c++;
		}
		if(count==3&&c==4) {
			numberOfThreesH++;
		}
				while(indexI<=row&&indexJ<=col){
					int startI=indexI;
					int startJ=indexJ;
					int y=0;int counter=0;
					while(y<4&&indexI<6&&indexJ<7) {
						if(state.getState()[indexI][indexJ]=='x'||(indexI==row&&indexJ==col)) {
							y++;counter++;
						}else if(state.getState()[indexI][indexJ]=='e') {
							y++;
						}else {
							break;
						}
						indexI++;indexJ++;
					}
					indexI=startI+1;
					indexJ=startJ+1;
					if(counter==3&&y==4) {
						numberOfThreesH++;
					}
				}
			
		
		
		///////////////////////////////////////////////////////////////
		//check being on the other diagonal for computer
		i=row-1;indexI=row;
		indexJ=col;
		j=col+1;count=1;c=1;
		while(i>=0&&j<7) {
			if(state.getState()[i][j]=='o'&&c<4) {
				if(c+1<4) {
					indexI=i;indexJ=j;
				}
				i--;j++;
				count++;
			}else if(state.getState()[i][j]=='e'&&c<4){
				if(c+1<4) {
					indexI=i;indexJ=j;
				}
				i--;j++;
			}
			else {
				break;
			}
			c++;
		}
		
		
		if(count==3&&c==4) {
			numberOfThreesC++;
		}
				while(indexI<=row&&indexJ>=col){
					int startI=indexI;
					int startJ=indexJ;
					int y=0;int counter=0;
					while(y<4&&indexI<6&&indexJ>=0) {
						if(state.getState()[indexI][indexJ]=='o'||(indexI==row&&indexJ==col)) {
							y++;counter++;
						}else if(state.getState()[indexI][indexJ]=='e') {
							y++;
						}else {
							break;
						}
						indexI++;indexJ--;
					}
					indexI=startI+1;
					indexJ=startJ-1;
					if(counter==3&&y==4) {
						numberOfThreesC++;
					}
				}
				
			
	
		
		
		//check being on the other diagonal for Human
		i=row-1;indexI=row;
		indexJ=col;
		j=col+1;count=1;c=1;
		while(i>=0&&j<7) {
			if(state.getState()[i][j]=='x'&&c<4) {
				if(c+1<4) {
					indexI=i;indexJ=j;
				}
				i--;j++;
				count++;
			}else if(state.getState()[i][j]=='e'&&c<4){
				if(c+1<4) {
					indexI=i;indexJ=j;
				}
				i--;j++;
			}
			else {
				break;
			}
			c++;
		}
		if(count==3&&c==4) {
			numberOfThreesH++;
		}
				while(indexI<=row&&indexJ>=col){
					int startI=indexI;
					int startJ=indexJ;
					int y=0;int counter=0;
					while(y<4&&indexI<6&&indexJ>=0) {
						if(state.getState()[indexI][indexJ]=='x'||(indexI==row&&indexJ==col)) {
							y++;counter++;
						}else if(state.getState()[indexI][indexJ]=='e') {
							y++;
						}else {
							break;
						}
						indexI++;indexJ--;
					}
					indexI=startI+1;
					indexJ=startJ-1;
					if(counter==3&&y==4) {
						numberOfThreesH++;
					}
				}
				
		return new Point(numberOfThreesC,numberOfThreesH);
	}
	public Point checkConnected4(State state,int row, int col){//output will be number of connected 4 from a point for both computer and human
		
		
		int i=row;
		int j = col;
		int numberOfFoursC=0;
		int numberOfFoursH=0;
		int count=0;
		//check 4 in a row containing this added number for computer
		int index=col;
		while(j>=0) {
			if(state.getState()[i][j]=='o'&&count<4) {
				count++;
				if(count<4) {
					index=j;
				}
				j--;
			}else {
				break;
			}
		}
		if(count==4) {
			numberOfFoursC++;
		}
		//check if there are overlapping between 2 sides
			while(index<=col) {
				int start=index;
				int y=0;int counter=0;
				while(y<4&&index<7) {
					if(state.getState()[i][index]=='o'||index==col) {
						counter++;
					}else {
						break;
					}y++;
					index++;
				}
				index=start+1;
				if(counter==4&&y==4) {
					numberOfFoursC++;
				}
			}
		
		
		
		
		//check 4 in a row containing this added number for human
		j=col-1;
		i=row;
		count=1;
		index=col;
		while(j>=0) {
			if(state.getState()[i][j]=='x'&&count<4) {
				count++;
				if(count<4) {
					index=j;
				}
				j--;
			}else {
				break;
			}
		}
		if(count==4) {
			numberOfFoursH++;
		}
		//check if there are overlapping between 2 sides
			while(index<=col) {
				int start=index;
				int y=0;int counter=0;
				while(y<4&&index<7) {
					if(state.getState()[i][index]=='x'||index==col) {
						counter++;
					}else {
						break;
					}y++;
					index++;
				}
				index=start+1;
				if(counter==4&&y==4) {
					numberOfFoursH++;
				}
			}
		
		
		
		////////////////////////////////////////////////////////////////
		
		//check 4 in a col containing this number added for computer
		index=row;
		i=row-1;
		j=col;
		count=1;
		while(i>=0) {
			if(state.getState()[i][j]=='o') {
				count++;
				if(count<4) {
					index=i;
				}
				i--;
			}else {
				break;
			}
		}
		if(count==4) {
			numberOfFoursC++;
		}
		//check for overlapping
			while(index<=row) {
				int counter=0;int y=0;
				int start=index;
				while(y<4&&index<6) {
					if(state.getState()[index][j]=='o'||index==row) {
						counter++;
					}else {
						break;
					}
					y++;index++;
				}
				index=start+1;
				if(counter==4&&y==4) {
					numberOfFoursC++;
				}
			}
		
		
		//check 4 in a col containing this number added for Human
		
		index=row;
		i=row-1;
		j=col;
		count=1;
		while(i>=0) {
			if(state.getState()[i][j]=='x') {
				count++;
				if(count<4) {
					index=i;
				}
				i--;
			}else {
				break;
			}
		}
		if(count==4) {
			numberOfFoursH++;
		}
		//check for overlapping
			while(index<=row) {
				int counter=0;int y=0;
				int start=index;
				while(y<4&&index<6) {
					if(state.getState()[index][j]=='x'||index==row) {
						counter++;
					}else {
						break;
					}
					y++;index++;
				}
				index=start+1;
				if(counter==4&&y==4) {
					numberOfFoursH++;
				}
			}
		
		////////////////////////////////////////////////////////////////
		//check diagonal up connected 4 for computer 
		i=row-1;
		int indexI=row;int indexJ=col; 
		j=col-1;count=1;
		while(i>=0&&j>=0) {
			if(state.getState()[i][j]=='o') {
				
				count++;
				if(count<4) {
					indexI=i;
					indexJ=j;
				}
				i--;j--;
			}else {
				break;
			}
		}
		if(count==4) {
			numberOfFoursC++;
		}
		
			while(indexI<=row&&indexJ<=col) {
				int counter=0;
				int y=0;
				int startI=indexI;
				int startJ=indexJ;
				while(y<4&&(indexI<6&&indexJ<7)) {
					if(state.getState()[indexI][indexJ]=='o'||(indexI==row&&indexJ==col)) {
						counter++;
					}else {
						break;
					}
					indexI++;indexJ++;y++;
				}
				indexI=startI+1;indexJ=startJ+1;
				if(counter==4&&y==4) {
					numberOfFoursC++;
				}
			}
			
		
		//check diagonal up connected 4 for human 
			i=row-1;
			indexI=row;indexJ=col; 
			j=col-1;count=1;
			while(i>=0&&j>=0) {
				if(state.getState()[i][j]=='x') {
					
					count++;
					if(count<4) {
						indexI=i;
						indexJ=j;
					}
					i--;j--;
				}else {
					break;
				}
			}
			if(count==4) {
				numberOfFoursH++;
			}
			
				while(indexI<=row&&indexJ<=col) {
					int counter=0;
					int y=0;
					int startI=indexI;
					int startJ=indexJ;
					while(y<4&&(indexI<6&&indexJ<7)) {
						if(state.getState()[indexI][indexJ]=='x'||(indexI==row&&indexJ==col)) {
							counter++;
						}else {
							break;
						}
						indexI++;indexJ++;y++;
					}
					indexI=startI+1;indexJ=startJ+1;
					if(counter==4&&y==4) {
						numberOfFoursH++;
					}
				}
		///////////////////////////////////////////////////////////////
		//check being on the other diagonal for computer
		i=row-1;
		indexI=row;
		indexJ=col;
		j=col+1;count=1;
		while(i>=0&&j<7) {
			if(state.getState()[i][j]=='o') {
				count++;
				if(count<4) {
					indexI=i;indexJ=j;
				}
				i--;j++;
				
			}else {
				break;
			}
		}
		if(count==4) {
			numberOfFoursC++;
		}
		
		while(indexI<=row&&indexJ>=col) {
			int startI=indexI;int startJ=indexJ;
			int y=0;
			int counter=0;
			while(y<4&&indexI<6&&indexJ>=0) {
				if(state.getState()[indexI][indexJ]=='o'||(indexI==row&&indexJ==col)) {
					counter++;
				}else {
					break;
				}
				y++;indexI++;indexJ--;
			}
			indexI=startI+1;indexJ=startJ-1;
			if(counter==4&&y==4) {
				numberOfFoursC++;
			}
		}		
			
		//check being on the other diagonal for Human
		i=row-1;
		indexI=row;
		indexJ=col;
		j=col+1;count=1;
		while(i>=0&&j<7) {
			if(state.getState()[i][j]=='x') {
				count++;
				if(count<4) {
					indexI=i;indexJ=j;
				}
				i--;j++;
				
			}else {
				break;
			}
		}
		if(count==4) {
			numberOfFoursH++;
		}
		
		while(indexI<=row&&indexJ>=col) {
			int startI=indexI;int startJ=indexJ;
			int y=0;
			int counter=0;
			while(y<4&&indexI<6&&indexJ>=0) {
				if(state.getState()[indexI][indexJ]=='x'||(indexI==row&&indexJ==col)) {
					counter++;
				}else {
					break;
				}
				y++;indexI++;indexJ--;
			}
			indexI=startI+1;indexJ=startJ-1;
			if(counter==4&&y==4) {
				numberOfFoursH++;
			}
		}		
		return new Point(numberOfFoursC,numberOfFoursH);
	}
	
	public Point checkConnected2(State state,int row, int col){
		int i=row;
		int j = col-1;
		int numberOfTwosC=0;
		int numberOfTwosH=0;
		int count=1;
		int c=1;//number of numbers I took with me
		//check 2 in a row containing this added number for computer
		int index=col;
		while(j>=0) {
			if((state.getState()[i][j]=='o')&&c<4) {
				if(c+1<4) {
					index=j;
				}
				j--;count++;
				
			}else if(state.getState()[i][j]=='e'&&c<4) {
				if(c+1<4) {
					index=j;
				}
				j--;
			}
			else {
				break;
			}
			c++;
		}
		if(count==2&&c==4) {
			numberOfTwosC++;
		}
				while(index<=col) {//begin counting connected 2 from here and so on
					int y=0;int counter=0;
					int start=index;
					while(y<4&&index<7) {
						if(state.getState()[i][index]=='o'||index==col) {
							counter++;y++;
						}else if(state.getState()[i][index]=='e'){
							y++;
						}else 
						{
							break;
						}
						index++;
					}
					if(counter==2&&y==4) {
						numberOfTwosC++;
					}
					index=start+1;
				}
			
		
		
		
		//check 2 in a row containing this added number for human
		index=col;
		j=col-1;
		i=row;
		count=1;c=1;
		while(j>=0) {
			if((state.getState()[i][j]=='x')&&c<4) {
				if(c+1<4) {
					index=j;
				}
				j--;count++;
				
			}else if(state.getState()[i][j]=='e'&&c<4) {
				if(c+1<4) {
					index=j;
				}
				j--;
			}
			else {
				break;
			}
			c++;
		}
		if(count==2&&c==4) {
			numberOfTwosH++;
		}
				while(index<=col) {//begin counting connected 2 from here and so on
					int y=0;int counter=0;
					int start=index;
					while(y<4&index<7) {
						if(state.getState()[i][index]=='x'||index==col){
							counter++;y++;
						}else if(state.getState()[i][index]=='e'){
							y++;
						}else 
						{
							break;
						}
						index++;
					}
					if(counter==2&&y==4) {
						numberOfTwosH++;
					}
					index=start+1;
				}
			
		
		
		
		////////////////////////////////////////////////////////////////
		
		//check 2 in a col containing this number added for computer
		
		i=row-1;j=col;
		count=1;c=1;
		index=row;
		while(i>=0) {
			if(state.getState()[i][j]=='o'&&c<4) {
				if(c+1<4) {
					index=i;
				}
				i--;count++;
			}else if(state.getState()[i][j]=='e'&&c<4) {
				if(c+1<4) {
					index=i;
				}
				i--;
			}
			else {
				break;
			}
			c++;
		}
		if(count==2&&c==4) {
			numberOfTwosC++;
		}
				while(index<=row) {
					int y=0;int counter=0;
					int start =index;
					while(y<4&&index<6) {
						if(state.getState()[index][j]=='o'||index==row) {
							counter++;y++;
						}else if(state.getState()[index][j]=='e') {
							y++;
						}else {
							break;
						}
						index++;
					}
					index=start+1;
					if(counter==2&&y==4) {
						numberOfTwosC++;
					}
				}
				
			
		
		
		
		//check 2 in a col containing this number added for Human
		i=row-1;j=col;
		count=1;c=1;
		index=row;
		while(i>=0) {
			if(state.getState()[i][j]=='x'&&c<4) {
				if(c+1<4) {
					index=i;
				}
				i--;count++;
			}else if(state.getState()[i][j]=='e'&&c<4) {
				if(c+1<4) {
					index=i;
				}
				i--;
			}
			else {
				break;
			}
			c++;
		}
		if(count==2&&c==4) {
			numberOfTwosH++;
		}
				while(index<=row) {
					int y=0;int counter=0;
					int start =index;
					while(y<4&&index<6) {
						if(state.getState()[index][j]=='x'||index==row) {
							counter++;y++;
						}else if(state.getState()[index][j]=='e') {
							y++;
						}else {
							break;
						}
						index++;
					}
					index=start+1;
					if(counter==2&&y==4) {
						numberOfTwosH++;
					}
				}
				
			
		
		
		////////////////////////////////////////////////////////////////
		//check diagonal up connected 2 for computer 
		i=row-1;c=1;int indexI=row;int indexJ=col;
		j=col-1;count=1;
		while(i>=0&&j>=0) {
			if(state.getState()[i][j]=='o'&&c<4) {
				if(c+1<4) {
					indexI=i;indexJ=j;
				}
				i--;j--;
				count++;
			}else if(state.getState()[i][j]=='e'&&c<4) {
				if(c+1<4) {
					indexI=i;indexJ=j;
				}
				i--;j--;
			}
			else {
				break;
			}
			c++;
		}
		if(count==2&&c==4) {
			numberOfTwosC++;
		}
				while(indexI<=row&&indexJ<=col){
					int startI=indexI;
					int startJ=indexJ;
					int y=0;int counter=0;
					while(y<4&&indexI<6&&indexJ<7) {
						if(state.getState()[indexI][indexJ]=='o'||(indexI==row&&indexJ==col)) {
							y++;counter++;
						}else if(state.getState()[indexI][indexJ]=='e') {
							y++;
						}else {
							break;
						}
						indexI++;indexJ++;
					}
					indexI=startI+1;
					indexJ=startJ+1;
					if(counter==2&&y==4) {
						numberOfTwosC++;
					}
				}
			
		
		//check diagonal up connected 2 for human 
		i=row-1;c=1;indexI=row;indexJ=col;
		j=col-1;count=1;
		while(i>=0&&j>=0) {
			if(state.getState()[i][j]=='x'&&c<4) {
				if(c+1<4) {
					indexI=i;indexJ=j;
				}
				i--;j--;
				count++;
			}else if(state.getState()[i][j]=='e'&&c<4) {
				if(c+1<4) {
					indexI=i;indexJ=j;
				}
				i--;j--;
			}
			else {
				break;
			}
			c++;
		}
		if(count==2&&c==4) {
			numberOfTwosH++;
		}
				while(indexI<=row&&indexJ<=col){
					int startI=indexI;
					int startJ=indexJ;
					int y=0;int counter=0;
					while(y<4&&indexI<6&&indexJ<7) {
						if(state.getState()[indexI][indexJ]=='x'||(indexI==row&&indexJ==col)) {
							y++;counter++;
						}else if(state.getState()[indexI][indexJ]=='e') {
							y++;
						}else {
							break;
						}
						indexI++;indexJ++;
					}
					indexI=startI+1;
					indexJ=startJ+1;
					if(counter==2&&y==4) {
						numberOfTwosH++;
					}
				}
			
		
		
		///////////////////////////////////////////////////////////////
		//check being on the other diagonal for computer
		i=row-1;indexI=row;
		indexJ=col;
		j=col+1;count=1;c=1;
		while(i>=0&&j<7) {
			if(state.getState()[i][j]=='o'&&c<4) {
				if(c+1<4) {
					indexI=i;indexJ=j;
				}
				i--;j++;
				count++;
			}else if(state.getState()[i][j]=='e'&&c<4){
				if(c+1<4) {
					indexI=i;indexJ=j;
				}
				i--;j++;
			}
			else {
				break;
			}
			c++;
		}
		
		
		if(count==2&&c==4) {
			numberOfTwosC++;
		}
				while(indexI<=row&&indexJ>=col){
					int startI=indexI;
					int startJ=indexJ;
					int y=0;int counter=0;
					while(y<4&&indexI<6&&indexJ>=0) {
						if(state.getState()[indexI][indexJ]=='o'||(indexI==row&&indexJ==col)) {
							y++;counter++;
						}else if(state.getState()[indexI][indexJ]=='e') {
							y++;
						}else {
							break;
						}
						indexI++;indexJ--;
					}
					indexI=startI+1;
					indexJ=startJ-1;
					if(counter==2&&y==4) {
						numberOfTwosC++;
					}
				}
				
			
	
		
		
		//check being on the other diagonal for Human
		i=row-1;indexI=row;
		indexJ=col;
		j=col+1;count=1;c=1;
		while(i>=0&&j<7) {
			if(state.getState()[i][j]=='x'&&c<4) {
				if(c+1<4) {
					indexI=i;indexJ=j;
				}
				i--;j++;
				count++;
			}else if(state.getState()[i][j]=='e'&&c<4){
				if(c+1<4) {
					indexI=i;indexJ=j;
				}
				i--;j++;
			}
			else {
				break;
			}
			c++;
		}
		if(count==2&&c==4) {
			numberOfTwosH++;
		}
				while(indexI<=row&&indexJ>=col){
					int startI=indexI;
					int startJ=indexJ;
					int y=0;int counter=0;
					while(y<4&&indexI<6&&indexJ>=0) {
						if(state.getState()[indexI][indexJ]=='x'||(indexI==row&&indexJ==col)) {
							y++;counter++;
						}else if(state.getState()[indexI][indexJ]=='e') {
							y++;
						}else {
							break;
						}
						indexI++;indexJ--;
					}
					indexI=startI+1;
					indexJ=startJ-1;
					if(counter==2&&y==4) {
						numberOfTwosH++;
					}
				}
				
		return new Point(numberOfTwosC,numberOfTwosH);
	}
	
	
	public int calculateHeoristic(State state){//Heuristic		
		int row=state.getRowDiffFromParent();
		int col=state.getColDiffFromParent();
		Point p4=checkConnected4(state, row, col);
		int x=(int)( 10000*4*(p4.getX() + p4.getY()) );
		Point p3=checkConnected3(state,row, col);
		x= x + (int)(1000*3*( p3.getX() + p3.getY()) );
		Point p2 = checkConnected2(state,row,col);
		x= x + (int)(100* 2*(p2.getX() + p2.getY()) );
		if(state.getState()[row][col]=='x'){//it is a human state
			x = x*-1;
		}
		return x+(state.AI_score-state.Human_score);//it is the heuristic which computer wants to maximize while human wants to minimize
	}
	
public static void main(String args[]) {
	Heuristic h=new Heuristic();
	char[][]arr=new char[][] {{'e', 'e', 'e', 'e', 'e', 'e', 'e'}
							, {'e', 'e', 'e', 'x', 'e', 'e', 'e'}
							, {'e', 'e', 'e', 'e', 'x', 'e', 'x'}
							, {'e', 'e', 'e', 'e', 'e', 'e', 'e'}
							, {'e', 'e', 'e', 'x', 'x', 'e', 'x'},
							  {'e', 'e', 'x', 'x', 'x', 'e', 'o'}};
		State s=new State();
		s.setState(arr);
		s.setRowDiffFromParent(4);
		s.setColDiffFromParent(6);
		//s.state=arr;
		System.out.println(h.checkConnected2(s,s.getRowDiffFromParent(),s.getColDiffFromParent()));//x e e x ,e x e x , x e x e so 3 seq are available to continue in them next time
		
}
}
