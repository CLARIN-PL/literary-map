package pl.edu.pwr.litmap.tasksManager;

public interface Task {

	public void run();
	public Status getStatus();
	public Result getResult();
	public boolean equals(Task t);
}
