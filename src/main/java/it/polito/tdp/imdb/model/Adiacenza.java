package it.polito.tdp.imdb.model;

public class Adiacenza {

	private Movie m1;
	private Movie m2;
	private int peso;
	
	public Adiacenza(Movie m1, Movie m2, int peso) {
		super();
		this.m1 = m1;
		this.m2 = m2;
		this.peso = peso;
	}
	
	public Movie getM1() {
		return m1;
	}
	public Movie getM2() {
		return m2;
	}
	public int getPeso() {
		return peso;
	}
	
	
}
