package com.example.touristguidere.service;

import com.example.touristguidere.model.TouristAttraction;
import com.example.touristguidere.repository.TouristRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristService {
    private TouristRepository touristRepository;


    public TouristService(TouristRepository touristRepository){
        this.touristRepository = touristRepository;
    }


    public boolean deleteAttraction(String attractionName){
        return touristRepository.deleteAttraction(attractionName);
    }

    public TouristAttraction addAttraction(TouristAttraction newAttraction){
        return touristRepository.addAttraction(newAttraction);
    }

    public TouristAttraction updateAttractionName(String attractionName, String newAttractionName){
        return touristRepository.updateAttractionName(attractionName, newAttractionName);
    }

    public TouristAttraction getAttractionByName(String attractionName){
        return touristRepository.getAttractionByName(attractionName);
    }

    public List<TouristAttraction> getAllTouristAttractions(){
        return  touristRepository.getAllTouristAttractions();
    }


}
