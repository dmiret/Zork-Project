package com.bayviewglen.zork;

import java.util.Scanner;

import com.bayviewglen.zork.inventory.Inventory;
import com.bayviewglen.zork.tool.Tool;

public class DialogueLevel3 {

		static Scanner keyboard = new Scanner(System.in);
		static Thread thread = new Thread();
		private int counter = 0;
		
	//level 3 Intro
		public static void Level3Intro() throws InterruptedException{
			System.out.println();
			System.out.println("LEVEL 3\n\n");
			System.out.println("You wake up to see the sunlight shinning directly in your face.");
			System.out.println("it lokes to be around noon in this world!");
			System.out.println("You Stand up to see that your in a deep, but very wide canyon that almost /n Looks unaturel");
			thread.sleep(1500);
			System.out.println("After a bit of thought you decide that the best option is to continue forward /n To find the next key.");
		}
//Room 7
		public static void Room7Msg() throws InterruptedException{
			System.out.println();
			System.out.printf("As you get to the edge of chasm you look up to see a crude stairway cut/ Into the Stone");
			System.out.println("You can either climb up them to see whats up top, or continue on adventuring");
		}
//Room 8
		public static void Room8Msg() throws InterruptedException{
			System.out.println();
			System.out.println("You look forward to see a long bridge carved of rock and stone, as you look");
			System.out.println("You see a golem on the other side blocking the entrance to the next room");
			System.out.println("However it doesn't stir as you look at it, and simply remains as stone");
		}
//Room 9
		public static void Room9Msg() throws InterruptedException{
			System.out.println();
			System.out.printf("You come to the edge of cliff that seems to climb downwards,/n there is no clear cut path, but it looks like you can get down if you try.");
			System.out.println("Your options now are to continue exploring or risk the climb down");
		}
//Room 15
		public static void Room15Msg() throws InterruptedException{
		System.out.println();
		System.out.printf("You have come to the northern corner of the room, and you see a crimson red dragon/n sleeping in the corner");
		System.out.println("It looks rather peaceful but you are unsure if you should wake it");
		thread.sleep(1500);
		System.out.println("Your options right now are:");
		System.out.printf("Wake the Dragon peacfully/nLeave the dragon alone/nAttack the Dragon");
		thread.sleep(1500);
		System.out.println("What do you do?");
		}
//Room 15 Reaction
		public static boolean Room15React(boolean finished) throws InterruptedException{
			String response = keyboard.nextLine();
			
			if(response.equalsIgnoreCase("Wake the Dragon peacfully")){
				System.out.println();
				System.out.println("You slowly creep over to the dragon and tap him on the head");
				System.out.printf("His eyes fling open and turn to gase at you, as you look/n you suddenly blank out");
				System.out.println("When you wake up you find yourself in a classroom sitting next to your bestfriend");
				System.out.println("You two are scanning a book about the development of lizards and serpents from aquatic animals");
				thread.sleep(1500);
				System.out.println("But suddenly theres a flash and the entire school is burning and your friend is just standing there");
				System.out.println("He is looking at you with fear and disbeleif, but all you see if the fire reflected in his eyes.");
				System.out.println("Then suddenly all is black");
				thread.sleep(1500);
				System.out.println("You wake up back on the plateau, but the dragon is gone, and it is now late in the afternoon");
				finished = false;
				return finished;
				}else if(response.equalsIgnoreCase("Leave the dragon alone")){
					System.out.println();
					System.out.println("You just walk away and continue on");
					finished = false;
					return finished;
				}else if(response.equalsIgnoreCase("Attack the Dragon")){
					System.out.println();
					System.out.println("You leap on the Dragon's head and start stabbing at his eye with your dagger");
					System.out.println("Your knife Snaps and you fall off the dragon as it raises its head");
					thread.sleep(1500);
					System.out.println("It looks at you in disgust and causally bends down and eats you");
					
					finished = true;
					return finished;
				}
			return false;
		}
//Room 20
		public static void Room20Msg() throws InterruptedException{
			
		}
//Room 20 React
		public static void Room20React() throws InterruptedException{
			
		}
		
		public static void Room22Msg() throws InterruptedException{
			
			
		}
		public static void level2Keypad() throws InterruptedException{
			System.out.println("\nDo you want to \'enter a code\'?\nOption 1: Enter \'yes\'.\nOption 2: Enter \'no\'.");
			System.out.print(">");
			
			String temp = " ";
			boolean validAnswer = false;
			while (!validAnswer){
				temp = keyboard.nextLine().toUpperCase();
				if (temp.equals("YES")){
					validAnswer = true;
				} else if (temp.equals("NO")){
					System.out.println("Okaaay, have fun waiting outside. Don\'t you have any curiosity? Don\'t you want to taste glory? Just say \'yes\'.");
					thread.sleep(2500);
					System.out.println("Actually, you know what? Too bad. Just a heads up, you actually have to enter a code. The only exit to this world is inside like it or not.");
					System.out.print(">");
				} else if (temp.equals("HELP")){
					System.out.println("\nType which option you are thinking.");
					System.out.print(">");
				} else {
					System.out.println("That option doesn't appear in your thoughts...");
					System.out.print(">");
				}
			}
			
			if (temp.equals("YES")){
				System.out.println("\n");
				System.out.println("Great! (*Hint: Hmmm... where there any clues along the way (MAYBE DOWN)?)");
				System.out.println("Like perhaps, there were some clues that made you go \'hmmmm....\'?\n"); // Code is "4739"
			}
			
			// check if player enters in correct code for keypad to open cave entrance
			level2CodeCheck();
		}
		
		// Level 2: Keypad Code Check (Adapted Hangman Game for this Keypad Lock :) - CM)
		public static void level2CodeCheck(){
			
			final String VALID_CODE_CHARACTERS = "123456789";
			boolean codeIsSolved = false; 				// for when code is solved by player
			String code = "4739";						// the ANSWER for the cave entrance (the code word)
			String encryptedCode = ""; 					// stores the characters the player has guessed, displayed for player to see updates 
			String guessedCode = ""; 				// the character that the player guessed
			String usedChars = ""; 						// stores all characters that have been guessed
			String characterList = "1 2 3 4 5 6 7 8 9";	// the displayed characters which the player may choose from 
			
			while (!codeIsSolved){
				// to update and show encrypted message
				for (int b = 0; b < encryptedCode.length(); b++){ 
					if (usedChars.indexOf(encryptedCode.charAt(b)) != -1){
						encryptedCode += usedChars.charAt(usedChars.indexOf(code.charAt(b))) + " ";
						//codedMessage += phrase.charAt(b); // simpler version of above line
					} else if (VALID_CODE_CHARACTERS.indexOf(encryptedCode.charAt(b)) != -1){
						encryptedCode += "_ ";
					} else {
						encryptedCode += "/ ";
					}
				}
				
				System.out.println(encryptedCode);
				
				// to display unused characters
				System.out.println("Unused Characters: ");
				System.out.println(characterList);
						
				System.out.println("Please enter a single character: "); 
											
				guessedCode = "";								
				boolean validGuessedNumber = false; // to check if guessedCharacter is a single character
				while (!validGuessedNumber){
					validGuessedNumber = true;
					guessedCode = keyboard.nextLine().toUpperCase(); 
					for (int i = 0; i < guessedCode.length() && validGuessedNumber; i++){
						for(int k =0;k<guessedCode.length()&&validGuessedNumber;k++){
						if (guessedCode.length() != 4 && VALID_CODE_CHARACTERS.indexOf(guessedCode.charAt(k)) == -1){ 
							validGuessedNumber = false; 
							System.out.println("Please enter the code (only numbers,don't use spaces):");
						}
						} if (guessedCode.length() != 4){ 
							validGuessedNumber = false; 
							System.out.println("Please enter a FOUR DIGIT code (don't use spaces):");
						} 
					}
				}
				// to check if guessed character is in message
				
					if (code.equals(guessedCode)){
						System.out.println("The Golem Says: Congradulations you have guessed the code you may now proceed ");
						codeIsSolved = true;
						
					} else {
						System.out.println("The Golem Says: That is not the code.");
						codeIsSolved = false;
					}
			}
			if(codeIsSolved = true){
				System.out.println("Congragulations the Door Has opened and now you can continue into the chasm");
			}
		}
		public static void Room27Msg() throws InterruptedException{
			
		}
		public static void Room31Msg() throws InterruptedException{
			
		}
		public static void Room34Msg() throws InterruptedException{
			
		}
		public static void Room41Msg() throws InterruptedException{
			
		}
		public static void Room47Msg() throws InterruptedException{
			
		}
		public static void Room50Msg() throws InterruptedException{
			
		}
		public static void Room59Msg() throws InterruptedException{
			
		}
		public static void level3Ending(int currentLevel, Tool ThirdKey) throws InterruptedException{
			thread.sleep(1000);
			System.out.println();
			System.out.println("");
			Inventory.add(ThirdKey);
			thread.sleep(5000);
			System.out.println("You feel a warmth come over you and then you black out... ");
			System.out.println();
			System.out.println("END OF LEVEL 3: EXIT THE ROCK WORLD");
		}
		
		// loading Method: prints the "Loading . . . . " message
		private static void loading() throws InterruptedException {
			System.out.println();
			thread.sleep(1500); // delays code
			System.out.print("Loading");
			
			for (int i = 0; i < 4; i++) {
				System.out.print("   .");
				thread.sleep(1500);
			}
		}
}