// package floor;

public class Treasure extends Item {

	public Treasure(int x, int y, int value, Floor floor) {
		super(x, y, value, floor);
		floor.setTile(x, y, 'G');
	}

}
