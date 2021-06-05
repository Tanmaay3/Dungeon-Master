// package character;
// import floor.Floor;

public class Dwarf extends Player {

	public Dwarf(int x, int y, Floor f, int cc) {
		super(x, y, 160, 160, 20, 70, 0, f, cc, "Dwarf");
	}
	
	public String getRace() { return "Dwarf"; }

}
