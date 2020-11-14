package com.example.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Serie {

	@GeneratedValue
	@Id
	private int id;
	
	@Column( name = "name")
	private String name;
	
	@Column ( name= "year")
	private int year;
	
	@Column ( name= "totalSeasons")
	private int totalSeasons;
	
	public Serie() {
		
	}
	public Serie(String name, int year, int totalSeasons) {
		super();
		this.name= name;
		this.year= year;
		this.totalSeasons = totalSeasons;
	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public int getYear() {
		return year;
	}
	public int getTotalSeasons() {
		return totalSeasons;
	}
	
	public void setName(String name){
		this.name = name;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public void setTotalSeasons(int totalSeasons) {
		this.totalSeasons = totalSeasons;
	}	
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "Serie [id=" + id + ", Name=" + name + ", Year=" + year + ", totalSeasons=" + totalSeasons + "]";
	}
}
