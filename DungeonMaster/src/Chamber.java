// package floor;
// import Character.*;

import java.util.Random;

class Chamber 
{
	int chamberID;
	Floor floor;

	int width, height;
	int floorX, floorY;
	boolean[][] available;
	
	class Coord 
	{
		int x, y;
		Coord(int X, int Y) 
		{
			x = X;
			y = Y;
		}
	}
	
	public Coord randomCoord() 
	{
		Random random = new Random();
		int x = random.nextInt(height);
		int y = random.nextInt(width);

		while (!available[x][y]) 
		{
			x = random.nextInt(height);
			y = random.nextInt(width);
		}

		available[x][y] = false;

		Coord tmp = new Coord(x, y);
		return tmp;
	}

	public Chamber(int chamberID, Floor floor) 
	{
		this.chamberID = chamberID;
		this.floor = floor;

		switch(chamberID) {
		case 0:
			width = 26;
			height = 4;
			floorX = 3;
			floorY = 3;
			break;
		case 1:
			width = 37;
			height = 10;
			floorX = 3;
			floorY = 39;
			break;
		case 2:
			width = 12;
			height = 3;
			floorX = 10;
			floorY = 38;
			break;
		case 3:
			width = 21;
			height = 7;
			floorX = 15;
			floorY = 4;
			break;
		case 4:
			width = 39;
			height = 6;
			floorX = 16;
			floorY = 37;
			break;
		}
		
		//initialize the boolean 2D array
		available = new boolean[height][width];
		
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				available[i][j] = true;
			}
		}
		
		switch(chamberID) 
		{
		case 1:
			for(int i=0; i<2; i++)
				for(int j=23; j<37; j++)
					available[i][j] = false;

			for(int i=31; i<37; i++)
				available[2][i] = false;

			for(int i=34; i<37; i++)
				available[3][i] = false;

			for(int i=4; i<10; i++)
				for(int j=0; j<22; j++)
					available[i][j] = false;
			break;
			
		case 4:
			for(int i=0; i<3; i++)
				for(int j=0; j<28; j++)
					available[i][j] = false;
			break;
		}
	}
		
	//randomly generate a player in the current chamber
	public void playerGenerator(char race) 
	{
		Coord random = randomCoord();

		//create the character with the given race
		switch(race) 
		{
		case 'h':
			floor.player = new Human(random.x + floorX,
					random.y + floorY,
					floor, chamberID);
			break;
		case 'e':
			floor.player = new Elf(random.x + floorX,
					random.y + floorY,
					floor, chamberID);
			break;
		case 'd':
			floor.player = new Dwarf(random.x + floorX,
					random.y + floorY,
					floor, chamberID);
			break;
		case 'o':
			floor.player = new Orc(random.x + floorX, 
					random.y + floorY, 
					floor, chamberID);
			break;
		}
	}
	
	public void stairwayGenerator() 
	{

		Coord random = randomCoord();

		//set the stairway's tile to back slash
		floor.setTile(random.x + floorX, random.y + floorY, '\\');
	}
	
	public void enemyGenerator() 
	{
		  //find a available coordinate
		  Coord random = randomCoord();

		  Enemy e;

		  //generate a enemy with a random type
		  //based on the following probability:
		  // * Werewolf: 4/18
		  // * Vampire: 3/18
		  // * Goblin: 5/18
		  // * Troll: 1/9
		  // * Phoenix: 1/9
		  Random rand = new Random();
		  int type = rand.nextInt(18);
		  
		  if(type < 4)
		    e = new Werewolf(random.x + floorX, 
				     random.y + floorY, 
				     floor);
		  else if(type < 7)
		    e = new Vampire(random.x + floorX, 
				    random.y + floorY, 
				    floor);
		  else if(type < 12)
		    e = new Goblin(random.x + floorX,
				   random.y + floorY,
				   floor);
		  else if(type < 14)
		    e = new Troll(random.x + floorX,
				  random.y + floorY,
				  floor);
		  else 
		    e = new Phoenix(random.x + floorX,
				    random.y + floorY,
				    floor);
		  
		  //assign the enemy
		  floor.enemies[floor.numEnemies++] = e;
	}
	
	public void itemGenerator(boolean isPotion) 
	{
		//find a available coordinate
		Coord random = randomCoord();
		Random ran = new Random();

		Item i = null;

		//create one of the following potions with equal possibility 
		// * restore HP up to 40
		// * increase ATK by 5
		// * increase DEF by 5
		if(isPotion) 
		{
			int type = ran.nextInt(3);
			switch(type) 
			{
				case 0:
					i = new Potion(random.x + floorX,
							random.y + floorY,
							40, floor, 'h');
					break;
				case 1:
					i = new Potion(random.x + floorX,
							random.y + floorY,
							5, floor, 'a');
					break;
				case 2:
					i = new Potion(random.x + floorX,
							random.y + floorY,
							5, floor, 'd');
					break;
			}

			//create gold with the following probability
			// * normal: 5/8
			// * small hoard: 1/4
		} 
		else 
		{
			int type = ran.nextInt(8);

			if(type < 5)
				i = new Treasure(random.x + floorX,
						random.y + floorY,
						10, floor);
			else if(type < 8)
				i = new Treasure(random.x + floorX,
						random.y + floorY,
						20, floor);
			
		}

		//assign the item
		floor.items[floor.numItems] = i;
		floor.numItems++;
	}

}
