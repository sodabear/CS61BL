import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;

public class Solver {
	public static HashSet<Piece> GOODGAME;

	// public final Board GOODLUCK;
	// public ArrayList<Piece> moves;

	public static boolean isGoal(Board currentBoard) {
		// System.out.println(" *********** brutal force *************  ");
		// System.out.println(" Piece in current Board :" + currentBoard.allPieces);
		// System.out.println(" Moves to currentBoard : " + currentBoard.pathToGG);
		// System.out.println(" Goal to Match with   :" + GOODGAME);
		// System.out.println(" *********** end *************  ");
		// System.out.println("                                     ");
		return currentBoard.allPieces.containsAll(GOODGAME);
	}

	public static void main(String[] args) {
		int lineCount = 0;
		Board gl = null;
		try {
			Path     goalPath   = Paths.get(args[1]);
			Scanner  go         = new Scanner(goalPath);
			Path     initPath   = Paths.get(args[0]);
			Scanner  in         = new Scanner(initPath);
			int      height     = in.nextInt();
			int      width      = in.nextInt();
			HashSet<Piece> allPiecehashed = new HashSet<Piece>();
			while (in.hasNextInt() ) {
				int y1 = in.nextInt();
				int x1 = in.nextInt();
				int y2 = in.nextInt();
				int x2 = in.nextInt();
				Piece p = new Piece(x1, y1, x2, y2);
				allPiecehashed.add(p);
			}
			//System.out.println("ccccc");
			HashSet<Piece> gg = new HashSet<Piece>();
			while (go.hasNextInt()) {
				int y1 = go.nextInt();
				int x1 = go.nextInt();
				int y2 = go.nextInt();
				int x2 = go.nextInt();
				Piece pp = new Piece(x1, y1, x2, y2);
				//System.out.println(pp.toString());
				gg.add(pp);
				lineCount++;

			}
			GOODGAME = gg;
			// for (Piece p : GOODGAME) {
			// 		// System.out.println(p.toString());
			// }

			//System.out.println("bbbbbb");
			 gl = new Board(width, height, allPiecehashed, gg, lineCount);
			 //System.out.println(gl.toString());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		HashSet<Board> visited = new HashSet<Board>();
		Queue<Board> queue = new LinkedList<Board>();

		//instant match
		if(isGoal(gl)) {
			//System.out.println("bbbbbb");
			for (String o : gl.pathToGG)
					System.out.println(o);
			return;
		}

		queue.add(gl);

		while (!queue.isEmpty()) {
			// System.out.println(queue.toString());
			Board current = queue.remove();
			//System.out.println("hhhhh");
			if (isGoal(current)) {
				//System.out.println("      a");
				for (String o : current.pathToGG)
					System.out.println(o);
				return;
			}
			//System.out.print("\n\n\n\n\n");
			//System.out.println("current: " + current);
			// if (current.getNeighbors() != null) {
				for (Board neighbor : current.getNeighbors()) {
					if (!visited.contains(neighbor)) {
						//System.out.println("++ " + neighbor);
						queue.add(neighbor);
						visited.add(neighbor);
					}
				}
			// }
			//	System.out.print("\n\n\n\n\n");
		}
		//System.out.println("gg");
	}

}
