package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	
	private Graph<Movie, DefaultWeightedEdge> grafo;
	
	private ImdbDAO dao;
	
	private Map<Integer, Movie> idMap;
	
	public Model() {
		
		this.dao = new ImdbDAO();
		idMap = new HashMap<Integer, Movie>();
	}
	
	public List<Movie> getAllMovies(){
		List<Movie> temp = null;
		if(!this.idMap.isEmpty()) {
			temp = new ArrayList<Movie>(this.idMap.values());
		}
		Collections.sort(temp);
		return temp;
	}
	public String creaGrafo(double rank) {
		
		this.dao.listAllMovies(idMap);	//Popolo la mappa quando creo il grafo perché potrebbero esserci aggiornati dai sul rank.
		
		this.grafo = new SimpleWeightedGraph<Movie, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, idMap.values());
		
		//Creo archi
		
		for(Adiacenza a: this.dao.getAdiacenze(rank, idMap)) {
			
			Graphs.addEdgeWithVertices(this.grafo, a.getM1(), a.getM2(), a.getPeso());
		}
		
		return "Grafo creato!\n" + "Numero vertici: "+this.grafo.vertexSet().size()+"\n"+ 
		"Numero archi = "+this.grafo.edgeSet().size();
		
		
	}
	
	public List<MoviePesato> getPesoMassimo(){
		
		List<MoviePesato> res = new ArrayList<MoviePesato>();
		double max = 0;
		
		for(Movie m: this.grafo.vertexSet()) {
		
			List<Movie> adiacenti = Graphs.neighborListOf(this.grafo, m);
				
				for(Movie m1: adiacenti) {	
					
					MoviePesato mp = this.getMoviePesato(m1);
						
					if(!res.contains(mp)) {
						if(mp.getPeso() > max) {
							max = mp.getPeso();
							res.clear();
							res.add(mp);
						}
						else if(mp.getPeso() == max) {
							res.add(mp);
							}
					}
			}
		}
		return res;
	}
	
	public MoviePesato getMoviePesato(Movie m) {
		
		List<Movie> adiacenti = Graphs.neighborListOf(this.grafo, m);
		MoviePesato mp = null;
		
		double peso = 0;
		
		for(Movie m1: adiacenti) {
			peso += this.grafo.getEdgeWeight(this.grafo.getEdge(m, m1));
			}
		
		if(peso > 0) {
			mp = new MoviePesato(m, peso);
		}
		return mp;
	}
	
	public List<Movie> cercaLista(Movie m){
		
		
		return null;
	}
	public void ricorsiva(List<Movie> parziale, List<Movie> valide) {
		
	}
}
