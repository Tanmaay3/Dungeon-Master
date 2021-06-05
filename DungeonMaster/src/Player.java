// package character;
// import floor.Floor;
// import floor.Item;
// import floor.Treasure;

public class Player extends Character 
{
	
	private int currentChamber = 0;
	private int defaultHP = 0;
	private char currentTile;
	private String race;
	
	public Player(int x, int y, int hp, int dhp, int atk, int def, float gold, Floor floor, int cc, String Race) 
	{
		super(x, y, hp, atk, def, gold, floor);
		currentChamber = cc;
		defaultHP = dhp;
		currentTile = '.';
		race = Race;
		floor.setTile(x, y, '@');
		
	}
	
	public int getCurrentChamber() 
	{
		return currentChamber;
	}
	
	public void setCurrentChamber(int currentChamber) 
	{
		this.currentChamber = currentChamber;
	}
	
	public int getdefaultHP() 
	{ 
		return defaultHP; 
	}
	
	public int attack(Enemy e) 
	{
		int dmg, temphp;
		dmg = (int) Math.ceil((100.0/(100.0 + e.getDef()))*this.getAtk());
		temphp = e.getHP() - dmg;
		e.hp = temphp;
		return dmg;
	}
	
	public String getRace() { return race; }
	
	public boolean move(char dir, char action) 
	{
		int tempx = x;
		int tempy = y;
		String direct = null;
		
	    switch (dir){
	      case '7':
	        tempx-=1;
	        tempy-=1;
	        direct = "Northwest";
	        break;
	      case '8':
	        tempx-=1;
	        direct = "North";
	        break;
	      case '9': 
	        tempy+=1;
	        tempx-=1;
	        direct = "Northeast";
	        break;
	      case '4':
	        tempy-=1;
	        direct = "West";
	        break;
	      case '6':
	        tempy+=1;
	        direct = "East";
	        break;
	      case '1':
	        tempy-=1;
	       tempx+=1;
	        direct = "Southwest";
	        break;
	      case '2':
	        tempx+=1;
	        direct = "South";
	        break;
	      case '3':
	        tempx+=1;
	        tempy+=1;
	        direct = "Southeast";
	        break;
	    }
	    
	    
	    if(action == 'm')
	    {

	        // Checks if the player interacts with a stairway.
	        if(floor.getTile(tempx, tempy) == '\\')
	          return true;
	    
	        // move the player
	        if(floor.getTile(tempx, tempy) == '.' ||
	          floor.getTile(tempx, tempy) == '+' ||
	          floor.getTile(tempx, tempy) == '#') 
	        {
	          
	          floor.setTile(x, y, currentTile);
	   
	          currentTile = floor.getTile(tempx, tempy);
	    
	          x = tempx;
	          y = tempy;
	          floor.setTile(x, y, '@');
	          floor.setAction("Player moves " + direct + ".");
	        }
	       
	        //checks if the player is interacting with a wall. 
	        else if(floor.getTile(tempx, tempy) == '-'||
	            floor.getTile(tempx, tempy) == ' ' ||
	            floor.getTile(tempx, tempy) == '|') 
	        {
	            floor.setAction("Player walks straight into a wall.");
	        }
	        
	        //checks if the player is interacting with a pile of gold.
	        else if(floor.getTile(tempx, tempy) == 'G')
	        {
	          
	          for(int i = 0; i < floor.getNumItems(); i++)
	          {
	            if((tempx == floor.getItem(i).getX()) && (tempy == floor.getItem(i).getY()))
	            {
	                
	                int value = (int)floor.getItem(i).getValue();

	                gold += value;
 
	                floor.setAction("Player moves " + direct + ".");
	                floor.concatAction(" Player picks up " + (int) value +" gold.");

	                floor.setTile(x,y, currentTile);
	                currentTile = '.';
	    
	  	      
	                // floor.getItem(i).setX(1);
	                // floor.getItem(i).setY(1);

	  		
	                x = tempx;
	                y = tempy;
	                floor.setTile(x,y, '@');
		                        
	            }
	          }
	        }
	        else
	        {
	          floor.setAction("Something is blocking Player's way.");
	        }
	              
	  }
	      //checks if the player is interacting with a potion.
	      if(action == 'u')
	      {
	          if(floor.getTile(tempx, tempy) == 'P')
	          {
	              
	              for(int i = 0;i < floor.getNumItems(); i++)
	              {
	                  if((tempx == floor.getItem(i).getX()) && (tempy == floor.getItem(i).getY()))
	                  {
	                	  int result;
	                	  
	                      //calculates the change in attribute
	                      int change = floor.getItem(i).getValue();
	                                            
	                      switch(floor.getItem(i).getType()) 
	                      {
	                              //checks if the potion affects health
	                          case 'h':
									result = hp + change;
									floor.setAction("You drank a Health Potion.");
									//checks if end result of HP exceeds the limit or 
									  //is beneath 0 and change accordingly.
									if(result > defaultHP) 
									{
										hp = defaultHP;
										break;
									}
									hp = result;
									break;                          
								  
	                              //checks if the potion affects attack
	                          case 'a':
	                              result = atk + change;
	                             
                                  floor.setAction("You drank an Attack Potion.");
                                  
	                              atk = result;
	                              break;
	                              
	                              //checks if the potion affects defense
	                          case 'd':
	                              result = def + change;
                                  floor.setAction("You drank a Defense Potion.");
	                              def = result;
	                              break;
	                      }

				            floor.setTile(tempx, tempy, '.');
	                  }
	              }
	          }
	          else
	          {
	               floor.setAction("Player sees nothing to use at that direction.");
	          }
	              
	       }
	      if(action == 'a')
	      {
	          char enemyRace = floor.getTile(tempx, tempy);
	          if(enemyRace == 'V' || enemyRace == 'W' ||
	             enemyRace == 'T' || enemyRace == 'N' ||
	             enemyRace == 'X')
	          {
	             
	              //checks which enemy is interacted
	              for(int i = 0; i < floor.getNumEnemies(); i++)
	              {
	                  if((tempx == floor.getEnemy(i).x) && (tempy == floor.getEnemy(i).y))
	                  {
	                      Enemy e = floor.getEnemy(i);
	                      //calculates the damage and the enemy's remaining HP
	                      int dmg = attack(e);
	                      int enemyHP = floor.getEnemy(i).getHP();
	                      
	                      //set the action that the player did in this turn.
	                      floor.setAction("Player deals " + dmg + " dmg to " + floor.getTile(tempx, tempy) + "("+ enemyHP +").");
	                      
	                      //checks if the enemy is dead after attack
	                      if(e.getHP()<1)
	                      {
	                          // add the action of the kill
	                          floor.concatAction(" Player kills " + enemyRace  + ".");
	                          // remove the enemy from the floor
	                          floor.setTile(tempx, tempy, '.');
	                          e.setCoord(1,1);
	                          gold += 20;
	                      }
	                             
	                  }
	              }
              }

	          else
	          {
	        	  floor.setAction("Player swipes at the air. There is nothing there to attack.");
	          }
	      }

	      
	      //returns false if the player has not interact with a stairway
	      return false;
		
	}
	

}
