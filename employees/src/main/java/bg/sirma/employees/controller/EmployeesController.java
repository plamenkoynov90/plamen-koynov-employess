package bg.sirma.employees.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import bg.sirma.employees.model.Team;
import bg.sirma.employees.service.EmployeesService;

@Controller
public class EmployeesController {

	@Autowired
	private EmployeesService employeesService;

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("teams", new TreeSet<>());
		return "home/home";
	}

	@PostMapping("/")
	public String handleUploadedFile(@RequestParam("file") MultipartFile file, Model model) throws IOException, ParseException {

		Team mostWorkedTogether = this.employeesService.getMostWorkTogether(file);
		model.addAttribute("team", mostWorkedTogether);
		return "home/result";
	}
	
}
