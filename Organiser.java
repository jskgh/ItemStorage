package BinTree;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
*
* @author j3-skelton (14017732)
*
* On using this program
*
*
* When the program runs, it will automatically populate the trees with the 
* existing test data and display a list of operations the user can do. Users can
* select an option by entering a corresponding number into the console.
*
* (0)   The program exits with no further operations.
* (1)   The program every item from every storage into the console, as well as 
*       the total value of the storage and the amount of items in storage.
* (2-6) Allows the user to add their items into any storage location. These 
*       options then prompt the user to enter the item data into the console.
* (7)   The program prints the capacity of each storage location in the form of 
*       a percentage. 100% being 2 billion.
* 
*/


public class Organiser {
    public static void main(String[] args) {
        
        // Create binary trees
        Tree treeA = new Tree('A');
        Tree treeB = new Tree('B');
        Tree treeC = new Tree('C');
        Tree treeD = new Tree('D');
        Tree treeE = new Tree('E');        
        
        // Create a list and add all trees
        ArrayList<Tree> treeList = new ArrayList<>();
        treeList.add(treeA);
        treeList.add(treeB);
        treeList.add(treeC);
        treeList.add(treeD);
        treeList.add(treeE);
        
        // Populate trees with data
        addData(treeA,"LocationA.txt");
        addData(treeB,"LocationB.txt");
        addData(treeC,"LocationC.txt");
        addData(treeD,"LocationD.txt");
        addData(treeE,"LocationE.txt");
        
        
        /* Main program loop to get user input */
        boolean run = true;
        
        while(run){
            System.out.println("Select a choice in the console:"
                             + "\n (0) Exit."
                             + "\n (1) Print all items."
                             + "\n (2) Add an item to Storage A."
                             + "\n (3) Add an item to Storage B."
                             + "\n (4) Add an item to Storage C."
                             + "\n (5) Add an item to Storage D."
                             + "\n (6) Add an item to Storage E."
                             + "\n (7) Print all statistics."
                             + "\n (8) Remove an item from Storage A."
                             + "\n (9) Remove an item from Storage B."
                             + "\n (10) Remove an item from Storage C." 
                             + "\n (11) Remove an item from Storage D."
                             + "\n (12) Remove an item from Storage E."            
            );
            
            Scanner sc = new Scanner(System.in);
            int input = -1;
                        
            // User input error checking
            try {
                input = sc.nextInt();
            } catch (InputMismatchException e){
                System.out.println("Please enter a number between 0 and 12");
            }
             
            switch(input){
            case 0: run = false;
                    break;
            case 1: printAllItems(treeList);
                    break;                 
            case 2: addItemToTree(treeList,treeA,createNewNode());
                    break;
            case 3: addItemToTree(treeList,treeB,createNewNode());
                    break;
            case 4: addItemToTree(treeList,treeC,createNewNode());
                    break;    
            case 5: addItemToTree(treeList,treeD,createNewNode());
                    break;
            case 6: addItemToTree(treeList,treeE,createNewNode());
                    break;
            case 7: printAllStats(treeList);
                    break;
            case 8: System.out.println("Please enter the item number:");
                    treeA.removeNode(sc.nextInt());
                    break;
            case 9: System.out.println("Please enter the item number:");
                    treeB.removeNode(sc.nextInt());
                    break;
            case 10: System.out.println("Please enter the item number:");
                    treeC.removeNode(sc.nextInt());
                    break;
            case 11: System.out.println("Please enter the item number:");
                    treeD.removeNode(sc.nextInt());
                    break;
            case 12: System.out.println("Please enter the item number:");
                    treeE.removeNode(sc.nextInt());
                    break;
            }
        }
    }
    
    /**
     * Creates a new node from the user's input.
     * 
     * @return Returns the newly created node.
     */
    public static Node createNewNode(){
        System.out.println("Enter the item details using comma seperated values. (item number, description, item value)");
 
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String text[] = line.split(",");

        Node node = new Node(Integer.parseInt(text[0]),text[1],Integer.parseInt(text[2]));
        
        return node;
    }
    
    /**
     * Adds a new node to a tree.
     * 
     * @param treeList The list of trees.
     * @param tree The tree that the node will be added to.
     * @param node The new node that is added to the tree.
     */
    public static void addItemToTree(ArrayList<Tree> treeList, Tree tree, Node node){
        
        // Reject item if larger than 2 billion
        if (node.getValue() > 2000000000){
            System.out.println("Warning: Item size is over 2 billion.");
            return;
        }
        
        // Reject item if no capacity amongst all storage locations
        if (calculateRemainingSpace(treeList) < node.getValue()){
            System.out.println("Warning: Not enough room in all storage locations.");
            return;
        }
        
        // Reject item if no capacity in location
        if (tree.addNode(node.getNo(),node.getDesc(),node.getValue()) == 1){
            // Makes room for item and trys again
            System.out.println("Warning: Not enough room in storage.");
            makeSpace(treeList,tree);
            addItemToTree(treeList,tree,node);
        }
        
        System.out.println("Item added to storage.");

    }
    
    /**
     * Makes room for a new item by moving other items in the tree to another tree.
     * 
     * @param treeList The list of trees.
     * @param currentTree The tree the items will be moved from.
     */
    public static void makeSpace(ArrayList<Tree> treeList, Tree currentTree){
        
        // Create new instance of a tree
        Tree nextTree = null;
        
        // Find the next tree in the list, looping back around to the first
        if (currentTree.id == 'A'){ 
            nextTree = treeList.get(1); 
        }
        if (currentTree.id == 'B'){ 
            nextTree = treeList.get(2); 
        }
        if (currentTree.id == 'C'){ 
            nextTree = treeList.get(3); 
        }
        if (currentTree.id == 'D'){ 
            nextTree = treeList.get(4); 
        }
        if (currentTree.id == 'E'){ 
            nextTree = treeList.get(0); 
        }   
 
        // Move the root node one tree along.
        moveNode(treeList,currentTree.root,currentTree,nextTree);
    }

    /**
     * Moves a node from one tree to another.
     * 
     * @param treeList The list of trees.
     * @param node The node to be moved.
     * @param src The tree the node will be moved from.
     * @param dest The tree the node will be moved to.
     */
    public static void moveNode(ArrayList<Tree> treeList, Node node, Tree src, Tree dest){
        System.out.println("Moving Item: " + node.getNo() +", "+ node.getDesc() + ", " + node.getValue() + " from storage " + src.id + " to storage " + dest.id);
        
        // Remove the node from one tree and add it to the other
        src.removeNode(node.getNo());
        addItemToTree(treeList,dest,node);
    }

    
    /**
    * Searches every tree for a single node.
    * 
    * @param treeList The list of trees.
    * @param no The item number of the node to be found.
    * @return Returns the found node, or null if no node is found.
    */
    public static Node searchItem(ArrayList<Tree> treeList, int no){
        
        for (Tree t : treeList){
            Node node = t.findNode(no);
            if (node != null){
                return node;
            }
        }
        
        return null;
    }
    
    /**
     * Calculates remaining storage capacity throughout all trees.
     * 
     * @param treeList The list of trees to be calculated.
     * @return Returns the amount of space left.
     */
    public static long calculateRemainingSpace(ArrayList<Tree> treeList){     
        long tvSum = 0;
        
        for (Tree t : treeList){
            tvSum += (2000000000 - t.getTotalValue());
        }

        return tvSum;
    }
    
    /**
    * Prints statistics on tree capacities.
    * 
    * @param treeList The list of trees to be calculated. 
    */
    public static void printAllStats(ArrayList<Tree> treeList){
        for (Tree t : treeList){
            t.calculateStats();
        }
    }
    
    /**
    * Prints all item information to the console using inorder traversal.
    * 
    * @param treeList The list of trees to be traversed. 
    */
    public static void printAllItems(ArrayList<Tree> treeList){
        for (Tree t : treeList){
            System.out.println("Storage " + t.id + ":");
            t.inOrder(t.root);
            System.out.println("Total value: " + t.totalValue + "\nItem Count: " + t.itemCount + "\n");
        }
    }
    
    /**
     * Populates a tree with predetermined data.
     * 
     * @param tree The tree to be populated.
     * @param path The path address for the text file with the data.
     */
    public static void addData(Tree tree, String path){
        Scanner sc = new Scanner(Organiser.class.getResourceAsStream(path));
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            String text[] = line.split(",");
            tree.addNode(Integer.parseInt(text[0]),text[1],Integer.parseInt(text[2]));
        }
    }

}

