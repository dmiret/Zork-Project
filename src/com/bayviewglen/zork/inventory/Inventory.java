package com.bayviewglen.zork.inventory;

import java.util.ArrayList;

import com.bayviewglen.zork.item.Item;

public class Inventory {
	//this is assuming the items inputed are actual items
	private static ArrayList<Item> bag ; // Mr.D said to change object to item
		
	public Inventory(){
		bag = new ArrayList<Item>();
	}
	
	public Inventory(ArrayList<Item> myBag){
		bag = myBag;
	}
	
	public Item get(Item getMe){
	
		return bag.get(findIndex(getMe));
	}
	
	public static void add(Item addMe){
		if(findIndex(addMe) < 0) {
			bag.add(addMe);
		} else {
			System.out.println("You already have a " + addMe.getName());
		}
	}
	
	public static void toss(Item tossMe){
		if(bag.size() <= 0){
			System.out.println("You have nothing in your Inventory");
		}
		bag.remove(findIndex(tossMe));
		System.out.println("You have tossed " + tossMe.getName());
	}
	
	//prints inventory 

	public static void printInventory(){
		System.out.print("Inventory : ");
		if (bag.size() <= 0){
			System.out.println("empty");
		} else {
			for(Item x : bag){
				System.out.printf("%5s ", x.getName() +  " ");
			}
			System.out.println(" ");
		}
	
	}

	// method to find the item in the list
	public static int findIndex(Item item) {
		
		for(int i = 0; i<bag.size(); i++){
			if(bag.get(i).getName().equalsIgnoreCase(item.getName())){
				return i;
			}
		}
		return -1;
	}

}
