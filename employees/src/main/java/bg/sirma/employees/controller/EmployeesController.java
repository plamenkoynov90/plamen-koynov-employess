package bg.sirma.employees.controller;

import java.io.IOException;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import bg.sirma.employees.model.Team;
import bg.sirma.employees.service.EmployeesService;

@Controller
public class EmployeesController {

	@Autowired
	private EmployeesService employeesService;

	@GetMapping("/")
	public String home(Model model) {
		
		return "home/home";
	}

	@PostMapping("/result")
	public String handleUploadedFile(@RequestParam("file") MultipartFile file, Model model, RedirectAttributes redirectAttributes){

		Team mostWorkedTogether = null;
		try {
			mostWorkedTogether = this.employeesService.getMostWorkTogether(file);
			model.addAttribute("team", mostWorkedTogether);
		} catch (IOException e) {
			model.addAttribute("error", "Problem while reading the file. Please try again!" );
		} catch (ParseException e) {
			model.addAttribute("error", "Date format not supported!" );
		} catch (Exception e) {
			model.addAttribute("error", "There was some problem reading the file. Please try again!");
		}
		return "home/result";
	}
	
}
