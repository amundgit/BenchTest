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
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping("/dates")
public class DateController {
    @Autowired
    DateRepository dateRepository;

    @GetMapping(path = "/getall")
    public @ResponseBody Iterable<Date> getAllDates(){return dateRepository.findAll();}

    /**
     * Creates an entry in the date table with today's date. Returns string to confirm successful creation
     * @return      Confirmation String
     */
    @GetMapping(path = "/create")
    public @ResponseBody String createDate(){
        String returnString = "success";
        Date currentDate = new Date();
        dateRepository.save(currentDate);
        return returnString;
    }

    /**
     * DEBUG METHOD: Allows creation of specified date. Only meant for debug use, hence "cumbersome" access through post with JSON containing date.
     * @param body  JSON object containing a date as string, variable name date
     * @return      Confirmation String
     */
    @PostMapping(path = "/testcreate")
    public @ResponseBody String createTestDate(@RequestBody Map<String, Object> body){
        String returnString = "success";
        Date currentDate = new Date();
        String dateArr[] = body.get("date").toString().split("-");
        LocalDate date = LocalDate.of(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]),
                Integer.parseInt(dateArr[2]));
        currentDate.setDate(date);
        dateRepository.save(currentDate);
        return returnString;
    }
}
