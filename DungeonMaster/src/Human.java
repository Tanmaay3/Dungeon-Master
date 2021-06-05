// package character;
// import floor.Floor;

public class Human extends Player {

	public Human(int x, int y, Floor f, int cc) {
		super(x, y, 180, 180, 30, 30, 0, f, cc, "Human");
	}
	
	public String getRace() { return "Human"; }

}
