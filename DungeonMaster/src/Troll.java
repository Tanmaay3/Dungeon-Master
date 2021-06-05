// package character;
// import floor.Floor;

public class Troll extends Enemy {

	public Troll(int x, int y, Floor f) {
		super(x, y, 90, 20, 15, 0, f);
		f.setTile(x, y, 'T');
	}
	
}
