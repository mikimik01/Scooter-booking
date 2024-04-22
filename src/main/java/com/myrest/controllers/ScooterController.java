package com.myrest.controllers;

import com.myrest.entities.Scooter;
import com.myrest.reps.ScooterRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.expression.ExpressionException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@SpringBootApplication
@RestController
@RequestMapping("api/v1/scooters")
public class ScooterController {
    private final ScooterRepository scooterRepository;

    public ScooterController(ScooterRepository scooterRepository) {
        this.scooterRepository = scooterRepository;
    }

    @GetMapping
    public List<Scooter> getScooters(){
        return scooterRepository.findAll();
    }

    @GetMapping("{scooterId}")
    public Scooter getScooterById(@PathVariable("scooterId") Integer id){
        Scooter scooter = scooterRepository.findById(id)
                .orElseThrow(() -> new ExpressionException("Id of a scooter does not exist!!!"));
        return scooter;
    }

    record NewScooterRequest(
            String name,
            String description
    ){}

    record NewDescriptionRequest(
            String description
    ){}

    @PostMapping
    public void addScooter(@RequestBody NewScooterRequest request){
        Scooter scooter = new Scooter();
        if (request.name==null) throw new ExpressionException("Name is not valid!!!");
        scooter.setName(request.name);
        scooter.setDescription(request.description);
        scooterRepository.save(scooter);
    }

    @PostMapping("{scooterId}")
    public void addScooterDescriptionById(@PathVariable("scooterId") Integer id, @RequestBody NewDescriptionRequest request){
        Scooter scooter = scooterRepository.findById(id)
                .orElseThrow(() -> new ExpressionException("Scooter not found!!!"));
        scooter.setDescription(request.description);
        scooterRepository.save(scooter);
    }

    @PatchMapping("{scooterId}")
    public void editScooterDescriptionById(@PathVariable("scooterId") Integer id, @RequestBody NewDescriptionRequest request){
        Scooter scooter = scooterRepository.findById(id)
                .orElseThrow(() -> new ExpressionException("Scooter not found!!!"));
        scooter.setDescription(request.description);
        scooterRepository.save(scooter);
    }

    @DeleteMapping("{scooterId}")
    public void deleteScooterById(@PathVariable("scooterId") Integer id){
        scooterRepository.deleteById(id);
    }

}
