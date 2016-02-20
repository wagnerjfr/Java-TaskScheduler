/**
 * abstract class Task
 *  
 * @author Wagner Franchin
 */

package factory;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Queue;

import scheduler.TaskCallable;
import scheduler.Scheduler;

public abstract class Task extends TaskCallable implements Runnable {
	
	private int id;
	private long delay;
	private long fixedRate;
	private Queue<Task> queuedDependecy;
	private ThreadFixedRate thread_fixed_rate;
	private boolean bstop;
	protected Task father; //super task
	protected Object response; //some information you want to keep for the dependency task
	
	
	public Task() {	
		queuedDependecy = new LinkedList<Task>();
		bstop = false;
	}
	
	public abstract String executeTask() throws Exception;
	
	@Override
	public void run() {
		
		try {
			
			executeMethod();
			
		} catch (Exception e) {
			System.err.println(Util.getCurrentHour() + " [" + this.getClass().getSimpleName() + "-" +  this.getID() + "] " + e.getMessage());
		}
	}

	private void executeMethod() throws Exception {
		
		StringBuilder sFather = new StringBuilder("[");
		if (father != null)
			sFather.append(father.getClass().getSimpleName()).append("-ID:").append(father.getID()).append("->");
			
		StringBuilder print_task = new StringBuilder(); 
		print_task.append(this.getClass().getSimpleName()).append("-ID:").append(id).append("]");
		
		print_task = sFather.append(print_task);
		
		System.out.println(Util.getCurrentHour() + " S " + print_task.toString() + " started -> CPU: " + Util.getProcessCPU() + " (Queue Size: " + Scheduler.getInstance().getQueueSize() + ")");
		
		long start = System.currentTimeMillis();
		
		String future = (String) execute(this);

		long end = System.currentTimeMillis();
		String time = new DecimalFormat("#0.00").format((end - start) / 1000d) + "s";
		
		System.out.println(Util.getCurrentHour() + " R " + print_task.toString() + " result: " + future + " in " + time);

		Thread.sleep(delay);
		
		for (Task dependecy : queuedDependecy) {
			Scheduler.getInstance().addQueue(dependecy);
		}
	}

	/**
	 * Return task id
	 * @return
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Set task id
	 * @param id
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * Function to get delay for a task
	 * @return
	 */
	public Long getDelay() {
		return delay;
	}
	
	/**
	 * Function to get fixed rate for a task
	 * @return
	 */
	public Long getFixedRate() {
		return fixedRate;
	}
	
	/**
	 * Method to set delay for a task
	 * @param delay
	 */
	public void setDelay(long delay) {
		this.delay = delay;
	}
	
	/**
	 * Method to set fixed rate for a task
	 * @param rate
	 */
	public void setFixedRate(long rate) {
		this.fixedRate = rate;
		
		if (fixedRate > 0)
			thread_fixed_rate = new ThreadFixedRate(this);
	}
	
	/**
	 * Start thread_fixed_rate
	 */
	public void startThreadFixedRate() {
		thread_fixed_rate.start();
	}
	
	/**
	 * Stop thread_fixed_rate
	 */
	public void stopThreadFixedRate() {
		bstop = true;
	}
	
	/**
	 * Verify if the thread_fixed_rate is running
	 * @return
	 */
	public boolean isRunningThreadFixedRate() {
		return thread_fixed_rate.isAlive();
	}
	
	/**
	 * Method for adding dependencies for a task
	 * @param task
	 * @param response 
	 */
	public void addDependency(Task task) {
		task.father = this;
		queuedDependecy.add(task);
	}
	
	/**
	 * Verify if the father exist than verify if him has a response 
	 * @return
	 */
	public boolean hasFatherResponse() {
		if (father == null)
			return false;
		
		return father.getResponse() != null;
	}
	
	public <T> boolean isFatherResponseType(Class<T> type) {
		return father.getResponse().getClass().equals(type);
	}
	
	/**
	 * Get the response object from the task
	 * @return
	 */
	public Object getResponse() {
		return response;
	}
	
	/**
	 * This thread class is started by the scheduler when the task has fixed rate greater than zero seconds
	 * @author Wagner
	 */
	final private class ThreadFixedRate extends Thread {
		
		private Task task;
		
		ThreadFixedRate(Task task) {
			this.task = task;
		}
		
		@Override
		public void run() {
			while (!bstop) {
				try {
					
					Thread.sleep(task.getFixedRate());
					Scheduler.getInstance().addQueue(task);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
