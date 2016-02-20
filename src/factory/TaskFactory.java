package factory;


public class TaskFactory {
	
	public static final String TASKS_PACKAGE = "task.";
	public static final String TASK_MAIN_SEPARATOR = ")";
	public static final String DEPENDENCY_SIGNAL = "->";
	public static final String PARALLEL_SIGNAL = "--";
	public static final String ATTRIBUTES_SEPARATOR = ",";
	public static final String TASKS_SEPARATOR = ";";
	public static final String TASKS_INFO_SEPARATOR = ":";
	
	public static Task create(String info) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		String task_main = info.substring(0, info.indexOf(TASK_MAIN_SEPARATOR));
		String task_depend = info.substring(info.indexOf(TASK_MAIN_SEPARATOR) + 1).trim();
		
		String[] dependencies = null;
		boolean bParallel = true;
		
		if (!task_depend.equals("")) {
			if (task_depend.contains(DEPENDENCY_SIGNAL)) {
				dependencies = task_depend.split(DEPENDENCY_SIGNAL);
				bParallel = false;
			}
			else if (task_depend.contains(PARALLEL_SIGNAL)) {
				dependencies = task_depend.split(PARALLEL_SIGNAL);
			}
			else { //just one dependency
				dependencies = new String[1];
				dependencies[0] = task_depend;
			}
		}
			
		
		String[] task_info = task_main.split(TASKS_INFO_SEPARATOR);
		
		Task task = createTask(task_info[0]); //class name
		
		if (task_info.length == 2) {
			String[] attributes = task_info[1].split(ATTRIBUTES_SEPARATOR);
			
			if (attributes.length == 2) {
				task.setFixedRate(Long.parseLong(attributes[0]) * 1000);
				task.setDelay(Long.parseLong(attributes[1]) * 1000);
			}
			else
				task.setDelay(Long.parseLong(attributes[0]) * 1000);
		}
		
		if (dependencies != null && dependencies.length > 0) { //if the task has dependents
			Task dependency_aux = null;
		
			for (int i = 0; i < dependencies.length; i++) {
				task_info = dependencies[i].split(TASKS_INFO_SEPARATOR);

				Task dependency = createTask(task_info[0]);
				
				if (task_info.length == 2) {
					dependency.setDelay(Long.parseLong(task_info[1]) * 1000);
				}
				
				if (bParallel)
					task.addDependency(dependency);
				else {
					if (i == 0)
						task.addDependency(dependency);
					else
						dependency_aux.addDependency(dependency);

					dependency_aux = dependency;
				}
			}
		}
		
		return task;
	}
	
	private static Task createTask(String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		Task task = (Task)Class.forName(TASKS_PACKAGE + className).newInstance();
		
		return task;
	}
	
    
	/**
	 * Function to test if the class names from file are correct
	 * @param tasks
	 * @throws NoClassDefFoundError
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
    public static void testTasksClassNames(String[] tasks) throws NoClassDefFoundError, InstantiationException, IllegalAccessException, ClassNotFoundException {
    	
    	for (String task_info : tasks) {

    		if (!task_info.contains(TASK_MAIN_SEPARATOR))
    			throw new TaskException("You must close the task main with the signal ')'. " + task_info);
    		
    		if (task_info.contains(DEPENDENCY_SIGNAL) && task_info.contains(PARALLEL_SIGNAL))
    			throw new TaskException(task_info + "\nYou can't have a parallel '--' and a sequential '->' dependencies in a main task.");
    		
    		task_info = task_info.replace(TASK_MAIN_SEPARATOR, DEPENDENCY_SIGNAL).replace(PARALLEL_SIGNAL, DEPENDENCY_SIGNAL);
    		
    		String[] classesName = task_info.split(DEPENDENCY_SIGNAL);
    		
    		for (String name : classesName) {
    			String n = name;
    			
    			if (name.contains(TASKS_INFO_SEPARATOR))
    				n = name.substring(0, name.indexOf(TASKS_INFO_SEPARATOR));
    			
    			Class.forName(TASKS_PACKAGE + n).newInstance();
    		}
    	}
    }
}
