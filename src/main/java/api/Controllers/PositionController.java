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

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping("/positions")
public class PositionController {
    @Autowired
    PositionRepository positionRepository;

    @Autowired
    DateRepository dateRepository;

    @Autowired
    CompanyRepository companyRepository;

    /**
     * Simple method to get all stored positions as JSONs. Accessed by get call.
     * @return      List of all stored positions as JSONs
     */
    @GetMapping(path = "/getall")
    public @ResponseBody Iterable<Position> getAllPositions(){return positionRepository.findAll();}

    /**
     * Method to get all stored positions in a given field as JSONs. Accessed by get call.
     * @param field String containing the field name.
     * @return      List of all stored positions in given field as JSONs
     */
    @GetMapping(path = "/getbyfield")
    public @ResponseBody Iterable<Position> getPositionsByField(@RequestParam(name = "field")String field){return positionRepository.getByField(field);}

    /**
     * Method to get all stored positions in a given field, for a given company, as JSONs. Accessed by get call.
     * @param field String containing the field name.
     * @param companyName String containing company name.
     * @return      List of all stored positions in given field as JSONs
     */
    @GetMapping(path = "/getbyfieldandcompany")
    public @ResponseBody Iterable<Position> getPositionsByFieldAndCompanyName(@RequestParam(name = "field")String field, @RequestParam(name = "companyName")String companyName){
        return positionRepository.getByFieldAndCompanyName(field,companyName);
    }

    @GetMapping(path = "/countbyfieldandcompany")
    public @ResponseBody Integer countPositionsByFieldAndCompanyName(@RequestParam(name = "field")String field, @RequestParam(name = "companyName")String companyName){
        List<Integer> list= positionRepository.countByFieldAndCompanyName(field,companyName);
        int count = 0;
        for(Integer i : list){
            count += i;
        }
        return count;
    }

    @GetMapping(path = "/countbycompany")
    public @ResponseBody Integer countPositionsByCompanyName(@RequestParam(name = "companyName")String companyName){
        List<Integer> list= positionRepository.countByCompanyName(companyName);
        int count = 0;
        for(Integer i : list){
            count += i;
        }
        return count;
    }

    @GetMapping(path = "/countbycompanyanddate")
    public @ResponseBody Integer countPositionsByCompanyNameAndDate(@RequestParam(name = "companyName")String companyName, @RequestParam(name = "searchDate")String searchDate){
        String dateArr[] = searchDate.split("-");
        LocalDate date = LocalDate.of(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]),
                Integer.parseInt(dateArr[2]));
        List<Integer> list= positionRepository.countByCompanyNameAndDate(companyName,date);
        int count = 0;
        for(Integer i : list){
            count += i;
        }
        return count;
    }

    /**
     * Lets the user add a position to the database with current date. Accessed by post request containing a JSON with necessary data.
     * JSON needs to contain variables companyName, positionDuration, positionName, noOfPositions and field.
     * JSON is permitted, but not required, to also contain variables area and customer.
     * All variables are accepted as strings, and then converted as necessary.
     * If area and/or customer are not present, a message is printed in the server log, and they default to an empty string.
     * @param body  JSON body containing required parameters
     * @return      A string indicating if the position was created, or already existed
     */
    @PostMapping(path = "/add")
    public @ResponseBody String addPosition(@RequestBody Map<String, Object> body){
        String returnString = "success";
        LocalDate currentDate = LocalDate.now();
        String companyName = body.get("companyName").toString();
        String positionDuration = body.get("positionDuration").toString();
        String positionName = body.get("positionName").toString();
        int noOfPositions = Integer.parseInt(body.get("noOfPositions").toString());
        String field = body.get("field").toString();
        Position existenceCheck = positionRepository.exists(currentDate,companyName,positionDuration,positionName,noOfPositions,field);
        if(existenceCheck == null){
            Date date = dateRepository.getByLocalDate(currentDate);
            Company company = companyRepository.getByCompanyAndDate(companyName, currentDate);
            Position newPosition = new Position(date, company, positionDuration, positionName, noOfPositions, field);
            try {
                String area = body.get("area").toString();
                newPosition.setArea(area);
            } catch (Exception e){
                System.out.println("Area not found");
            }
            try {
                String customer = body.get("customer").toString();
                newPosition.setCustomer(customer);
            } catch (Exception e){
                System.out.println("Customer not found");
            }
            positionRepository.save(newPosition);
        }else{
            returnString = "Position already exists";
        }

        return returnString;
    }

    /**
     * A test to check if a given position is unique. This is mostly meant for testing, as the functionality is a part of the add function. Accessed by post request containing a JSON with the necessary data.
     * @param body  a json body containing String parameters required to test for uniqueness: companyName, positionDuration, positionName, noOfPositions and field
     * @return      returns a string indicating whether the position is unique or not
     */
    @PostMapping(path = "/uniquetest")
    public @ResponseBody String uniqueTest(@RequestBody Map<String, Object> body){
        String returnString = "is a new position";
        LocalDate currentDate = LocalDate.now();
        String companyName = body.get("companyName").toString();
        String positionDuration = body.get("positionDuration").toString();
        String positionName = body.get("positionName").toString();
        int noOfPositions = Integer.parseInt(body.get("noOfPositions").toString());
        String field = body.get("field").toString();
        Position test = positionRepository.exists(currentDate,companyName,positionDuration,positionName,noOfPositions,field);
        if(test != null){
            returnString = "already exists";
        }
        return returnString;
    }

    /**
     * DEBUG FUNCTION: Lets the user add a position to the database with a specified date. Not intended for release use. Accessed by post request containing a JSON with necessary data.
     * JSON needs to contain variables date, companyName, positionDuration, positionName, noOfPositions and field.
     * JSON is permitted, but not required, to also contain variables area and customer.
     * All variables are accepted as strings, and then converted as necessary.
     * If area and/or customer are not present, a message is printed in the server log, and they default to an empty string.
     * @param body  JSON body containing required parameters
     * @return      A string indicating if the position was created, or already existed
     */
    @PostMapping(path = "/addTest")
    public @ResponseBody String addPositionTest(@RequestBody Map<String, Object> body){
        String returnString = "success";
        String dateArr[] = body.get("date").toString().split("-");
        LocalDate currentDate = LocalDate.of(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]),
                Integer.parseInt(dateArr[2]));
        String companyName = body.get("companyName").toString();
        String positionDuration = body.get("positionDuration").toString();
        String positionName = body.get("positionName").toString();
        int noOfPositions = Integer.parseInt(body.get("noOfPositions").toString());
        String field = body.get("field").toString();
        Position existenceCheck = positionRepository.exists(currentDate,companyName,positionDuration,positionName,noOfPositions,field);
        if(existenceCheck == null){
            Date date = dateRepository.getByLocalDate(currentDate);
            Company company = companyRepository.getByCompanyAndDate(companyName, currentDate);
            Position newPosition = new Position(date, company, positionDuration, positionName, noOfPositions, field);
            try {
                String area = body.get("area").toString();
                newPosition.setArea(area);
            } catch (Exception e){
                System.out.println("Area not found");
            }
            try {
                String customer = body.get("customer").toString();
                newPosition.setCustomer(customer);
            } catch (Exception e){
                System.out.println("Customer not found");
            }
            positionRepository.save(newPosition);
        }else{
            returnString = "Position already exists";
        }

        return returnString;
    }
}
