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

    public TouristAttraction editAttractionName(String attractionName, String newName){
        return touristRepository.editAttractionName(attractionName, newName);
    }

    public TouristAttraction addAttraction(TouristAttraction newAttraction){
        return touristRepository.addAttraction(newAttraction);
    }

    public TouristAttraction updateAttraction(String attractionName, String newName, String newDescription, String newCity ,List<String> newTags){
        return touristRepository.updateTouristAttraction(attractionName, newName, newDescription, newCity ,newTags);
    }

    public List<String> getTagsByAttractionName(String attractionName){
        return touristRepository.getTagsByAttractionName(attractionName);
    }

    public List<String> getAvailableTags() {
        return touristRepository.getAvailableTags();
    }

    public TouristAttraction getAttractionByName(String attractionName){
        return touristRepository.getAttractionByName(attractionName);
    }

    public List<TouristAttraction> getAllTouristAttractions(){
        return  touristRepository.getAllTouristAttractions();
    }

    public List<String> getAvailableCities(){
        return touristRepository.getAvailableCities();
    }


}
