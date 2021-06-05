// package character;
// import floor.Floor;

public class Orc extends Player {

	public Orc(int x, int y, Floor f, int cc) {
		super(x, y, 280, 280, 20, 25, 0, f, cc, "Orc");
	}
	
	public String getRace() { return "Orc"; }

}
