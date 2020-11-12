package com.example.demo.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
		serieRepository.save(serie);
		return new ResponseEntity<>(serie, HttpStatus.OK);
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
}
