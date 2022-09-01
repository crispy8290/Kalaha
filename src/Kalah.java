import java.util.Scanner;

public class Kalah
{
	private int board[];
	private int pitsNumber;
	private int seedNumber;
	private int playerTurn;
	private int playerStart;

	// Executable method
	public static void main(String[] args) 
	{
		Scanner input = new Scanner(System.in);

		System.out.print("Enter your values for p and s (separated by a space): ");

		int p = input.nextInt();
		int s = input.nextInt();

		Kalah Game = new Kalah(p, s);
	}

	// Constructor; Kalah(p, s) format: consult specifications document for more clarity
	Kalah(int p, int s)
	{
		this.pitsNumber = (p * 2) + 2;
		this.seedNumber = s;

		for (int i = 0; i < pitsNumber; i++)
		{
			if (i <= ((pitsNumber / 2) - 2)) // index range: 0 to (pitsNumber/2)-2); player1's pits
			{
				board[i] = s;
			}

			if (i >= (pitsNumber / 2) && i <= (pitsNumber - 2)) // index range: (pitsNumber / 2) to (pitsNumber - 2); player2's pits
			{
				board[i] = s;
			}
		}

		board[((pitsNumber / 2) - 1)] = 0; // index: ((pitsNumber / 2) - 1); player1's store
		board[(pitsNumber -1)] = 0; // index: (pitsNumber -1); player2's store
	}
}