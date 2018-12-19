package bg.sirma.employees.model;

public class Project {

	private int id;
	private long daysWorked;

	public Project(int id, long daysWorked) {
		this.id = id;
		this.daysWorked = daysWorked;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getDaysWorked() {
		return daysWorked;
	}

	public void setDaysWorked(long daysWorked) {
		this.daysWorked = daysWorked;
	}

}
