package api.Controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

    @Autowired
    PositionRepository positionRepository;

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
     * Get all stored companies in a month as JSON objects. Accessed by get call.
     * @param searchDate    String containing the search date, format YYYY-MM
     * @return      List of all daily stored companies as JSON objects
     */
    @GetMapping(path = "/getallmonthly")
    @ApiOperation(value = "Get all stored companies in a month as JSON objects",notes = "Date should be a string of format YYYY-MM")
    public @ResponseBody Iterable<Company> getAllCompaniesMonthly(@RequestParam(name = "searchDate")String searchDate){
        YearMonth month = YearMonth.parse(searchDate);
        LocalDate start = month.atDay(1);
        LocalDate end = month.atEndOfMonth();
        return companyRepository.getByPeriod(start,end);
    }

    /**
     * Get all stored data for given company in a given month as JSON objects. Accessed by get call.
     * @param companyName   String containing company name
     * @param searchDate    String containing the search date, format YYYY-MM
     * @return      List of all daily stored companies as JSON objects
     */
    @GetMapping(path = "/getmonthlybyname")
    @ApiOperation(value = "Get all stored data for given company in a given month as JSON objects",notes = "Date should be a string of format YYYY-MM, companyName should contain the name of the company")
    public @ResponseBody Iterable<Company> getMonthlyByName(@RequestParam(name = "companyName")String companyName,@RequestParam(name = "searchDate")String searchDate){
        YearMonth month = YearMonth.parse(searchDate);
        LocalDate start = month.atDay(1);
        LocalDate end = month.atEndOfMonth();
        return companyRepository.getByCompanyAndPeriod(companyName,start,end);
    }

    /**
     * Simple method to get all stored companies for a given date as JSON objects. Accessed by get call
     * @param searchDate    String containing the search date, format YYYY-MM-DD
     * @return              List of all stored companies for the given date as JSON objects
     */
    @GetMapping(path = "/getbydate")
    @ApiOperation(value = "Get all stored companies for a given date as JSON objects",notes = "Date should be a string of format YYYY-MM-DD")
    public @ResponseBody Iterable<Company> getCompaniesByDate(@RequestParam(name = "searchDate")String searchDate){
        LocalDate date = LocalDate.parse(searchDate);
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
        LocalDate date = LocalDate.parse(searchDate);
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
     * Method to get peak numbers for all values for a given company in a given month
     * @param searchDate    String containing search date, format YYYY-MM
     * @param companyName   String containing  company name
     * @return              Highest number of relevant positions as integer
     */
    @GetMapping(path = "/getallpeaksbymonthandcompany")
    @ApiOperation(value = "Get the largest number of relevant positions in given month, for given company", notes = "Date should be given as YYYY-MM, company name plain string")
    public @ResponseBody PeakInfo getAllPeaksByMonthAndCompany(@RequestParam(name = "searchDate")String searchDate, @RequestParam(name = "companyName")String companyName){
        YearMonth month = YearMonth.parse(searchDate);
        LocalDate start = month.atDay(1);
        LocalDate end = month.atEndOfMonth();
        return getPeaksByCompanyAndPeriod(companyName,start,end);
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
        Date date = dateRepository.getByLocalDate(currentDate);
        if(date == null){
            date = new Date();
            date = dateRepository.save(date);
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
        LocalDate currentDate = LocalDate.parse(body.get("date").toString());
        Date date = dateRepository.getByLocalDate(currentDate);
        if(date == null){
            date = new Date();
            date.setDate(currentDate);
            date = dateRepository.save(date);
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

    //SIMPLE, UNMAPPED UTILITY METHODS

    /**
     * Gets all peak values for a given company within a given period. IMPORTANT: To avoid confusion, start and end should be in the same calendar month.
     * If start and end are not in the same month, the month field of the PeakInfo will simply be the month of the start date.
     * @param companyName   String containing company name
     * @param start         LocalDate object with start date (SHOULD BE IN SAME CALENDAR MONTH AS END)
     * @param end           LocalDate object with end date (SHOULD BE IN SAME CALENDAR MONTH AS START)
     * @return              PeakInfo object containing all peak values
     */
    public PeakInfo getPeaksByCompanyAndPeriod(String companyName, LocalDate start, LocalDate end){
        PeakInfo result = new PeakInfo();
        List<Company> companies = companyRepository.getByCompanyAndPeriod(companyName,start,end);
        result.setItPositions(PositionController.getPeakByCompanyAndFieldAndPeriod(companyName,"IT",start,end,positionRepository));
        result.setFinancePositions(PositionController.getPeakByCompanyAndFieldAndPeriod(companyName,"Finance",start,end,positionRepository));
        result.setExecutivePositions(PositionController.getPeakByCompanyAndFieldAndPeriod(companyName,"Executive",start,end,positionRepository));
        result.setEngineeringPositions(PositionController.getPeakByCompanyAndFieldAndPeriod(companyName,"Engineering",start,end,positionRepository));
        for(Company c : companies){
            if(c.getTotalPositions() > result.getTotalPositions()){result.setTotalPositions(c.getTotalPositions());}
            if(c.getTempPositions() > result.getTempPositions()){result.setTempPositions(c.getTempPositions());}
            if(c.getPermanentPositions() > result.getPermanentPositions()){result.setPermanentPositions(c.getPermanentPositions());}
            if(c.getRelevantPositions() > result.getRelevantPositions()){result.setRelevantPositions(c.getRelevantPositions());}
            if(c.getRelevantTempPositions() > result.getRelevantTempPositions()){result.setRelevantTempPositions(c.getRelevantTempPositions());}
            if(c.getRelevantPermanentPositions() > result.getRelevantPermanentPositions()){result.setRelevantPermanentPositions(c.getRelevantPermanentPositions());}
        }
        result.setCompanyName(companyName);
        result.setMonth(start.getMonth().name());
        return result;
    }
}