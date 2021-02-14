 package com.binarytree;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;
		
public class BinarySearchTree {
	// Define a class to hold bid information
	static class Bid
	{
	    public String bidId; // Unique Identifier
	    public String title;  
	    public String fund;
	    public double amount;
	 };
	 
	// Class for tree node
	static class Node
	{
	    Bid bid; 
	    Node left;  
	    Node right;
	    
	    // Default constructor to initialize variables for right and left node
		public Node() {
			left = null;
			right = null;
		}
		
		// Constructor to initialize with a bid
		public Node(Bid aBid) {
			this.bid = aBid;
		}
	 };	
	 
	 // Root of binary search tree
	 Node root; 
	 
	 // Default constructor
	 BinarySearchTree() { 
		 root = null;
	 }
	 
	 // Removes $ sign from amount and converts String to Double type
	static double strToDouble(String str) {
		str = str.replace("$","");
		return Double.parseDouble(str);
	}

	// Implements InOrder traversal of tree
	 void InOrder() {
		 DecimalFormat df = new DecimalFormat("##.00"); // Formats number to always have two decimal values
		 Stack<Node> stack = new Stack<Node>(); // Creates empty stack
		 Node current = root; // Starts at root
		 
		 // Traverses tree
		 while (current != null || !stack.empty()) {
			 // Traverses left
			 if (current != null) {
				 stack.push(current);
				 current = current.left;
			 }						
			 else {
			 	current = stack.pop();				
			 	// Prints current bid
			 	System.out.println(current.bid.bidId + " " 
			 					 + current.bid.title + " $"
			 					 + df.format(current.bid.amount) + " "
			 					 + current.bid.fund);
			 	// Traverses right
			 	current = current.right;
			 }
		 }
	 }
	 
	 // Implements PreOrder traversal of tree
	 void PreOrder() {
		 DecimalFormat df = new DecimalFormat("##.00"); // Formats number to always have two decimal values
		 Stack<Node> stack = new Stack<Node>(); // Creates empty stack
		 Node current = root;
		 stack.push(root); // Push root to stack
		 
		 // Prevents null pointer exception if tree is empty
		 if (root == null)
			 return;
		 
		 while (!stack.empty()) {
			 // Pops node from stack
			 current = stack.pop();
			 
			 // Prints bid
			 System.out.println(current.bid.bidId + " " 
 					 + current.bid.title + " $"
 					 + df.format(current.bid.amount) + " "
 					 + current.bid.fund);
			
			 // Pushes children to stack  
			 if (current.right != null) {
				 stack.push(current.right);
			 }		
			 if (current.left != null) {
				 stack.push(current.left);
			 }
		 }
	 }
	 
	 // Implements PostOrder traversal of tree
	 void PostOrder() {
		 DecimalFormat df = new DecimalFormat("##.00"); 
		 Stack<Node> stack1 = new Stack<Node>(); // Creates empty stacks
		 Stack<Node> stack2 = new Stack<Node>();
		 Node current = root;
		 stack1.push(root); // Push root to first stack
		 
		 // Prevents null pointer exception if tree is empty
		 if (root == null)
			 return;
		 
		 // Traverses tree while first stack is not empty
		 while (!stack1.empty()) {
			 // Pops bid from first stack and pushes to second
			 current = stack1.pop();
			 stack2.push(current);
			 
			 // Pushes children nodes of current popped bid to first stack
			 if(current.left != null)
				 stack1.push(current.left); 
			 if(current.right != null)
				 stack1.push(current.right);
		 }
			 
		 // Pops bids from second stack and prints
		 while (!stack2.isEmpty()) {
			current = stack2.pop(); 
			System.out.println(current.bid.bidId + " " 
					 		  + current.bid.title + " $"
					 		  + df.format(current.bid.amount) + " "
					 		  + current.bid.fund);
		 }
	 }
	 
	 // Inserts a bid into the tree
	 void Insert(Bid bid) {
	  	// Inserts bid if tree if empty
	 	if (root == null) {
	 		root = new Node(bid);
	 	}
	 	// Adds bid to tree when not empty
	 	else {
	 		this.addNode(root, bid);
	 	}
	 }
	 
	// Remove a bid from the tree
	 void Remove(String bidId) {
	 	this.removeNode(root, bidId);
	 }
	 
	 // Searches the tree for a bid
	 Bid Search(String bidId) {
	 	// Start search at root
	 	Node current = root;

	 	// Continue looping until bid is found or bottom of tree is reached
	 	while (current != null) {
	 		// Return current node if it matches
	 		if (current.bid.bidId.compareTo(bidId) == 0) {
	 			return current.bid;
	 		}
	 		// If bid is smaller than current, traverse left
	 		if (bidId.compareTo(current.bid.bidId) < 0) {
	 			current = current.left;
	 		}
	 		// If bid is larger than current, traverse right
	 		else {
	 			current = current.right;
	 		}
	 	}
	 	// Returns empty bid if not found
	 	Bid bid = null;
	    return bid;
	 }	 
	
	 // Add a bid to a node recursively
	 void addNode(Node node, Bid bid) {
	 	// If node is greater than the bid, insert in left subtree
	 	if (node.bid.bidId.compareTo(bid.bidId) > 0) {
	 		// If left pointer is null, construct new node and insert pointer
	 		if (node.left == null) {
	 			node.left = new Node(bid);
	 		}
	 		// If pointer is not null, traverse down until place to insert is found
	 		else {
	 			this.addNode(node.left, bid);
	 		}
	 	}
	 	// If node is less than the bid, insert in right subtree
	 	else {
	 		if (node.right == null) {
	 			node.right = new Node(bid);
	 		}
	 		else {
	 			this.addNode(node.right, bid);
	 		}
	 	}
	 }

	 // Removes node from tree
	 Node removeNode(Node node, String bidId) {
	 	// If node is null then return to avoid crashing
	 	if (node == null) {
	 		return node;
	 	}

	 	// Recurse down left subtree
	 	if (bidId.compareTo(node.bid.bidId) < 0) {
	 		node.left = removeNode(node.left, bidId);
	 	}

	 	// Recurse down right subtree
	 	else if (bidId.compareTo(node.bid.bidId) > 0) {
	 		node.right = removeNode(node.right, bidId);
	 	}

	 	// Remove node if found
	 	else {
	 		// leaf node with no children; if left and right nodes are null, delete node and set to null
	 		if (node.left == null && node.right == null) {
	 			node = null;
	 		}
	 		// One child on left; replace internal node with child and delete child
	 		else if (node.left != null && node.right == null) {
	 			node = node.left;
	 		}
	 		// One child on right; replace internal node with child and delete child
	 		else if (node.right != null && node.left == null) {
	 			node = node.right;
	 		}
	 		// Two children
	 		else {
	 			Node temp = node.right;
	 			// Keep searching left side to find minimum
	 			while (temp.left != null) {
	  				temp = temp.left;
		 		}
		 		node.bid = temp.bid;
		 		node.right = removeNode(node.right, temp.bid.bidId);
		 	}
		 }
		 return node;
	 }
	 
	 // Outputs bid information to console
	 static void displayBid(Bid bid) {
	 	// Formats number to always have two decimal values
	 	DecimalFormat df = new DecimalFormat("##.00");
	    System.out.println(bid.bidId + ": " + bid.title + " $" + df.format(bid.amount) + " " + bid.fund);
	    return;
	 }
	 
	 // Loads CSV file containing bids into a container
	 public static void loadBids(String csvPath, BinarySearchTree bst) throws FileNotFoundException {
	 	System.out.println("\nLoading CSV file at " + new File(csvPath).getAbsolutePath() + "\n");
			
	 	// Uses scanner class to read CSV file
	 	Scanner scanCSV = new Scanner(new File(csvPath));
	 	scanCSV.useDelimiter("\\n");   

	 	// Reads next line to skip column headers in CSV file
	 	scanCSV.nextLine();
	 	
	 	// Loops through each row in the CSV file and adds to collection of bids
	 	while (scanCSV.hasNext()) {
	 		Bid bid = new Bid();
	
	 		// Splits line by commas to get data for each bid item
	 		String row = scanCSV.next();
	 		String[] aBid = row.split(",");
				
	 		bid.title  = aBid[0];
	 		bid.bidId  = aBid[1];
	 		bid.fund   = aBid[2];
	 		bid.amount = strToDouble(aBid[3]); // Converts string to double type

	 		bst.Insert(bid); // Inserts bid into tree
	 	}   
	 	// Closes scanner 
	 	scanCSV.close();  	
	 }
	 
	 // Main menu display and choices 
	 public static void main(String[] args) throws Exception {
		 String bidKey;
		 String csvPath = "eBid_Monthly_Sales.csv";
		 int choice = 0;
		 
		 BinarySearchTree bst;
		 bst = new BinarySearchTree();

		 Scanner scan = new Scanner(System.in);
		
		 while (choice !=9) {
			 System.out.println("Binary Search Tree Menu\n"
					 		  + "  1. Load Bids\n"	
					 		  + "  2. Display All Bids (InOrder)\n"
					 		  + "  3. Display All Bids - (PreOrder)\n"
					 		  + "  4. Display All Bids - (PostOrder)\n"
					 		  + "  5. Find Bid\n"
					 		  + "  6. Remove Bid\n"
					 		  + "  9. Exit\n"
					 		  + "Enter choice: ");
			
			 try {
				 choice = scan.nextInt();
			 } 
			 // Handles exception if invalid input is entered
			 catch (InputMismatchException e) {
				 System.out.println("Invalid input. Please select another option\n");
				 scan.nextLine();
				 continue;
			 }
			
			 switch(choice) {
			 case 1:
				 loadBids(csvPath, bst);
				 break;			
			 case 2:
				 // Prints bids using InOrder Traversal
				 bst.InOrder();
				 System.out.println();
				 break;
			 case 3:
				 // Prints bids using PreOrder Traversal
				 bst.PreOrder();
				 System.out.println();
				 break;
			 case 4:
				 // Prints bids using PostOrder Traversal
				 bst.PostOrder(); 
				 System.out.println();
				 break;
			 case 5: 
				 System.out.println("Please enter the bid key you would like to find.");
				 bidKey = scan.next();
				 
				 // Searches for input bid
				 Bid bid = bst.Search(bidKey);
					
				 // Checks if bid returned is null and prints bid if found
				 if (bid == null) {
					 System.out.println("Bid Id " + bidKey + " not found\n" );
				 } else {
					 displayBid(bid);
					 System.out.println();
				 }
				 break;
			 case 6:
				 System.out.println("Please enter the bid key you would like to remove.");
				 bidKey = scan.next();
				 
				 // Removes input bid if found
				 bst.Remove(bidKey);
				 System.out.println();
				 break;
			 default:
				 System.out.println();
				 break;
			 }
		 } 		 
		 System.out.println("Goodbye.");
		 scan.close();
	 }
}
