/**
 * MainScheduler class, loads the file properties and starts the scheduler
 *  
 * @author Wagner Franchin
 */

package main;

import factory.Task;
import factory.TaskFactory;
import factory.Util;
import scheduler.Scheduler;

public class MainScheduler {
	
    public static void main(String args[]) throws InterruptedException {
    	
		Util.loadProperties();
    	
    	//Starting the Scheduler thread
    	new Thread(Scheduler.getInstance(), "shceduler_thread").start();

    	//create tasks
        for(String name : Util.tasks){
    		try {
    			
    			Task t = TaskFactory.create(name);

    			if (t != null)
        			Scheduler.getInstance().addQueue(t);
    			
    		} catch (Exception e) {
    			System.err.println("Some problem while creating tasks. Exception message: " + e.getMessage());
    			System.exit(-1);
    		}
        }
    }
}
