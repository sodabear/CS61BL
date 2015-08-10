import java.util.*;

public class Piece{
	public  int x1;
	public  int y1;
	public  int x2;
	public  int y2;
	public  int high;
	public  int wide;
	public  int storePiece;

	public Piece(int x1, int y1, int x2, int y2){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.high = y2 - y1 +1;
		this.wide = x2 - x1 +1;
		this.storePiece = x1 + y1*256 +  x2 *65536 +  y2*1507328;

	}

	@Override
	public boolean equals(Object p){
		return this.x1 == ((Piece)p).x1
		    && this.y1 == ((Piece)p).y1
		    && this.x2 == ((Piece)p).x2
		    && this.y2 == ((Piece)p).y2;
	}

	@Override
	public int hashCode(){
		return storePiece;
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		//sb.append("b");
		sb.append(storePiece);
		return sb.toString();
	}

}
