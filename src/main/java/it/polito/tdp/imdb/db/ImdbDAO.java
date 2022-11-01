package it.polito.tdp.imdb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Adiacenza;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Movie;

public class ImdbDAO {
	
	public List<Actor> listAllActors(){
		String sql = "SELECT * FROM actors";
		List<Actor> result = new ArrayList<Actor>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Actor actor = new Actor(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
						res.getString("gender"));
				
				result.add(actor);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void listAllMovies(Map<Integer, Movie> idMap){
		String sql = "SELECT * "
				+ "FROM movies "
				+ "WHERE RANK IS NOT NULL";
		
		//List<Movie> result = new ArrayList<Movie>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(!idMap.containsKey(res.getInt("id"))) {
				Movie movie = new Movie(res.getInt("id"), res.getString("name"), 
						res.getInt("year"), res.getDouble("rank"));
				
				idMap.put(res.getInt("id"), movie);	//Popolo mappa
				}
			}
			conn.close();
			//return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			//return null;
		}
	}
	
	
	public List<Director> listAllDirectors(){
		String sql = "SELECT * FROM directors";
		List<Director> result = new ArrayList<Director>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				
				result.add(director);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Adiacenza> getAdiacenze(double rank, Map<Integer, Movie> idMap){
		
		String sql = "SELECT m1.id AS id1, m2.id AS id2, COUNT(DISTINCT r1.actor_id) AS peso "
				+ "FROM movies m1, roles r1, movies m2, roles r2 "
				+ "WHERE m1.id = r1.movie_id AND m1.rank > ? AND m2.id = r2.movie_id AND m2.rank > ? "
				+ "AND m1.id > m2.id AND r1.actor_id = r2.actor_id "
				+ "GROUP BY m1.id, m2.id";
		
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		
		Connection conn = DBConnect.getConnection();
		
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setDouble(1, rank);
			st.setDouble(2, rank);
			
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				
				if(res.getInt("peso") > 0) {
				Adiacenza a = new Adiacenza(idMap.get(res.getInt("id1")), idMap.get(res.getInt("id2")), res.getInt("peso"));
				result.add(a);
				}
			}
			
			conn.close();
			return result;
			
	} catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
	}
	
	
	
}
