package mathUtil;

public class Coordinate2D {
	
	private int x;
	
	private int y;
	
	private int dimension;
	
	
	public Coordinate2D(){
		this(0, 0, 0);
	}
	
	
	public Coordinate2D(int x, int y, int dimension){
		
		this.x = x;
		
		this.y = y;
		
		this.dimension = dimension;
		
	}
	
	
	
	public void setCoordinate(int x, int y, int dimension){
		this.x = x;
		
		this.y = y;
		
		this.dimension = dimension;
	}
	
	
	public int getX(){
		
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void moveUp(int step){
		y -= step;
	}
	
	public void moveDown(int step){
		y += step;
	}
	
	public void moveLeft(int step){
		x -= step;
	}
	
	public void moveRight(int step){
		x += step;
	}
	
	
	
	public Coordinate2D centerCoord(){
		int x_alias = (x + 952/2) - 1737/2;
		
		int y_alias = 1154/2 - (y + 296/2);
		
		return new Coordinate2D(x_alias, y_alias, dimension);
	}
	
	
	
	public String toString(){
		
		int x_alias = x - dimension/2;
		
		int y_alias = dimension/2 - y;
		
		String result = "(" + x_alias + ", " + y_alias + ")";
		
		return result;
	}

}
