package com.example.demo.resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.json.*;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.models.Serie;
import com.example.demo.repositories.SerieRepository;

@RestController
@RequestMapping(path="/serie")

public class SerieResource {
	private SerieRepository serieRepository;
	
	public SerieResource(SerieRepository serieRepository) {
		super();
		this.serieRepository = serieRepository;
	}
	
	@PostMapping
	public ResponseEntity<Serie> save(@RequestBody Serie serie){
		try {
			serieRepository.save(serie);
			return new ResponseEntity<Serie>(HttpStatus.OK);
		}catch(NoSuchElementException nsee) {
			return new ResponseEntity<Serie>(HttpStatus.NOT_FOUND);
		}	
	}
	
	@GetMapping 
	public ResponseEntity<List<Serie>> getAll(){
		List<Serie> series = new ArrayList<>();
		series = serieRepository.findAll();
		return new ResponseEntity<>(series, HttpStatus.OK);
	}
	
	@GetMapping(path ="/{id}") 
	public ResponseEntity<Optional<Serie>> getById(@PathVariable Integer id){
		Optional <Serie> serie;
		try {
			serie = serieRepository.findById(id);
			return new ResponseEntity<Optional<Serie>>(serie,HttpStatus.OK);
		}catch(NoSuchElementException nsee) {
			return new ResponseEntity<Optional<Serie>>(HttpStatus.NOT_FOUND);
		}
	}
			
	@DeleteMapping(path ="/{id}") 
	public ResponseEntity<Optional<Serie>> deleteById(@PathVariable Integer id){
		try {
			serieRepository.deleteById(id);
			return new ResponseEntity<Optional<Serie>>(HttpStatus.OK);
		}catch(NoSuchElementException nsee) {
			return new ResponseEntity<Optional<Serie>>(HttpStatus.NOT_FOUND);
		}	
	}
	
	@PutMapping(path ="/{id}") 
	public ResponseEntity<Serie> update(@PathVariable Integer id, @RequestBody Serie newSerie){
		return serieRepository.findById(id)
			.map(serie-> {
				serie.setName(newSerie.getName());
				serie.setYear(newSerie.getYear());
				serie.setTotalSeasons(newSerie.getTotalSeasons());
				Serie serieUpdated = serieRepository.save(serie);
				return ResponseEntity.ok().body(serieUpdated);
			}).orElse(ResponseEntity.notFound().build());
	
	}
	
	@GetMapping(path ="/search/{term}") 
	public ResponseEntity<List<Serie>> getBySearch(@PathVariable String term){
	    final String showSearchUrl = "http://api.tvmaze.com/search/shows?q="+term;
	    try {
	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(showSearchUrl, String.class);
	    JSONArray jsonShowResults = new JSONArray(result);
	    List <Serie> series = new ArrayList<Serie>();
	    for(int i = 0; i<jsonShowResults.length(); i++){
	    	JSONObject obj = jsonShowResults.getJSONObject(i);
	    	Serie serie = new Serie();
	    	serie.setName(obj.getJSONObject("show").getString("name"));
	    	if (!obj.getJSONObject("show").isNull("premiered")) {
		    	String dateString = obj.getJSONObject("show").getString("premiered");
		        Date date;
				try {
					date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
					LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			        serie.setYear(localDate.getYear());
				} catch (ParseException e) {
					e.printStackTrace();
				}
	    	}
	    	final String seasonSearchUrl = "http://api.tvmaze.com/shows/"+
	    			obj.getJSONObject("show").getInt("id")+"/seasons";
	    	String result2 = restTemplate.getForObject(seasonSearchUrl, String.class);
	    	JSONArray jsonSeasonResults = new JSONArray(result2);
	        serie.setTotalSeasons(jsonSeasonResults.length());
	        serie.setId(i);
	        series.add(serie);   
	    }	
	    	return new ResponseEntity<>(series, HttpStatus.OK);
	    }catch(NoSuchElementException nsee) {
			return new ResponseEntity<List<Serie>>(HttpStatus.NOT_FOUND);
		}
	   
	}
}
