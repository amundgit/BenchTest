package api.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ResponseBody;

import api.Repositories.*;
import api.Models.*;
import api.Pojos.*;

import api.*;

import java.util.Map;
import java.time.LocalDate;

@CrossOrigin
@Controller
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    DateRepository dateRepository;

    @GetMapping(path = "/getall")
    public @ResponseBody Iterable<Company> getAllCompanies(){return companyRepository.findAll();}

    @PostMapping(path = "/add")
    public @ResponseBody String addCompany(@RequestBody Map<String, Object> body){
        String returnString = "success";
        LocalDate currentDate = LocalDate.now();
        Date date = dateRepository.getByLocalDate(currentDate);
        String companyName = body.get("companyName").toString();
        int totalPositions = Integer.parseInt(body.get("totalPositions").toString());
        int tempPositions = Integer.parseInt(body.get("tempPositions").toString());
        int permanentPositions = Integer.parseInt(body.get("permanentPositions").toString());
        int relevantPositions = Integer.parseInt(body.get("relevantPositions").toString());
        int relevantTempPositions = Integer.parseInt(body.get("relevantTempPositions").toString());
        int relevantPermanentPositions = Integer.parseInt(body.get("relevantPermanentPositions").toString());
        Company newCompany = new Company(companyName, date, totalPositions, tempPositions, permanentPositions, relevantPositions, relevantTempPositions, relevantPermanentPositions);
        companyRepository.save(newCompany);
        return returnString;
    }
}

/*
@PostMapping(path = "/update")
	public @ResponseBody Object updateGoalType(@RequestBody Map<String, Object> body) {
		Messages msg = new Messages();
		msg = SecurityUtil.verifySession(body.get("sessionid").toString(), body.get("sessionuser").toString(), userRepository);
		if (msg.getRole() != 1) {
			return msg;
		} else {
			boolean check = true;
			Integer goal_type_id = Integer.parseInt(body.get("goal_type_id").toString());
			Goal_type goaltype = goal_typeRepository.getById(goal_type_id);
			if(goaltype != null){
				check = false;
				msg.setError("Error: Invalid id");
			}
			if (check) {
				String type = body.get("type").toString();
				goaltype.setType(type);
				goal_typeRepository.save(goaltype);
				// Return the id the new address got in the database.
				msg.setMessage(goaltype.getId().toString());
			}
			return msg;
		}
	}
 */