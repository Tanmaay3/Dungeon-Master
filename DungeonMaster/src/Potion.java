// package floor;

public class Potion extends Item {
	
	char type;

	public Potion(int x, int y, int value, Floor floor, char type) 
	{
		super(x, y, value, floor);
		this.type = type;
		floor.setTile(x, y, 'P');
	}
	
	public char getType() { return type; }

}
