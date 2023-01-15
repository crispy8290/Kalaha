import java.util.Scanner;

public class Main 
{
	public static void main(String[] args)
	{
		KalahGame kalah = new KalahGame();
		Scanner input = new Scanner(System.in);
		Player player1 = new Player(1, input);
		Player player2 = new Player(2, input);
		
		do
		{
			kalah.startNewGame(player1, player2, input); //start new game
			kalah.printBoard();
			int startPoint = kalah.playerMove(player1, player2, input); //starting player makes first move
			kalah.printBoard();
			kalah.playerTurnSwitch(); //switch turns
	
			boolean steal = kalah.promptSteal(player1, player2, input); //prompt non-starting player to steal first move
			if(steal == true) //if non-starting player decides to steal
			{
				kalah.stealMove(player1, player2, input, startPoint); //perform steal
				kalah.printBoard();
				kalah.gameEndCheck(player1, player2, input);
				kalah.checkRepeatTurn(player1, player2, input); 
				kalah.checkStealSeeds(player1, player2, input); 
				kalah.playerTurnSwitch(); //switch turns
			}
			kalah.gameEndCheck(player1, player2, input);
			kalah.checkRepeatTurn(player1, player2, input); 
			
			//while game is not over, perform regular moves until win condition is met
			while(kalah.getGameEnd() == false) 
			{
				kalah.playerMove(player1, player2, input);
				kalah.printBoard();
				kalah.gameEndCheck(player1, player2, input); 
				kalah.checkRepeatTurn(player1, player2, input); 
				kalah.checkStealSeeds(player1, player2, input); 
				kalah.playerTurnSwitch();
			}
			kalah.displayEndGame(player1, player2, input);
			
		} while(kalah.playAgain(player1, player2, input) == true); //while players wish to start new game
		
		input.close();
	}
}
