package task;

import java.util.Arrays;

import factory.Task;

public class SortingFindNumbers extends Task {

	@Override
	public String executeTask() throws Exception {

		StringBuilder result = new StringBuilder();
		
		//Expecting a int array
		if (hasFatherResponse() && father.getResponse().getClass().isArray()) {
			
			int[] array = ((int[]) father.getResponse()).clone();
			
			result.append("[has 10: " + getPosition(array, 10)  + "]");
			result.append("[has 100: " + getPosition(array, 100)  + "]");
		}
		else
			result.append("Response isn't an int[]");

		return result.toString();
	}

	private String getPosition(int[] array, int n) {
		String result;
		int index = Arrays.binarySearch(array, n);
		
		if (index >= 0)
			result = "index " + index;
		else
			result = "number not found";
		
		return result;
	}
}
