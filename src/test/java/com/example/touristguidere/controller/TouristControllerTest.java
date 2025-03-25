package com.example.touristguidere.controller;

import com.example.touristguidere.model.TouristAttraction;
import com.example.touristguidere.service.TouristService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TouristController.class)
class TouristControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TouristService touristService;

    private List<TouristAttraction> mockAttractions;
    private TouristAttraction tivoli;

    @BeforeEach
    void setup() {
        tivoli = new TouristAttraction("Tivoli", "Sjov Forlystelesparke", "København",
                Arrays.asList("Park", "Family-friendly"));

        TouristAttraction havfrue = new TouristAttraction("Den Lille Havfrue", "Statue", "København",
                Arrays.asList("Historic", "Family-friendly"));

        mockAttractions = Arrays.asList(tivoli, havfrue);
    }

    @Test
    void getAttractions_shouldReturnViewWithAttractions() throws Exception {
        when(touristService.getAllTouristAttractions()).thenReturn(mockAttractions);

        mockMvc.perform(get("/attractions"))
                .andExpect(status().isOk())
                .andExpect(view().name("attractionsList"))
                .andExpect(model().attributeExists("touristAttractions"))
                .andExpect(model().attribute("touristAttractions", mockAttractions));

        verify(touristService, times(1)).getAllTouristAttractions();
    }

    @Test
    void getAttractionByName_shouldReturnViewWhenFound() throws Exception {
        when(touristService.getAttractionByName("Tivoli")).thenReturn(tivoli);

        mockMvc.perform(get("/attractions/Tivoli"))
                .andExpect(status().isOk())
                .andExpect(view().name("attraction-found"))
                .andExpect(model().attributeExists("foundAttraction"))
                .andExpect(model().attribute("foundAttraction", tivoli));

        verify(touristService, times(1)).getAttractionByName("Tivoli");
    }

    @Test
    void getAttractionByName_shouldRedirectWhenNotFound() throws Exception {
        when(touristService.getAttractionByName("Unknown")).thenReturn(null);

        mockMvc.perform(get("/attractions/Unknown"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/attractions"));

        verify(touristService, times(1)).getAttractionByName("Unknown");
    }

    @Test
    void showAddForm_shouldReturnFormWithAttributes() throws Exception {
        List<String> mockTags = Arrays.asList("Family-friendly", "Historic");
        List<String> mockCities = Arrays.asList("København", "Odense");

        when(touristService.getAvailableTags()).thenReturn(mockTags);
        when(touristService.getAvailableCities()).thenReturn(mockCities);

        mockMvc.perform(get("/attractions/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-attraction-form"))
                .andExpect(model().attributeExists("touristAttraction"))
                .andExpect(model().attributeExists("allTags"))
                .andExpect(model().attributeExists("allCities"));

        verify(touristService, times(1)).getAvailableTags();
        verify(touristService, times(1)).getAvailableCities();
    }

    @Test
    void submitForm_shouldAddAttraction() throws Exception {
        TouristAttraction newAttraction = new TouristAttraction("New", "Desc", "København", Arrays.asList("Park"));

        mockMvc.perform(post("/attractions/add/save")
                        .flashAttr("touristAttraction", newAttraction))
                .andExpect(status().isOk())
                .andExpect(view().name("add-success"));

        verify(touristService, times(1)).addAttraction(newAttraction);
    }

    @Test
    void getAttractionTags_shouldReturnTags() throws Exception {
        List<String> mockTags = Arrays.asList("Park", "Family-friendly");
        when(touristService.getTagsByAttractionName("Tivoli")).thenReturn(mockTags);

        mockMvc.perform(get("/attractions/Tivoli/tags"))
                .andExpect(status().isOk())
                .andExpect(view().name("tags"))
                .andExpect(model().attributeExists("tags"))
                .andExpect(model().attribute("tags", mockTags));

        verify(touristService, times(1)).getTagsByAttractionName("Tivoli");
    }

    @Test
    void deleteAttraction_shouldRedirectWithMessage() throws Exception {
        when(touristService.deleteAttraction("Tivoli")).thenReturn(true);

        mockMvc.perform(post("/attractions/delete/Tivoli"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/attractions"))
                .andExpect(flash().attributeExists("message"));

        verify(touristService, times(1)).deleteAttraction("Tivoli");
    }

    @Test
    void editAttraction_shouldReturnFormWithAttributes() throws Exception {
        List<String> mockTags = Arrays.asList("Family-friendly", "Historic");
        List<String> mockCities = Arrays.asList("København", "Odense");

        when(touristService.getAttractionByName("Tivoli")).thenReturn(tivoli);
        when(touristService.getAvailableTags()).thenReturn(mockTags);
        when(touristService.getAvailableCities()).thenReturn(mockCities);

        mockMvc.perform(get("/attractions/Tivoli/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-attraction"))
                .andExpect(model().attributeExists("touristAttraction"))
                .andExpect(model().attributeExists("allTags"))
                .andExpect(model().attributeExists("allCities"));

        verify(touristService, times(1)).getAttractionByName("Tivoli");
        verify(touristService, times(1)).getAvailableTags();
        verify(touristService, times(1)).getAvailableCities();
    }

    @Test
    void updateAttraction_shouldUpdateAndRedirect() throws Exception {
        mockMvc.perform(post("/attractions/update")
                        .param("originalName", "Tivoli")
                        .param("name", "Tivoli Gardens")
                        .param("description", "Updated description")
                        .param("city", "København")
                        .param("tags", "Park", "Family-friendly"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/attractions"));

        verify(touristService, times(1)).updateAttraction(
                "Tivoli", "Tivoli Gardens", "Updated description", "København",
                Arrays.asList("Park", "Family-friendly"));
    }
}