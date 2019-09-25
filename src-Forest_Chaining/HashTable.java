// A hash table implemented with separate chaining.
// Every chain is organized as a binary search tree.

// imports for debugging only

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * A hash table implemented with separate chaining
 * Every chain is organized as a binary search tree
 *
 * @param <T> the generic type
 */
class HashTable<T extends Comparable<T>> {


    static private int minLength = 2;

    private int size = 0;
    private SimpleBST<T>[] storage;//array base implementation

    /**
     * Return the table length
     *
     * @return the length
     */
    public int getLength() {
        return storage.length;
    }

    /**
     * Return the number of values in hash table
     *
     * @return the Size
     */
    public int size() {
        return size;
    }

    /**
     * Return a string representation of all values in hash table
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                s.append(storage[i].toString());
            }
        }
        return s.toString().trim();
    }


    /**
     * Return a detailed string representation of the hash table.
     *  Give the contents of each table entry
     *
     * @param verbose a detailed report of tree features for every entry
     * @return the string
     */
    public String toStringDebug(boolean verbose) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null && storage[i].size() != 0) {
                s.append("[" + i + "]: " + storage[i].toString().trim() + "\n");
                if (verbose) {
                    s.append("\t tree size:" + storage[i].size() + "\n");
                    s.append("\t tree height:" + storage[i].height() + "\n");
                    s.append("\t number of leaves:" + storage[i].numLeaves() + "\n");
                }
            } else
                s.append("[" + i + "]: null\n");

        }
        return s.toString().trim();
    }

    /**
     *  Instantiates a new hash table
     *  Use minLength to initialize table if given length goes below that
     *
     * @param length the length
     */
    @SuppressWarnings("unchecked")
    public HashTable(int length) {
        if (length < minLength)
            length = minLength;

        storage = new SimpleBST[length];

    }

    //
    // - Uses separate chaining for collision.
    // - Returns false if value cannot be added
    // (duplicate values or null values).
    // - Returns true for a successful insertion.
    // - After adding a new value, if the table
    // has a load>= 80%, rehash the table to twice
    // the size.
    //
    // Worst case: O(N), Average case: O(load)
    // - N as the number of values in hash table
    // - not considering rehashing overhead

    /**
     * Adds value into hash table
     *
     *
     * @param value the value
     * @return true, if successful
     */
    public boolean add(T value) {
        if (value == null)
            return false;

        int index = index(value, getLength());
        if (storage[index] == null)
            storage[index] = new SimpleBST<>();

        if (storage[index].insert(value)) {
            size++;

            double loadFactor = (double) size * 100 / storage.length;
            if (loadFactor >= 80)//80% reached
                rehash(storage.length * 2);//double the size

            return true;
        }

        return false;
    }

    /**
     * Index function for the hashtable
     *
     * @param value the value
     * @param capacity the capacity
     * @return the int
     */
    private int index(T value, int capacity) {
        int index = Math.abs(value.hashCode() % capacity);
        return index;
    }

    // Checks whether value is in hash table.
    // - Returns true is present, false otherwise
    // Worst case: O(N), Average case: O(load)
    // - N as the number of values in hash table

    /**
     * Contains method
     *
     * @param value the value
     * @return true, if successful
     */
    public boolean contains(T value) {
        int index = index(value, getLength());
        return storage[index] != null && storage[index].contains(value);
    }

    // Removes and returns true if value is in hash table.
    // Returns false if value cannot be removed
    // (values not in tree or null values)
    // Worst case: O(N), Average case: O(load)
    // - N as the number of values in hash table

    /**
     * Removes the value
     *
     * @param value the value
     * @return true, if successful
     */
    public boolean remove(T value) {
        int index = index(value, getLength());
        if (storage[index] != null && storage[index].remove(value)) {
            size--;
            return true;
        } else
            return false;
    }

    // Rehash hash table to newLength.
    // - This can be used to increase or decrease the
    // capacity of the storage.

    // - If the new capacity is smaller than minLength,
    // return false and do not rehash.

    // - If you are asked to rehash to a length such that
    // the load would be >= 0.8, double the length parameter
    // until the load would be < 0.8 and use that new length.

    // - If the new length has to go beyond Integer.MAX_VALUE
    // to satisfy the load requirement, return false

    // - Return true if you were able to rehash.

    // All values should be rehashed following this order:
    // - Hash table entries should be rehashed based on array index
    // in ascending order.
    // - Multiple values in a chain (i.e. a binary search tree)
    // should be rehashed in pre-order.

    // O(N+M): N as the number of values in hash table;
    // M as the table length.

    /**
     * Rehash the table to new length if not less than previous length
     *
     * @param newLength the new length
     * @return true, if successful
     */
    @SuppressWarnings("unchecked")
    public boolean rehash(int newLength) {
        if (newLength < minLength)
            return false;

        double load = size * 100.0 / newLength;
        while (load >= 80.0) {//while load is >= .8
            if (newLength > Integer.MAX_VALUE / 2)
                return false;

            newLength *= 2;//double the length
            load = size * 100.0 / newLength;
        }

        SimpleBST<T>[] newStorage = new SimpleBST[newLength];//create new table pass values to new re hashed table
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                Object[] elements = storage[i].toArray();
                for (int j = 0; j < elements.length; j++) {
                    int index = index((T) elements[j], newStorage.length);
                    if (newStorage[index] == null)
                        newStorage[index] = new SimpleBST<>();

                    newStorage[index].insert((T) elements[j]);
                }
            }
        }

        storage = newStorage;

        return true;

    }

    // Returns an array representation of all value in hash table.
    // The array length should be the same as number of values in hash table.
    //
    // O(N+M): N as the number of values in hash table;
    /**
     * Converts the hashtable elements to an array
     *
     * @return the object[] array
     */
    // M as the table length.
    public Object[] toArray() {
        Object[] array = new Object[size];
        int index = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                Object[] elements = storage[i].toArray();
                for (int j = 0; j < elements.length; j++) {
                    array[index++] = elements[j];
                }
            }
        }

        return array;
    }

    // Returns the average tree height.
    // - If nonEmptyOnly is true, only consider non-empty trees;
    // otherwise all trees are considered.
    // - Returns -1.0 if all trees are empty but only non-empty
    // trees are considered
    /**
     * Avg tree height.
     *
     * @param nonEmptyOnly if nonEmptyOnly is true, only consider non-empty trees
     * @return the average tree height
     */

    public double avgTreeHeight(boolean nonEmptyOnly) {
        int count = 0;
        int totalHeight = 0;
        for (int i = 0; i < storage.length; i++) {

            if (nonEmptyOnly) {
                if (storage[i] != null && storage[i].size() > 0) {
                    count++;
                    totalHeight += storage[i].height();
                }
            } else {
                totalHeight += storage[i] == null ? -1 : storage[i].height();
                count++;
            }
        }

        return count == 0 && nonEmptyOnly ? -1 : (double) totalHeight / count;
    }

    //
    //  If nonEmptyOnly is true, only consider non-empty trees;
    // otherwise all trees are considered.
    // Returns 0.0 if all trees are empty but only non-empty
    /**
     * Return the average tree size.
     *
     * @param nonEmptyOnly if nonEmptyOnly is true, only consider non-empty trees
     * @return the average tree size
     */
    // trees are considered
    public double avgTreeSize(boolean nonEmptyOnly) {
        int count = 0;
        int total = 0;
        for (int i = 0; i < storage.length; i++) {

            if (nonEmptyOnly) {
                if (storage[i] != null && storage[i].size() > 0) {
                    count++;
                    total += storage[i].size();
                }
            } else {
                total += storage[i] == null ? 0 : storage[i].size();
                count++;
            }
        }

        return count == 0 && nonEmptyOnly ? 0 : (double) total / count;
    }

    // Returns the average number of leaves.
    // - If nonEmptyOnly is true, only consider non-empty trees;
    // otherwise all trees are considered.
    // - Return 0.0 if all trees are empty but only non-empty
    /**
     * Avg number of  leaves.
     *
     * @param nonEmptyOnly if nonEmptyOnly is true, only consider non-empty trees
     * @return the average number of leaves
     */
    // trees are considered
    public double avgNumLeaves(boolean nonEmptyOnly) {
        int count = 0;
        int total = 0;
        for (int i = 0; i < storage.length; i++) {

            if (nonEmptyOnly) {
                if (storage[i] != null && storage[i].size() > 0) {
                    count++;
                    total += storage[i].numLeaves();
                }
            } else {
                total += storage[i] == null ? 0 : storage[i].numLeaves();
                count++;
            }
        }

        return count == 0 && nonEmptyOnly ? 0 : (double) total / count;
    }

    /**
     * Min and max of the tree size
     *
     * @return the pair
     */
    // Return the min and max tree size as a pair.
    public Pair<Integer, Integer> minAndMaxTreeSize() {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < storage.length; i++) {

            int current = storage[i] == null ? 0 : storage[i].size();

            if (current < min)
                min = current;

            if (current > max)
                max = current;

        }

        return new Pair<Integer, Integer>(min, max);
    }

    /**
     * Min and max of the tree height
     *
     * @return the pair
     */
    // Return the min and max tree height as a pair.
    public Pair<Integer, Integer> minAndMaxTreeHeight() {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < storage.length; i++) {

            int current = storage[i] == null ? -1 : storage[i].height();

            if (current < min)
                min = current;

            if (current > max)
                max = current;

        }

        return new Pair<Integer, Integer>(min, max);
    }

    /**
     * Min and max of num leaves
     *
     * @return the pair
     */
    // Return the min and max number of leaves in trees as a pair.
    public Pair<Integer, Integer> minAndMaxNumLeaves() {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < storage.length; i++) {

            int current = storage[i] == null ? 0 : storage[i].numLeaves();

            if (current < min)
                min = current;

            if (current > max)
                max = current;

        }

        return new Pair<Integer, Integer>(min, max);
    }


    //--------------------------------
    // Main Method For Your Testing 
    //--------------------------------

    public static void main(String[] args) {
        BufferedWriter writer=null;
        boolean debug=false;

        try{
            if (args.length==1 && args[0].equals("-debug")){
                writer = new BufferedWriter(new FileWriter(new File("debug.txt")));
                debug = true;
            }

            HashTable<String> ht1 = new HashTable<>(10);

            //empty hash table
            if(ht1.getLength() == 10 && ht1.size() == 0 && ht1.toString().equals("")) {
                System.out.println("Yay 1");
            }

            //add
            if (ht1.add("a") && ht1.add("c") && ht1.add("computer") && !ht1.add("c")
                    && ht1.getLength() == 10 && ht1.size() == 3){
                System.out.println("Yay 2");
            }
            // hashCode() references:
            // "a": 97, "c": 99, "computer": -599163109


            //basic features of hash table
            if(ht1.contains("a") && ht1.contains("computer") && ht1.contains("c") && !ht1.contains("cs")
                    && ht1.toString().equals("a c computer")
                    && ht1.toStringDebug(false).equals("[0]: null\n[1]: null\n[2]: null\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: a\n[8]: null\n[9]: c computer")) {
                System.out.println("Yay 3");
            }
            //System.out.println(ht1.toStringDebug(true));

            if (debug){
                writer.write("====================================================="+System.getProperty("line.separator"));
                writer.write("ht1 after add(\"a\"), add(\"c\"), add(\"computer\")"+System.getProperty("line.separator"));
                writer.write("-----------------------------------------------------"+System.getProperty("line.separator"));
                writer.write(ht1.toStringDebug(true)+System.getProperty("line.separator")+System.getProperty("line.separator"));
            }

            //remove
            if (!ht1.remove("data") && ht1.remove("c") && ht1.size() == 2
                    && !ht1.contains("c")  && ht1.contains("computer")){
                System.out.println("Yay 4");

            }

            //rehash
            HashTable<Integer> ht2 = new HashTable<>(5);
            ht2.add(105);
            ht2.add(26);
            ht2.add(11);
            if (ht2.getLength()==5 && ht2.size()==3 && ht2.add(55) && ht2.getLength()==10
                    && ht2.add(5) && ht2.add(-11) && ht2.add(31) && ht2.getLength()==10 && ht2.size()==7){
                System.out.println("Yay 5");
            }

            //System.out.println(ht2.toString());
            //System.out.println(ht2.toStringDebug(true));
            if (debug){
                writer.write("====================================================="+System.getProperty("line.separator"));
                writer.write("ht2 after adding these in order: 105, 26, 11, 55, 5, -11, 31"+System.getProperty("line.separator"));
                writer.write("-----------------------------------------------------"+System.getProperty("line.separator"));
                writer.write(ht2.toStringDebug(true)+System.getProperty("line.separator")+System.getProperty("line.separator"));
            }

            if (ht2.toString().equals("-11 11 31 5 55 105 26") && ht2.size() == 7 &&
                    ht2.toStringDebug(false).equals("[0]: null\n[1]: -11 11 31\n[2]: null\n[3]: null\n[4]: null\n[5]: 5 55 105\n[6]: 26\n[7]: null\n[8]: null\n[9]: null")) {
                System.out.println("Yay 6");
            }

            //tree features from hash table
            if (ht2.avgTreeSize(false)==0.7 && ht2.avgTreeSize(true)==7.0/3 &&
                    ht2.avgTreeHeight(false)==-4.0/10 && ht2.avgTreeHeight(true)==1 &&
                    ht2.avgNumLeaves(false)==0.4 && ht2.avgNumLeaves(true)==4.0/3){
                System.out.println("Yay 7");
            }

            //more tree features
            if (ht2.minAndMaxTreeSize().toString().equals("<0,3>") &&
                    ht2.minAndMaxTreeHeight().toString().equals("<-1,2>") &&
                    ht2.minAndMaxNumLeaves().toString().equals("<0,2>")){
                System.out.println("Yay 8");
            }

            //more rehash
            if(ht2.rehash(2) == false && ht2.size() == 7 && ht2.getLength() == 10) {
                System.out.println("Yay 9");
            }

            if(ht2.rehash(11) == true && ht2.size() == 7 && ht2.getLength() == 11) {
                System.out.println("Yay 10");
            }
            //System.out.println(ht2.toString());
            //System.out.println(ht2.toStringDebug(true));

            if (debug){
                writer.write("====================================================="+System.getProperty("line.separator"));
                writer.write("ht2 after rehash to length 11"+System.getProperty("line.separator"));
                writer.write("-----------------------------------------------------"+System.getProperty("line.separator"));
                writer.write(ht2.toStringDebug(true)+System.getProperty("line.separator")+System.getProperty("line.separator"));

                //bigger hashtable w/ major clusterings
                HashTable<Integer> ht3 = new HashTable<>(20);
                ht3.add(160); ht3.add(20);ht3.add(100); ht3.add(80);
                ht3.add(400); ht3.add(280); ht3.add(640);

                ht3.add(543); ht3.add(3); ht3.add(283); ht3.add(343); ht3.add(443);

                ht3.add(334); ht3.add(974); ht3.add(454);

                writer.write("====================================================="+System.getProperty("line.separator"));
                writer.write("ht3 of 15 values clustered into three trees"+System.getProperty("line.separator"));
                writer.write("-----------------------------------------------------"+System.getProperty("line.separator"));
                writer.write(ht3.toStringDebug(true)+System.getProperty("line.separator")+System.getProperty("line.separator"));

                //bigger hashtable w/ uniform distribution
                ht3 = new HashTable<>(20);
                int count=0;
                Random r = new Random(0);
                while (count<15){
                    if (ht3.add(r.nextInt(1000)))
                        count++;
                }
                writer.write("====================================================="+System.getProperty("line.separator"));
                writer.write("ht3 of 15 values uniformly distributed"+System.getProperty("line.separator"));
                writer.write("-----------------------------------------------------"+System.getProperty("line.separator"));
                writer.write(ht3.toStringDebug(true)+System.getProperty("line.separator"));

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                if (writer!=null)
                    writer.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

    }


}