package factory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;

public class Util {
	
	private static final String ERROR_LOAD_FILE = "Some problem while loading properties file. Message: ";
	
	private static Properties prop;

	public static long delay_scheduler;
	public static double cpu_bound;
	public static String[] tasks;
	public static boolean print_cpu_low;

	public static String getCurrentHour() {
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}
	
	
	
	/**
	 * Function to get CPU usage as percentage
	 * @return double
	 * @throws Exception
	 */
	final public static double getProcessCPU() throws Exception {
		
	    MBeanServer mbs    = ManagementFactory.getPlatformMBeanServer();
	    ObjectName name    = ObjectName.getInstance("java.lang:type=OperatingSystem");
	    AttributeList list = mbs.getAttributes(name, new String[]{ "ProcessCpuLoad" });

	    if (list.isEmpty())
	    	return Double.NaN;

	    Attribute att = (Attribute)list.get(0);
	    Double value  = (Double)att.getValue();

	    if (value == -1.0)
	    	return Double.NaN;
	    
	    return ((int)(value * 1000) / 10.0);
	}
	
    /**
     * Method to load file properties
     * @throws IOException
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    final public static void loadProperties() {
    	prop = new Properties();

    	InputStream input = null;

    	try {

    		input = new FileInputStream("config.properties");

    		prop.load(input);

    		delay_scheduler = Long.parseLong(prop.getProperty("delay_scheduler_seconds")) * 1000 + 10; //+10 milliseconds
    		cpu_bound = Double.parseDouble(prop.getProperty("cpu_bound_percent"));
    		
    		tasks = prop.getProperty("list_of_tasks").split(TaskFactory.TASKS_SEPARATOR);
    		TaskFactory.testTasksClassNames(tasks);
    		
    		print_cpu_low = Boolean.parseBoolean(prop.getProperty("print_cpu_low"));

		} catch (Exception e) {
			System.err.println(ERROR_LOAD_FILE + e.getMessage());
			System.exit(-1);
		} catch (NoClassDefFoundError er) {
			System.err.println(ERROR_LOAD_FILE + er.getMessage());
			System.exit(-1);
		}
    	finally {
    		if (input != null) {
    			try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    	}
    }
}
