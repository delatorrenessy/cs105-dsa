class Node {
    int data;
    Node left, right;

    public Node(int item) {
        data = item;
        left = right = null;
    }
}

public class Main
{
    Node root;

    // Fundamental Insertion
    void insert(int data) {
        root = insertRec(root, data);
    }

    Node insertRec(Node root, int data) {
        if (root == null) {
            return new Node(data);
        }
        if (data < root.data) {
            root.left = insertRec(root.left, data);
        } else if (data > root.data) {
            root.right = insertRec(root.right, data);
        }
        return root;
    }
    
    // 1. Calculate the total height of the tree
    static int getHeight(Node root) {
        if (root == null) return 0;
        
        int leftHeight = getHeight(root.left);
        int rightHeight = getHeight(root.right);
        
        return (leftHeight > rightHeight) ? (leftHeight + 1) : (rightHeight + 1);
    }

    // 2. Print all nodes at a specific level (1-indexed)
    static void printLevel(Node root, int level) {
        if (root == null) return;
        
        if (level == 1) {
            System.out.print(root.data + " ");
        } else if (level > 1) {
            printLevel(root.left, level - 1);
            printLevel(root.right, level - 1);
        }
    }

    // 3. Loop through each level from top to bottom
    static void levelOrder(Node root) {
        int height = getHeight(root);
        for (int i = 1; i <= height; i++) {
            printLevel(root, i);
            //System.out.println(); 
        }
    }
    
    public static void main(String[] args) {
        Main bst = new Main();

        // Build a basic tree
        bst.insert(45);
        bst.insert(25);
        bst.insert(65);
        bst.insert(15);
        bst.insert(35);
        bst.insert(85);

        System.out.println("BST Level-Order:");
        Main.levelOrder(bst.root);
    }
}
