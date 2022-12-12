import java.util.Scanner;

public class Player 
{
	private String playerName;
	private int playerWinCounter = 0;
	
	public Player(String name)
	{
		this.playerName = name;
	}
	
	public Player(int playerNumber, Scanner input)
	{
		System.out.print("Please enter Player "+playerNumber+" name: ");
		this.playerName = input.nextLine();
	}
	
	public String getPlayerName()
	{
		return playerName;
	}
	
	public int getPlayerWinCounter()
	{
		return playerWinCounter;
	}
	
	public void incPlayerWinCounter()
	{
		playerWinCounter++;
	}
}
