package BinTree;

public class Tree {
    
    Node root;
    long totalValue;
    int itemCount;
    char id;
    
    /* Constructor */
    public Tree(char id) {
        this.id = id;
    }
    
    /**
    * Adds a new node to the tree.
    *
    * @param no The item number of the new item.
    * @param desc The item description of the new item.
    * @param value The item value of the new item.
    * @return   Returns 1 if there is no room in the tree for the new item.
    *           Returns 0 if there is no error.
    */
    public int addNode(int no, String desc, int value){
        Node node = new Node(no,desc,value);
        
        if (value > (2000000000 - this.totalValue))
        {
            return 1;
        }
        
        this.itemCount++;
        this.totalValue += value;

        // Create a root node if the tree has none
        if (root == null){
            root = node;

        } else {
            Node workingNode = root; // Working node
            Node parentNode; // Each node has a parent node, sans the root node.

            // Endless loop
            while(true){
                parentNode = workingNode;

                // Node is sent left if item number is smaller than parent node
                if (no < workingNode.no){
                    workingNode = workingNode.leftChild;

                    if (workingNode == null){
                        parentNode.leftChild = node;
                        return 0;
                    }
                
                // Otherwise it is sent right
                } else {
                    workingNode = workingNode.rightChild;

                    if (workingNode == null){
                        parentNode.rightChild = node;
                        return 0;
                    }
                }
            }
        }
        
        return 0;
    }
    
    /**
     * Find a node in the tree.
     * 
     * @param no The item number of the node to be found.
     * @return Returns the found node, or null if no node is found.
     */
    public Node findNode(int no){
        Node workingNode = root; // Start at the root
        
        // If node not found
        while (workingNode.no != no){
            // Explore the tree
            if (no < workingNode.no){
                workingNode = workingNode.leftChild;
            } else {
                workingNode = workingNode.rightChild;
            }
            
            // In case of error, return null
            if (workingNode == null){
                return null;
            }
        }
    
        return workingNode;
    }
    
    /**
     * Removes a node from the tree.
     * 
     * @param no The item number of the item to be removed.
     * @return Return false if error.
     */
    public boolean removeNode(int no){
        
        /* Remove item value from the tree */
        totalValue -= findNode(no).getValue();
        itemCount --;
        
        
        Node workingNode = root;
        Node parentNode = root;
        boolean isLeftChild = true;
        
        while(workingNode.no != no){
            parentNode = workingNode;
            
            // If item number is less than item number of parent node
            if (no < workingNode.no){
                // The node is the left child of the parent node
                isLeftChild = true;
                workingNode = workingNode.leftChild;
            } else {
                // Otherwise the node is the right child of the parent node
                isLeftChild = false;
                workingNode = workingNode.rightChild;
            }
            
            // Return in case of error
            if (workingNode == null){
                return false;
            }
        }
        
        // If node has no children
        if(workingNode.leftChild == null && workingNode.rightChild == null){
            
            if(workingNode == root){
                // If node is root node, make null
                root = null;
            } else if(isLeftChild){
                // Otherwise null children
                parentNode.leftChild = null;
            } else {
                parentNode.rightChild = null;
            }
        
        // If the node has no right child
        } else if (workingNode.rightChild == null){
            
            // Move node up the tree
            if (workingNode == root){
                root = workingNode.leftChild;
            } else if (isLeftChild){
                parentNode.leftChild = workingNode.leftChild;
            } else {
                parentNode.rightChild = workingNode.rightChild;
            }
            
        // If the node has no left child
        } else if (workingNode.leftChild == null){
            
            // Move node up the tree
            if (workingNode == root){
                root = workingNode.rightChild;
            } else if (isLeftChild){
                parentNode.leftChild = workingNode.rightChild;
            } else {
                parentNode.rightChild = workingNode.leftChild;
            }
        
        // If node has both children
        } else {
            Node replacementNode = getReplacementNode(workingNode);
            
            if (workingNode == root){
                root = replacementNode;
                
            } else if (isLeftChild){
                parentNode.leftChild = replacementNode;
            } else {
                parentNode.rightChild = replacementNode;
            }
            
            replacementNode.leftChild = workingNode.leftChild;
        }
        
        return true;
    }
    
    /**
     * A helper function to remove node.
     * 
     * @param oldNode The node to be replaced.
     * @return Returns node to replace the old node.
     */
    public Node getReplacementNode(Node oldNode){
        Node replacementParentNode = oldNode;
        Node replacementNode = oldNode;
        
        Node curNode = oldNode.rightChild;
        
        while (curNode != null){
            replacementParentNode = replacementNode;
            replacementNode = curNode;
            curNode = curNode.leftChild;
        }
        
        if (replacementNode != oldNode.rightChild){
            replacementParentNode.leftChild = replacementNode.rightChild;
            replacementNode.rightChild = oldNode.rightChild;
        }
        
        return replacementNode;
        
    }
    
    /**
     * Traverses the tree in-order.
     * 
     * @param curNode The starting node, usually the root node.
     */
    public void inOrder(Node curNode){
        if (curNode != null){
            inOrder(curNode.leftChild);
            System.out.println("Item: " + curNode.no + ", " + curNode.desc + ", " + curNode.value); // Visit Node
            inOrder(curNode.rightChild);
        }
    }
        
    /**
     * Traverses the tree pre-order.
     * 
     * @param curNode The starting node, usually the root node.
     */
    public void preOrder(Node curNode){
        if (curNode != null){
            System.out.println("Item: " + curNode.no + ", " + curNode.desc + ", " + curNode.value); // Visit Node
            preOrder(curNode.leftChild);
            preOrder(curNode.rightChild);
        }
    }
    
    /**
     * Traverses the tree post-order.
     * 
     * @param curNode The starting node, usually the root node.
     */
    public void postOrder(Node curNode){
        if (curNode != null){
            postOrder(curNode.leftChild);
            postOrder(curNode.rightChild);
            System.out.println("Item: " + curNode.no + ", " + curNode.desc + ", " + curNode.value); // Visit Node
        }
    }

    /**
     * Generates a percentage of the tree's capacity.
     */
    public void calculateStats(){
        float bil = 2000000000;
        float tot = totalValue;
        float pcnt = (tot / bil)*100;
        System.out.println("Storage " + this.id + " is " + pcnt + "% full.");
    }
    
    /* Getters and Setters */
    public long getTotalValue() {
        return totalValue;
    }

}
