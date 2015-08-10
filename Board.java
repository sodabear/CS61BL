import java.util.*;

public class Board {
	public static final int UP =    -1;
	public static final int DOWN =   0;
	public static final int LEFT =   1;
	public static final int RIGHT =  2;
	public static final int EMPTY =  0;
	public static final int EXIST =  1;
	public static int WIDTH;
	public static int HEIGHT;
	public static int TOTAL;
	public static ArrayList<Integer> dir;
	public final String boardStore;
	public int[][] tray;
	public HashSet<Piece> allPieces;
	public ArrayList<String> pathToGG;
	public static HashSet<Piece> GOODGAME;

	static {
		dir = new  ArrayList<Integer>();
		dir.add(UP);
		dir.add(DOWN);
		dir.add(LEFT);
		dir.add(RIGHT);
	}

	public Board(int width, int height, HashSet<Piece> all, HashSet<Piece> goal, int total){
		WIDTH  = width;
		HEIGHT = height;
		GOODGAME = goal;
		TOTAL = total + 10;
		//System.out.println(width + "  " + height );
		tray = new int[HEIGHT][WIDTH];
		allPieces = all;
		setAll(tray,EXIST);
		pathToGG = new ArrayList<String>();
		boardStore = this.toString();

	}

	public Board(HashSet<Piece> all, ArrayList<String> m, int[][] thetray){
		allPieces = all;
		pathToGG = m;
		tray = thetray;
		boardStore = this.toString();

	}

	// for all piece to apply all move;
	public HashSet<Board> getNeighbors() {
		HashSet<Board> neighbors = new HashSet<Board>();
		HashSet<Piece> all = this.allPieces;
		for (Piece p: all) {
			// System.out.println( "-----------------------");
			//System.out.println( "This is the current piece " + 	p.toString() +"\n");
			for (Integer d : dir) {
				// System.out.println( "*****************" + d);
				// System.out.println( "                 " );
				if(isLegalmove(p,d)) {
					Board move = applyMove(p, d); // 1234567890
					neighbors.add(move);
				}
			}

		}

		// if(neighbors.isEmpty()){
		// 	return null;
		// }

		return neighbors;

	}


	public void setAll(int[][] thetray, int change) {
		for (Piece p : allPieces) {
			setPiece(p ,thetray, EXIST);
		}
	}

// pos = (2,2)
// size = 3x3
	public void setPiece(Piece p, int[][] thistray, int change) {
		int x1 = p.x1;
		int y1 = p.y1;
		int x2 = p.x2;
		int y2 = p.y2;
		//System.out.println(p.toString());
			//thetray[y1][x1] = EXIST;
			//thetray[y2][x2] = EXIST;
		for(int i = 0; i < p.wide; i++)
			thistray[y1][x1 +i] = change ;
		for(int i = 0; i < p.high;  i++)
			thistray[y1+i][x1] = change;
		for(int i = 0; i < p.wide; i++)
			thistray[y2][x1 + i] = change;
		for(int i = 0; i < p.high;  i++)
			thistray[y1+ i][x2] = change;
	}



	public boolean isLegalmove(Piece p, int direction){
		int x1 = p.x1;
		int y1 = p.y1;
		int x2 = p.x2;
		int y2 = p.y2;

		switch(direction){
			case LEFT:
				// System.out.println("along the x1 " + x1 );
				if(x1-1 < 0){
					return false;
				}
				for(int i = y1; i <= y2;i++){
					if( this.tray[i][x1-1] == EXIST){
						return false;
					}
				}
				return true;
			case RIGHT:
				// System.out.println("along the x2 " + x2 );
				if(x2+1 >= WIDTH){
					return false;
				}
				for(int i = y1; i <= y2;i++){
					if( this.tray[i][x2 + 1] == EXIST){
						return false;
					}
				}
				return true;
			case UP:
				// System.out.println("along the y1 " + y1 );
				if(y1-1 < 0){
					return false;
				}
				for(int i = x1; i <= x2;i++){
					if( this.tray[y1-1][i] == EXIST){
						return false;
					}
				}
				return true;
			case DOWN:
				// System.out.println("HEIGHT is  " + HEIGHT);
				// System.out.println("widthH is  " + WIDTH);
				if(y2 + 1 >= HEIGHT){
					return false;
				}

				for(int i = x1; i <= x2; i++){
				// System.out.println("along the y2 " + y2 );
				// System.out.println("along the y2 " + x2 );
				// System.out.println("along the y2 " + y1 );
				// System.out.println("along the y2 " + x1 );
				// System.out.println("along the y2 " + tray[0][0]);
					if( this.tray[y2 + 1][i] == EXIST){
						return false;
					}
				}
				return true;
		}

		//System.out.println("wrong direttion");
		return false;
	}

	public Board applyMove(Piece p, int direction){
		// copy construcotr
		// System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^" );
		// System.out.println("old all peices ---------->"+  allPieces.toString() );
		// System.out.println("old--------->" +  p.toString() );
		int x1 = p.x1;
		int y1 = p.y1;
		int x2 = p.x2;
		int y2 = p.y2;
		HashSet<Piece>  newPieces = new HashSet<Piece>(this.allPieces);
		newPieces.remove(p);
		ArrayList<String> newpathToGG = new ArrayList<String>(this.pathToGG);
		int[][] newTray = new int[HEIGHT][WIDTH];
		// tray.length = HEIGHT
		// tray[0].length = width
		for (int i = 0; i < tray.length ; i++) {
    		System.arraycopy(tray[i], 0, newTray[i], 0, tray[0].length);
		}
		setPiece(p,newTray,EMPTY);
		StringBuilder sb = new StringBuilder();
		//String str = "";
		String whereTO = " ";
		switch(direction){
			case LEFT:
					x1 = x1-1;
					x2 = x2-1;
					//str = "" + p.y1 + " " + p.x1 + " " + y1 + " " + x1;
					//System.out.println(str);
					whereTO += "left";
					break;
			case RIGHT:
					x1 = x1+1;
					x2 = x2+1;
					//str = "" + p.y1 + " " + p.x1 + " " + y1 + " " + x1;
					whereTO += "right";
					break;
			case UP:
					y1 = y1-1;
					y2 = y2-1;
					//str = "" + p.y1 + " " + p.x1 + " " + y1 + " " + x1;
					whereTO += "up   ";
					break;
			case DOWN:
					y1 = y1+1;
					y2 = y2+1;
					//str = "" + p.y1 + " " + p.x1 + " " + y1 + " " + x1;
					whereTO += "down ";
					break;
		}

		sb.append(p.y1);
		sb.append(" ");
		sb.append(p.x1);
		sb.append(" ");
		sb.append(y1);
		sb.append(" ");
		sb.append(x1);
		String str = sb.toString();
		Piece newP = new Piece(x1,y1,x2,y2);
		// System.out.println("new ---------->"+  newP.toString() );
		newPieces.add(newP);
		// System.out.println("new All Pieces ---------->"+ newPieces.toString() );
		// System.out.println("old all peices ------==-->"+  allPieces.toString() );
		newpathToGG.add(str);
		//System.out.println("before move "+ p.toString() +  "  @@@@  moving to  " + whereTO + " || after move P is  " + str);
		setPiece(newP,newTray,EXIST);

		// System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^" );
		return new Board(newPieces,newpathToGG,newTray);

	}
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(Piece p : allPieces){
			//+ " "+ p.wide + " "+ p.high
			//sb.append(p.storePiece.);
			//sb.append(p.y1);
			//sb.append(p.x2);
			//sb.append(p.y2);
			//sb.append("e");
			sb.append(p.storePiece);
		}
		return sb.toString();
	}


	@Override
	public boolean equals(Object p){
		return  boardStore.equals(((Board)p).toString());
	}

	@Override
	public int hashCode(){
		return  boardStore.hashCode();
	}




}
