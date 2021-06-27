package it.polito.tdp.PremierLeague.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Simulator {

	// Coda degli eventi
	PriorityQueue <Event> queue;
	
	// Modello del mondo 
	Map<Team, Integer> mappaReporter;
	
	// Parametri di input 
	private int N; 
	private int x;
	private List<Match> matches;
	private List<Team> teams;
	
	// Parametri di output
	int rAssistenti;
	int nPartiteDeficit;
	
	
	// Inizializzo:
	// - modello del mondo
	// - parametri di input 
	// - parametri di ouput
	public void init(int N, int x, List<Match> partite, List<Team> squadre) {
		mappaReporter = new HashMap<Team, Integer>();
		for(Team t: this.teams) {
			this.mappaReporter.put(t, N);
		}
		
		this.N=N;
		this.x=x;
		this.matches=partite;
		this.teams=squadre;
		
		this.rAssistenti=0;
		this.nPartiteDeficit=0;
			
	}
	
	public void run(int x) {
		for(Match m: this.matches) {
			Team vincente=null;
			Team perdente= null;
			Team casa= new Team(m.getTeamHomeID(), m.getTeamHomeNAME());
			Team ospite= new Team (m.getTeamAwayID(), m.getTeamAwayNAME());
			int res= m.getReaultOfTeamHome();
			
			int totReporter= mappaReporter.get(casa)+mappaReporter.get(ospite);
			this.rAssistenti=rAssistenti+ mappaReporter.get(casa)+mappaReporter.get(ospite);
			
			if(totReporter<x)
				this.nPartiteDeficit++;
			
			
			if(res==1) {
				vincente=casa;
				perdente=ospite;
				
				
			}else if(res==-1) {
				vincente=ospite;
				perdente=casa;
			}
			
			// "CON IL 50% DI PROBABILITA' LA TESTATA GIORNALISTICA PROMUOVE..."
			if(Math.random()>0.50) {
				if(this.mappaReporter.get(vincente) > 0) {
					
					// "Si simuli la scelta di una squadra più blasonata 
					// SELEZIONANDO CASUALMENTE UN TEAM
					// che abbia concluso il campionato in una posizione di classifica migliore
					mappaReporter.replace(vincente, mappaReporter.get(vincente), mappaReporter.get(vincente)-1);
					int index = (int) (Math.round((Math.random()*1)+1));
					Team t= this.teams.get(index);
					this.mappaReporter.replace(t, mappaReporter.get(t), mappaReporter.get(t)+1);
				}
			}
			
			// "CON IL 20% DI PROBABILITA' LA TESTATA GIORNALISTICA BOCCIA..."
			if(Math.random()>0.80) {
				if(mappaReporter.get(perdente)>0) {
					
					// "Si simuli la scelta di una squadra meno blasonata 
					// SELEZIONANDO CASUALMENTE UN TEAM
					// che abbia concluso il campionato
					// in una posizione di classifica peggiore"
					int index = (int) (Math.round((Math.random()*1)+1));
					Team t= this.teams.get(index);
					
					this.mappaReporter.replace(t, mappaReporter.get(t), mappaReporter.get(t)+1);
					
				}
			}
				
		}
	}
		
	
}
