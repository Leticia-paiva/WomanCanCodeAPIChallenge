package com.example.demo.testingweb;
 
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.example.demo.models.Serie;
import com.example.demo.repositories.SerieRepository;
 
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class SerieRepositoryTests {
 
    @Autowired
    private SerieRepository repo;
    
    @BeforeEach
    public void beforeEachTestMethod() {
    	repo.deleteAll();
    }
    
    @Test
    public void testCreateSerie() {
    	List<Serie> series = (List<Serie>) repo.findAll();
    	assertThat(series).isEmpty();
        Serie newSerie = repo.save(new Serie("Rick and Morty",2013,4));   
        series = (List<Serie>) repo.findAll();
        assertThat(series).isNotEmpty();
        Serie savedSerie = series.get(0);
        assertThat(newSerie).isEqualToComparingFieldByField(savedSerie);
        assertThat(newSerie.getId()).isGreaterThan(0);
    }
    
    @Test
    public void testListSerie() {
    	List<Serie> series = (List<Serie>) repo.findAll();
    	assertThat(series).isEmpty();
        Serie firstSerie = repo.save(new Serie("Rick and Morty",2013,4)); 
        Serie secondSerie = repo.save(new Serie("A grande familia",2001,14)); 
        series = (List<Serie>) repo.findAll();
        assertThat(series).isNotEmpty();
        assertThat(series.get(0)).isEqualToComparingFieldByField(firstSerie);
        assertThat(series.get(1)).isEqualToComparingFieldByField(secondSerie);
    }
    
    @Test
    public void testListSerieById() {
    	List<Serie> series = (List<Serie>) repo.findAll();
    	assertThat(series).isEmpty();
        Serie serie = repo.save(new Serie("Rick and Morty",2013,4)); 
        Optional<Serie> foundSerie = repo.findById(serie.getId());
        assertThat(foundSerie.get()).isEqualToComparingFieldByField(serie);
    }
    
    @Test
    public void testUpdateSerie() {
    	List<Serie> series = (List<Serie>) repo.findAll();
    	assertThat(series).isEmpty();
        Serie newSerie = repo.save(new Serie("Rick and Morty",2013,4)); 
        newSerie.setYear(2020);
        repo.save(newSerie);
        series = (List<Serie>) repo.findAll();
        assertThat(series.size()).isEqualTo(1);
        assertThat(series.get(0)).isEqualToComparingFieldByField(newSerie);
    }
    
    @Test
    public void testDeleteSerie() {
        Serie serie = repo.save(new Serie("Rick and Morty",2013,4));         
        List<Serie> series = (List<Serie>) repo.findAll();
        assertThat(series).isNotEmpty();
        repo.deleteById(serie.getId());
        series = (List<Serie>) repo.findAll();         
        assertThat(series).isEmpty();       
         
    }  
    
}