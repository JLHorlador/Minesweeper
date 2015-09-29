import java.util.Scanner;

public class MineSweeper {
	private char[][] minefield;
	private int spaceLeft = 80;
	
	public MineSweeper()
	{
		minefield = new char[10][10];	//Sets up a 10 x 10 grid
		setupField();
	}
	
	public static int checkInput(String stringValue)
	{
		Scanner input = new Scanner(System.in);
		boolean intCheck = true;
		
		for (int i = 0; i < stringValue.length(); i++)
		{
			if ((int)stringValue.charAt(i) >= 48 && (int)stringValue.charAt(i) <= 57);	//Checks if the character is between 0 - 9 in ascii
			else
				intCheck = false;
		}
		
		if (intCheck == false)
		{
			System.out.println("Enter a numerical value: ");	//If it is not a numerical value, prompts the user to input a numerical value
			return checkInput(input.next());
		}
		else
			return Integer.valueOf(stringValue);
	}
	
	public static int checkRange(int value, int min, int max)
	{
		Scanner input = new Scanner(System.in);
		while (value < min || value > max)
		{
			System.out.println("Enter an integer within the range: ");	//If the input goes beyond the array's size, prompts the user to input a new value
			value = checkInput(input.next());
		}
		return value;
	}
	
	public void setupField()
	{
		int numberOfMines = 0;
		while (numberOfMines < 20)
		{
			int xValue = (int)(Math.random() * 10);	//Randomly generates a number between 0 and 9
			int yValue = (int)(Math.random() * 10);
			if (minefield[xValue][yValue] != 'X')	//These random values will determine where the mines will go
			{
				minefield[xValue][yValue] = 'X';
				numberOfMines++;
			}
		}
		
		for (int y = 0; y < 10; y++)
		{
			for (int x = 0; x < 10; x++)
			{
				if (minefield[x][y] != 'X')
				{
					minefield[x][y] = '*';	//If the chosen position in the array is not an 'X', then it is a '*'
				}
			}
		}
	}
	
	public int playerMove(int mx, int my)
	{
		int numberOfMines = 0;

		if (minefield[mx][my] == 'X')	//When the player encounters a bomb
		{
			minefield[mx][my] = 'M';	//Replaces the mine with an 'M', indicating the mine's position
			return 1;
		}
		else
		{
			for (int y = -1; y <= 1; y++)
			{
				for (int x = -1; x <= 1; x++)
				{
					if (mx + x < 0 || mx + x > 9 || my + y < 0 || my + y > 9)	//Prevents the program from going beyond the array when checking the area
						numberOfMines += 0;
					else if (minefield[mx + x][my + y] == 'X' && (mx + x >= 0 && mx + x <= 9) && (my + y >= 0 && my + y <= 9))
						numberOfMines++;
				}
			}
		}
		
		if (minefield[mx][my] == '*')
			spaceLeft--;
		
		minefield[mx][my] = Character.forDigit(numberOfMines, 10);	//Converts a numerical character into an int
		
		if (spaceLeft == 0)
			return 2;
		else
			return 0;	
	}

	public void displayField()
	{
		for (int y = 0; y < 10; y++)
		{
			for (int x = 0; x < 10; x++)
			{
				if (minefield[x][y] == 'X')
				{
					System.out.print('*' + " "); 	//Makes mines have the appearance of a '*'
				}
				else
					System.out.print(minefield[x][y] + " ");
			}	
		System.out.print("\n");
		}
	}

	public static void main(String[] args)
	{
		MineSweeper game = new MineSweeper();

		Scanner input = new Scanner(System.in);
		int x, y, result;
		
		do
		{
			game.displayField();
			
			System.out.print("What column: ");
			x = checkRange(checkInput(input.next()), 0, 9);
			System.out.print("What row: ");
			y = checkRange(checkInput(input.next()), 0, 9);
			
			result = game.playerMove(x, y);
		} while(result == 0);
		
		if (result == 1)
		{
			game.displayField();
			System.out.println("You found a mine. Game Over!");
		}
		else if (result == 2)
		{
			game.displayField();
			System.out.println("You win!");
		}
		
		input.close();
	}
}
