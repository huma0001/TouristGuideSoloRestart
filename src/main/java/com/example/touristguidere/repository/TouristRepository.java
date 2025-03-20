package com.example.touristguidere.repository;

import com.example.touristguidere.model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {
    private List<TouristAttraction> touristAttractions = new ArrayList<>();


    public TouristRepository(){
        populateAttractions();
    }


    public void populateAttractions(){
        TouristAttraction tivoli = new TouristAttraction("Tivoli", "Sjov Forlystelesparke");
        TouristAttraction havfrue = new TouristAttraction("Den Lille Havfrue", "Statue");
        TouristAttraction rundeTårn = new TouristAttraction("Rundetårn", "Fortæller lidt om Danmarks Historie");
        TouristAttraction nyhavn = new TouristAttraction("Nyhavn", "Nyhavn er et sted at se i Danmark");

        touristAttractions.add(tivoli);
        touristAttractions.add(havfrue);
        touristAttractions.add(rundeTårn);
        touristAttractions.add(nyhavn);
    }


    public boolean deleteAttraction(String attractionName){
        for(int i = 0; i < touristAttractions.size(); i++){
            if(touristAttractions.get(i).getName().equalsIgnoreCase(attractionName)){
                touristAttractions.remove(i);
                System.out.println(attractionName + " Deleted");
                return true; // Return true if attraction was deleted
            }
        }
        System.out.println("Attraction Not Found");
        return false; // Return false if attraction was not found
    }




    public TouristAttraction addAttraction(TouristAttraction newAttraction){
        touristAttractions.add(newAttraction);
        System.out.println(newAttraction + " Added!");

        return newAttraction;
    }


    public TouristAttraction updateAttractionName(String attractionName, String newAttractionName){

        for(TouristAttraction t : touristAttractions)
            if(attractionName.equalsIgnoreCase(t.getName())) {
                t.setName(newAttractionName);
                return t;
            }

        return null;
    }



    public TouristAttraction getAttractionByName(String attractionName){
        for(TouristAttraction t : touristAttractions){
            if(attractionName.equalsIgnoreCase(t.getName())){
                return t;
            }
        }
        return null;
    }


    public List<TouristAttraction> getAllTouristAttractions(){
        return touristAttractions;
    }




}



