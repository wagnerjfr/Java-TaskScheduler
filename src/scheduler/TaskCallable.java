/**
 * class TaskCallable runs method call()
 *  
 * @author Wagner Franchin
 */

package scheduler;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import factory.Task;

public class TaskCallable implements Callable<Object> {
	
	private ExecutorService executorService;
	private Task task;
	
	/**
	 * Function that creates a executorService with just 1 thread and submit the Callable.
	 * @param task
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	protected Object execute(Task task) throws InterruptedException, ExecutionException {
		
		this.task = task;
		
		executorService = Executors.newSingleThreadExecutor();
		Future<Object> futureResult = executorService.submit(this);
		executorService.shutdown();

		return futureResult.get();
	}

	@Override
	public Object call() throws Exception {
		
		Object r = task.executeTask();
		
        return r;
	}
}
