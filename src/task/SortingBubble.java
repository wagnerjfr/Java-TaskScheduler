/**
 * class TaskBubbleSort: 
 *  
 * @author Wagner Franchin
 */

package task;

import factory.Util_Functions;

public class SortingBubble extends SortingAbstract {

	@Override
	protected void runSorting(int[] array) {
		
		Util_Functions.bubbleSort(array);
		
	}
	
}
