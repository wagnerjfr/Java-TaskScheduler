/**
 * Scheduler class, responsible for scheduling tasks
 * 
 * @author Wagner Franchin
 */

package scheduler;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import factory.Task;
import factory.Util;

final public class Scheduler implements Runnable {

	private static int taskID = 0;

	private Queue<Task> queue;
	private boolean bStop;
	
	/**
	 * SINGLETON -----------------------------------------------------
	 */
	private static class SchedulerSingleton {
		private static final Scheduler INSTANCE = new Scheduler();
	}

	public static Scheduler getInstance() {
		return SchedulerSingleton.INSTANCE;
	}
	/**
	 * ----------------------------------------------------------------
	 */
	
	private Scheduler() {
		queue = new LinkedList<Task>();
		bStop = false;
	}
	
	/**
	 * Method to add Tasks in the queue
	 * @param task
	 * @throws InterruptedException 
	 */
	public void addQueue(Task task) throws InterruptedException {
		synchronized(queue) {
			queue.add(task);
		}
	}
	
	@Override
	public void run() {

		try {
			Thread.sleep(100); //wait some milliseconds for tasks..
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
	   	System.out.println(new Date() + ":: Scheduler started..");
		
		Task task;
		
		while (!bStop) {
			try {
				synchronized(queue) {
					while (Util.cpu_bound <= Util.getProcessCPU()) {
						new Thread(new CPUChecker(), "check_cpu_thread").start();
						queue.wait();
					}
				}
				
				if (!queue.isEmpty()) {
					task = (Task) queue.poll();
					
					if (task != null) {
						task.setID(++taskID); //give an ID to the task
						
						new Thread(task).start(); //starts Task
						
						//if it has fixed rate, starts thread responsible for repeating the Task
						if (task.getFixedRate() > 0 && !task.isRunningThreadFixedRate()) {
							task.startThreadFixedRate();
						}
						
						Thread.sleep(Util.delay_scheduler);
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getQueueSize() {
		return queue.size();
	}
	
	/**
	 * Method to shutdown the scheduler
	 * @throws InterruptedException
	 */
	public void shutdown() throws InterruptedException {
		
		Thread.sleep(1000);

		System.out.println(Util.getCurrentHour() +  "   Scheduler shutting down..");
		Thread.sleep(2000);
		
		bStop = true;
	}
	
	/**
	 * Runnable class for checking the CPU usage and wakes thread when necessary
	 */
	final private class CPUChecker implements Runnable {

		@Override
		public void run() {
			try {
				
				if (Util.print_cpu_low)
					System.out.println(Util.getCurrentHour() + " checking CPU.. it's low!");
				
				synchronized(queue) {
					while (Util.cpu_bound <= Util.getProcessCPU()) {
						Thread.sleep(10);
					}
					queue.notify();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
