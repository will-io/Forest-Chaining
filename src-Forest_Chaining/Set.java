// for addAll() only
import java.util.Collection;

//

/**
 * A Set using HashTable
 *
 * @param <T> the generic type
 */
class Set<T extends Comparable<T>> {


    private HashTable<T> storage = new HashTable<>(5);

    /**
     * the Size
     *
     * @return the size
     */
    public int size() {
        return storage.size();
    }

    /**
     * If value not present in set, add value and return true
     *
     * @param value is the value
     * @return true, if successful
     */
    // Otherwise, do not add and return false.
    public boolean add(T value) {
        return storage.add(value);
    }

    /**
     * // Return true if value in set, false otherwise
     *
     * @param value is the value
     * @return true, if present
     */

    public boolean contains(T value) {
        return storage.contains(value);
    }

    //
    /**
     * If value present in set, remove value and return true
     *
     * @param value the value
     * @return true, if successful
     */
    // Otherwise return false and no change to set.
    public boolean remove(T value) {
        return storage.remove(value);
    }

   /**
     *  java.lang.Object#toString()
     */
    public String toString() {
        return storage.toString();
    }

    /**
     * To array
     *
     * @return the object[]
     */
    public Object[] toArray() {
        return storage.toArray();
    }

   
    /**
     * Accept a collection of values and add them into set one by one
     *
     * @param c the collection values
     * @return the int
     */
    // Return the number of values successfully added.
    public int addAll(Collection<T> c) {
        int count = 0;
        for (T t : c) {
            if (add(t))
                count++;
        }

        return count;
    }

    /**
     * Construct and return the intersection set of this and other
     *
     * @param other the other
     * @return the intersection
     */
    // Original sets should not be modified.
    @SuppressWarnings("unchecked")
    public Set<T> intersection(Set<T> other) {
        Set<T> set = new Set<>();

        Object[] arrayOther = other.toArray();

        for (int i = 0; i < arrayOther.length; i++) {
            if (this.contains((T) arrayOther[i]))
                set.add((T) arrayOther[i]);
        }

        return set;
    }

    /**
     * Construct and return the union set of this and other
     *
     * @param other the other
     * @return the union
     */
    // Original sets should not be modified.
    @SuppressWarnings("unchecked")
    public Set<T> union(Set<T> other) {
        Set<T> set = new Set<>();

        Object[] array = this.toArray();

        for (int i = 0; i < array.length; i++) {
            set.add((T) array[i]);
        }

        array = other.toArray();

        for (int i = 0; i < array.length; i++) {
            set.add((T) array[i]);
        }

        return set;
    }

    /**
     * Construct and return the difference set: this - other
     *
     * @param other the other
     * @return the difference
     */
    // Original sets should not be modified.
    @SuppressWarnings("unchecked")
    public Set<T> difference(Set<T> other) {
        Set<T> set = new Set<>();

        Object[] array = this.toArray();

        for (int i = 0; i < array.length; i++) {
            if (!other.contains((T) array[i]))
                set.add((T) array[i]);
        }

        return set;

    }

    /**
     * Construct and return the symmetric difference set
     *
     * @param other the other
     * @return the symmetric Difference
     */
    // Original sets should not be modified.
    @SuppressWarnings("unchecked")
    public Set<T> symmetricDifference(Set<T> other) {
        Set<T> set = new Set<>();

        Object[] array = this.toArray();

        for (int i = 0; i < array.length; i++) {
            if (!other.contains((T) array[i]))
                set.add((T) array[i]);
        }

        array = other.toArray();

        for (int i = 0; i < array.length; i++) {
            if (!this.contains((T) array[i]))
                set.add((T) array[i]);
        }

        return set;
    }

    /**
     * Return true if this set is a subset of other
     *
     * @param other the other
     * @return true, if is subset
     */
    // return false otherwise.
    @SuppressWarnings("unchecked")
    public boolean isSubset(Set<T> other) {
        Object[] array = this.toArray();

        for (int i = 0; i < array.length; i++) {
            if (!other.contains((T) array[i]))
                return false;
        }

        return true;
    }

    /**
     * Return true if there is no overlap between this set and other; return false otherwise
     *
     * @param other the other
     * @return true, if is disjoint
     */
    @SuppressWarnings("unchecked")
    public boolean isDisjoint(Set<T> other) {
        Object[] array = this.toArray();

        for (int i = 0; i < array.length; i++) {
            if (other.contains((T) array[i]))
                return false;
        }

        array = other.toArray();

        for (int i = 0; i < array.length; i++) {
            if (this.contains((T) array[i]))
                return false;
        }

        return true;
    }

    // ----------------------------
    // Main Method For Your Testing 
    // ----------------------------

    public static void main(String args[]){
        // arrays only used for testing here
        Integer[] data1 = { 1, 2, 3,   5,    7 };
        Integer[] data2 = {    2,   4, 5, 6    };
        String[] duplicate = {"a","a","a","b","c","b","c","d","a","e","c","b"};
        Set<Integer> set1 = new Set<>();
        Set<Integer> set2 = new Set<>();
        Set<String> noduplicate = new Set<>();

        //addAll
        if (set1.addAll(java.util.Arrays.asList(data1))==5 &&
                set2.addAll(java.util.Arrays.asList(data2))==4 &&
                noduplicate.addAll(java.util.Arrays.asList(duplicate)) == 5){
            System.out.println("Yay 1");
        }

        //System.out.println(set1);
        //System.out.println(set2);

        Set<Integer> set3 = set1.intersection(set2);
        //System.out.println(set3);  //should have 2 and 5 only
        if (set3.contains(2) && set3.contains(5) && !set3.contains(1) && set3.size()==2){
            System.out.println("Yay 2");
        }

        Set<Integer> set4 = set1.union(set2);
        //System.out.println(set4);  //should have 1,2,3,4,5,6,7 (not necessarily in that order)
        boolean ok = true;
        for (int i=1;i<8;i++){
            ok = ok && set4.contains(i);
        }
        if (ok && set4.size()==7){
            System.out.println("Yay 3");
        }

        Set<Integer> set5 = set1.difference(set2);
        //System.out.println(set5);  //should have 1,3,7 (unordered)
        if (set5.size()==3 && set5.contains(1) && set5.contains(3) && set5.contains(7)
                && !set5.contains(2) && !set5.contains(5) && !set5.contains(4)){
            System.out.println("Yay 4");
        }

        Set<Integer> set6 = set1.symmetricDifference(set2);
        //System.out.println(set6);  // should have 1,3,4,6,7 (unordered) done

        if (!set1.isSubset(set2) && set3.isSubset(set2)
                && !set1.isDisjoint(set2) && set5.isDisjoint(set2)){
            System.out.println("Yay 5");
        }

    }

}
