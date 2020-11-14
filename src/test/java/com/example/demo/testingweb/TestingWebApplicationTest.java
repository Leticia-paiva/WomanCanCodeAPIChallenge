package com.example.demo.testingweb;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import com.example.demo.repositories.SerieRepository;
import com.example.demo.models.Serie;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestingWebApplicationTest {
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private SerieRepository repo;

    @BeforeEach
    public void beforeEachTestMethod() {
    	repo.deleteAll();
    }
    
	@Test
    public void testGetSeries() throws Exception {
    	Serie newSerie = repo.save(new Serie("Chaves",1971,2)); 
    	Serie [] series = this.restTemplate
    			.getForObject("http://localhost:" + port + "/serie",Serie[].class);
    	assertThat(newSerie).isEqualToComparingFieldByField(series[0]);	
	} 
	
	@Test
    public void testPostSeries() throws Exception {
    	Serie newSerie = repo.save(new Serie("Chaves",1971,2)); 
    	this.restTemplate.postForObject("http://localhost:" + port + "/serie", 
    			newSerie, String.class);
    	List<Serie> series = (List<Serie>) repo.findAll();
    	assertThat(series).isNotEmpty();
    	assertThat(series.get(0)).isEqualToComparingFieldByField(newSerie);
	}	
	
	@Test
    public void testPuSeries() throws Exception {
    	Serie newSerie = repo.save(new Serie("Chaves",1971,2)); 
    	newSerie.setYear(2020);
    	this.restTemplate.put("http://localhost:" + port + "/serie/"+newSerie.getId(), newSerie);
    	List<Serie> series = (List<Serie>) repo.findAll();
    	assertThat(series.size()).isEqualTo(1);
    	assertThat(series.get(0)).isEqualToComparingFieldByField(newSerie);
	} 
	
	@Test
    public void testDeleteSeries() throws Exception {
    	Serie newSerie = repo.save(new Serie("Chaves",1971,2)); 
    	List<Serie> series = (List<Serie>) repo.findAll();
    	assertThat(series).isNotEmpty();
    	this.restTemplate.delete("http://localhost:" + port + "/serie/"+newSerie.getId());
    	series = (List<Serie>) repo.findAll();
    	assertThat(series).isEmpty();
	} 
	
}
