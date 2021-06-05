// package character;
// import floor.Floor;

public class Elf extends Player {

	public Elf(int x, int y, Floor f, int cc) {
		super(x, y, 150, 150, 70, 20, 0, f, cc, "Elf");
	}
	
	public String getRace() { return "Elf"; }

}
