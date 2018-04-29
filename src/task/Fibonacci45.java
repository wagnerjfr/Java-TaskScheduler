/**
 * class Fibonacci45: 
 *
 * @author Wagner Franchin
 */

package task;

import factory.Task;
import factory.Util_Functions;

public class Fibonacci45 extends Task {

    @Override
    public String executeTask() {
        return Util_Functions.runFibonacci(45);
    }
}
