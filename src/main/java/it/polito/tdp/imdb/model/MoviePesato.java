package it.polito.tdp.imdb.model;

import java.util.Objects;

public class MoviePesato {

	private Movie movie;
	double peso;
	public MoviePesato(Movie movie, double peso) {
		super();
		this.movie = movie;
		this.peso = peso;
	}
	public Movie getMovie() {
		return movie;
	}
	public double getPeso() {
		return peso;
	}
	@Override
	public String toString() {
		return movie + ", peso=" + peso;
	}
	@Override
	public int hashCode() {
		return Objects.hash(movie);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MoviePesato other = (MoviePesato) obj;
		return Objects.equals(movie, other.movie);
	}
	
	
}
