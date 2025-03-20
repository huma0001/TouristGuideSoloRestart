package com.example.touristguidere.controller;

import com.example.touristguidere.model.TouristAttraction;
import com.example.touristguidere.service.TouristService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("attractions")
public class TouristController {
    TouristService touristService;

    public TouristController(TouristService touristService){
        this.touristService = touristService;
    }

    @GetMapping("")
    public ResponseEntity<List<TouristAttraction>> getAttractions(){
        List<TouristAttraction> allAttractions = touristService.getAllTouristAttractions();
        return new ResponseEntity<>(allAttractions, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<TouristAttraction> getAttractionByName(@PathVariable String name){
        TouristAttraction touristAttraction = touristService.getAttractionByName(name);
        if(touristAttraction == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(touristAttraction, HttpStatus.OK);
    }


    @PostMapping("/add")
    public ResponseEntity<TouristAttraction> addAttraction(@RequestBody TouristAttraction touristAttraction){
        TouristAttraction addedAttractions = touristService.addAttraction(touristAttraction);
        return new ResponseEntity<>(addedAttractions, HttpStatus.CREATED);
    }


    @PostMapping("/update")
    public ResponseEntity<TouristAttraction> updateAttraction(
            @RequestParam String attractionName,
            @RequestParam String newAttractionName){

        TouristAttraction updatedAttraction = touristService.updateAttractionName(attractionName, newAttractionName);

        if (updatedAttraction == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updatedAttraction, HttpStatus.OK);

    }


    @PostMapping("/delete/{name}")
    public ResponseEntity<String> deleteAttraction(@PathVariable String name){
        boolean isDeleted = touristService.deleteAttraction(name);

        if (isDeleted) {
            return new ResponseEntity<>("Attraction deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Attraction not found", HttpStatus.NOT_FOUND);
        }
    }





}
