package mathUtil;

public class Coordinate2D {
	
	private int x;
	
	private int y;
	
	private int dimensionX;
	
	private int dimensionY;
	
	
	public Coordinate2D(){
		this(0, 0, 0, 0);
	}
	
	public Coordinate2D(int x, int y){
		
		this.x = x;
		
		this.y = y;
	}
	
	
	public Coordinate2D(int x, int y, int dimensionX, int dimensionY){
		
		this.x = x;
		
		this.y = y;
		
		this.dimensionX = dimensionX;
		
		this.dimensionY = dimensionY;
		
	}
	
	
	
	public void setCoordinate(int x, int y, int dimensionX, int dimensionY){
		this.x = x;
		
		this.y = y;
		
		this.dimensionX = dimensionX;
		
		this.dimensionY = dimensionY;
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
	
	
	
	public Coordinate2D shiftedCoord(){
//		int x_alias = (x + 952/2) - 1737/2;
//		
//		int y_alias = 1154/2 - (y + 296/2);
		
		if(dimensionX > 0 && dimensionY > 0){
		
			int x_alias = x - dimensionX/2;
			
//			x_alias -= 95;
			
			int y_alias = dimensionY/2 - y;
			
			return new Coordinate2D(x_alias, y_alias, dimensionX, dimensionY);
		}
		
		return this;
	}
	
	public boolean isIncluded(Coordinate2D coordA, Coordinate2D coordB){
		
		int rightBound = coordA.getX() > coordB.getX() ? coordA.getX() : coordB.getX();
		
		int leftBound =  coordA.getX() < coordB.getX() ? coordA.getX() : coordB.getX();
		
		int bottomBound = coordA.getY() > coordB.getY() ? coordA.getY() : coordB.getY();
		
		int topBound = coordA.getY() < coordB.getY() ? coordA.getY() : coordB.getY();
		
		if(this.x >= leftBound && this.x <= rightBound && this.y >= topBound && this.y <= bottomBound)
			return true;
		
		return false;
	}
	
	
	
	
	
	public String toString(){
		
		Coordinate2D shiftedCoord = this.shiftedCoord();
		
		String result = "(" + shiftedCoord.getX() + ", " + shiftedCoord.getY() + ")";
		
		return result;
	}

}
