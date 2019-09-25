/**
 * A generic class representing two values as a pair
 *
 * @param <T1> the generic type of first value
 * @param <T2> the generic type of second value
 */
class Pair<T1, T2> {

    T1 first;
    T2 second;

    /**
     * Instantiates a new pair
     *
     * @param v1 the value 1
     * @param v2 the value 2
     */
    public Pair(T1 v1, T2 v2) {
        this.first = v1;
        this.second = v2;
    }



    /**
     * Return string representation of a pair as <first,second> with no spaces
     */
    public String toString() {
        return "<" + first + "," + second + ">";
    }


    // Returns true if o is a pair and that
    // the two pairs have the same first value and the same second value.
    // i.e. <"Alice", 1> is considered as equal to <"Alice", 1>


     /**
     * java.lang.Object#equals()
     */
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (o instanceof Pair) {
            Pair<T1, T2> pair = (Pair<T1, T2>) o;
            return this.first.equals(pair.first) && this.second.equals(pair.second);
        }

        return false;
    }

    // Computes an integer hash code for this object.
    // It must follow hash contract and distribute well.
    /**
     *
     * java.lang.Object#hashCode()
     */
    public int hashCode() {

        int result = 1;
        result = 31 * result + first.hashCode();//following contract with use of prime to create hashcode
        result = 31 * result + second.hashCode();//following contract with use of prime to create hashcode
        return result;
    }



    public static void main(String[] args){
        Pair<String,String> name1 = new Pair<>("George", "Mason");
        Pair<String,String> name2 = new Pair<>("George", "Washington");
        Pair<String,String> name3 = new Pair<>("George", "Mason");

        //equals
        if(!name1.equals(name2) && name3.equals(name3)){
            System.out.println("Yay 1");
        }

        //hashCode: hash contract and distribute well
        if (name1.hashCode() == name3.hashCode() && name1.hashCode()!=name2.hashCode()){
            System.out.println("Yay 2");
        }

        int[] buckets= new int[20];
        Pair<Integer,Integer> pair;
        for (int i=-400; i<570; i+=97){
            for (int j=-401; j<571; j+=97){
                pair = new Pair<>(i, j);
                int hashCode = pair.hashCode();
                buckets[Math.abs(hashCode)%20]++;
            }
        }
        int nonEmpty=0;
        for (int i=0;i<buckets.length;i++){
            if (buckets[i]>0)
                nonEmpty ++;
        }
        // all buckets have been hit
        if (nonEmpty==buckets.length){
            System.out.println("Yay 3");
        }

        //more hashCode distribution checking
        // - needs a working Set class: enable the testing below
        //    after you are done with Set


		Set<Integer> hashCodes = new Set<>();
		int repeat = 0;

		for (int i=-10; i<10; i++){
			for (int j=-10; j<10; j++){

				pair = new Pair<>(i, j);
				int hashCode = pair.hashCode();

				// if it cannot add into the set, we have seen this hash code
				if (!hashCodes.add(hashCode))
					repeat ++;
			}
		}

		// report the percentage of repeating hash code
		System.out.printf("Percentage of repeat: %.3f%%\n",(double)repeat/(20*20-1)*100);



    }
}
