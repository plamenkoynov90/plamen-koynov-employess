package bg.sirma.employees.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import bg.sirma.employees.model.Employee;
import bg.sirma.employees.model.Project;
import bg.sirma.employees.model.Team;
import bg.sirma.employees.utils.DateUtils;

@Service
public class EmployeesService {

	public Team getMostWorkTogether(MultipartFile file) throws IOException, ParseException {
		final String fileContent = new String(file.getBytes());
		final String[] employeesInfoRaw = fileContent.split("\r\n");
		final Map<Integer, List<Employee>> project2employees = mapByProjectID(employeesInfoRaw);
		final Map<String, Team> teams = mapByTeams(project2employees);
		final TreeSet<Team> sortedTeams = new TreeSet<>(teams.values());
		return sortedTeams.first();
	}

	private Map<Integer, List<Employee>> mapByProjectID(final String[] employeesInfoRaw) throws ParseException {
		final Map<Integer, List<Employee>> project2employees = new HashMap<>();
		for (final String employeeInfoRaw : employeesInfoRaw) {
			final String[] employeeInfoSplited = employeeInfoRaw.split(",\\s*");
			final int employeeId = Integer.parseInt(employeeInfoSplited[0]);
			final int projectId  = Integer.parseInt(employeeInfoSplited[1]);
			final String from = employeeInfoSplited[2];
			final String to = employeeInfoSplited[3];
			final Date workedFrom = DateUtils.formatDate(from);
			if(workedFrom == null) {
				throw new ParseException();
			}
			Date workedTo = null;
			if (to.equals("NULL")) {
				workedTo = new Date();
			} else {
				workedTo = DateUtils.formatDate(to);
				if(workedTo == null) {
					throw new ParseException();
				}
			}
			final Employee employee = new Employee(employeeId, workedFrom, workedTo);
			project2employees.putIfAbsent(projectId, new ArrayList<>());
			project2employees.get(projectId).add(employee);
		}
		return project2employees;
	}
	
	private Map<String, Team> mapByTeams(final Map<Integer, List<Employee>> project2employees) {
		Map<String, Team> teams = new HashMap<>();
		for (Integer projectId : project2employees.keySet()) {
			List<Employee> projectEmployees = project2employees.get(projectId);
			for (int i = 0; i < projectEmployees.size(); i++) {
				Employee employee1 = projectEmployees.get(i);
				for (int j = i + 1; j < projectEmployees.size(); j++) {
					Employee employee2 = projectEmployees.get(j);
					if(employee1.workedTogether(employee2)) {
						Date fromWorkedTogether = employee1.getFrom().getTime() >= employee2.getFrom().getTime() ? employee1.getFrom() : employee2.getFrom();
						Date toWorkedTogether   = employee1.getTo().getTime() >= employee2.getTo().getTime() ? employee2.getTo() : employee1.getTo();
						long daysWorkedTogether = (toWorkedTogether.getTime() - fromWorkedTogether.getTime()) / (1000 * 60 * 60 * 24) + 1;
						String key = employee1.getId() < employee2.getId() ? employee1.getId() + "-" + employee2.getId() : employee2.getId() + "-" + employee1.getId();
						Project project = new Project(projectId, daysWorkedTogether);
						if(!teams.containsKey(key)) {
							Team team = new Team(employee1.getId(), employee2.getId());
							teams.put(key, team);
						}
						teams.get(key).addProject(project);
						teams.get(key).setTotalDaysWorked(daysWorkedTogether);
					}
				}
			}
		}
		return teams;
	}
}
