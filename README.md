## Java-Task-Scheduler

-------
##### HOW TO CREATE A TASK
-------

1. Create a class "Task1" (or the name you want) inside the package "task" that extends **_factory.Task class_**;
2. You must override the method **_"executeTask()"_** that returns a String;
3. You can display, in the Scheduler execution, the result from a Task by returning some String;
4. You can also save some result to an Object type variable "response" in order to use it in some dependent task;

-------
##### HOW TO CREATE A DEPENDENCY TASK
-------

1. All the steps from "How to create a Task";
2. You must certify if the Task has a response from a Father Task by using the function **_"hasFatherTaskResponse()"_**;
3. There is also ways to check the response type **_"isFatherResponseType(Class<T> type)"_** or the way you prefer, before casting the Object to the type you wanted;

-------
##### HOW TO INSERT THE TASK IN THE LIST TO BE EXCECUTED BY THE SCHEDULER
-------

1. Open **config.properties** file;

2. You can change an existing property "list_of_tasks", or comment (//) and create another one;

3. The syntax for creating a unique task with no dependency in the list is:
  * _name_father_task_class:fixed_rate,given_delay)_;
  ```java
  Fibonacci46:60,0);
  ```
  * The scheduler will run the Fibonacci46 Task every 60 seconds, with a zero second delay for a dependency (there is none).

4. The syntax for creating a task with 1 or more parallel dependencies. 
  * You must separate the tasks by using the **--** symbol and the "**:given_delay**" words in dependencies are not mandatory;
  * _name_father_task_class:fixed_rate,given_delay)name_class_task1:given_delay--name_class_task2:given_delay--name_class_taskN:given_delay_;
  ```java
  SortingCreateArrays:60,5)SortingBubble--SortingInsertion;
  ```
  * As the dependencies run in parallel, it's not necessary to specify a given delay, as it's show in the above example.
 
5. The syntax for creating a task with 1 or more sequential dependencies. 
  * You must separate the tasks by using the **->** symbol and the "**:given_delay**" words are not mandatory;
  * _name_father_task_class:fixed_rate,given_delay)name_class_task1:given_delay->name_class_task2:given_delay->name_class_taskN:given_delay_;
  ```java
  BitcoinInfo:120,10)BitcoinPrice:5->BitcoinEvaluate;
  ```
  * This task will be running every 2 minutes. The father task will get all the bitcoin info, waits for 10 seconds and schedule the dependency task (BitcoinPrice). The BitcoinPrice will run and after 5 seconds it finishes, will pass information for its dependency task (BitcoinEvaluate) to evaluate.

6. The syntax to run more than one father task in parallel.
  * You must separate the task by the symbol **;**.
  * _name_father_task_class:fixed_rate,given_delay)[dependencies];name_father_task_class:fixed_rate,given_delay)[dependencies]_;
  ```java
  SortingCreateArrays:60,5)SortingBubble;Fibonacci45:0,10);Fibonacci46:0,10);
  ```
  * In the example above, the SortingCreateArrays task will be running every 60 seconds. Fibonacci45 and Fibonacci46 tasks will run just one time because the fixed_rate is 0 (zero).

7. You cannot have a father task with sequential **->** and parallel **--** dependencies.
  ```java
  BitcoinInfo:120,10)BitcoinPrice:5->BitcoinEvaluate--Fibonacci46;
  ```