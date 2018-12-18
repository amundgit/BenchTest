package api.Controllers;

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


    //BASIC QUERIES

    /**
     * Simple method to get a stored position by its id. Accessed by get call
     * @param id    Id as Integer value
     * @return      Position with corresponding id as JSON
     */
    @GetMapping(path = "/getbyid")
    @ApiOperation(value = "Simple method to get a stored position by its id", notes = "Mostly for completion. Id should be an integer")
    public @ResponseBody Position getById(@RequestParam(name = "id")Integer id){
        return positionRepository.getById(id);
    }

    /**
     * Simple method to get all stored positions as JSONs. Accessed by get call.
     * @return      List of all stored positions as JSONs
     */
    @GetMapping(path = "/getall")
    @ApiOperation(value = "Get all stored positions as JSONs",notes = "Takes no arguments")
    public @ResponseBody Iterable<Position> getAllPositions(){return positionRepository.findAll();}

    /**
     * Simple method to get all daily stored positions as JSONs. Accessed by get call.
     * @return      List of all stored positions as JSONs
     */
    @GetMapping(path = "/getalldaily")
    @ApiOperation(value = "Get all daily stored positions as JSONs",notes = "Takes no arguments")
    public @ResponseBody Iterable<Position> getAllPositionsDaily(){
        LocalDate current = LocalDate.now();
        return positionRepository.getByDate(current);
    }

    /**
     * Method to get all stored positions in a given field as JSONs. Accessed by get call.
     * @param field String containing the field name.
     * @return      List of all stored positions in given field as JSONs
     */
    @GetMapping(path = "/getbyfield")
    @ApiOperation(value = "Get all stored positions in a given field as JSONs",notes = "Field should be a string corresponding to an existing field(currently IT, Executive, Engineering, Finance or Other)")
    public @ResponseBody Iterable<Position> getPositionsByField(@RequestParam(name = "field")String field){return positionRepository.getByField(field);}

    /**
     * Method to get all stored positions for a given company name as JSONs. Accessed by get call.
     * @param companyName String containing the name of the company
     * @return      List of all stored positions for given company name as JSONs
     */
    @GetMapping(path = "/getbycompanyname")
    @ApiOperation(value = "Get all stored positions for a given company name as JSONs",notes = "Company name should be a string")
    public @ResponseBody Iterable<Position> getPositionsByCompanyName(@RequestParam(name = "companyName")String companyName){return positionRepository.getByCompanyName(companyName);}

    /**
     * Method to get all stored positions for a given duration as JSONs. Accessed by get call.
     * @param duration String containing the duration (temporary/permanent or similar)
     * @return      List of all stored positions for given duration as JSONs
     */
    @GetMapping(path = "/getbyduration")
    @ApiOperation(value = "Get all stored positions for a given duration as JSONs",notes = "Duration should be a string containing the duration")
    public @ResponseBody Iterable<Position> getPositionsByDuration(@RequestParam(name = "duration")String duration){return positionRepository.getByDuration(duration);}

    /**
     * Method to get all stored positions for a given date as JSONs. Accessed by get call.
     * @param searchDate String containing the search date, format YYYY-MM-DD.
     * @return      List of all stored positions for given date as JSONs
     */
    @GetMapping(path = "/getbydate")
    @ApiOperation(value = "Get all stored positions for a given date as JSONs",notes = "Date should be a string of format YYYY-MM-DD")
    public @ResponseBody Iterable<Position> getPositionsByDate(@RequestParam(name = "searchDate")String searchDate){
        String dateArr[] = searchDate.split("-");
        LocalDate date = LocalDate.of(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]),
                Integer.parseInt(dateArr[2]));
        return positionRepository.getByDate(date);
    }

    /**
     * Method to get all stored positions in a given field, for a given company, as JSONs. Accessed by get call.
     * @param field String containing the field name.
     * @param companyName String containing company name.
     * @return      List of all stored positions in given field, for given company, as JSONs
     */
    @GetMapping(path = "/getbyfieldandcompany")
    @ApiOperation(value = "Get all stored positions in a given field, for a given company, as JSONs",notes = "Takes field and companyName as separate strings")
    public @ResponseBody Iterable<Position> getPositionsByFieldAndCompanyName(@RequestParam(name = "field")String field, @RequestParam(name = "companyName")String companyName){
        return positionRepository.getByFieldAndCompanyName(field,companyName);
    }

    /**
     * Method to get all stored positions in a given field, with a given duration, as JSONs. Accessed by get call.
     * @param field             String containing the field name.
     * @param positionDuration  String containing the duration (temporary/permanent or similar)
     * @return                  List of all stored positions in given field, with given duration, as JSONs
     */
    @GetMapping(path = "/getbyfieldandduration")
    @ApiOperation(value = "Get all stored positions in a given field, with a given duration, as JSONs",notes = "Takes field and positionDuration as separate strings")
    public @ResponseBody Iterable<Position> getPositionsByFieldAndDuration(@RequestParam(name = "field")String field, @RequestParam(name = "positionDuration")String positionDuration){
        return positionRepository.getByFieldAndDuration(field,positionDuration);
    }

    /**
     * Method to get all stored positions with a given duration, for a given company, as JSONs. Accessed by get call.
     * @param positionDuration  String containing the duration (temporary/permanent or similar)
     * @param companyName       String containing company name.
     * @return                  List of all stored positions with given duration, for given company, as JSONs
     */
    @GetMapping(path = "/getbydurationandcompany")
    @ApiOperation(value = "Get all stored positions with a given duration, for a given company, as JSONs",notes = "Takes positionDuration and companyName as separate strings")
    public @ResponseBody Iterable<Position> getPositionsByDurationAndCompanyName(@RequestParam(name = "positionDuration")String positionDuration, @RequestParam(name = "companyName")String companyName){
        return positionRepository.getByDurationAndCompanyName(positionDuration,companyName);
    }

    //BASIC QUERIES WITH DATE
    /**
     * Method to get all stored positions for a given company, on a given date, as JSONs. Accessed by get call.
     * @param companyName   String containing company name
     * @param searchDate    String containing search date, format YYYY-MM-DD
     * @return              List of all stored positions for given company and date as JSONs
     */
    @GetMapping(path = "/getbycompanyanddate")
    @ApiOperation(value = "Get all stored positions for a given company, on a given date, as JSONs",notes = "Takes companyName and searchDate as separate strings")
    public @ResponseBody Iterable<Position> getPositionsByCompanyNameAndDate(@RequestParam(name = "companyName")String companyName, @RequestParam(name = "searchDate")String searchDate){
        String dateArr[] = searchDate.split("-");
        LocalDate date = LocalDate.of(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]),
                Integer.parseInt(dateArr[2]));
        return positionRepository.getByCompanyNameAndDate(companyName, date);
    }

    /**
     * Method to get all stored positions for a given field, on a given date, as JSONs. Accessed by get call.
     * @param field         String containing the field name
     * @param searchDate    String containing search date, format YYYY-MM-DD
     * @return              List of all stored positions for given field and date as JSONs
     */
    @GetMapping(path = "/getbyfieldanddate")
    @ApiOperation(value = "Get all stored positions for a given field, on a given date, as JSONs",notes = "Takes field and searchDate as separate strings")
    public @ResponseBody Iterable<Position> getPositionsByFieldAndDate(@RequestParam(name = "field")String field, @RequestParam(name = "searchDate")String searchDate){
        String dateArr[] = searchDate.split("-");
        LocalDate date = LocalDate.of(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]),
                Integer.parseInt(dateArr[2]));
        return positionRepository.getByFieldAndDate(field, date);
    }

    /**
     * Method to get all stored positions for a given duration, on a given date, as JSONs. Accessed by get call.
     * @param positionDuration  String containing the duration (temporary/permanent or similar)
     * @param searchDate    String containing search date, format YYYY-MM-DD
     * @return              List of all stored positions for given duration and date as JSONs
     */
    @GetMapping(path = "/getbydurationanddate")
    @ApiOperation(value = "Get all stored positions for a given duration, on a given date, as JSONs",notes = "Takes positionDuration and searchDate as separate strings")
    public @ResponseBody Iterable<Position> getPositionsByDurationAndDate(@RequestParam(name = "positionDuration")String positionDuration, @RequestParam(name = "searchDate")String searchDate){
        String dateArr[] = searchDate.split("-");
        LocalDate date = LocalDate.of(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]),
                Integer.parseInt(dateArr[2]));
        return positionRepository.getByDurationAndDate(positionDuration, date);
    }

    /**
     * Method to get all stored positions in a given field, for a given company, on a given date, as JSONs. Accessed by get call.
     * @param field         String containing the field name
     * @param companyName   String containing company name
     * @param searchDate    String containing search date, format YYYY-MM-DD
     * @return              List of all stored positions for given field, company and date as JSONs
     */
    @GetMapping(path = "/getbyfieldandcompanyanddate")
    @ApiOperation(value = "Get all stored positions in a given field, for a given company, on a given date, as JSONs",notes = "Takes field, companyName and searchDate as separate strings")
    public @ResponseBody Iterable<Position> getPositionsByFieldAndCompanyNameAndDate(@RequestParam(name = "field")String field, @RequestParam(name = "companyName")String companyName, @RequestParam(name = "searchDate")String searchDate){
        String dateArr[] = searchDate.split("-");
        LocalDate date = LocalDate.of(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]),
                Integer.parseInt(dateArr[2]));
        return positionRepository.getByFieldAndCompanyNameAndDate(field, companyName, date);
    }

    /**
     * Method to get all stored positions in a given field, for a given company, on a given date, as JSONs. Accessed by get call.
     * @param field             String containing the field name
     * @param positionDuration  String containing the duration (temporary/permanent or similar)
     * @param searchDate        String containing search date, format YYYY-MM-DD
     * @return                  List of all stored positions for given field, company and date as JSONs
     */
    @GetMapping(path = "/getbyfieldanddurationanddate")
    @ApiOperation(value = "Get all stored positions in a given field, for a given company, on a given date, as JSONs",notes = "Takes field, positionDuration and searchDate as separate strings")
    public @ResponseBody Iterable<Position> getPositionsByFieldAndDurationAndDate(@RequestParam(name = "field")String field, @RequestParam(name = "positionDuration")String positionDuration, @RequestParam(name = "searchDate")String searchDate){
        String dateArr[] = searchDate.split("-");
        LocalDate date = LocalDate.of(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]),
                Integer.parseInt(dateArr[2]));
        return positionRepository.getByFieldAndDurationAndDate(field, positionDuration, date);
    }

    /**
     * Method to get all stored positions with a given duration, for a given company, on a given date, as JSONs. Accessed by get call.
     * @param positionDuration  String containing the duration (temporary/permanent or similar)
     * @param companyName       String containing company name
     * @param searchDate        String containing search date, format YYYY-MM-DD
     * @return                  List of all stored positions for given field, company and date as JSONs
     */
    @GetMapping(path = "/getbydurationandcompanyanddate")
    @ApiOperation(value = "Get all stored positions with a given duration, for a given company, on a given date, as JSONs",notes = "Takes positionDuration, companyName and searchDate as separate strings")
    public @ResponseBody Iterable<Position> getPositionsByDurationAndCompanyNameAndDate(@RequestParam(name = "positionDuration")String positionDuration, @RequestParam(name = "companyName")String companyName, @RequestParam(name = "searchDate")String searchDate){
        String dateArr[] = searchDate.split("-");
        LocalDate date = LocalDate.of(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]),
                Integer.parseInt(dateArr[2]));
        return positionRepository.getByDurationAndCompanyNameAndDate(positionDuration, companyName, date);
    }

    //BASIC COUNT QUERIES

    /**
     * Method to count the number of positions from a given company. Returns an int with the count, accessed by get call.
     * @param companyName   String containing the name of the company
     * @return              An int with the number of positions
     */
    @GetMapping(path = "/countbycompany")
    @ApiOperation(value = "Count the number of positions from a given company",notes = "companyName should be a string containing the name of the company")
    public @ResponseBody Integer countPositionsByCompanyName(@RequestParam(name = "companyName")String companyName){
        List<Integer> list= positionRepository.countByCompanyName(companyName);
        int count = 0;
        for(Integer i : list){
            count += i;
        }
        return count;
    }

    /**
     * Method to count the number of positions in a given field. Returns an int with the count, accessed by get call.
     * @param field   String containing the name of the field
     * @return        An int with the number of positions
     */
    @GetMapping(path = "/countbyfield")
    @ApiOperation(value = "Count the number of positions in a given field",notes = "field should be a string containing the name of the field(currently IT, Executive, Engineering, Finance or Other)")
    public @ResponseBody Integer countPositionsByField(@RequestParam(name = "field")String field){
        List<Integer> list= positionRepository.countByField(field);
        int count = 0;
        for(Integer i : list){
            count += i;
        }
        return count;
    }

    /**
     * Method to count the number of positions with a given duration. Returns an int with the count, accessed by get call.
     * @param positionDuration  String containing the duration (temporary/permanent or similar)
     * @return                  An int with the number of positions
     */
    @GetMapping(path = "/countbyduration")
    @ApiOperation(value = "Count the number of positions with a given duration",notes = "positionDuration should be a string containing the duration")
    public @ResponseBody Integer countPositionsByDuration(@RequestParam(name = "positionDuration")String positionDuration){
        List<Integer> list= positionRepository.countByDuration(positionDuration);
        int count = 0;
        for(Integer i : list){
            count += i;
        }
        return count;
    }

    /**
     * Method to count the number of positions in a given field, from a given company. Returns an int with the count, accessed by get call.
     * @param field         String containing the name of the field
     * @param companyName   String containing the name of the company
     * @return              An int with the number of positions
     */
    @GetMapping(path = "/countbyfieldandcompany")
    @ApiOperation(value = "Count the number of positions in a given field, from a given company",notes = "Takes field and companyName as separate strings")
    public @ResponseBody Integer countPositionsByFieldAndCompanyName(@RequestParam(name = "field")String field, @RequestParam(name = "companyName")String companyName){
        List<Integer> list= positionRepository.countByFieldAndCompanyName(field,companyName);
        int count = 0;
        for(Integer i : list){
            count += i;
        }
        return count;
    }

    /**
     * Method to count the number of positions in a given field, with a given duration. Returns an int with the count, accessed by get call.
     * @param field             String containing the name of the field
     * @param positionDuration  String containing the duration (temporary/permanent or similar)
     * @return                  An int with the number of positions
     */
    @GetMapping(path = "/countbyfieldandduration")
    @ApiOperation(value = "Count the number of positions in a given field, with a given duration",notes = "Takes field and positionDuration as separate strings")
    public @ResponseBody Integer countPositionsByFieldAndDuration(@RequestParam(name = "field")String field, @RequestParam(name = "positionDuration")String positionDuration){
        List<Integer> list= positionRepository.countByFieldAndDuration(field,positionDuration);
        int count = 0;
        for(Integer i : list){
            count += i;
        }
        return count;
    }

    /**
     * Method to count the number of positions with a given duration, from a given company. Returns an int with the count, accessed by get call.
     * @param positionDuration  String containing the duration (temporary/permanent or similar)
     * @param companyName       String containing the name of the company
     * @return                  An int with the number of positions
     */
    @GetMapping(path = "/countbydurationandcompany")
    @ApiOperation(value = "Count the number of positions with a given duration, from a given company",notes = "Takes positionDuration and companyName as separate strings")
    public @ResponseBody Integer countPositionsByDurationAndCompanyName(@RequestParam(name = "positionDuration")String positionDuration, @RequestParam(name = "companyName")String companyName){
        List<Integer> list= positionRepository.countByDurationAndCompanyName(positionDuration,companyName);
        int count = 0;
        for(Integer i : list){
            count += i;
        }
        return count;
    }

    //COUNT QUERIES WITH DATE
    /**
     * Method to count the number of positions from a given company, on a given date. Returns an int with the count, accessed by get call.
     * @param companyName   String containing the name of the company
     * @param searchDate    String containing the date, format YYYY-MM-DD
     * @return              An int with the number of positions
     */
    @GetMapping(path = "/countbycompanyanddate")
    @ApiOperation(value = "Count the number of positions from a given company, on a given date",notes = "Takes companyName and searchDate as separate strings")
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
     * Method to count the number of positions in a given field, on a given date. Returns an int with the count, accessed by get call.
     * @param field         String containing the name of the field
     * @param searchDate    String containing the date, format YYYY-MM-DD
     * @return              An int with the number of positions
     */
    @GetMapping(path = "/countbyfieldanddate")
    @ApiOperation(value = "Count the number of positions in a given field, on a given date",notes = "Takes field and searchDate as separate strings")
    public @ResponseBody Integer countPositionsByFieldAndDate(@RequestParam(name = "field")String field, @RequestParam(name = "searchDate")String searchDate){
        String dateArr[] = searchDate.split("-");
        LocalDate date = LocalDate.of(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]),
                Integer.parseInt(dateArr[2]));
        List<Integer> list= positionRepository.countByFieldAndDate(field,date);
        int count = 0;
        for(Integer i : list){
            count += i;
        }
        return count;
    }

    /**
     * Method to count the number of positions with a given duration, on a given date. Returns an int with the count, accessed by get call.
     * @param positionDuration  String containing the duration (temporary/permanent or similar)
     * @param searchDate        String containing the date, format YYYY-MM-DD
     * @return                  An int with the number of positions
     */
    @GetMapping(path = "/countbydurationanddate")
    @ApiOperation(value = "Count the number of positions with a given duration, on a given date",notes = "Takes positionDuration and searchDate as separate strings")
    public @ResponseBody Integer countPositionsByDurationAndDate(@RequestParam(name = "positionDuration")String positionDuration, @RequestParam(name = "searchDate")String searchDate){
        String dateArr[] = searchDate.split("-");
        LocalDate date = LocalDate.of(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]),
                Integer.parseInt(dateArr[2]));
        List<Integer> list= positionRepository.countByDurationAndDate(positionDuration,date);
        int count = 0;
        for(Integer i : list){
            count += i;
        }
        return count;
    }

    /**
     * Method to count the number of positions in a given field, from a given company, on a given date. Returns an int with the count, accessed by get call.
     * @param field         String containing the name of the field
     * @param companyName   String containing the name of the company
     * @param searchDate    String containing the date, format YYYY-MM-DD
     * @return              An int with the number of positions
     */
    @GetMapping(path = "/countbyfieldandcompanyanddate")
    @ApiOperation(value = "Ccount the number of positions in a given field, from a given company, on a given date",notes = "Takes field, companyName and searchDate as separate strings")
    public @ResponseBody Integer countPositionsByFieldAndCompanyNameAndDate(@RequestParam(name = "field")String field, @RequestParam(name = "companyName")String companyName, @RequestParam(name = "searchDate")String searchDate){
        String dateArr[] = searchDate.split("-");
        LocalDate date = LocalDate.of(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]),
                Integer.parseInt(dateArr[2]));
        List<Integer> list= positionRepository.countByFieldAndCompanyNameAndDate(field, companyName, date);
        int count = 0;
        for(Integer i : list){
            count += i;
        }
        return count;
    }

    /**
     * Method to count the number of positions in a given field, with a given duration, on a given date. Returns an int with the count, accessed by get call.
     * @param field             String containing the name of the field
     * @param positionDuration  String containing the duration (temporary/permanent or similar)
     * @param searchDate        String containing the date, format YYYY-MM-DD
     * @return                  An int with the number of positions
     */
    @GetMapping(path = "/countbyfieldanddurationanddate")
    @ApiOperation(value = "Count the number of positions in a given field, with a given duration, on a given date",notes = "Takes field, positionDuration and searchDate as separate strings")
    public @ResponseBody Integer countPositionsByFieldAndDurationAndDate(@RequestParam(name = "field")String field, @RequestParam(name = "positionDuration")String positionDuration, @RequestParam(name = "searchDate")String searchDate){
        String dateArr[] = searchDate.split("-");
        LocalDate date = LocalDate.of(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]),
                Integer.parseInt(dateArr[2]));
        List<Integer> list= positionRepository.countByFieldAndDurationAndDate(field, positionDuration, date);
        int count = 0;
        for(Integer i : list){
            count += i;
        }
        return count;
    }

    /**
     * Method to count the number of positions with a given duration, from a given company, on a given date. Returns an int with the count, accessed by get call.
     * @param positionDuration  String containing the duration (temporary/permanent or similar)
     * @param companyName       String containing the name of the company
     * @param searchDate        String containing the date, format YYYY-MM-DD
     * @return                  An int with the number of positions
     */
    @GetMapping(path = "/countbydurationandcompanyanddate")
    @ApiOperation(value = "Count the number of positions with a given duration, from a given company, on a given date",notes = "Takes positionDuration, companyName and searchDate as separate strings")
    public @ResponseBody Integer countPositionsByDurationAndCompanyNameAndDate(@RequestParam(name = "positionDuration")String positionDuration, @RequestParam(name = "companyName")String companyName, @RequestParam(name = "searchDate")String searchDate){
        String dateArr[] = searchDate.split("-");
        LocalDate date = LocalDate.of(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]),
                Integer.parseInt(dateArr[2]));
        List<Integer> list= positionRepository.countByDurationAndCompanyNameAndDate(positionDuration, companyName, date);
        int count = 0;
        for(Integer i : list){
            count += i;
        }
        return count;
    }

    //OTHER
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
    @ApiOperation(value = "Method to add a position with relevant data",notes = "This should only reasonably be accessed by the crawlers. Structure of JSON in java file/javadoc and on Github")
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
    @ApiOperation(value = "Test if a position is unique, with relevant data",notes = "Mostly meant for testing, as functionality is built into add function. Structure of JSON in java file/javadoc and on Github")
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
    @ApiOperation(value = "DEBUG METHOD: Method to add a position with relevant data, on specified date",notes = "Should only be used for debugging, not in production. Structure of JSON in java file/javadoc and on Github")
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
