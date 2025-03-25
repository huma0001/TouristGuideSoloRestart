package com.example.touristguidere.controller;

import com.example.touristguidere.model.TouristAttraction;
import com.example.touristguidere.service.TouristService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("attractions")
public class TouristController {
    TouristService touristService;

    public TouristController(TouristService touristService){
        this.touristService = touristService;
    }

    @GetMapping("")
    public String getAttractions(Model model){
        List<TouristAttraction> allAttractions = touristService.getAllTouristAttractions();

        model.addAttribute("touristAttractions", allAttractions);
        return "attractionsList";
    }

    @GetMapping("/{name}")
    public String getAttractionByName(@PathVariable String name, Model model){
        TouristAttraction touristAttraction = touristService.getAttractionByName(name);

        model.addAttribute("foundAttraction", touristAttraction);

        return "attraction-found";
    }


    /*
    @PostMapping("/add")
    public String addAttraction(@RequestBody TouristAttraction touristAttraction){
        TouristAttraction addedAttractions = touristService.addAttraction(touristAttraction);
        return "add-attraction";
    }

     */

    //Handler method to handle user registration page request
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("touristAttraction", new TouristAttraction());
        model.addAttribute("allTags", touristService.getAvailableTags());
        model.addAttribute("allCities", touristService.getAvailableCities());

        return "add-attraction-form";
    }


    @PostMapping("/add/save")
    public String submitForm(Model model,
                             @ModelAttribute("touristAttraction") TouristAttraction touristAttraction){
        touristService.addAttraction(touristAttraction);
        model.addAttribute("touristAttraction", touristAttraction);

        return "add-success";
    }

    @GetMapping("/{name}/tags")
    public String getAttractionTags(@PathVariable String name, Model model){
        List<String> tags = touristService.getTagsByAttractionName(name);

        model.addAttribute("tags", tags);

        return "tags";
    }



    @PostMapping("/delete/{name}")
    public String deleteAttraction(@PathVariable String name, RedirectAttributes redirectAttributes) {
        boolean isDeleted = touristService.deleteAttraction(name);

        if (isDeleted) {
            redirectAttributes.addFlashAttribute("message", "Attraction successfully deleted!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Attraction not found!");
        }

        return "redirect:/attractions"; // Redirecter tilbage til listen
    }




    @PostMapping("/update")
    public String updateAttraction(@RequestParam String originalName,
                                   @RequestParam String name,
                                   @RequestParam String description,
                                   @RequestParam String city,
                                   @RequestParam(value = "tags", required = false) List<String> tags) {
        touristService.updateAttraction(originalName, name, description, city, tags);
        return "redirect:/attractions";
    }



    @GetMapping("/{name}/edit")
    public String editAttraction(@PathVariable String name, Model model) {
        TouristAttraction attraction = touristService.getAttractionByName(name);

        if (attraction == null) {
            return "redirect:/attractions"; // Hvis attraktionen ikke findes
        }

        model.addAttribute("touristAttraction", attraction);
        model.addAttribute("allCities", touristService.getAvailableCities());
        model.addAttribute("allTags", touristService.getAvailableTags());

        return "edit-attraction"; // Viser Thymeleaf-formular til redigering
    }








}
