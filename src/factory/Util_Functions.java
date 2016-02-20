/**
 * Util Functions
 *  
 * @author Wagner Franchin
 */

package factory;

public class Util_Functions {

	final public static String runFibonacci(int i) {
		int r = fibonacci(i);
		return String.valueOf(r);
	}

	/**
	 * recursive Fibonacci to increase CPU consumption
	 * @param n
	 * @return
	 */
	final private static int fibonacci(int n)  {
		if(n == 0)
			return 0;
		else if(n == 1)
			return 1;
		else
			return fibonacci(n - 1) + fibonacci(n - 2);
	}
	
	/**
	 * Bubble sort algorithm
	 * @param array
	 */
	final public static void bubbleSort(int array[]) {  

		int n = array.length;
		int temp = 0;

		for(int i=0; i < n; i++){
			for(int j=1; j < (n-i); j++){

				if(array[j-1] > array[j]){
					//swap the elements!
					temp = array[j-1];
					array[j-1] = array[j];
					array[j] = temp;
				}
			}
		}
    }
	
	/**
	 * Insertion sort algorithm
	 * @param array
	 */
    final public static void insertionSort(int array[]) {
        int n = array.length;
        for (int j = 1; j < n; j++) {
            int key = array[j];
            int i = j-1;
            while ( (i > -1) && ( array [i] > key ) ) {
                array [i+1] = array [i];
                i--;
            }
            array[i+1] = key;
        }
    }
	
}
