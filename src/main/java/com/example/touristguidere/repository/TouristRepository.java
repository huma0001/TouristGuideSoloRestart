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
        TouristAttraction tivoli = new TouristAttraction("Tivoli", "Sjov Forlystelesparke", "København", List.of(getAvailableTags().get(5), getAvailableTags().get(0)));
        TouristAttraction havfrue = new TouristAttraction("Den Lille Havfrue", "Statue","København" , List.of(getAvailableTags().get(1), getAvailableTags().get(0), getAvailableTags().get(3)));
        TouristAttraction rundeTårn = new TouristAttraction("Rundetårn", "Fortæller lidt om Danmarks Historie", "Odense" , List.of(getAvailableTags().get(1), getAvailableTags().get(3)));
        TouristAttraction nyhavn = new TouristAttraction("Nyhavn", "Nyhavn er et sted at se i Danmark", "Lyngby", List.of(getAvailableTags().get(3), getAvailableTags().get(1)));

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

    public List<String> getTagsByAttractionName(String attractionName){
        for(TouristAttraction t : touristAttractions){
            if (attractionName.equalsIgnoreCase(t.getName())){
                return t.getTags();
            }
        }
        return null;
    }

    public List<String> getAvailableTags() {
        return List.of("Family-friendly", "Historic", "Free", "Outdoor", "Museum", "Park");
    }

    public TouristAttraction addAttraction(TouristAttraction newAttraction){
        touristAttractions.add(newAttraction);
        System.out.println(newAttraction.getName() + " Added!");

        return newAttraction;
    }

    public TouristAttraction updateTouristAttraction(String name, String newName, String newDescription, String newCity, List<String> newTags) {
        for (TouristAttraction t : touristAttractions){
            if (t.getName().equalsIgnoreCase(name)){
                t.setName(newName);
                t.setDescription(newDescription);
                t.setCity(newCity);
                t.setTags(newTags);
                return t;
            }
        }
        return null;
    }

    public List<String> getAvailableCities(){
        return List.of("Odense", "København", "Lyngby", "Birkerød", "Vejle", "Søborg");
    }



    public TouristAttraction editAttractionName(String attractionName, String newAttractionName){

        for(TouristAttraction t : touristAttractions)
            if(attractionName.equalsIgnoreCase(t.getName())) {
                t.setName(newAttractionName);
                return t;
            }

        return null;
    }

    public TouristAttraction editAttractionDescription(String attractionName, String newDescription){

        for(TouristAttraction t : touristAttractions)
            if(attractionName.equalsIgnoreCase(t.getName())) {
                t.setDescription(newDescription);
                return t;
            }

        return null;
    }

    public String getCityByAttractionName(String attractionName){
        for(TouristAttraction t : touristAttractions)
            if(attractionName.equalsIgnoreCase(t.getName())) {
                return t.getCity();
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



