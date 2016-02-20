/**
 * class Task_InsertionSort: 
 *  
 * @author Wagner Franchin
 */

package task;

import factory.Util_Functions;

public class SortingInsertion extends SortingAbstract {

	@Override
	protected void runSorting(int[] array) {
		
		Util_Functions.insertionSort(array);
		
		response = array;
	}
	
}
