package api.Controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ExampleProperty;
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

    /**
     * Simple method to get all stored companies as JSON objects. Accessed by get call.
     * @return      List of all stored companies as JSON objects
     */
    @GetMapping(path = "/getall")
    public @ResponseBody Iterable<Company> getAllCompanies(){return companyRepository.findAll();}

    /**
     * Simple method to get all stored companies for a given date as JSON objects. Accessed by get call
     * @param searchDate    String containing the search date, format YYYY-MM-DD
     * @return              List of all stored companies for the given date as JSON objects
     */
    @GetMapping(path = "/getbydate")
    public @ResponseBody Iterable<Company> getCompaniesByDate(@RequestParam(name = "searchDate")String searchDate){
        String dateArr[] = searchDate.split("-");
        LocalDate date = LocalDate.of(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]),
                Integer.parseInt(dateArr[2]));
        return companyRepository.getByDate(date);
    }

    /**
     * Simple method to get all stored companies for a given company name as JSON objects. Accessed by get call
     * @param companyName   String containing company name
     * @return              List of all stored companies for given name as JSON objects
     */
    @GetMapping(path = "getbyname")
    public @ResponseBody Iterable<Company> getCompaniesByName(@RequestParam(name = "companyName")String companyName){
        return companyRepository.getByCompany(companyName);
    }

    /**
     * Method to get specific company by company name and date as JSON object. Accessed by get call
     * @param companyName   String containing company name
     * @param searchDate    String containing search date, format YYYY-MM-DD
     * @return              Company with given name and date, as JSON object
     */
    @GetMapping(path = "getbynameanddate")
    public @ResponseBody Company getCompanyByNameAndDate(@RequestParam(name = "companyName")String companyName,@RequestParam(name = "searchDate")String searchDate){
        String dateArr[] = searchDate.split("-");
        LocalDate date = LocalDate.of(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]),
                Integer.parseInt(dateArr[2]));
        return companyRepository.getByCompanyAndDate(companyName,date);
    }

    /**
     * Method to add a company with relevant data, accessed by post call. Takes a JSON object containing variables companyName, totalPositions, tempPositions, permanentPositions, relevantPositions, relevantTempPositions and relevantPermanentPositions, all strings.
     * Adds company with the above data and current date.
     * Can also be used to update an existing company, given that company name and date are equal to an existing company: Takes the same variables and overwrites previously stored ones.
     * Returns a string to confirm success.
     * @param body  JSON object containing necessary variables, as mentioned above.
     * @return      Confirmation String
     */
    @PostMapping(path = "/add")
    @ApiOperation(value = "/add")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "body",
            dataType = "Company",
            examples = @io.swagger.annotations.Example(
                    value = {@ExampleProperty(value = "{'permanentPositions': '13', 'relevantPositions': '10'}", mediaType = "application/json")}
            ))
    })
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

    /**
     * DEBUG METHOD: Allows creation of company with a specified date. Not intended for release use. Takes a String date in addition to the variables in the normal function.
     * Method to add a company with relevant data, accessed by post call. Takes a JSON object containing variables companyName, totalPositions, tempPositions, permanentPositions, relevantPositions, relevantTempPositions and relevantPermanentPositions, all strings.
     * Adds company with the above data and current date.
     * Can also be used to update an existing company, given that company name and date are equal to an existing company: Takes the same variables and overwrites previously stored ones.
     * Returns a string to confirm success.
     * @param body  JSON object containing necessary variables, as mentioned above.
     * @return      Confirmation String
     */
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