// package character;
// import floor.Floor;

public class Goblin extends Enemy {

	public Goblin(int x, int y, Floor f) {
		super(x, y, 40, 5, 10, 0, f);
		f.setTile(x, y, 'N');
	}

}
