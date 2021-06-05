// package character;
// import floor.Floor;

public class Werewolf extends Enemy {

	public Werewolf(int x, int y, Floor f) {
		super(x, y, 80, 30, 5, 0, f);
		f.setTile(x, y, 'W');
	}

}
