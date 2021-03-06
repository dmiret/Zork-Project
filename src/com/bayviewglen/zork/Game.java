package com.bayviewglen.zork;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.bayviewglen.zork.characters.Grunt;
import com.bayviewglen.zork.inventory.Inventory;
import com.bayviewglen.zork.item.Food;
import com.bayviewglen.zork.item.Item;
import com.bayviewglen.zork.tool.Key;
import com.bayviewglen.zork.tool.Tool;

/**
 * Class Game - the main class of the "Zork" game.
 *
 * Author: Michael Kolling Version: 1.1 Date: March 2000
 * 
 * This class is the main class of the "Zork" application. Zork is a very
 * simple, text based adventure game. Users can walk around some scenery. That's
 * all. It should really be extended to make it more interesting!
 * 
 * To play this game, create an instance of this class and call the "play"
 * routine.
 * 
 * This main class creates and initialises all the others: it creates all rooms,
 * creates the parser and starts the game. It also evaluates the commands that
 * the parser returns.
 */

class Game {
	
	// currentLevel controls the level that the game is on. You may manually change if you would like, otherwise start with level 1
	public int currentLevel = 2;

	// Level 1 Items
	Tool firstKey = new Tool("1: Air Key");
	Food bean = new Food("bean");
	
	// Level 2 Items
	Tool secondKey = new Tool("2: Sea Key");
	Tool stone = new Tool("stone");
	Tool bubble = new Tool("bubble");
	Tool knife = new Tool("knife");	

	// Level 3 Items
	Tool thirdKey = new Tool("3: Earth Key");
	
	// Level 4 Items
	Tool forthKey = new Tool("4: Fire Key");
	
	static Thread thread = new Thread(); // thread for delays

	private Parser parser;
	private Room currentRoom;

	/*
	 * This is a MASTER object that contains all of the rooms and is easily
	 * accessible. The key will be the name of the room -> no spaces (Use all
	 * caps and underscore -> Great Room would have a key of GREAT_ROOM In a
	 * hashmap keys are case sensitive. masterRoomMap.get("GREAT_ROOM") will
	 * return the Room Object that is the Great Room (assuming you have one).
	 */
	private HashMap<String, Room> masterRoomMap;

	private void initRooms(String fileName) throws Exception {
		masterRoomMap = new HashMap<String, Room>();
		Scanner roomScanner;
		try {
			HashMap<String, HashMap<String, String>> exits = new HashMap<String, HashMap<String, String>>();
			roomScanner = new Scanner(new File(fileName));
			while (roomScanner.hasNext()) {
				Room room = new Room();

				// Read the Name
				String roomName = roomScanner.nextLine();
				room.setRoomName(roomName.split(":")[1].trim());

				// Read The Locks
				String roomLock = roomScanner.nextLine();

				// Read the Description
				String roomDescription = roomScanner.nextLine();
				room.setDescription(roomDescription.split(":")[1].replaceAll("<br>", "\n").trim());

				// Read the Exits
				String roomExits = roomScanner.nextLine();

				// An array of strings in the format E-RoomName
				String[] rooms = roomExits.split(":")[1].split(",");
				HashMap<String, String> temp = new HashMap<String, String>();
				for (String s : rooms) {
					temp.put(s.split("-")[0].trim(), s.split("-")[1]);
				}

				exits.put(roomName.substring(10).trim().toUpperCase().replaceAll(" ", "_"), temp);

				// Reads the Items String roomItems = roomScanner.nextLine();

				// Reads the Enemies String
				/*
				 * String[] roomEnemies =
				 * roomScanner.nextLine().trim().split(":")[1].split(","); int
				 * counter = 0; if (!roomEnemies[0].trim().equals("none")) { for
				 * (String s : roomEnemies) { String currentEnemyType =
				 * roomEnemies[counter].trim().split("-")[0]; String inRange =
				 * roomEnemies[counter].trim().split("-")[1]; int hitsToKill =
				 * Integer.parseInt(roomEnemies[counter].trim().split("-")[2]);
				 * if(currentEnemyType.equals("Grunt")){ room.addRoomEnemy(new
				 * Grunt(hitsToKill,"Grunt",inRange.equals("C"))); }else
				 * if(currentEnemyType.equals("Grunt")){ room.addRoomEnemy(new
				 * Grunt(hitsToKill,"Grunt",inRange.equals("C"))); }else
				 * if(currentEnemyType.equals("Grunt")){ room.addRoomEnemy(new
				 * Grunt(hitsToKill,"Grunt",inRange.equals("C"))); }else
				 * if(currentEnemyType.equals("Grunt")){ room.addRoomEnemy(new
				 * Grunt(hitsToKill,"Grunt",inRange.equals("C"))); }else
				 * if(currentEnemyType.equals("Grunt")){ room.addRoomEnemy(new
				 * Grunt(hitsToKill,"Grunt",inRange.equals("C"))); }else
				 * if(currentEnemyType.equals("Grunt")){ room.addRoomEnemy(new
				 * Grunt(hitsToKill,"Grunt",inRange.equals("C"))); } counter++;
				 * 
				 * } }
				 */

				// This puts the room we created (Without the exits in the
				// masterMap)
				masterRoomMap.put(roomName.toUpperCase().substring(10).trim().replaceAll(" ", "_"), room);

				// Now we better set the exits.
			}

			for (String key : masterRoomMap.keySet()) {
				Room roomTemp = masterRoomMap.get(key);
				HashMap<String, String> tempExits = exits.get(key);
				for (String s : tempExits.keySet()) {
					// s = direction
					// value is the room.

					String roomName2 = tempExits.get(s.trim());
					Room exitRoom = masterRoomMap.get(roomName2.toUpperCase().replaceAll(" ", "_"));
					roomTemp.setExit(s.trim().charAt(0), exitRoom);

				}
			}

			roomScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	
/*	public Game() { 
		
	}
*/
	// resetLevel method for when the player wins a level
	public void resetLevel(int currentLevel) throws Exception {
		if (currentLevel == 1) {
			DialogueLevel1.level1Intro();
			initRooms("data/levels/level1.dat");
			currentRoom = masterRoomMap.get("ROOM_13");
			if (currentRoom.equals(masterRoomMap.get("ROOM_13"))) {
				System.out.println();
				System.out.println(currentRoom.longDescription()); // to print
																	// out room
																	// description
																	// of ROOM_1
			}

		} else if (currentLevel == 2) {
			DialogueLevel2.level2Intro();
			initRooms("data/levels/level2.dat");
			currentRoom = masterRoomMap.get("ROOM_1");

			/*
			 * if (currentRoom.equals(masterRoomMap.get("ROOM_1"))){
			 * 	 System.out.println();
			 *	 System.out.println(currentRoom.longDescription()); // to print out room description of ROOM_1 
			 * }
			 */

		} else if (currentLevel == 3) {
			DialogueLevel3.Level3Intro();
			initRooms("data/levels/level3.dat");
			currentRoom = masterRoomMap.get("ROOM_1");
			
			// System.out.println("TESTING: currentLevel: " + currentLevel);
			if (currentRoom.equals(masterRoomMap.get("ROOM_1"))) {
				System.out.println();
				System.out.println(currentRoom.longDescription()); // to print
																	// out room
																	// description
																	// of ROOM_1
			}

		} else if (currentLevel == 4) {
			DialogueLevel4.LevelIntro();
			initRooms("data/levels/level4.dat");
			currentRoom = masterRoomMap.get("ROOM_1");
			if (currentRoom.equals(masterRoomMap.get("ROOM_1"))) {
				System.out.println();
				System.out.println(currentRoom.longDescription()); // to print
																	// out room
																	// description
																	// of ROOM_1
			}
			
		}
	}

	/*
	 * Main play routine. Loops until end of play.
	 * 
	 * @throws Exception
	 */
	public void play() throws Exception {
		//printWelcome();
		
		/*
		 * Create the game and initialize its internal map.
		 */
		try {
			resetLevel(currentLevel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		parser = new Parser();
		// Enter the main command loop. Here we repeatedly read commands and
		// execute them until the game is over.

		boolean finished = false;
		while (!finished) {
			Command command = parser.getCommand();
			finished = processCommand(command);
			int counter = 0; // to count the number of times you enter a room,
								// so dialogue only shows once

			
			if (currentLevel == 1) {
				if (currentRoom.equals(masterRoomMap.get("ROOM_13"))) {
					//DialogueLevel1.level1Intro();
				} else if (currentRoom.equals(masterRoomMap.get("ROOM_4"))) {
					if (Inventory.findIndex(bean) == -1){
						DialogueLevel1.Jack1stMeeting();
					} else if (Inventory.findIndex(bean) != -1){
						DialogueLevel1.Jack2ndMeeting(firstKey);
					}
				} else if (currentRoom.equals(masterRoomMap.get("ROOM_17"))) {
					DialogueLevel1.level1Giant();
				} else if (currentRoom.equals(masterRoomMap.get("ROOM_22"))) {
					Inventory.add(bean);
				} else if (currentRoom.equals(masterRoomMap.get("ROOM_1"))) {
					if (Inventory.findIndex(firstKey) == -1){
					//something
					} else if (Inventory.findIndex(firstKey) != -1){
						DialogueLevel1.level1Door();
						DialogueLevel1.level1CodeCheck();
						DialogueLevel1.level1Outro();
						DialogueLevel1.loading();
						currentLevel++;
						resetLevel(currentLevel);
					}
				}
			} else if (currentLevel == 2) { // CM
				if (currentRoom.equals(masterRoomMap.get("ROOM_1"))) {
					DialogueLevel2.level2Intro();
				} else if (currentRoom.equals(masterRoomMap.get("ROOM_6"))) {
					if (Inventory.findIndex(bubble) == -1) {
						DialogueLevel2.level2NoAir();
						finished = true;
					}
				} else if (currentRoom.equals(masterRoomMap.get("ROOM_6"))) {
					DialogueLevel2.level2Shark();
				} else if (currentRoom.equals(masterRoomMap.get("ROOM_29"))) {
					if (DialogueLevel2.level2Oarfish(knife) == true){
						System.out.println();
						finished = true;
					} 
				} else if (currentRoom.equals(masterRoomMap.get("ROOM_30"))) {
					DialogueLevel2.level2Mirror();
					DialogueLevel2.level2Ending(currentLevel, secondKey);
					currentLevel++;
					resetLevel(currentLevel);
				}
			} else if (currentLevel == 3) {
				if(currentRoom.equals(masterRoomMap.get("ROOM_7"))){
					DialogueLevel3.Room7Msg();
				}else if(currentRoom.equals(masterRoomMap.get("ROOM_8"))){
					DialogueLevel3.Room8Msg();
				}else if(currentRoom.equals(masterRoomMap.get("ROOM_9"))){
					DialogueLevel3.Room9Msg();
				}else if(currentRoom.equals(masterRoomMap.get("ROOM_15"))){
					DialogueLevel3.Room15Msg();
					finished=DialogueLevel3.Room15React(finished);
				}else if(currentRoom.equals(masterRoomMap.get("ROOM_20"))){
					DialogueLevel3.Room20Msg();
					DialogueLevel3.Room20React();
				}else if(currentRoom.equals(masterRoomMap.get("ROOM_22"))){
					DialogueLevel3.Room22Msg();
				}else if(currentRoom.equals(masterRoomMap.get("ROOM_27"))){
					DialogueLevel3.Room27Msg();
					currentRoom.longDescription();
				}else if(currentRoom.equals(masterRoomMap.get("ROOM_36"))){
					DialogueLevel3.Room36Msg();
				}else if(currentRoom.equals(masterRoomMap.get("ROOM_41"))){
					DialogueLevel3.Room41Msg();
				}else if(currentRoom.equals(masterRoomMap.get("ROOM_59"))){
					DialogueLevel3.Room59Msg();
					finished = DialogueLevel3.Room59React(finished);
					currentRoom.longDescription();
				}else if (currentRoom.equals(masterRoomMap.get("ROOM_60"))) {
					DialogueLevel3.level3Ending(currentLevel, thirdKey);
					currentLevel++;
					resetLevel(currentLevel);
				}
			} else { // currentLevel == 4
				if (currentRoom.equals(masterRoomMap.get("ROOM_21"))) {
					if(DialogueLevel4.keyWord()){
						DialogueLevel4.levelEnd();
						currentLevel++;
						resetLevel(currentLevel);
					}
				}else if(currentRoom.equals(masterRoomMap.get("ROOM_9"))){
					System.out.println("You press the glowing purple button.");
					Thread.sleep(2000);
					System.out.println("A voice can be heard.");
					Thread.sleep(2000);
					System.out.println("A");
					Thread.sleep(2000);
					System.out.println("You think to yourself that maybe this can be used in the future.");
					Thread.sleep(2000);
					System.out.println("");
					System.out.println(currentRoom.longDescription());
				}else if(currentRoom.equals(masterRoomMap.get("ROOM_14"))){
					System.out.println("You find the letter \"V\" carved into the rock floor");
					Thread.sleep(2000);
					System.out.println("");
					System.out.println(currentRoom.longDescription());
				}else if(currentRoom.equals(masterRoomMap.get("ROOM_17"))){
					System.out.println("You find the letter \"A\" carved into the wall of the Volcano");
					Thread.sleep(2000);
					System.out.println("");
					System.out.println(currentRoom.longDescription());
				}else if(currentRoom.equals(masterRoomMap.get("ROOM_5"))){
							DialogueLevel4.personOne();
							System.out.println("");
							System.out.println(currentRoom.longDescription());
				}
			}
		}
		System.out.println("Thank you for playing. Good bye for now!");
	}

	/**
	 * Print out the opening message for the player.
	 */
	// Game Introduction
	private void printWelcome() throws InterruptedException {

		System.out.println();
		System.out.println("Welcome to \'Anonymous'!");
		System.out.println();
		System.out.println(
				"\'Anonymous\' is going to be the most incredible and mysterious adventure game you've ever seen!!");
		System.out
				.println("You play the game by entering commands with your keyboard when you see the \'>\' symbol.\n");
		System.out.println("You can also type \'help\' for a bit of advice.\n");
		thread.sleep(2000);
		printGameRules();
		thread.sleep(10000);
		System.out.println("\n\nAre you prepared for some serious challenges?");
		System.out.println();

		// And the game begins!!
		loading(); // displays loading message
		System.out.println();
		System.out.println("\nIt's dark. You hear a distant voice and open your eyes to find that you're in a cage.\n"
				+ "The cage is inside a dark airplane hanger. It is night time.\n");
		thread.sleep(6500);
		System.out
				.println("The ground is cold cement. You are wearing a T-Shirt and worn out jeans. You are also wearing"
						+ "\ndress shoes covered in dust. You don't remember who you are, where you came from or even what"
						+ "\n your name is.");
		System.out.println("A dark figure approaches the cage shown by the back light of the night sky.");
		thread.sleep(8000);
		DialogueLevel0.beginningOfGame();
		System.out.println();
		loading();
		System.out.println("\n\n");
		System.out.println("To Begin say hello.");
	}

	// JT Level 1
	public boolean printLevel1() throws InterruptedException {
		boolean level1Over = true;
		DialogueLevel1.level1Intro();

		currentLevel++;
		return (level1Over == true);
	}

	// loading Method: prints the "Loading . . . . " message
	private void loading() throws InterruptedException {
		System.out.println();
		thread.sleep(1500); // delays code
		System.out.print("Loading");
		for (int i = 0; i < 4; i++) {
			System.out.print("   .");
			thread.sleep(1500);
		}

	}

	/**
	 * Given a command, process (that is: execute) the command. If this command
	 * ends the game, true is returned, otherwise false is returned.
	 * 
	 * @throws InterruptedException
	 */
	private boolean processCommand(Command command) throws InterruptedException { //CM
		if (command.isUnknown(command)) {
			System.out.println("I don't know what you mean...");
			return false;
		}

		String commandWord = command.getCommandWord(); 
		String secondWord = command.getSecondWord();

		// Help commands
		if (commandWord.equalsIgnoreCase("help")) {
			printHelp();
		} else if (commandWord.equalsIgnoreCase("rules")) {
			printGameRules();
		} else if (commandWord.equalsIgnoreCase("commandlist")) {
			printCommandList();

			// look command
		} else if (commandWord.equalsIgnoreCase("look")) {
			printLook();

			// directions
		} else if (commandWord.equalsIgnoreCase("go")) {
			goRoom(command);
		} else if (commandWord.equalsIgnoreCase("north") || commandWord.equalsIgnoreCase("n")) {
			goRoomDirection("north");
		} else if (commandWord.equalsIgnoreCase("east") || commandWord.equalsIgnoreCase("e")) {
			goRoomDirection("east");
		} else if (commandWord.equalsIgnoreCase("south") || commandWord.equalsIgnoreCase("s")) {
			goRoomDirection("south");
		} else if (commandWord.equalsIgnoreCase("west") || commandWord.equalsIgnoreCase("w")) {
			goRoomDirection("west");
		} else if (commandWord.equalsIgnoreCase("up") || commandWord.equalsIgnoreCase("u")) {
			goRoomDirection("up");
		} else if (commandWord.equalsIgnoreCase("down") || commandWord.equalsIgnoreCase("d")) {
			goRoomDirection("down");

			// Other command actions
		} else if (commandWord.equalsIgnoreCase("eat") || commandWord.equalsIgnoreCase("drink")) {
			System.out.println("Do you really think you should be having a meal at a time like this?");
		} else if (commandWord.equalsIgnoreCase("read")) {
			readItem(command);
		} else if (commandWord.equalsIgnoreCase("hi")) {
			System.out.println("Hi back! What's up?");
		} else if (commandWord.equalsIgnoreCase("use")) {
			useItem(command);

			// Inventory actions
		} else if (commandWord.equalsIgnoreCase("inventory")) {
			Inventory.printInventory();
		} else if (commandWord.equalsIgnoreCase("take")) {
			takeItem(command);
			// testing: Inventory.add(apple);
		} else if (commandWord.equalsIgnoreCase("drop") || commandWord.equalsIgnoreCase("toss")) {
			dropItem(command);
			// testing: Inventory.toss(apple);

			// Quit command
		} else if (commandWord.equalsIgnoreCase("quit")) {
			if (command.hasSecondWord()) {
				System.out.println("Do you want to quit? If so, only enter: \'quit\'");																// da																// file!!
			} else {
				return true; // signal that we want to quit <-- we need to do
			} 
		}
		return false;
	}

	private void useItem(Command command) { //CM
		// if there is no second word, we don't know what to use...
		if (!command.hasSecondWord()) {
			System.out.println("Use what? (*Hint: item)");
			return;
		}

		String secondWord = command.getSecondWord();
		String thirdWord = command.getThirdWord();
		String fourthWord = command.getFourthWord();

		// making player more specific about which items they want to use - CM
		if (secondWord.equalsIgnoreCase("item")) {
			System.out.println("You're going to have to be way more specific. What is the item called?");
		} else if (currentLevel == 2) {
			if (secondWord.equalsIgnoreCase("bubble")) {
				DialogueLevel2.level2YesAir();
				Inventory.add(bubble);
			} else {
				System.out.println("That is not an item you can use.");
			}
		} else {
			System.out.println("That is not an item you can use.");
		}
	}

	/*
	 * Method reads an item
	 */
	private void readItem(Command command) throws InterruptedException { //CM
		// if there is no second word, we don't know what to read...
		if (!command.hasSecondWord()) { 
			System.out.println("Read what? (*Hint: item)");
			return;
		}

		String secondWord = command.getSecondWord(); 
		String thirdWord = command.getThirdWord();
		String fourthWord = command.getFourthWord();

		// making player more specific about which items they want to read 
		if (secondWord.equalsIgnoreCase("item")) {
			System.out.println("You're going to have to be way more specific. What is the item called?");
		} else if (secondWord.equalsIgnoreCase("note")) {
			System.out.println("Please be more specific. Which note? What is it called?");
		} else if (secondWord.equalsIgnoreCase("sign")) {
			System.out.println("Please be more specific. Which sign? What is it called?");
		} else {
			if (currentLevel == 2) {
				// intro sign
				if (secondWord.equalsIgnoreCase("intro") && thirdWord.equalsIgnoreCase("sign")) {
					System.out.println("\nINTRO SIGN");
					System.out.println("BEWARE: SHARK!!!\nJust stay inside the \'Kelp Forest\' to the east. ");
				// abyss sign
				} else if (secondWord.equalsIgnoreCase("abyss") && thirdWord.equalsIgnoreCase("sign")) {
					System.out
							.println("\nABYSS SIGN\nGood job once more! Follow the path of the glow in the dark road.");
					System.out.println("\'E\'\nHmmmm.... interesting, another letter.");
				// mignight zone note
				} else if (secondWord.equalsIgnoreCase("midnight") && thirdWord.equalsIgnoreCase("zone")
						&& fourthWord.equalsIgnoreCase("note")) {
					DialogueLevel2.level2Note();
				} else {
					System.out.println("That is not an item with legible words on it.");
				}
			} else {
				System.out.println("That is not an item with legible words on it.");
			}
		}
	}

	/*
	 * Allows player to take an item and add it to their inventory
	 */
	private void takeItem(Command command) throws InterruptedException { //CM
		// if there is no second word, we don't know what item to take...
		if (!command.hasSecondWord()) {
			System.out.println("Take what? (*Hint: item)");
			return;
		}

		String itemSecondWord = command.getSecondWord();
		String itemThirdWord = command.getThirdWord();
		String itemFourthWord = command.getFourthWord();

		// making player more specific about which items they want to take 
		if (itemSecondWord.equalsIgnoreCase("item")) {
			System.out.println("You're going to have to be way more specific. What is the item called?");
		} else {
			if (currentLevel == 1) {
				if (itemSecondWord.equalsIgnoreCase("key")) {
					Inventory.add(firstKey);
				} else {
					System.out.println("That is not an item that you can take.");
				}
			} else if (currentLevel == 2) {
				if (itemSecondWord.equalsIgnoreCase("stone")) {
					Inventory.add(stone);
				} else {
					System.out.println("That is not an item that you can take.");
				}
			} else if (currentLevel == 3) {
				// list stuff in here
				System.out.println("That is not an item that you can take.");
			} else if (currentLevel == 4) {
				// list stuff in here
				System.out.println("That is not an item that you can take.");
			} else {
				System.out.println("That is not an item that you can take.");
			}
		}
	}

	/*
	 * Allows player to drop an item and remove it from their inventory.
	 */
	private void dropItem(Command command) { //CM
		// if there is no second word, we don't know what item to drop...
		if (!command.hasSecondWord()) {
			System.out.println("Read what? (*Hint: item)");
			return;
		}

		String itemSecondWord = command.getSecondWord();
		String itemThirdWord = command.getThirdWord();
		String itemFourthWord = command.getFourthWord();

		// making player more specific about which items they want to drop
		if (itemSecondWord.equalsIgnoreCase("item")) {
			System.out.println("You're going to have to be way more specific. What is the item called?");
		} else {
			if (currentLevel == 1) {
				// list stuff in here
				System.out.println("That is not an item that you can drop.");
			} else if (currentLevel == 2) {
				if (itemSecondWord.equalsIgnoreCase("stone")) {
					Inventory.toss(stone);
				} else {
					System.out.println("That is not an item that you can drop.");
				}
			} else if (currentLevel == 3) {
				// list stuff in here
				System.out.println("That is not an item that you can drop.");
			} else if (currentLevel == 4) {
				// list stuff in here
				System.out.println("That is not an item that you can drop.");
			} else {
				System.out.println("That is not an item that you can drop.");
			}
		}

	}

	// printLook() Method lets you see room/environment description
	private void printLook() {
		System.out.println(currentRoom.getDescription());
	}

	/*
	 * printHelp() method prints out helpful suggestions and commands player can reference
	 * @throws InterruptedException
	 */
	private void printHelp() throws InterruptedException { //CM
		System.out.println("\n");
		System.out.println("You are lost. You are going to die alone and forever be forgotten.");
		System.out.println();
		thread.sleep(6000);
		System.out.println("Just joking!! :)");
		System.out.println("Don't worry. We will help guide you through this:");
		System.out.println("- to see the game rules, enter: \'rules\' (*HINT*: you should actually look at this for helpful tips at least once)");
		System.out.println("- to see the list of commands you may use, enter: \'commandlist\'");
	}

	/*
	 * printGameRules() method prints the rules of the game
	 */
	private void printGameRules() throws InterruptedException { //CM
		System.out.println("\n");
		System.out.println("Remember what your goal is. Here is a refresher of the game rules. \n");
		System.out.println(
				"- purpose of this game is to get your memory back (because that\'s sorta important) and get out of this game");
		System.out.println("- there will be command hints in quotes, \'like this\', you should use");
		System.out.println(
				"- you will have to enter commands into the game to control your actions. Here are some starter commands:");
		System.out.println("- to view all commands, enter: \'commandlist\'");
		System.out.println("- to take and drop items, simply enter: \'take\' or \'drop\' respectively");
		System.out.println("- to see what is in your inventory, enter: \'inventory\'");
		System.out.println("- to see the room description again, enter: \'look\'");
		System.out.println("- to quit the game, you may enter: \'quit\'");
		System.out.println("- for help, enter: \'help\'");
		System.out.println("- once again, if you would like to see the rules, enter: \'rules\'");
	}

	/*
	 * printCommandList() method prints all the commands that the player can use
	 */
	private void printCommandList() throws InterruptedException { //CM
		System.out.println("\n");
		System.out.println("You can try using the following command words:");
		parser.showCommands(); // prints command words from validCommands String
		System.out.println("Yes, you can call for help if you need it (like what you are doing right now)\n");
	}

	/*
	 * Try to go to one direction. If there is an exit, enter the new room,
	 * otherwise print an error message.
	 * 
	 * This method is for when the user types in "go" as first word
	 */
	private void goRoom(Command command) { //CM
		// if there is no second word, we don't know where to go...
		if (!command.hasSecondWord()) {
			System.out.println("Go where? (*Hint: direction)");
			return;
		}

		String secondWord = command.getSecondWord();
		String direction = "";
		if (secondWord.equalsIgnoreCase("north") || secondWord.equalsIgnoreCase("n"))
			direction = "north";
		else if (secondWord.equalsIgnoreCase("east") || secondWord.equalsIgnoreCase("e"))
			direction = "east";
		else if (secondWord.equalsIgnoreCase("south") || secondWord.equalsIgnoreCase("s"))
			direction = "south";
		else if (secondWord.equalsIgnoreCase("west") || secondWord.equalsIgnoreCase("w"))
			direction = "west";
		else if (secondWord.equalsIgnoreCase("up") || secondWord.equalsIgnoreCase("u"))
			direction = "up";
		else if (secondWord.equalsIgnoreCase("down") || secondWord.equalsIgnoreCase("d"))
			direction = "down";
		else {
			System.out.println();
			System.out.println("That is not a valid direction.");
			System.out.println("Please enter \'commandlist\' to see the valid directions.");
		}

		// Try to leave current room.
		Room nextRoom = currentRoom.nextRoom(direction);

		if (nextRoom == null) {
			System.out.println();
			System.out.println("There is no exit here!");
		} else {
			currentRoom = nextRoom;
			System.out.println(currentRoom.longDescription());
		}
	}

	/*
	 * This method determines whether the player can move in a direction. This
	 * is for when the player directly types in the direction, and not "go"
	 * first.
	 */
	private void goRoomDirection(String direction) { 
		// Try to leave current room.
		Room nextRoom = currentRoom.nextRoom(direction);

		if (nextRoom == null)
			System.out.println("There is no exit here!");
		else {
			currentRoom = nextRoom;
			System.out.println(currentRoom.longDescription());
		}
	}

}