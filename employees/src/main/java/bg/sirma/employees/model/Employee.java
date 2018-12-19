package bg.sirma.employees.model;

import java.util.Date;

public class Employee {

	private int id;
	private Date from;
	private Date to;

	public Employee(int id, Date from, Date to) {
		this.setId(id);
		this.from = from;
		this.to = to;
	}

	public boolean workedTogether(Employee employee) {
		if (this.to.before(employee.getFrom()) || this.from.after(employee.getTo())) {
			return false;
		}
		return true;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

}
