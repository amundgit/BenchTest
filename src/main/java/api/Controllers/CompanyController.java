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

    @PostMapping(path = "/addtest")
    public @ResponseBody String addCompanyTest(@RequestBody Map<String, Object> body){
        String returnString = "success";
        String dateArr[] = body.get("date").toString().split("-");
        LocalDate currentDate = LocalDate.of(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]),
                Integer.parseInt(dateArr[2]));
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