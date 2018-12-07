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
@RequestMapping("/positions")
public class PositionController {
    @Autowired
    PositionRepository positionRepository;

    @Autowired
    DateRepository dateRepository;

    @Autowired
    CompanyRepository companyRepository;

    @GetMapping(path = "/getall")
    public @ResponseBody Iterable<Position> getAllPositions(){return positionRepository.findAll();}

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
}
