/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Team;
import it.polito.tdp.PremierLeague.model.TeamMigliori;
import it.polito.tdp.PremierLeague.model.TeamPeggiori;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnClassifica"
    private Button btnClassifica; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="cmbSquadra"
    private ComboBox<Team> cmbSquadra; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doClassifica(ActionEvent event) {

    	txtResult.clear();
    	
    	Team t = this.cmbSquadra.getValue();
    	if( t == null) {
    		txtResult.appendText("Creare il grafo e selezionare una squadra!\n");
    		return ;
    	}
    	
    	txtResult.appendText("SQUADRE MIGLIORI:\n");
    	for (TeamMigliori tm : this.model.getTeamMigliori(t)) {
    		txtResult.appendText(tm+"\n");
    	}
    	
    	txtResult.appendText("\nSQUADRE PEGGIORI:\n");
    	for (TeamPeggiori tp: this.model.getTeamPeggiori(t)) {
    		txtResult.appendText(tp+"\n");
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {

    	txtResult.clear();
    	
    	this.model.creaGrafo();
    	txtResult.appendText("GRAFO CREATO!\n");
    	txtResult.appendText("# VERTICI: "+this.model.getNumVertici()+"\n");
    	txtResult.appendText("# ARCHI: "+this.model.getNumArchi()+"\n");
    	
    	this.cmbSquadra.getItems().addAll(this.model.getVerticiTendina());
    }

    @FXML
    void doSimula(ActionEvent event) {

    	String Nscelta = this.txtN.getText();
    	int N;
    	try {
    		N = Integer.parseInt(Nscelta);
    	} catch (NumberFormatException ne) {
    		txtResult.appendText("Inserire un numero N di reporter!\n");
    		return;
    	}
    	
    	String soglia = this.txtX.getText();
    	int X ;
    	try {
    		X = Integer.parseInt(soglia);
    	} catch (NumberFormatException ne) {
    		txtResult.appendText("Inserire una soglia critica, ossia un numero minimo di reporter!\n");
    		return;
    	}
    	
    	
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnClassifica != null : "fx:id=\"btnClassifica\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbSquadra != null : "fx:id=\"cmbSquadra\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    
    }
}