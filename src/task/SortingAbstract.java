/**
 * Sorting abstract class
 *
 * @author Wagner Franchin
 */
package task;

import factory.Task;

public abstract class SortingAbstract extends Task {

    protected abstract void runSorting(int[] array);

    @Override
    public String executeTask() {
        StringBuilder result = new StringBuilder();
        //Expecting a int array
        if (hasFatherResponse() && father.getResponse().getClass().isArray()) {
            int[] array = ((int[]) father.getResponse()).clone();
            result.append("[" + array[0] + "..." + array[array.length-1] + "]");
            runSorting(array);
            result.append("->[" + array[0] + "..." + array[array.length-1] + "]");
        }
        else
            result.append("Response isn't an int[]");

        return result.toString();
    }
}
