import java.util.InputMismatchException;
import java.util.Scanner;

public class KalahGame 
{
	
	
	private int[] board;
	private int pits;
	private int seeds;
	private int player1StoreIndex;
	private int player2StoreIndex;
	private int roundNumber = 0;
	private int playerTurn = 1;
	private int winner;
	private int curr;
	private boolean gameEnd;
	
	boolean getGameEnd() { return gameEnd; }
	
	public void startNewGame(Player player1, Player player2, Scanner input)
	{
		gameEnd = false;
		roundNumber++;
	      
	    boolean pitsValid = false;
	    while(pitsValid == false)
	    {
	    	System.out.print("Please enter the total number of pits: ");
	    	try
	    	{
	    		pits = input.nextInt()+2;
	    		if(pits >= 2 && pits <= 20 && pits % 2 == 0)
	    		{
	    			pitsValid = true;
	    		}
	    		else
	    		{
	    			System.out.println("Error, please enter an even number between 2 and 20");
	    		}
	    	}
	    	catch(InputMismatchException e)
	    	{
	    		System.out.println("Error, please enter an even number between 2 and 20");
	    		input.next();
	    	}
	    }
	    
	    boolean seedsValid = false;
	    while(seedsValid == false)
	    {
	    	System.out.print("Please enter the total number of seeds: ");
	    	try
	    	{
	    		seeds = input.nextInt();
	    		if(seeds >= 1 && seeds <= 12) //# of seeds allowed is 1-12
	    		{
	    			seedsValid = true;
	    		}
	    		else
	    		{
	    			System.out.println("Error, please enter an integer between 1 and 12");
	    		}
	    	}
	    	catch(InputMismatchException e)
	    	{
	    		System.out.println("Error, please enter an integer between 1 and 12");
	    		input.next();
	    	}
	    }
	    
		makeBoard(pits, seeds);
		
		System.out.println("\nPlayer 1 is: "+player1.getPlayerName());
		System.out.println("Player 2 is: "+player2.getPlayerName()+"\n");
		System.out.println("Round Number: "+roundNumber);
	}
	
	public void makeBoard(int pits, int seeds)
	{
		board = new int [pits];
		for(int i=0;i<pits;i++)
		{
			if(i == 0) //player 2's pit
			{
				board[i] = 0;
				player2StoreIndex = 0;
			}
			else if(i == pits/2) //player 1's pit
			{
				board[i] = 0;
				player1StoreIndex = pits/2;
			}
			else
			{
				board[i] = seeds;
			}
		}
	}
	
	public void printBoard()
	{
		System.out.print("\nCurrent board: \n      ");
		for(int i=pits-1;i>(pits/2);i--) //prints player 2's pits
		{
			System.out.print("["+board[i]+"]");
		}
		System.out.print("\nP2 ["+board[player2StoreIndex]+"]"); //prints player 1's store
		for(int i=0;i<((pits-1)/2);i++)
		{
			System.out.print("   ");
		}
		System.out.print("["+board[player1StoreIndex]+"] P1\n      "); //prints player 2's store
		for(int i=1;i<pits/2;i++)
		{
			System.out.print("["+board[i]+"]"); //prints player 1's pits
		}
		System.out.println("\n");
	}
	
	public int obtainValidUserInput(Player player1, Player player2, Scanner input)
	{
		int startPoint = 0; 
		
		if(playerTurn == 1)
		{
			boolean validMove = false;
		    while(validMove == false)
		    {
		    	if(player1.getPlayerName().charAt(player1.getPlayerName().length()-1) == 's') //name ends with 's'
		    	{
		    		System.out.print("Please enter "+player1.getPlayerName()+"' move: ");
		    	}
		    	else
		    	{
		    		System.out.print("Please enter "+player1.getPlayerName()+"'s move: "); //name does not end with 's'
		    	}
		    	try
		    	{
		    		startPoint = input.nextInt();
		    		if(startPoint < pits/2 && startPoint > 0) //validate that pit is owned by player 1
		    		{
		    			validMove = true;
		    		}
		    		else
		    		{
		    			System.out.println("Error, please enter your own pit number.");
		    		}
		    	}
		    	catch(InputMismatchException e)
		    	{
		    		System.out.println("Error, please enter your own pit number.");
		    		input.next();
		    	}
		    }
		}
		else //playerTurn == 2
	    {
			boolean validMove = false;
			while(validMove == false)
		    {
		    	if(player2.getPlayerName().charAt(player2.getPlayerName().length()-1) == 's')
		    	{
		    		System.out.print("Please enter "+player2.getPlayerName()+"' move: ");
		    	}
		    	else
		    	{
		    		System.out.print("Please enter "+player2.getPlayerName()+"'s move: ");
		    	}
		    	try
		    	{
		    		startPoint = input.nextInt();
		    		if(startPoint > pits/2 && startPoint < pits) //validate that pit is owned by player 2
		    		{
		    			validMove = true;
		    		}
		    		else
		    		{
		    			System.out.println("Error, please enter your own pit number.");
		    		}
		    	}
		    	catch(InputMismatchException e)
		    	{
		    		System.out.println("Error, please enter your own pit number.");
		    		input.next();
		    	}
		    }
	    }
		
		return startPoint;
	}
	
	public int playerMove(Player player1, Player player2, Scanner input) //playerMove with user input
	{
		int startPoint = obtainValidUserInput(player1, player2, input); //obtains valid user input
		curr = startPoint; //keeps track of where the index is at
		int seedDrop = board[startPoint]; //stores number of seeds to be moved
		board[startPoint] = 0; //sets starting point to 0
		
		for(int i=seedDrop;i>0;i--) //moves until there are no more seeds left to be dropped
		{	
			int next = curr+1; //keeps track of next pit index
			
			if(curr % (pits-1) == 0 && curr != 0) //reached end of array and makes sure curr != 0 (curr is only 0 when pointer resets to beginning of array)
			{
				curr = -1; 
				next = 0;
			}
			
			if(playerTurn == 1 && next == player2StoreIndex || playerTurn == 2 && next == player1StoreIndex) //if opponent's store, skip seed drop **multiply by loops?**
			{
				i++; //no seed is dropped, counter restores by one
				curr++; //moves to next index
				continue; //break out of iteration
			}
			else //drop seed
			{
				board[next] = board[next]+1; //drops a seed
				curr++; //moves to next index
			}
		}
		return startPoint;
	}

	public boolean promptSteal(Player player1, Player player2, Scanner input)
	{
		if(roundNumber % 2 == 1) //if player 1 had the first move
		{
			System.out.print(player2.getPlayerName()+" to steal move? (Y/N): "); //prompt player 2 to steal move
		}
		else //player 2 had the first move
		{
			System.out.print(player1.getPlayerName()+" to steal move? (Y/N): "); //prompt player 1 to steal move
		}
		
		String ans = input.next(); //yes or no (if opponent wants to steal move)
		while(!ans.equalsIgnoreCase("Y") && !ans.equalsIgnoreCase("N")) //if user input is invalid
		{
			System.out.println("Error, please enter \"Y\" or \"N\": ");
			ans = input.next();
		}
		
		if(ans.equalsIgnoreCase("Y")) //opponent wishes to steal turn
		{
			return true;
		}
		else //opponent does not wish to steal turn
		{
			return false;
		}
	}
	
	public void stealMove(Player player1, Player player2, Scanner input, int startPoint) 
	{
		if(roundNumber % 2 == 1) //if player 1 had the first move
		{
			System.out.println("\n"+player2.getPlayerName()+" stole the first move!");
		}
		else //if player 2 had the first move
		{
			System.out.println("\n"+player1.getPlayerName()+" stole the first move!");
		}
		
		makeBoard(pits, seeds); //restart the board
		
		int newStartPoint;
		if(roundNumber % 2 == 1) //if player 1 started
		{
			newStartPoint = (pits/2) + startPoint; //initialize new start point for player 2
		}
		else //player 2 started
		{
			newStartPoint = startPoint - (pits/2); //initialize new start point for player 1
		}
		int curr = newStartPoint; //keep track of new starting point index
		int seedDrop = board[newStartPoint]; //obtain number of seeds to be dropped
		board[newStartPoint] = 0; //set starting point to 0 seeds
		
		for(int i=seedDrop;i>0;i--) //moves until there are no more seeds left to be dropped
		{	
			int next = curr+1; //keeps track of next pit index
			
			if(curr % (pits-1) == 0 && curr != 0) //reached end of array and makes sure curr != 0 (curr is only 0 when pointer resets to beginning of array)
			{
				curr = -1; 
				next = 0;
			}
			
			if(playerTurn == 1 && next == player2StoreIndex || playerTurn == 2 && next == player1StoreIndex) //if opponent's store, skip seed drop **multiply by loops?**
			{
				i++; //no seed is dropped, counter restores by one
				curr++; //moves to next index
				continue; //break out of iteration
			}
			else //drop seed
			{
				board[next] = board[next]+1; //drops a seed
				curr++; //moves to next index
			}
		}
	}
	
	public void checkRepeatTurn(Player player1, Player player2, Scanner input) //checks to see if sowing ends in own store
	{
		if(gameEnd == false) //if game has not already ended, check for repeat turn
		{
			if(playerTurn == 1 && curr == player1StoreIndex || playerTurn == 2 && curr == player2StoreIndex) //player ended turn by dropping seed in own store
			{
				System.out.println("Go again!");
				playerTurnSwitch(); //change player turn so playerTurnSwitch() from main will change it back 
			}
		}
	}
	
	public void checkStealSeeds(Player player1, Player player2, Scanner input) //checks to see if sowing ends on own empty pit, if yes last seed + opposing pit seeds is all deposited in store
	{
		if(gameEnd == false) //if game has not already ended, check for stealing seeds
		{
			int temp, steal;
			
			if(playerTurn == 1 && curr > 0 && curr < pits/2 && board[curr] == 1) //player 1's last seed drops on own empty pit
			{
				System.out.println("Player 1's last seed is dropped in their own empty pit. Player 1 steals player 2's seeds in the opposing pit!");
				board[curr] = 0; //last pit set to 0 (that last seed gets added to player 1's store)
				temp = (pits/2)-curr; //distance from curr to pits/2
				steal = board[(pits/2)+temp]; //steal distance from pits/2 in P2's side
				board[(pits/2)+temp] = 0; //set p2 pit where seeds were stolen to 0 seeds
				board[player1StoreIndex] += steal+1; //add all seeds to P2's store
				printBoard();
			}
			else if(playerTurn == 2 && curr < pits && curr > pits/2 && board[curr] == 1) //player 2's last seed drops on own empty pit
			{
				System.out.println("Player 2's last seed is dropped in their own empty pit. Player 2 steals player 1's seeds in the opposing pit!");
				board[curr] = 0; //last pit set to 0 (that last seed gets added to player 2's store)
				temp = curr-(pits/2); //distance from curr to pits/2
				steal = board[(pits/2)-temp]; //steal distance from pits/2 in P1's side
				board[(pits/2)-temp] = 0; //set p1 pit where seeds were stolen to 0 seeds
				board[player2StoreIndex] += steal+1; //add all seeds to P2's store
				printBoard();
			}
		}
	}
	
	public void playerTurnSwitch()
	{
		if(playerTurn == 1)
		{
			playerTurn = 2;
		}
		else //playerTurn == 2
		{
			playerTurn = 1;
		}
	}
	
	public void gameEndCheck(Player player1, Player player2, Scanner input) //if win condition is met, initialize/replace winner value
	{
		//condition 1 for winning the game: either player holds the majority of seeds in their store
		if(board[player1StoreIndex] > (((pits-2) * seeds) / 2)) //if player 1 has majority of seeds, player 1 wins
		{
			winner = 1;
			gameEnd = true;
			System.out.println("Player 1 wins by having the majority of total seeds!");
			return;
		}
		else if(board[player2StoreIndex] > (((pits-2) * seeds) / 2)) //if player 2 has majority of seeds, player 2 wins
		{
			winner = 2;
			gameEnd = true;
			System.out.println("Player 2 wins by having the majority of total seeds!");
			return;
		}
		
		//condition 2 for winning the game: either player's pits are all empty
		int pitsPerPlayer = (pits-2) / 2; //number of pits per player
		int p1EmptyPitCounter = 0; //number of player 1 pits that are empty
		int p2EmptyPitCounter = 0; //number of player 2 pits that are empty
		
		if(playerTurn == 1)
		{
			for(int i=0;i<pitsPerPlayer;i++) 
			{
				if(board[i+1] != 0) //if any of player 1's pits are not empty, exit loop
				{
					break; 
				}
				p1EmptyPitCounter++; //increment number of empty pits counted
			}
		}
		else //playerTurn == 2
		{
			for(int i=0;i<pitsPerPlayer;i++) 
			{
				if(board[pits-1-i] != 0) //if any of player 1's pits are not empty, exit loop
				{
					break; 
				}
				p2EmptyPitCounter++; //increment number of empty pits counted
			}
		}
		
		int temp;
		
		if(p1EmptyPitCounter == pitsPerPlayer) //all of player 1's pits are empty
		{
			System.out.println("Player 1 has no more seeds left in their pits.\nPlayer 2 takes all of the seeds in their own pits into their store");
			
			for(int i=0;i<pitsPerPlayer;i++) //player 2 takes all of their own seeds into their own store
			{
				temp = board[pits-1-i];
				board[pits-1-i] = 0;
				board[player2StoreIndex] += temp;
			}
			
			printBoard(); //display new board
			
			//determine winner
			if(board[player1StoreIndex] > board[player2StoreIndex])
			{
				winner = 1;
				gameEnd = true;
				System.out.println("\nPlayer 1 wins by having the majority of total seeds!");
				return;
			}
			else if(board[player2StoreIndex] > board[player1StoreIndex])
			{
				winner = 2;
				gameEnd = true;
				System.out.println("\nPlayer 2 wins by having the majority of total seeds!");
				return;
			}
			else //tie
			{
				winner = 0;
				gameEnd = true;
				System.out.println("\nBoth players have the same number of seeds in their store. Game tied!");
				return;
			}
		}
		else if(p2EmptyPitCounter == pitsPerPlayer) //all of player 2's pits are empty
		{
			System.out.println("Player 2 has no more seeds left in their pits.\nPlayer 1 takes all of the seeds in their own pits into their store");
			
			for(int i=0;i<pitsPerPlayer;i++) //player 1 takes all of their own seeds into their own store
			{
				temp = board[i+1];
				board[i+1] = 0;
				board[player1StoreIndex] += temp;
			}
			
			printBoard();

			//determine winner
			if(board[player1StoreIndex] > board[player2StoreIndex])
			{
				winner = 1;
				gameEnd = true;
				System.out.println("\nPlayer 1 wins by having the majority of total seeds!");
				return;
			}
			else if(board[player2StoreIndex] > board[player1StoreIndex])
			{
				winner = 2;
				gameEnd = true;
				System.out.println("\nPlayer 2 wins by having the majority of total seeds!");
				return;
			}
			else //tie
			{
				winner = 0;
				gameEnd = true;
				System.out.println("\nBoth players have the same number of seeds in their store. Game tied!");
				return;
			}
		}
	}
	
	public void displayEndGame(Player player1, Player player2, Scanner input)
	{
		if(winner == 1) //player 1 won
		{
			player1.incPlayerWinCounter();
		}
		else if(winner == 2) //player 2 won
		{
			player2.incPlayerWinCounter();
		} //if winner == 0, no counters are incremented
		
		System.out.println("\n"+player1.getPlayerName()+" win counter: "+player1.getPlayerWinCounter());
		System.out.println(player2.getPlayerName()+" win counter: "+player2.getPlayerWinCounter()+"\n");
		
		roundNumber = roundNumber++;
	}
	
	public boolean playAgain(Player player1, Player player2, Scanner input)
	{
		System.out.print("Would you like to play again? (Y/N): ");
		String ans = input.next();
		while(!ans.equalsIgnoreCase("Y") && !ans.equalsIgnoreCase("N")) //if incorrect input is entered
		{
			System.out.print("\nError, please enter \"Y\" or \"N\": ");
			ans = input.next();
		}
		
		if(ans.equalsIgnoreCase("Y")) //if player wishes to play again
		{
			if(roundNumber % 2 == 1) //if player 1 started last round
			{
				playerTurn = 2; //player 2 starts new round
			}
			else
			{
				playerTurn = 1; //player 1 starts new round
			}
			
			return true;
		}
		else //user enters "n" or "N"
		{
			return false;
		}
	}
}
