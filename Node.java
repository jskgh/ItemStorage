package BinTree;

public class Node {
    
    /* Related nodes */
    Node leftChild;
    Node rightChild;
    
    /* Item attributes*/
    int no;
    String desc;   
    int value;

    /* Constructor */
    public Node(int no, String desc, int value) {
        this.no = no;
        this.desc = desc;
        this.value = value;
    }
    
    /* Getters and Setters */
    public int getNo() {
        return no;
    }

    public String getDesc() {
        return desc;
    }

    public int getValue() {
        return value;
    }
    
}
