package program;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import enums.Direction;

public class Guessing {
	
	private static int num;
	private static int difficulty = 0;
	private static int attempts = 5;
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Integer[] guessed = new Integer[] {0, 0, 0, 0, 0};
	private static Direction heading = Direction.WEST;
	
	public static void run() throws IOException {
		System.out.println("Welcome to the best number guessing game in the universe!");
		promptForDiff();
	}
	
	public static void promptForDiff() throws IOException {
		do {
			System.out.println("Select a difficulty:");
			System.out.println("* Easy");
			System.out.println("* Medium");
			System.out.print("* Hard ");
			String input = reader.readLine().replace(" ", "");
			switch (input.toLowerCase()) {
			case "easy":
				difficulty = 10;
				break;
			case "medium":
				difficulty = 50;
				break;
			case "hard":
				difficulty = 100;
				break;
			default:
				System.out.println("Enter in a valid difficulty. Easy, Medium, or Hard.");
				break;
			}
		}while(difficulty == 0);
		num = createRandom();
		guess();
	}
	
	public static void guess() throws IOException {
		int userNum = 0;
		boolean valid = false;
		
		do {
			try {
				System.out.print("Guess a number between 1 and " + difficulty + ". ");
				String input = reader.readLine();
				userNum = Integer.parseInt(input);
			}catch(IOException ieo) {
				System.out.println("Wrong input");
			}catch(NumberFormatException nfe) {
				System.out.println("Please enter a vlaid number");
			}
			
			if (userNum == num) {
				outputResult(true, userNum, num);
				valid = true;
			}
			else {
				if (checkValidInput(userNum)) {
				guessed[attempts - 1] = userNum;
				attempts--;
				outputResult(false, userNum, num);
				valid = true;
				}
				else {
					valid = false;
				}
			}
		}while(!valid);
	}
	
	public static int createRandom() {
		Random rnd = new Random();
		int randomNumber = rnd.nextInt(difficulty) + 1;
		
		return randomNumber;
	}
	
	public static boolean checkValidInput(int userNum) {
		boolean valid = true;
		if (userNum > 0 && userNum <= difficulty) {
		for (int i = 0; i < 5; i++) {
			if (userNum > 0 && userNum <= difficulty) {
				if (guessed[i] == userNum) {
					System.out.println("You already guessed that number!");
					valid = false;
				}
			}
		}
		}
		else {
			System.out.println("You have to enter in a valid number between 1 and " + difficulty + ". ");
			valid = false;
		}
		if (valid) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static void outputResult(boolean correct, int userNum, int ranNum) throws IOException {
		String output;
		if (attempts > 0) {
			if (correct) {
				if (5 - attempts == 1)
					output = "You win! It only took you " + ((5 - attempts) + 1) + " attempt!";
				else
					output = "You win! It only took you " + ((5 - attempts) + 1) + " attempts!";
				System.out.println(output);
				playAgain();
			}
			else {
				if (5 - attempts == 4)
					output = "Incorrect guess! You have " + attempts+ " attempt remaining.";
				else
					output = "Incorrect guess! You have " + attempts+ " attempts remaining.";
				System.out.println(output);
				if (userNum > ranNum) {
					System.out.println("The number is lower than what you guessed.");
				}
				else {
					System.out.println("The number is higher than what you guessed.");
				}
				System.out.println("");
				guess();
			}
		}
		else {
			output =  "You lose, the number was " + num + "... I guess 5 attempts wasn't enough for you.";
			System.out.println(output);
			playAgain();
		}
	}
	
	public static void playAgain() throws IOException {
		String input = "";
		do {
		System.out.print("Do you wanna play again? (yes or no)");
		input = reader.readLine();
		
		switch (input.toLowerCase()) {
		case "yes":
			difficulty = 0;
			attempts = 5;
			guessed = new Integer[]{0, 0, 0, 0, 0};
			promptForDiff();
			break;
		case "no":
			System.out.println("Closing application...");
			break;
		default:
			input = "";
			System.out.println("You have to enter yes or no.");
			break;
		}
		}while(input.equalsIgnoreCase(""));
	}
}
