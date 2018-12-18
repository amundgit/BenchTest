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

import java.sql.SQLException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
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
    @ApiOperation(value = "Get all stored companies as JSON objects",notes = "Takes no arguments")
    public @ResponseBody Iterable<Company> getAllCompanies(){return companyRepository.findAll();}

    /**
     * Get all daily stored companies as JSON objects. Accessed by get call.
     * @return      List of all daily stored companies as JSON objects
     */
    @GetMapping(path = "/getalldaily")
    @ApiOperation(value = "Simple method to get all daily stored companies as JSON objects",notes = "Takes no arguments")
    public @ResponseBody Iterable<Company> getAllCompaniesDaily(){
        LocalDate current = LocalDate.now();
        return companyRepository.getByDate(current);
    }

    /**
     * Simple method to get all stored companies for a given date as JSON objects. Accessed by get call
     * @param searchDate    String containing the search date, format YYYY-MM-DD
     * @return              List of all stored companies for the given date as JSON objects
     */
    @GetMapping(path = "/getbydate")
    @ApiOperation(value = "Get all stored companies for a given date as JSON objects",notes = "Date should be a string of format YYYY-MM-DD")
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
    @ApiOperation(value = "Get all stored companies for a given company name as JSON objects",notes = "Company name should be a string")
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
    @ApiOperation(value = "Get specific company by company name and date as JSON object",notes = "Date should be a string of format YYYY-MM-DD, company name plain string")
    public @ResponseBody Company getCompanyByNameAndDate(@RequestParam(name = "companyName")String companyName,@RequestParam(name = "searchDate")String searchDate){
        String dateArr[] = searchDate.split("-");
        LocalDate date = LocalDate.of(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]),
                Integer.parseInt(dateArr[2]));
        return companyRepository.getByCompanyAndDate(companyName,date);
    }

    /**
     * Method to get highest number of relevant positions for a given company in a given month
     * @param searchDate    String containing search date, format YYYY-MM
     * @param companyName   String containing  company name
     * @return              Highest number of relevant positions as integer
     */
    @GetMapping(path = "/getrelevantpeakbymonthandcompany")
    @ApiOperation(value = "Get the largest number of relevant positions in given month, for given company", notes = "Date should be given as YYYY-MM, company name plain string")
    public @ResponseBody Integer getRelPeakByMonthAndCompany(@RequestParam(name = "searchDate")String searchDate, @RequestParam(name = "companyName")String companyName){
        int result = 0;
        YearMonth month = YearMonth.parse(searchDate);
        LocalDate start = month.atDay(1);
        LocalDate end = month.atEndOfMonth();
        List<Integer> relevantPositions = companyRepository.getRelNoByCompanyAndPeriod(companyName,start,end);
        for(Integer i : relevantPositions) {
            if (i > result) {
                result = i;
            }
        }
        return result;
    }

    /**
     *  Method to get a list of highest number of relevant positions per month, for a given company.
     * @param searchDate    int containing search year
     * @param companyName   String containing company name
     * @return              List of each month's highest number of relevant positions, as JSON
     */
    @GetMapping(path = "/getrelevantpeaksbyyearandcompany")
    @ApiOperation(value = "Get list of highest number of relevant positions per month, for given company in given year", notes = "Date should be year as int, company name plain string")
    public @ResponseBody Iterable<MonthInfo> getRelPeaksByYearAndCompany(@RequestParam(name = "searchDate")int searchDate, @RequestParam(name = "companyName")String companyName){
        List<MonthInfo> result = new ArrayList<>();
        YearMonth current = YearMonth.now();
        int countTo;
        if(current.getYear() == searchDate){
            //If we are checking this year, only go up to current month
            countTo = current.getMonthValue();
        } else if (current.getYear() < searchDate){
            //Check if trying to see the future
            countTo = -1;
        } else {
            //If we are checking a previous year, get all months
            countTo = 12;
        }
        for(int i=1;i<=countTo;i++){
            YearMonth month = YearMonth.of(searchDate,i);
            LocalDate start = month.atDay(1);
            LocalDate end = month.atEndOfMonth();
            List<Integer> relevantPositions = companyRepository.getRelNoByCompanyAndPeriod(companyName,start,end);
            int temp = 0;
            for(Integer j : relevantPositions) {
                if (j > temp) {
                    temp = j;
                }
            }
            result.add(new MonthInfo(month.getMonth().name(),temp,companyName));
        }
        return result;
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
    @ApiOperation(value = "Method to add a company with relevant data",notes = "This should only reasonably be accessed by the crawlers. Structure of JSON in java file/javadoc and on Github")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "body",
            dataType = "Company")
    })
    public @ResponseBody String addCompany(@RequestBody Map<String, Object> body){
        String returnString = "success";
        LocalDate currentDate = LocalDate.now();
        Date date = new Date();
        try {
            date = dateRepository.getByLocalDate(currentDate);
        } catch (SQLException s) {
            System.out.println("SQL Error");
            returnString = "failure";
            return returnString;
        } catch (Exception e) {
            System.out.println("Generic error");
        }
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
    @ApiOperation(value = "DEBUG METHOD: Method to add a company with relevant data, on specified date",notes = "Should only be used for debugging, not in production. Structure of JSON in java file/javadoc and on Github")
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