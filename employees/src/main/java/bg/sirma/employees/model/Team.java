package bg.sirma.employees.model;

import java.util.ArrayList;
import java.util.List;

public class Team implements Comparable<Team>{

	private int employee1ID;
	private int employee2ID;
	private List<Project> projects = new ArrayList<>();
	private long totalDaysWorked;

	
	public Team(int employee1id, int employee2id) {
		employee1ID = employee1id;
		employee2ID = employee2id;
	}
	
	public void addProject(Project project) {
		this.projects.add(project);
	}

	public int getEmployee1ID() {
		return employee1ID;
	}

	public void setEmployee1ID(int employee1id) {
		employee1ID = employee1id;
	}

	public int getEmployee2ID() {
		return employee2ID;
	}

	public void setEmployee2ID(int employee2id) {
		employee2ID = employee2id;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public long getTotalDaysWorked() {
		return totalDaysWorked;
	}

	public void setTotalDaysWorked(long totalDaysWorked) {
		this.totalDaysWorked += totalDaysWorked;
	}

	@Override
	public int compareTo(Team team) {
		return Long.compare(team.getTotalDaysWorked(), this.totalDaysWorked);
	}
}
