
import java.io.*;
import java.util.*;


final class DungeonMaster 
{
	
	static int currentHp;
	static float currentGold = 0;
	

	//save player status for next floor
	static void save(Player player) 
	{
		currentHp = player.getHP();
		currentGold = player.getGold();
	}

	//load player status from previous floor
	static void load(Player player) 
	{

		player.setHP(currentHp);
		player.setGold(currentGold);
	}

	//reset player status
	static void reset() {

		currentGold = 0;
	}

	//help menu
	private static void help(){
		System.out.println("Move: ");
		System.out.println("no = Move north");
		System.out.println("ea = Move east");
		System.out.println("so = Move south");
		System.out.println("we = Move west");
		System.out.println("nw = Move northwest");
		System.out.println("ne = Move northeast");
		System.out.println("se = Move southeast");
		System.out.println("sw = Move southwest");
		System.out.println();
		System.out.println("Attack: ");
		System.out.println("Input 'a' followed by direction");
		System.out.println("e.g.: ano = attack north");
		System.out.println("");
		System.out.println("Use potion: ");
		System.out.println("Input 'u' followed by direction");
	}
	
	public static void main(String[] args) 
	{

		char instruction, playerRace;
		boolean nextFloor;
		boolean newGame = true;

		File file = new File("map.txt");
		BufferedReader reader = null;	
		Scanner scanner = new Scanner(System.in);

		while(newGame) 
		{
			Player player = null;
			newGame = false;

			System.out.println("Welcome to Dungeon Master brave hero.\nA terrific adventure awaits. Venture forth with"
					+ "courage and be victorious!");
			System.out.println("Please choose a race:");
			System.out.println("Human(h), Elf(e), Dwarf(d), Orc(o)");
			System.out.println();
			System.out.println("Quit(q) Restart(r) Help(?)");

			instruction = scanner.next().charAt(0);

			while(instruction != 'q' &&
					instruction != 'r' &&
					instruction != 'h' &&
					instruction != 'e' &&
					instruction != 'd' &&
					instruction != 'o' &&
					instruction != '?') 
			{
				System.out.println("Invalid command!");
				instruction = scanner.next().charAt(0);
			}

			if (instruction == 'q') 
			{
				//clear the screen and display "Game over"
				System.out.println("Game over");
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				scanner.close();
				continue;
			} 
			else if(instruction == 'r') 
			{
				newGame = true;
				continue;
			} 
			else if(instruction == '?')
			{
				help();
				newGame = true;
				continue;
			}

			//if the instruction is player's race
			playerRace = instruction;

			//initialize the player's hp for future save/load
			switch(playerRace) 
			{
			case 'h':
				currentHp = 180;
				break;
			case 'e':
				currentHp = 150;
				break;
			case 'd':
				currentHp = 160;
				break;
			case 'o':
				currentHp = 280;
				break;
			}

			//start the game
			for(int i = 0; i < 5; i++) 
			{

				Floor currentFloor = new Floor(i);
				
				try {
					reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
				} catch (Exception e) {
					e.printStackTrace();
				}


				//read in the map
				try {
					String line;
					int j = 0;
					while ((line = reader.readLine()) != null) {
						char[] chars = line.toCharArray();
						for (int k = 0; k < 79; k++) {
							currentFloor.setTile(j, k, chars[k]);
						}
						j++;
					}

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				currentFloor.playerGenerator(playerRace);
				player = currentFloor.getPlayer();
				

				//generate the contents of the current floor
				currentFloor.contentGenerator();

				//load the player's status form previous floor
				load(player);

				//print the layout of the current floor
				currentFloor.printFloor();
				

				//while the player haven't reach the stairway
				nextFloor = false;
				

				while(!nextFloor) 
				{			
					
					String a = scanner.next();
					char[] b = a.toCharArray();
					instruction = b[0];
					boolean helped = false;
					

					//if the instruction is restart
					if(instruction == 'r') 
					{
						//reset the player's status
						reset();
						newGame = true;
						break;
						//if the instruction is quit
					} 
					else if(instruction == 'q') 
					{
						//clear the screen and display "Game over"
						System.out.println("Game over");
						break;
						//if the instruction is to move
					} 
					else if (instruction == '?')
					{
						help();
						helped = true;
					} 
					else if (b.length < 2 || b.length > 3) 
					{
						System.out.println("Invalid command!");
						continue;
					} 
					else if(instruction == 'n' ||
							instruction == 's' ||
							instruction == 'e' ||
							instruction == 'w' ||
							instruction == 'u' ||
							instruction == 'a' ||
							instruction == 'p') 
					{

						char command;
						//set the command to "use", "attack" or "move"
						if(instruction == 'u' ||
								instruction == 'a') 
						{
							command = instruction;
							if (b.length < 3) 
							{
								
								System.out.println("Invalid command!");
								continue;
							}
							else
							{
								
								instruction = b[1];
								System.out.println(instruction);
							}
							
						} 
						else
							command = 'm';

						
						if(instruction == 'n') 
						{
							
							if (b.length == 2)
							{
								instruction = b[1];
							}
							else
							{
								instruction = b[2];
							}
							if(instruction == 'o')
							{
								nextFloor = player.move('8', command);
								
							}
								
							else if(instruction == 'e')
								nextFloor = player.move('9', command);
							else if(instruction == 'w')
								nextFloor = player.move('7', command);
							else 
							{
								System.out.println("Invalid command!");
								continue;
							}
						} 
						else if(instruction == 's') 
						{
							if (b.length == 2)
							{
								instruction = b[1];
							}
							else
							{
								instruction = b[2];
							}
							if(instruction == 'o')
								nextFloor = player.move('2', command);
							else if(instruction == 'e')
								nextFloor = player.move('3', command);
							else if(instruction == 'w')
								nextFloor = player.move('1', command);
							else 
							{
								System.out.println("Invalid command!");
								continue;
							}
						} 
						else if(instruction == 'e') 
						{
							if (b.length == 2)
							{
								instruction = b[1];
							}
							else
							{
								instruction = b[2];
							}

							if(instruction == 'a')
								nextFloor = player.move('6', command);
							else {
								System.out.println("Invalid command!");
								continue;
							}
						}
						
						else if(instruction == 'w') 
						{
							if (b.length == 2)
							{
								instruction = b[1];
							}
							else
							{
								instruction = b[2];
							}
							
							if(instruction == 'e')
								nextFloor = player.move('4', command);

							
							else {
								System.out.println("Invalid command!");
								continue;
							}
						}

						//move all the enemies
						for(int i1=0; i1<currentFloor.getNumEnemies(); i1++)
							currentFloor.getEnemy(i1).move();

						//if the player is dead
						if(player.getHP() <= 0) 
						{
							nextFloor = false;
							
							
							System.out.println("You are dead! Game over!");

							System.out.println("Score: " + (int) player.getGold());
							System.out.println("Quit(q) Restart(r)");

							//read in the instruction
							instruction = scanner.next().charAt(0);

							//keep reading in the instruction until it is valid
							while(instruction != 'q' && instruction != 'r')
								instruction = scanner.next().charAt(0);

							//if the instruction is restart
							if(instruction == 'r') {
								//reset the player's status
								reset();
								newGame = true;
							}

							break;
						}
					}
					
					else {
						System.out.println("Invalid command!");
						continue;
					}

					//print the floor layout, if have not seeked for help
					if (!helped)
					 currentFloor.printFloor();
				}

				//if the player defeated the game
				if(i == 4 && nextFloor && !newGame) 
				{                

					System.out.println("Congratulations! You defeated the game!");

					
					System.out.println("Score: " + (int) player.getGold());

					System.out.println("Quit(q) Restart(r)");

					//read in the instruction
					instruction = scanner.next().charAt(0);

					//keep reading in the instruction until it is valid
					while(instruction != 'q' && instruction != 'r')
						instruction = scanner.next().charAt(0);

					//if the instruction is restart
					if(instruction == 'r') {
						reset();
						newGame = true;
					}

				} 
				else if (!nextFloor) 
				{
					break;
				}

				//if the current floor is finished 
				//save the player's status
				save(player);
			}
		}

	}

}

