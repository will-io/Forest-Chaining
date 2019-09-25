/**
 * The Class SimpleBST for a binary search tree implementation.
 *
 * @param <T> the generic type
 */
class SimpleBST<T extends Comparable<T>> {

   
    // bad practice to have public inst. variables, but we want to test this...
    // Root of tree
    public Node<T> root;

    // size of the tree (the number of nodes)
    public int size;

    /**
     * Total elements in bst
     *
     * @return the size
     */
    public int size() {
        return size;
    }


    /**
     * binary tree node class
     *
     * @param <T> is the generic type
     */
    public static class Node<T> {
        T data;
        Node<T> left, right;

        /**
         * Instantiates a new node
         *
         * @param data is the data
         */
        public Node(T data) {this.data = data;}

        /**
         * Instantiates a new node
         *
         * @param data the data
         * @param l the left node
         * @param r the right node
         */
        public Node(T data, Node<T> l, Node<T> r) {
            this.data = data;this.left = l;this.right = r;
        }
    }


    /**
     * checks if value is present int bst
     *
     * @param value is the value
     * @return Return true if value is in tree, false if value is not in tree or if value is null
     */
    // O(H): H as the tree height
    public boolean contains(T value) {
        if (value == null)
            return false;

        Node<T> current = root;

        // find
        while (current != null) {
            int diff = current.data.compareTo(value);
            if (diff == 0)//when found
                return true;
            else if (diff < 0)
                current = current.right;//check right sub tree
            else
                current = current.left;//check left subtree
        }

        return false;//not found
    }


    /**
     * Inserts value into tree
     * No duplicates allowed; no null value allowed
     *
     * @param value is the value
     * @return true, for a successful insertion
     */
    // O(H): H as the tree height
    public boolean insert(T value) {
        if (value == null)
            return false;

        int prevSize = size;//record starting size

        root = insert( value, root );//helper method called

        return prevSize != size;//return true if size changed
    }

    /**
     * Inserts helper method
     *
     * @param x is the value to inset
     * @param t is the the current node
     * @return the new root
     */
    private Node<T> insert(T x, Node<T> t) {
        if (t == null) {
            t = new Node<>(x);//create new node
            size++;
        } else if (x.compareTo(t.data) < 0)
            t.left = insert(x, t.left);//recursive call to traverse left
        else if (x.compareTo(t.data) > 0)
            t.right = insert(x, t.right);//recursive call to traverse right
        return t;
    }

    /**
     * Removes value from tree
     * Returns false if value cannot be removed
     * or values not in tree or null values
     *
     * @param value is the value to remove
     * @return true for a successful removal
     */
    // O(H): H as the tree height
    public boolean remove(T value) {
        int prevSize = size;
        root = remove( value, root );

        return prevSize != size;//return true if size changed

    }

    /**
     * Removes helper method
     *
     * @param x is the value to remove
     * @param t is the the current node
     * @return the new root
     */
    private Node<T> remove(T x, Node<T> t) {
        if (t == null)
            return t;
        if (x.compareTo(t.data) < 0)
            t.left = remove(x, t.left);//recursive call to traverse left
        else if (x.compareTo(t.data) > 0)
            t.right = remove(x, t.right);//recursive call to traverse right
        else if (t.left != null && t.right != null) // Two children
        {
            size--;//update size
            t.data = findMax(t.left);//call helper method
            t.left = removeMax(t.left);//call helper method
        } else {
            t = (t.left != null) ? t.left : t.right;//if left node is not null go left otherwise go right
            size--;//update size
        }
        return t;
    }

    /**
     * Returns the biggest value in the tree rooted at t
     * Returns null if tree is null
     *
     * @param <T> the generic type
     * @param t the root
     * @return the max
     */
    // O(H): H as the tree height
    public static <T> T findMax(Node<T> t) {
        if (t == null)
            return null;
        else if (t.right == null)//right-most node found
            return t.data;
        else
            return findMax(t.right);//recursive call to traverse as far right
    }

    /**
     * Removes the biggest value in the binary search tree rooted at t
     *
     * @param <T> the generic type
     * @param t the tree root
     * @return he tree root after removal
     */
    // O(H): H as the tree height
    public static <T> Node<T> removeMax(Node<T> t) {
        if (t == null)
            return null;
        else if (t.right != null) {
            t.right = removeMax(t.right);//recursive call to remove max
            return t;
        } else
            return t.left;// otherwise return left node
    }


    /**
     * Returns the in-order predecessor of t's data in the tree with t as root
     * The in-order predecessor is the biggest value that is smaller than t's data
     * Returns null if t's data is the smallest or if tree is null
     *
     * @param <T> the generic type
     * @param t the tree root
     * @return the predecessor
     */
    // O(H): H as the tree height
    public static <T> T findPredecessor(Node<T> t) {
        if(t == null)
            return null;
        else
            return findMax(t.left);//call to helper method to find the right most node of the left sub-tree
    }

    /**
     * Returns the height of the tree
     * Returns -1 for null trees
     *
     * @return the height
     */
    // O(H): H as the tree height
    public int height() {
        return height(root);
    }

    /**
     * Height helper
     *
     * @param currentNode the current node
     * @return the height
     */
    private int height(Node<T> currentNode) {
        if(currentNode == null)
            return -1;
        else
            return 1 + Math.max(height(currentNode.left), height(currentNode.right));// recursive call return number greater in length either left or right subtree plus root
    }

    /**
     * Returns the number of leaf nodes in the tree
     * Returns zero for null trees
     *
     * @return the number of leaf nodes
     */
    // O(N): N is the tree size
    public int numLeaves() {
        return numLeaves(root);//invokes helper method
    }

    /**
     * Num leaves helper
     *
     * @param currentNode is the current node
     * @return the num Leaves
     */
    private int numLeaves(Node<T> currentNode) {
        if(currentNode == null)
            return 0;
        else if(currentNode.left == null || currentNode.right == null)
            return 1;//if no left or right subtree return 1
        else
            return numLeaves(currentNode.left) + numLeaves(currentNode.right);//recursive call to traverse to the leaves in left and right subtree


    }

    // Return a string representation of the tree
    // follows IN-ORDER traversal to include data of all nodes.
    // Includes one space after each node.
    // O(N): N is the tree size
    //
    // Returns empty string "" for null trees.
    // Check main method below for more examples.
    //
    // Example 1: a single-node tree with the root data as "A":
    // toString() should return "A "
    //
    // Example 2: a tree with three nodes: B
    // / \
    // A C
    // toString() should return "A B C "
    /**
     * Inorder string representation of the tree
     */
    public String toString() {
        return toString(root) + " ";//call helper method for in-order traversal representation
    }

    /**
     * To string helper
     *
     * @param currentNode the current node
     * @return the string representation
     */
    private String toString(Node<T> currentNode) {
        if(currentNode == null)
            return "";//if root null return nothing
        else {
            String s = "";
            if(currentNode.left != null)
                s += toString(currentNode.left) + " ";//recursive call visit left subtree first until null

            s += currentNode.data;

            if(currentNode.right != null)
                s += " " + toString(currentNode.right);//recursive call visit right subtree until null

            return s;

        }
    }


    // Return an array representation of all values
    // following PRE-ORDER traversal.
    // - The root value of the tree should be element 0
    // in the array.
    // - The length of the array should be the size
    // of tree.
    // O(N): N is the tree size
    //
    // Example 1: a single-node tree with the root data as "A"
    // toArray() should return {"A"}
    //
    // Example 2: a tree with three nodes: B
    // / \
    // A C
    // toArray() should return {"B","A","C"}
    // Check main method below for more examples.

    /**
     * Return an array representation of all values
     * following pre-order traversal
     *
     * @return the object[]
     */
    // @SuppressWarnings("unchecked")
    public Object[] toArray() {
        Object[] array = new Object[size];//create new array object of size
        toArray(0, array, root); //set root to array 0
        return array;
    }

    /**
     * To array helper
     *
     *
     * @param i the index in the array
     * @param array the array
     * @param currentNode the current node
     * @return the new index after insertion
     */
    public int toArray(int i, Object[] array, Node<T> currentNode) {
        if(currentNode == null)
            return i;

        array[i++] = currentNode.data;//set to follow pre-order traversal
        i = toArray(i, array, currentNode.left);
        i = toArray(i, array, currentNode.right);
        return i;
    }




    //------------------------------
    // Main Method For Your Testing 
    //------------------------------
    public static void main(String args[]){
        SimpleBST<Integer> t = new SimpleBST<Integer>();

        //build the tree / set the size manually
        //only for testing purpose
        Node<Integer> node = new Node<>(112);
        Node<Integer> node2 = new Node<>(330);
        node2 = new Node<>(440,node2,null);
        node = new Node<>(310,node,node2);
        t.root = node;
        t.size = 4;

        // Current tree:
        //			  310
        //           /   \
        //        112     440
        //                /
        //              330


        //checking basic features
        if (t.root.data == 310 && t.contains(112) && !t.contains(211) && t.height() == 2){
            System.out.println("Yay 1");
        }

        //checking more features
        if (t.numLeaves() == 2 && SimpleBST.findMax(t.root)==440
                && SimpleBST.findPredecessor(t.root) == 112){
            System.out.println("Yay 2");
        }

        //toString and toArray
        if (t.toString().equals("112 310 330 440 ") &&  t.toArray().length==t.size()
                && t.toArray()[0].equals(310) && t.toArray()[1].equals(112)
                && t.toArray()[2].equals(440) && t.toArray()[3].equals(330) ){
            System.out.println("Yay 3");
            //System.out.println(t.toString());
        }

        // start w/ an empty tree and insert to build the same tree as above
        t = new SimpleBST<Integer>();
        if (t.insert(310) && !t.insert(null) && t.size()==1 && t.height()==0
                && t.insert(112) && t.insert(440) && t.insert(330) && !t.insert(330)
        ){
            System.out.println("Yay 4");
        }

        if (t.size()==4 && t.height()==2 && t.root.data == 310 &&
                t.root.left.data == 112 && t.root.right.data==440
                && t.root.right.left.data == 330){
            System.out.println("Yay 5");
        }

        // more insertion
        t.insert(465);
        t.insert(321);
        t.insert(211);

        //			  310
        //           /   \
        //        112     440
        //          \     /  \
        //         211   330 465
        //               /
        //             321



        //remove leaf (211), after removal:
        //			  310
        //           /   \
        //        112     440
        //                /  \
        //               330 465
        //               /
        //             321

        if (t.remove(211) && !t.contains(211) && t.size()==6 && t.height() == 3
                && SimpleBST.findMax(t.root.left) == 112){
            System.out.println("Yay 6");
        }

        //remove node w/ single child (330), after removal:
        //			  310
        //           /   \
        //        112     440
        //                /  \
        //              321 465

        if (t.remove(330) && !t.contains(330) && t.size()==5 && t.height() == 2
                && t.root.right.left.data == 321){
            System.out.println("Yay 7");
        }

        //remove node w/ two children (440), after removal:
        //			  310
        //           /   \
        //        112     321
        //                  \
        //                 465

        if ((t.root!=null) && SimpleBST.findPredecessor(t.root.right) == 321 && t.remove(440) && !t.contains(440)
                && t.size()==4 && t.height() == 2 && t.root.right.data == 321){
            System.out.println("Yay 8");
        }

    }

}