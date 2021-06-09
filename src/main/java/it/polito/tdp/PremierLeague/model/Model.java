package it.polito.tdp.PremierLeague.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.Adiacenza;
import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private SimpleDirectedWeightedGraph <Team, DefaultWeightedEdge> grafo;
	private PremierLeagueDAO dao;
	private Map<Integer,Team> idMap;
	
	public Model() {
		dao = new PremierLeagueDAO();
		idMap = new HashMap<Integer,Team>();
		dao.listAllTeams(idMap);
	}
	
	public void creaGrafo() {
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		// aggiungo i vertici
		Graphs.addAllVertices(grafo, idMap.values());
		
		// aggiungo gli archi
		for (Adiacenza a: dao.getAdiacenze(idMap)) {
			if (this.grafo.containsVertex(a.getTh()) && this.grafo.containsVertex(a.getTa())) {
				Team t1 = a.getTh();
				Team t2 = a.getTa();
				
				int punti1 = this.calcolaPunti(t1);
				int punti2 = this.calcolaPunti(t2);
				
				if (punti1>punti2) {
					// arco orientato da t1 a t2 
					int peso = punti1-punti2;
					Graphs.addEdgeWithVertices(this.grafo, t1, t2, peso);
					
				}else if (punti2>punti1) {
					int peso = punti2-punti1;
					Graphs.addEdgeWithVertices(this.grafo, t2, t1, peso);
				}
				
			}
		}
		
		
	}
	
	
	// calcolo i punti per ogni team
	
	public int calcolaPunti(Team t) {
		int punti = 0;
		for (Adiacenza a : dao.getAdiacenze(idMap)) {
			
			// se la squadra passata è la squadra di casa
			if (a.getTh().equals(t)) {
			
				
				if(a.getRisultato()==1) {
					punti = punti + 3;
				} else if (a.getRisultato()==0) {
					punti = punti + 1;
				} else {
					punti = punti + 0;
				}
				t.setPunti(punti);
				
			}
			else if (a.getTa().equals(t)) {
				
				if(a.getRisultato()== -1) {
					punti = punti + 3;
				} else if (a.getRisultato()==0) {
					punti = punti + 1;
				} else {
					punti = punti + 0;
				}
				t.setPunti(punti);
			}
		}
		return t.getPunti();
	}
	
	public int getNumeroVertici() {
		if(this.grafo!=null) {
			return this.grafo.vertexSet().size();
		}
		return 0;
	}
	
	public int getNumeroArchi() {
		if(this.grafo!=null) {
			return this.grafo.edgeSet().size();
		}
		return 0;
	}
	
	public Set<Team> getVerticiTendina(){
		return this.grafo.vertexSet();
	}
	
	public List<TeamMigliori> getMigliori (Team t){
		LinkedList <TeamMigliori> migliori = new LinkedList<TeamMigliori>();
		
		for (Team tt: this.grafo.vertexSet()) {
			
			if (!t.equals(tt)) {
				if(t.getPunti()<tt.getPunti()) {
					DefaultWeightedEdge arco = this.grafo.getEdge(tt, t);
					double peso = this.grafo.getEdgeWeight(arco);
					
					TeamMigliori tm = new TeamMigliori (tt, peso);
					migliori.add(tm);
				}
			}
			
		}
		Collections.sort(migliori, new Comparator<TeamMigliori>(){

			@Override
			public int compare(TeamMigliori o1, TeamMigliori o2) {
				Double p1 = o1.getPeso();
				Double p2 = o2.getPeso();
				return p1.compareTo(p2);
			}
			
		});
		return migliori;
	}
	
	public List<TeamPeggiori> getPeggiori (Team t){
		LinkedList <TeamPeggiori> peggiori = new LinkedList<TeamPeggiori>();
		
		for (Team tt: this.grafo.vertexSet()) {
			
			if (!t.equals(tt)) {
				if(t.getPunti()>tt.getPunti()) {
					DefaultWeightedEdge arco = this.grafo.getEdge(t, tt);
					double peso = this.grafo.getEdgeWeight(arco);
					
					TeamPeggiori tp = new TeamPeggiori (tt, peso);
					peggiori.add(tp);
				}
			}
			
		}
		Collections.sort(peggiori, new Comparator<TeamPeggiori>() {

			@Override
			public int compare(TeamPeggiori o1, TeamPeggiori o2) {
				Double p1 = o1.getPeso();
				Double p2 = o2.getPeso();
				return p1.compareTo(p2);
			}
			
		});
		return peggiori;
	}
	
	
}
