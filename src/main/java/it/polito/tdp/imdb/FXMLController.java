/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.Model;
import it.polito.tdp.imdb.model.Movie;
import it.polito.tdp.imdb.model.MoviePesato;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnGrandoMax"
    private Button btnGrandoMax; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="txtRank"
    private TextField txtRank; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMovie"
    private ComboBox<Movie> cmbMovie; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	String t = txtRank.getText();
    	double rank;
    	if(t == null) {
    		txtResult.appendText("Errore: inserire un rank");
    		return;
    	}
    	
    	try {
    		rank = Double.parseDouble(t);
    	}catch (NumberFormatException e) {
    		txtResult.appendText("Inserire un valore double di rank");
    		return;
    	}
    	
    	if(rank < 0.0 || rank > 10.0) {
    		txtResult.appendText("Errore: il range del rank va da 0.0 a 10.0");
    		return;
    	}
    	String msg = this.model.creaGrafo(rank);
    	txtResult.appendText(msg);
    	
    	this.cmbMovie.getItems().addAll(this.model.getAllMovies());
    	
    }

    @FXML
    void doGradoMax(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	List<MoviePesato> lista = this.model.getPesoMassimo();
    	
    	txtResult.appendText("FILM DI GRADO MASSIMO: \n");
    	for(MoviePesato m: lista) {
    		txtResult.appendText(m.toString()+"\n");
    	}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnGrandoMax != null : "fx:id=\"btnGrandoMax\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRank != null : "fx:id=\"txtRank\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMovie != null : "fx:id=\"cmbMovie\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
